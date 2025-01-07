///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит парсер исходного файла.
//В парсере есть функция, которая последовательно обрабатывает каждую строку.
//В нём есть функция для анализа предиката и анализ матрицы
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

using System.Globalization;
namespace ReverceInference
{
    public static class Parser
    {
        private enum ParserState
        {
            ExpectOpenBracket,
            ParseVarName,
            ParseVarNumber,
            ParseFirstNumber,
            Drop,
            ParseNumber,
            ExpectComma,
            ExpectCloseBracket
        }

        public static (double[], double[][],string) ParseFile(string fileName)
        {
            string[] lines = File.ReadAllLines(fileName);
            double[] values = ParsePredicate(lines[0]);

            if (!string.IsNullOrWhiteSpace(lines[1]))
                throw new ArgumentException("Ошибка: ожидалась пустая строка");

            // Находим индекс первой пустой строки после второй строки
            int emptyLineIndex = Array.FindIndex(lines, 2, line => string.IsNullOrWhiteSpace(line));

            // Если пустая строка найдена и она не последняя, выбираем строки между второй и пустой строкой
            if (emptyLineIndex == -1 && emptyLineIndex >= lines.Length - 1)
                throw new ArgumentException("Ошибка: не найдена пустая строка перед концом файла.");
            string[] matrixLines = lines.Skip(2).Take(emptyLineIndex - 2).ToArray();
            double[][] coefficients = ParseMatrix(matrixLines);
            string PredicateName = ParseLastLine(lines[emptyLineIndex + 1]);

            return (values, coefficients, PredicateName);
        }

        public static string ParseLastLine(string line)
        {
            string PredicateName = "";
            string[] subLines = line.Split('=');
            if(subLines.Length != 2)
                throw new ArgumentException("Invalid input: Ожидалось = в последней строке.");
            if (subLines[0][0]!='{'&& subLines[0][subLines.Length] != '}')
                throw new ArgumentException("Ожидалось выражение в фигурных скобках");
            string[] subLines1= subLines[0].Split(',');
            if (subLines1.Length != 2)
                throw new ArgumentException("Invalid input: Ожидалось запятая в фигурных скобках");
            PredicateName = subLines1[0];
            return PredicateName.Substring(1, PredicateName.Length-1);
        }

        public static double[][] ParseMatrix(string[] lines)
        {
            if (lines == null || lines.Length < 2)
                throw new ArgumentException("Invalid input: matrix definition is incomplete.");

            // Проверяем первую строку на корректность (должна содержать букву, '=', и '{')
            string firstLine = lines[0].Trim();
            if (!(firstLine.Length == 3 &&
                    char.IsLetter(firstLine[0]) &&
                    firstLine[1] == '=' &&
                    firstLine[2] == '{'))
            {
                throw new ArgumentException("Invalid format: the first line should start with a matrix name, '=', and '{'.");
            }

            // Проверяем последнюю строку на наличие '}'
            string lastLine = lines[^1].Trim();
            if (lastLine != "}")
            {
                throw new ArgumentException("Invalid format: the last line should be '}'.");
            }

            // Убираем первую и последнюю строку
            var matrixLines = lines.Skip(1).Take(lines.Length - 2).ToArray();

            // Создаем массив для хранения результата
            var matrix = new double[matrixLines.Length][];

            for (int i = 0; i < matrixLines.Length; i++)
            {
                // Разделяем строку по пробелам и запятым и проверяем на корректность
                matrix[i] = matrixLines[i]
                    .Split(new[] { ' ', ',' }, StringSplitOptions.RemoveEmptyEntries)
                    .Select(element =>
                    {
                        // Удаляем пробелы, чтобы правильно обрабатывать значения
                        element = element.Trim();

                        // Пробуем парсить элемент, если это не удалось, выбрасываем исключение
                        double value;
                        try
                        {
                            value = double.Parse(element, System.Globalization.CultureInfo.InvariantCulture);
                        }
                        catch (FormatException)
                        {
                            throw new ArgumentException($"Invalid matrix element: '{element}' is not a valid number.");
                        }

                        // Проверяем диапазон [0, 1]
                        if (value < 0 || value > 1)
                            throw new ArgumentException($"Invalid matrix element: '{element}' is out of the allowed range [0, 1].");

                        return value;
                    })
                    .ToArray();
            }

            return matrix;
        }

        private static double[] ParsePredicate(string line)
        {
            List<double> elements = new List<double>();
            line = line.Replace(" ", "");
            int i = 0;
            int n = line.Length;

            string PredicateName = "";
            if (i < n && char.IsUpper(line[i]))
            {
                PredicateName += line[i];
                i++;

                // Читаем все последующие цифры в имени предиката
                while (i < n && char.IsDigit(line[i]))
                    PredicateName += line[i++];
            }
            else
                ThrowParsingError(nameof(ParsePredicate), line, i, "Имя предиката (одна заглавная буква и цифры)", line[i]);

            // Используем метод Expect для проверки символа '='
            Expect('=', ref i, line, nameof(ParsePredicate));

            // Используем метод Expect для проверки символа '{'
            Expect('{', ref i, line, nameof(ParsePredicate));

            ParserState state = ParserState.ExpectOpenBracket;
            char letter = '0';
            int previousNumber = 0;
            string varNumber = "";
            string varName = "";
            string numStr = "";

            while (i < n - 1)
            {
                char currentChar = line[i];
                switch (state)
                {
                    case ParserState.ExpectOpenBracket:
                        Expect('<', ref i, line, nameof(ParsePredicate));
                        i--;
                        state = ParserState.ParseVarName;
                        break;

                    case ParserState.ParseVarName:
                        if (char.IsLower(currentChar))
                        {
                            if (letter == '0')
                                letter = currentChar;
                            else if (currentChar != letter)
                                ThrowParsingError(nameof(ParsePredicate), line, i, $"латинская буква {letter}", currentChar);
                            varName = currentChar.ToString();
                            state = ParserState.ParseVarNumber;
                        }
                        else
                            ThrowParsingError(nameof(ParsePredicate), line, i, "строчная латинская буква", currentChar);
                        break;

                    case ParserState.ParseVarNumber:
                        if (char.IsDigit(currentChar))
                            varNumber += currentChar;
                        else if (currentChar == ',')
                        {
                            if (varNumber == "")
                                throw new ArgumentException($"Ошибка: Имя переменной не содержит цифр на позиции {i}");
                            else if (int.Parse(varNumber) != previousNumber + 1)
                                throw new ArgumentException($"Ошибка: Номера переменных не идут по порядку на позиции {i}");
                            else
                            {
                                previousNumber = int.Parse(varNumber);
                                varName += varNumber;
                                varNumber = "";
                                state = ParserState.ParseFirstNumber;
                            }
                        }
                        else
                            ThrowParsingError(nameof(ParsePredicate), line, i, "цифра или запятая", currentChar);
                        break;

                    case ParserState.ParseFirstNumber:
                        if (char.IsDigit(currentChar))
                        {
                            numStr += currentChar;
                            state = ParserState.Drop;
                        }
                        else
                            ThrowParsingError(nameof(ParsePredicate), line, i, "цифра (0 или 1)", currentChar);
                        break;

                    case ParserState.Drop:
                        Expect('.', ref i, line, nameof(ParsePredicate));
                        numStr += ".";
                        i--;
                        state = ParserState.ParseNumber;
                        break;

                    case ParserState.ParseNumber:
                        if (char.IsDigit(currentChar))
                            numStr += currentChar;
                        else if (currentChar == '>')
                        {
                            if (numStr != "")
                            {
                                try
                                {
                                    double degree = double.Parse(numStr, CultureInfo.InvariantCulture);
                                    elements.Add(degree);
                                    varName = "";
                                    numStr = "";
                                    state = ParserState.ExpectComma;
                                }
                                catch (FormatException)
                                {
                                    ThrowParsingError(nameof(ParsePredicate), line, i, "число", currentChar);
                                }
                            }
                            else
                                ThrowParsingError(nameof(ParsePredicate), line, i, "числовое значение", currentChar);
                        }
                        break;

                    case ParserState.ExpectComma:
                        Expect(',', ref i, line, nameof(ParsePredicate));
                        i--;
                        state = ParserState.ExpectOpenBracket;
                        break;
                }
                i++;
            }

            Expect('}', ref i, line, nameof(ParsePredicate));

            return elements.ToArray();
        }

        private static void Expect(char expected, ref int index, string line, string methodName)
        {
            if (index >= line.Length || line[index] != expected)
                ThrowParsingError(methodName, line, index, $"символ '{expected}'", line[index]);
            index++;
        }
        private static void ThrowParsingError(string methodName, string line, int index, string expected, char found)
        {
            throw new ArgumentException($"Ошибка в {methodName}: ожидалось {expected}, но найден '{found}' на позиции {index} в строке '{line}'");
        }
    }
}
