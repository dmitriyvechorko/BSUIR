import sqlite3
from dataclasses import dataclass
import tkinter as tk
from tkinter import ttk, filedialog, messagebox
from idlelib.tooltip import Hovertip
import webbrowser
import json
import os
import re


@dataclass
class SearchResult:
    wordform: str
    lemma: str
    pos: str
    link: str
    examples: list[str]


class DBConnection:
    def __init__(self, path) -> None:
        self.db = sqlite3.connect(path)
        self.cursor = self.db.cursor()

    def find_info_by_word(self, word):
        BE_FORMS = {
            'be', 'am', 'is', 'are', 'was', 'were', 'being', 'been',
            "'m", "'s", "'re", "am", "is", "are", "was", "were"
        }

        search_be = word.lower() in BE_FORMS or word.lower() == 'be'

        if search_be:
            search_forms = BE_FORMS
            lemma_condition = "wf.lemma = 'be'"
        else:
            search_forms = {word.lower()}
            lemma_condition = "wf.lemma = ? OR wf.wordform = ?"

        forms_condition = " OR ".join(["wf.wordform = ?"] * len(search_forms))
        params = list(search_forms)
        if not search_be:
            params.extend([word, word])

        # Сначала получаем ОБЩЕЕ количество вхождений
        self.cursor.execute(f"""
            SELECT COUNT(*)
            FROM wordforms wf
            JOIN texts ts ON wf.file_id = ts.file_id
            WHERE ({forms_condition}) {f'OR ({lemma_condition})' if not search_be else ''}
        """, params)
        total_occurrences = self.cursor.fetchone()[0]

        # Затем получаем случайные примеры для отображения
        self.cursor.execute(f"""
            SELECT ts.text, ts.title, ts.country, ts.date, wf.wordform
            FROM texts ts
            JOIN wordforms wf ON wf.file_id = ts.file_id
            WHERE ({forms_condition}) {f'OR ({lemma_condition})' if not search_be else ''}
            ORDER BY RANDOM()
            LIMIT 5000
        """, params)

        raw_examples = self.cursor.fetchall()
        examples = []

        for example in raw_examples:
            full_text = example[0]
            found_form = example[4]
            pattern = re.compile(r'\b' + re.escape(found_form) + r'\b', re.IGNORECASE)
            matches = list(pattern.finditer(full_text))

            for match in matches:
                start, end = match.span()
                left = start
                while left > 0 and full_text[left] not in ['.', '!', '?', '\n']:
                    left -= 1
                right = end
                while right < len(full_text) and full_text[right] not in ['.', '!', '?', '\n']:
                    right += 1

                sentence = full_text[left:right].strip()
                if sentence:
                    examples.append({
                        "text": sentence,
                        "matched_word": found_form,
                        "link": f"{example[1]} ({example[2]}, {example[3]})"
                    })

        query_results = f"""
            SELECT DISTINCT
                wf.wordform, wf.lemma, wf.pos,
                ts.title, ts.country, ts.date
            FROM wordforms wf
            JOIN texts ts ON wf.file_id = ts.file_id
            WHERE ({forms_condition}) {f'OR ({lemma_condition})' if not search_be else ''}
        """
        self.cursor.execute(query_results, params)
        results = self.cursor.fetchall()

        results = map(lambda x: SearchResult(x[0], x[1], x[2], f"{x[3]} ({x[4]}, {x[5]})", []), results)
        return {
            "occurences": str(total_occurrences),  # Теперь используем общее количество
            "search_results": list(results),
            "examples": examples[:400]
        }

    def close(self):
        self.db.close()


class JSONToDBConverter:
    @staticmethod
    def convert(json_path: str, db_path: str) -> bool:
        """Конвертирует JSON файл в SQLite базу данных"""
        try:
            with open(json_path, 'r', encoding='utf-8') as f:
                data = json.load(f)

            conn = sqlite3.connect(db_path)
            cursor = conn.cursor()

            # Создаем таблицы
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS texts (
                    file_id TEXT PRIMARY KEY,
                    year TEXT,
                    title TEXT,
                    country TEXT,
                    genre TEXT,
                    director TEXT,
                    duration TEXT,
                    subcorpus TEXT,
                    date TEXT,
                    text TEXT
                )
            """)

            cursor.execute("""
                CREATE TABLE IF NOT EXISTS wordforms (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    file_id TEXT,
                    wordform TEXT,
                    lemma TEXT,
                    pos TEXT,
                    FOREIGN KEY (file_id) REFERENCES texts (file_id)
                )
            """)

            # Очищаем таблицы, если они уже существовали
            cursor.execute("DELETE FROM wordforms")
            cursor.execute("DELETE FROM texts")

            # Заполняем таблицы данными из JSON
            for file_id, entry in data.items():
                # Вставляем метаданные в таблицу texts
                cursor.execute("""
                    INSERT INTO texts (
                        file_id, year, title, country, genre, director, 
                        duration, subcorpus, date, text
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, (
                    file_id,
                    entry.get('year', ''),
                    entry.get('title', ''),
                    entry.get('country', ''),
                    entry.get('genre', ''),
                    entry.get('director', ''),
                    entry.get('duration', ''),
                    entry.get('subcorpus', ''),
                    entry.get('date', ''),
                    entry.get('text', '')
                ))

                # Анализируем текст и извлекаем wordforms (упрощенная версия)
                text = entry.get('text', '')
                words = re.findall(r'\b\w+\b', text.lower())

                # Вставляем wordforms в таблицу (в реальном приложении нужно использовать морфологический анализатор)
                for word in set(words):  # используем set чтобы избежать дубликатов
                    cursor.execute("""
                        INSERT INTO wordforms (
                            file_id, wordform, lemma, pos
                        ) VALUES (?, ?, ?, ?)
                    """, (
                        file_id,
                        word,
                        word,  # в реальном приложении lemma должна быть нормальной формой слова
                        'UNKN'  # часть речи неизвестна без морфологического анализа
                    ))

            conn.commit()
            conn.close()
            return True

        except Exception as e:
            messagebox.showerror("Error", f"Failed to create database: {str(e)}")
            return False


class CorpusManager:
    def __init__(self):
        self.data = {}
        self.current_json_path = None

    def create_from_txt(self, meta_path: str, text_path: str) -> bool:
        try:
            with open(meta_path, 'r', encoding='utf-8') as f:
                meta_lines = f.readlines()

            with open(text_path, 'r', encoding='utf-8') as f:
                text_lines = f.readlines()

            headers = meta_lines[0].strip().split('\t')
            for line in meta_lines[1:]:
                values = line.strip().split('\t')
                if len(values) == len(headers):
                    file_id = values[1]
                    self.data[file_id] = dict(zip(headers, values))
                    self.data[file_id]['text'] = ""

            for i, line in enumerate(text_lines[1:], start=1):
                if str(i) in self.data:
                    self.data[str(i)]['text'] = line.strip()

            return True

        except Exception as e:
            messagebox.showerror("Error", f"Failed to create corpus: {str(e)}")
            return False

    def save_to_json(self, filepath: str) -> bool:
        try:
            with open(filepath, 'w', encoding='utf-8') as f:
                json.dump(self.data, f, ensure_ascii=False, indent=4)
            return True
        except Exception as e:
            messagebox.showerror("Error", f"Failed to save JSON: {str(e)}")
            return False

    def load_from_json(self, filepath: str) -> bool:
        try:
            with open(filepath, 'r', encoding='utf-8') as f:
                self.data = json.load(f)
            self.current_json_path = filepath
            return True
        except Exception as e:
            messagebox.showerror("Error", f"Failed to load JSON: {str(e)}")
            return False


class ManagerApp:
    def __init__(self, root) -> None:
        self.root = root
        self.corpus = CorpusManager()
        self.db_converter = JSONToDBConverter()
        self.conn = None

        # Конфигурация
        self.config_file = "corpus_manager.cfg"
        self.db_path = self.load_config()

        # Инициализируем соединение с БД
        self.initialize_db_connection()

        self.setup_ui()

    def setup_ui(self):
        self.root.title("Corpus Manager")
        self.root.geometry("2300x1800")

        style = ttk.Style()
        style.theme_use("clam")
        style.configure("Treeview", rowheight=40)

        # Меню работы с корпусом
        corpus_frame = ttk.Frame(self.root)
        corpus_frame.pack(pady=10)

        self.create_json_btn = ttk.Button(corpus_frame, text="Create JSON Corpus", command=self.create_json_corpus)
        self.create_json_btn.pack(side=tk.LEFT, padx=5)
        Hovertip(self.create_json_btn, "Create new JSON corpus from metadata and text files")

        self.load_json_btn = ttk.Button(corpus_frame, text="Load JSON Corpus", command=self.load_json_corpus)
        self.load_json_btn.pack(side=tk.LEFT, padx=5)
        Hovertip(self.load_json_btn, "Load existing JSON corpus")

        # Меню работы с БД
        db_frame = ttk.Frame(self.root)
        db_frame.pack(pady=10)

        self.create_db_btn = ttk.Button(db_frame, text="Create Database from JSON",
                                        command=self.create_database_from_json)
        self.create_db_btn.pack(side=tk.LEFT, padx=5)
        Hovertip(self.create_db_btn, "Create SQLite database from selected JSON file")

        self.load_db_btn = ttk.Button(db_frame, text="Load Database", command=self.load_database)
        self.load_db_btn.pack(side=tk.LEFT, padx=5)
        Hovertip(self.load_db_btn, "Load existing SQLite database")

        self.contact_button = ttk.Button(self.root, text="Contact with developers", command=self.contact_developers)
        Hovertip(self.contact_button, "Contact developers for support")
        self.contact_button.pack(pady=10)

        # Поле поиска
        ttk.Label(self.root, text="Query:").pack(pady=10)
        self.entry_var = tk.StringVar()
        entry = ttk.Entry(self.root, textvariable=self.entry_var)
        entry.pack()
        Hovertip(entry, "Type your query here")

        search_btn = ttk.Button(self.root, text="Search", command=self.search)
        search_btn.pack(pady=10)
        Hovertip(search_btn, "Search in the database")

        # Результаты
        ttk.Label(self.root, text="Number of occurrences: ").pack(pady=10)
        self.occ_numb = tk.StringVar()
        ttk.Entry(self.root, textvariable=self.occ_numb, state="disabled").pack(pady=10)

        ttk.Label(self.root, text="Search results:").pack(pady=10)
        self.setup_results_table()

        ttk.Label(self.root, text="Examples of usage:").pack(pady=10)
        self.setup_examples_text()

    def setup_results_table(self):
        self.table_frame = ttk.Frame(self.root)
        self.table_frame.pack(pady=10)

        self.tree = ttk.Treeview(self.table_frame, columns=("Wordform", "Lemma", "POS", "Link"), show="headings",
                                 height=7)
        for col in ("Wordform", "Lemma", "POS", "Link"):
            self.tree.heading(col, text=col)
            self.tree.column(col, width=500)
        self.tree.pack(side="left")

    def setup_examples_text(self):
        self.text_widget = tk.Text(self.root, height=15, width=200, wrap="word")
        self.text_widget.pack(pady=10)
        self.text_widget.tag_config("highlight", foreground="red")

    def load_config(self):
        """Загружает путь к последней использованной БД из конфига"""
        if os.path.exists(self.config_file):
            try:
                with open(self.config_file, 'r') as f:
                    config = json.load(f)
                    return config.get('db_path')
            except:
                return None
        return None

    def save_config(self):
        """Сохраняет текущий путь к БД в конфиг"""
        config = {'db_path': self.db_path}
        with open(self.config_file, 'w') as f:
            json.dump(config, f)

    def initialize_db_connection(self):
        """Инициализирует соединение с БД"""
        if self.db_path and os.path.exists(self.db_path):
            try:
                self.conn = DBConnection(self.db_path)
                return
            except Exception as e:
                messagebox.showwarning("Warning", f"Failed to connect to database: {str(e)}")

        # Пробуем подключиться к стандартной БД
        default_db = "../analyzer/movies.db"
        if os.path.exists(default_db):
            self.db_path = default_db
            self.conn = DBConnection(self.db_path)
        else:
            self.conn = None
            messagebox.showinfo("Info", "No database found. Please create or load one.")

    def create_json_corpus(self):
        """Создает JSON корпус из TXT файлов"""
        meta_path = filedialog.askopenfilename(title="Select Metadata File", filetypes=[("Text files", "*.txt")])
        if not meta_path:
            return

        text_path = filedialog.askopenfilename(title="Select Texts File", filetypes=[("Text files", "*.txt")])
        if not text_path:
            return

        if self.corpus.create_from_txt(meta_path, text_path):
            save_path = filedialog.asksaveasfilename(
                title="Save JSON Corpus As",
                defaultextension=".json",
                filetypes=[("JSON files", "*.json")]
            )
            if save_path and self.corpus.save_to_json(save_path):
                messagebox.showinfo("Success", f"Corpus saved to:\n{save_path}")

    def load_json_corpus(self):
        """Загружает JSON корпус"""
        filepath = filedialog.askopenfilename(title="Select JSON Corpus", filetypes=[("JSON files", "*.json")])
        if filepath and self.corpus.load_from_json(filepath):
            messagebox.showinfo("Success", f"Corpus loaded from:\n{filepath}")

    def create_database_from_json(self):
        """Создает БД из выбранного JSON файла"""
        json_path = filedialog.askopenfilename(title="Select JSON File", filetypes=[("JSON files", "*.json")])
        if not json_path:
            return

        db_path = filedialog.asksaveasfilename(
            title="Save Database As",
            defaultextension=".db",
            filetypes=[("SQLite Database", "*.db")]
        )
        if not db_path:
            return

        if self.db_converter.convert(json_path, db_path):
            self.db_path = db_path
            self.save_config()

            if self.conn:
                self.conn.close()
            self.conn = DBConnection(self.db_path)

            messagebox.showinfo("Success", f"Database created at:\n{db_path}")

    def load_database(self):
        """Загружает существующую базу данных"""
        db_path = filedialog.askopenfilename(
            title="Select Database File",
            filetypes=[("SQLite Database", "*.db"), ("All files", "*.*")]
        )
        if not db_path:
            return

        try:
            # Проверяем, что файл действительно является SQLite базой данных
            if not self.is_valid_sqlite_db(db_path):
                messagebox.showerror("Error", "Selected file is not a valid SQLite database")
                return

            if self.conn:
                self.conn.close()

            self.conn = DBConnection(db_path)
            self.db_path = db_path
            self.save_config()

            messagebox.showinfo("Success", f"Database loaded from:\n{db_path}")
        except Exception as e:
            messagebox.showerror("Error", f"Failed to load database: {str(e)}")

    def is_valid_sqlite_db(self, path):
        """Проверяет, является ли файл валидной SQLite базой данных"""
        if not os.path.isfile(path):
            return False

        try:
            with open(path, 'rb') as f:
                header = f.read(16)
                return header == b'SQLite format 3\x00'
        except:
            return False

    def contact_developers(self):
        webbrowser.open_new("mailto:dimonvechorko@gmail.com")

    def search(self):
        if not self.conn:
            messagebox.showwarning("Warning", "No database connection")
            return

        word = self.entry_var.get()
        if not word:
            messagebox.showwarning("Warning", "Please enter a word")
            return

        res = self.conn.find_info_by_word(word)
        self.occ_numb.set(res["occurences"])

        # Очищаем предыдущие результаты
        for item in self.tree.get_children():
            self.tree.delete(item)

        # Добавляем новые результаты
        for result in res["search_results"]:
            self.tree.insert("", "end", values=(
                result.wordform,
                result.lemma,
                result.pos,
                result.link
            ))

        # Показываем примеры
        self.text_widget.delete("1.0", tk.END)
        for example in res["examples"]:
            start_index = self.text_widget.index(tk.INSERT)
            self.text_widget.insert(tk.END, f"{example['text']}\n[{example['link']}]\n\n")

            # Подсвечиваем искомое слово
            pattern = re.compile(r'\b' + re.escape(example['matched_word']) + r'\b', re.IGNORECASE)
            for match in pattern.finditer(example['text']):
                start = f"{start_index}+{match.start()}c"
                end = f"{start_index}+{match.end()}c"
                self.text_widget.tag_add("highlight", start, end)


if __name__ == "__main__":
    root = tk.Tk()
    app = ManagerApp(root)
    root.mainloop()