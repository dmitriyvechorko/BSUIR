import spacy
import tkinter as tk
from tkinter import filedialog
from tkinter import ttk
from idlelib.tooltip import Hovertip
import webbrowser
import tempfile
from bs4 import BeautifulSoup
import networkx as nx
import matplotlib.pyplot as plt

nlp = spacy.load('en_core_web_sm')

translations = {
    "ROOT": "Root",
    "nsubj": "Subject",
    "dobj": "Direct object",
    "iobj": "Indirect object",
    "pobj": "Object of preposition",
    "subj": "Nominal subject",
    "csubj": "Clausal subject",
    "attr": "Attribute",
    "agent": "Agent",
    "advcl": "Adverbial clause modifier",
    "advmod": "Adverbial modifier",
    "amod": "Adjectival modifier",
    "appos": "Appositional modifier",
    "aux": "Auxiliary",
    "auxpass": "Passive auxiliary",
    "cc": "Coordinating conjunction",
    "ccomp": "Clausal complement",
    "compound": "Compound word",
    "conj": "Conjunct",
    "dative": "Dative",
    "dep": "Unspecified dependency",
    "det": "Determiner",
    "expl": "Expletive",
    "intj": "Interjection",
    "mark": "Marker",
    "meta": "Meta modifier",
    "neg": "Negation modifier",
    "nmod": "Nominal modifier",
    "npadvmod": "Noun phrase as adverbial modifier",
    "nummod": "Numeric modifier",
    "oprd": "Object predicate",
    "parataxis": "Parataxis",
    "pcomp": "Prepositional complement",
    "poss": "Possession modifier",
    "preconj": "Preconjunct",
    "predet": "Pre-determiner",
    "prep": "Prepositional modifier",
    "prt": "Particle",
    "punct": "Punctuation",
    "quantmod": "Quantifier modifier",
    "relcl": "Relative clause modifier",
    "xcomp": "Open clausal complement"
}


class SyntacticAnalyzer:

    def __init__(self, text) -> None:
        self._text = text
        self._spacy_tokens = nlp(text)
        self._tokens = []

        for token in self._spacy_tokens:
            self._tokens.append({
                "token": token.text,
                "dep": translations.get(token.dep_, token.dep_)
            })

    def get_tokens(self):
        return self._tokens


def generate_html(tokens):
    html = """
    <!DOCTYPE html>
    <html>
    <head>
        <style>
            body {
                font-size: 17px;
            }

            .hovertip {
                position: relative;
                display: inline-block;
                cursor: pointer;
            }

            .hovertip:hover::after {
                content: attr(data-dep);
                position: absolute;
                bottom: 125%;
                left: 50%;
                transform: translateX(-50%);
                padding: 5px;
                background-color: #000;
                color: #fff;
                border-radius: 4px;
                white-space: nowrap;
            }

            table {
                border-collapse: collapse;
                width: 100vw;
                max-width: 600px;
                margin-bottom: 20px;
                margin: 20px;
            }

            th, td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f2f2f2;
            }

            .tag {
                display: inline-block;
                width: 15px;
                height: 15px;
                margin-right: 5px;
                border-radius: 50%;
                border: 1px solid #ccc;
            }

            #token-container {
                padding: 10px;
                border: 1px solid #ccc;
                line-height: 25px;
            }

            .custom-button {
                padding: 10px 20px;
                font-size: 16px;
                font-weight: bold;
                border: none;
                border-radius: 4px;
                background-color: #4CAF50;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .custom-button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div style="text-align: center; padding: 10px;">
            <button class="custom-button" onclick="saveHtml()">Save</button>
        </div>

        <div id="token-container">
            """
    for token in tokens:
        html += f'<span class="hovertip" data-dep="{token["dep"]}" onclick="changeDep(this)">{token["token"]}</span> '
    html += """
        </div>

        <table>
    <tr>
        <th>Tag</th>
        <th>Color</th>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: darkred;"></span>Root</td>
        <td>Dark Red</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: darkgreen;"></span>Subject, Nominal subject, Clausal subject</td>
        <td>Dark Green</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: darkblue;"></span>Direct object, Indirect object, Object of preposition</td>
        <td>Dark Blue</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: purple;"></span>Attribute</td>
        <td>Purple</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: darkorange;"></span>Agent</td>
        <td>Dark Orange</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: teal;"></span>Adverbial clause modifier, Adverbial modifier, Adjectival modifier, Appositional modifier</td>
        <td>Teal</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: darkgoldenrod;"></span>Auxiliary, Passive auxiliary</td>
        <td>Dark Goldenrod</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: maroon;"></span>Coordinating conjunction</td>
        <td>Maroon</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: darkcyan;"></span>Clausal complement, Open clausal complement</td>
        <td>Dark Cyan</td>
    </tr>
    <tr>
        <td><span class="tag" style="background-color: firebrick;"></span>Compound word</td>
        <td>Firebrick</td>
    </tr>
    <!-- Add more rows for other tags -->
</table>

        <script>
            renewUnderlining();

            function changeDep(token) {
                var newDep = prompt("Enter new dep value:", token.dataset.dep);
                if (newDep !== null) {
                    token.dataset.dep = newDep;
                }
                renewUnderlining();
            }
            function saveHtml() {
                var html = document.documentElement.outerHTML;
                var blob = new Blob([html], {type: "text/html"});
                var url = URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = "output.html";
                a.click();
            }

            function renewUnderlining() {
                const elements = document.querySelectorAll('[data-dep]');
                elements.forEach((element) => {
                    const dep = element.getAttribute('data-dep');

                    element.style.textDecoration = null;
                    element.style.fontWeight = null;
                    element.style.fontStyle = null;
                    element.style.color = null;

                    switch (dep) {
                        case 'Root':
                            element.style.color = "darkred";
                            break;
                        case 'Subject':
                        case 'Nominal subject':
                        case 'Clausal subject':
                            element.style.color = "darkgreen";
                            break;
                        case 'Direct object':
                        case 'Indirect object':
                        case 'Object of preposition':
                            element.style.color = "darkblue";
                            break;
                        case 'Attribute':
                            element.style.color = "purple";
                            break;
                        case 'Agent':
                            element.style.color = "darkorange";
                            break;
                        case 'Adverbial clause modifier':
                        case 'Adverbial modifier':
                        case 'Adjectival modifier':
                        case 'Appositional modifier':
                            element.style.color = "teal";
                            break;
                        case 'Auxiliary':
                        case 'Passive auxiliary':
                            element.style.color = "darkgoldenrod";
                            break;
                        case 'Coordinating conjunction':
                            element.style.color = "maroon";
                            break;
                        case 'Clausal complement':
                        case 'Open clausal complement':
                            element.style.color = "darkcyan";
                            break;
                        case 'Compound word':
                            element.style.color = "firebrick";
                            break;
                        case 'Conjunct':
                        case 'Preconjunct':
                            element.style.color = "darkolivegreen";
                            break;
                        case 'Dative':
                            element.style.color = "indigo";
                            break;
                        case 'Unspecified dependency':
                            element.style.color = "dimgray";
                            break;
                        case 'Determiner':
                        case 'Pre-determiner':
                            element.style.color = "fuchsia";
                            break;
                        case 'Expletive':
                        case 'Particle':
                            element.style.color = "navy";
                            break;
                        case 'Interjection':
                            element.style.color = "olive";
                            break;
                        case 'Marker':
                            element.style.color = "darkred";
                            break;
                        case 'Meta modifier':
                            element.style.color = "saddlebrown";
                            break;
                        case 'Negation modifier':
                            element.style.color = "limegreen";
                            break;
                        case 'Nominal modifier':
                            element.style.color = "coral";
                            break;
                        case 'Noun phrase as adverbial modifier':
                            element.style.color = "hotpink";
                            break;
                        case 'Numeric modifier':
                            element.style.color = "silver";
                            break;
                        case 'Object predicate':
                            element.style.color = "darkcyan";
                            break;
                        case 'Parataxis':
                            element.style.color = "darkgray";
                            break;
                        case 'Prepositional complement':
                            element.style.color = "darkorange";
                            break;
                        case 'Possession modifier':
                            element.style.color = "navy";
                            break;
                        case 'Prepositional modifier':
                            element.style.color = "goldenrod";
                            break;
                        case 'Punctuation':
                            // No specific color
                            break;
                        case 'Quantifier modifier':
                            element.style.color = "deepskyblue";
                            break;
                        case 'Relative clause modifier':
                            element.style.color = "deeppink";
                            break;
                        default:
                            // No specific color
                            break;
                    }
                })
            }
        </script>
    </body>
    </html>
    """

    return html


class Interface:

    def __init__(self, root) -> None:
        self.root = root
        self.root.title("Syntactic Analyzer")
        self.root.geometry("600x350")
        self.token_buttons = []

        style = ttk.Style()
        style.theme_use("clam")

        # File Selector
        self.file_path = tk.StringVar()
        self.file_label = ttk.Label(root, text="Selected File:")
        self.file_label.pack(pady=10)
        self.file_entry = ttk.Entry(root, textvariable=self.file_path, state='disabled', width=40)
        self.contact_button = ttk.Button(self.root, text="Contact with developers", command=self.contact_developers)
        Hovertip(self.contact_button, "Contact developers for support")
        self.contact_button.pack(pady=10)
        Hovertip(self.file_entry,
                 "This field shows the path to the file that is currently selected. To change it press `Select File` button below.")
        self.file_entry.pack(pady=10)
        self.file_button = ttk.Button(root, text="Select File", command=self.select_file)
        Hovertip(self.file_button,
                 "Press this button in order to choose a file to analyze.\nThe application supports .html and .htm formats.")
        self.file_button.pack(pady=10)

        # Button frame
        button_frame = ttk.Frame(root)
        button_frame.pack(pady=10)

        self.analyze_button = ttk.Button(button_frame, text="Analyze File", command=self.analyze_file)
        self.analyze_button.pack(side=tk.LEFT, padx=5)
        Hovertip(self.analyze_button,
                 "Press this button in order to start the process of analyzing the HTML file specified higher.\nYou will see the results in a new browser window.")

        self.tree_button = ttk.Button(button_frame, text="Show Syntax Tree", command=self.show_syntax_tree)
        self.tree_button.pack(side=tk.LEFT, padx=5)
        Hovertip(self.tree_button,
                 "Press this button to visualize the syntax tree of the text in the selected file.")

    def select_file(self):
        file_path = filedialog.askopenfilename(filetypes=[("HTML Files", "*.html *.htm"), ("All Files", "*.*")])

        if file_path:
            self.file_path.set(file_path)

    def extract_text_from_html(self, html_content):
        """Extract text from HTML content using BeautifulSoup"""
        soup = BeautifulSoup(html_content, 'html.parser')
        # Remove script and style elements
        for script in soup(["script", "style"]):
            script.decompose()
        # Get text
        text = soup.get_text()
        # Break into lines and remove leading and trailing space on each
        lines = (line.strip() for line in text.splitlines())
        # Break multi-headlines into a line each
        chunks = (phrase.strip() for line in lines for phrase in line.split("  "))
        # Drop blank lines
        text = '\n'.join(chunk for chunk in chunks if chunk)
        return text

    def analyze_file(self):
        path = self.file_path.get()
        try:
            with open(path, 'r', encoding='utf-8') as f:
                html_content = f.read()

            # Extract text from HTML
            text = self.extract_text_from_html(html_content)

            # Analyze the extracted text
            tokens = SyntacticAnalyzer(text).get_tokens()
            html_content = generate_html(tokens)

            with tempfile.NamedTemporaryFile(mode='w', delete=False, suffix='.html', encoding='utf-8') as temp_file:
                temp_filename = temp_file.name
                temp_file.write(html_content)

            webbrowser.open('file://' + temp_filename)
        except Exception as e:
            tk.messagebox.showerror("Error", f"An error occurred while processing the file: {str(e)}")

    def show_syntax_tree(self):
        path = self.file_path.get()
        if not path:
            tk.messagebox.showwarning("Warning", "Please select a file first.")
            return

        try:
            with open(path, 'r', encoding='utf-8') as f:
                html_content = f.read()

            # Extract text from HTML
            text = self.extract_text_from_html(html_content)

            # Process the text with spaCy
            doc = nlp(text)

            # Create a figure for each sentence
            for sent in doc.sents:
                self.draw_syntax_tree(sent)

        except Exception as e:
            tk.messagebox.showerror("Error", f"An error occurred while creating syntax tree: {str(e)}")

    def draw_syntax_tree(self, sentence):
        G = nx.DiGraph()
        pos = {}
        levels = {}

        def add_edges(node, level=0):
            # Add only meaningful tokens (filter out punctuation and spaces)
            if node.text not in ['_self', 'punct', 'SPACE', '']:
                for child in node.children:
                    if child.text not in ['_self', 'punct', 'SPACE', '']:
                        G.add_edge(node.text, child.text)
                        levels[child.text] = level + 1
                        add_edges(child, level + 1)

        root = [token for token in sentence if token.head == token][0]
        levels[root.text] = 0
        add_edges(root)

        # Position nodes by level
        nodes_by_level = {}
        for node, lvl in levels.items():
            nodes_by_level.setdefault(lvl, []).append(node)
        for lvl, nodes in nodes_by_level.items():
            for i, node in enumerate(nodes):
                pos[node] = (i, -lvl)

        plt.figure(figsize=(12, 8))
        nx.draw(
            G,
            pos,
            with_labels=True,
            arrows=True,
            node_size=200,
            node_color="lightblue",
            font_size=6,
            edge_color="gray",
            linewidths=0.5,
            width=0.5
        )
        plt.title(f"Sentence: {sentence.text[:50]}...", fontsize=10)
        plt.tight_layout()
        plt.show()

    def contact_developers(self):
        webbrowser.open_new("mailto:dimonvechorko@gmail.com")


if __name__ == "__main__":
    root = tk.Tk()
    app = Interface(root)
    root.mainloop()