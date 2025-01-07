///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит класс Program содержит входную точку программ
// в нём происходит чтение исходного файла и запись файла после выполнения прямого нечеткого вывода.
//Исходные файлы располагаются в папке Input проекта, а записываются файлы в папку Output
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

using System.Text;

namespace ReverceInference
{
    class Program
    {
        static void Main()
        {
            bool repeat = true;
            Console.OutputEncoding = System.Text.Encoding.UTF8;

            while (repeat)
            {
                try
                {
                    string kbFileName = GetFileNameFromConsole();

                    // Если введено слово "all", обрабатываем все файлы в директории
                    if (kbFileName.Equals("all", StringComparison.OrdinalIgnoreCase))
                    {
                        ProcessAllFilesInDirectory();
                    }
                    else
                    {
                        ProcessFile(kbFileName); // Обрабатываем отдельный файл
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Произошла ошибка: {ex.Message}");
                }

                repeat = AskToRepeat();
            }

            Console.WriteLine("Программа завершена.");
        }

        static void ProcessFile(string kbFileName)
        {
            var (results, coefficients, PredicateName) = Parser.ParseFile(kbFileName);
            UnionDomain unionSolution = Solver.SolveSystem(coefficients, results,true);
            string solution = WriteSolution(unionSolution, PredicateName, coefficients[0].Length);
            Console.WriteLine(solution);
            string fullResultFilePath = GetResultFilePath(kbFileName);
            SaveToFile(fullResultFilePath, kbFileName, solution);
        }

        static void ProcessAllFilesInDirectory()
        {
            string inputDirectory = GetInputDirectory();
            string[] kbFiles = Directory.GetFiles(inputDirectory, "*.kb");

            foreach (string kbFileName in kbFiles)
            {
                try
                {
                    ProcessFile(kbFileName); 
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Произошла ошибка при обработке файла {Path.GetFileName(kbFileName)}: {ex.Message}");
                }
            }
        }

        public static string WriteSolution(UnionDomain unionSolution, string PredicateName, int n)
        {
            if (unionSolution.IsEmpty)
                return "\nНет решений";
            StringBuilder sb = new StringBuilder();
            sb.Append("\n<");

            for (int i = 0; i < n; i++)
            {
                sb.Append($"{PredicateName}(x{i + 1})");
                if (i < n - 1)
                    sb.Append($",");
            }

            sb.Append("> \u2208");
            sb.Append(unionSolution.ToString());
            return sb.ToString();
        }

        static string GetFileNameFromConsole()
        {
            string fullPath;
            string fileName;

            while (true)
            {
                Console.WriteLine("Введите имя файла базы знаний (или введите 'all' для обработки всех файлов):");
                fileName = Console.ReadLine();
                if (fileName.Equals("all", StringComparison.OrdinalIgnoreCase))
                {
                    return fileName; // Возвращаем "all" для обработки всех файлов
                }

                fullPath = Path.Combine(GetInputDirectory(), fileName);

                if (IsValidFileName(fileName, fullPath))
                    break;
            }
            return fullPath;
        }

        static bool IsValidFileName(string fileName, string fullPath)
        {
            if (string.IsNullOrWhiteSpace(fileName))
            {
                Console.WriteLine("Имя файла не может быть пустым.");
                return false;
            }
            if (!File.Exists(fullPath))
            {
                Console.WriteLine("Файл не найден. Убедитесь, что файл существует.");
                return false;
            }
            if (Path.GetExtension(fileName).ToLower() != ".kb")
            {
                Console.WriteLine("Неверное расширение файла. Требуется .kb.");
                return false;
            }
            return true;
        }

        static string GetInputDirectory() =>
            Path.GetFullPath(Path.Combine(Directory.GetCurrentDirectory(), @"..\..\..\", "Input"));

        static string GetResultFilePath(string kbFileName)
        {
            string resultFileName = Path.GetFileNameWithoutExtension(kbFileName) + "_inference.kb";
            return Path.Combine(GetOutputDirectory(), resultFileName);
        }

        static string GetOutputDirectory() =>
            Path.GetFullPath(Path.Combine(Directory.GetCurrentDirectory(), @"..\..\..\", "Output"));

        static void SaveToFile(string filePath, string kbFileName, string content)
        {
            string fileContent = File.ReadAllText(kbFileName);
            File.WriteAllText(filePath, fileContent + "\n" + content);
            Console.WriteLine($"Отчёт сохранён в файл: {Path.GetFileName(filePath)}");
        }

        static bool AskToRepeat()
        {
            Console.WriteLine("Хотите ввести другой файл? (y/n)");
            string input = Console.ReadLine()?.Trim().ToLower();
            return input == "y" || input == "да";
        }
    }
}
