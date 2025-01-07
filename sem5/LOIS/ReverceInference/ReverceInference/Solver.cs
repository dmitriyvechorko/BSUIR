///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит алгоритм решения системы уравнений
//В нём есть функция для решения одного уравнения и системы
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

namespace ReverceInference
{
    public static class Solver
    {
        public static UnionDomain SolveMaxEquation(double[] coefficients, double result)
        {
            List<VariableDomain> solution = new();

            if (result == 0)
                solution.Add(SolveForZeroResult(coefficients));
            else
                solution.AddRange(SolveForNonZeroResult(coefficients, result));
            return new UnionDomain(solution);
        }

        // Обработка случая, когда result равен 0
        private static VariableDomain SolveForZeroResult(double[] coefficients)
        {
            List<IIntersectable> variables = new();

            foreach (double coefficient in coefficients)
            {
                if (coefficient == 0)
                    variables.Add(new Segment(1, true));
                else
                    variables.Add(new Point(0));
            }

            return new VariableDomain(variables);
        }

        // Обработка случаев, когда result не равен 0
        private static List<VariableDomain> SolveForNonZeroResult(double[] coefficients, double result)
        {
            List<VariableDomain> solutions = new();

            for (int i = 0; i < coefficients.Length; i++)
            {
                double xiValue = result / coefficients[i];

                if (xiValue <= 1)
                {
                    List<IIntersectable> variables = new();

                    for (int j = 0; j < coefficients.Length; j++)
                    {
                        if (j == i)
                        {
                            variables.Add(new Point(xiValue));
                        }
                        else
                        {
                            double jVal = result / coefficients[j];
                            if (jVal > 1) jVal = 1;
                            variables.Add(new Segment(jVal, true));
                        }
                    }

                    solutions.Add(new VariableDomain(variables));
                }
            }

            return solutions;
        }


        public static UnionDomain SolveSystem(double[][] coefficients, double[] result, bool log=false)
        {
            // Извлекаем первое уравнение и решаем его
            UnionDomain rez = SolveMaxEquation(coefficients[0], result[0]);
            if (rez.IsEmpty)
                return new UnionDomain(new List<VariableDomain>());

            // Логирование первого решения
            if (log)
            {
                Console.WriteLine("Solution for equation 1:");
                Console.WriteLine(rez.ToString()); 
            }

            // Перебираем все остальные уравнения
            for (int eqIndex = 1; eqIndex < coefficients.Length; eqIndex++)
            {
                UnionDomain currentSolution = SolveMaxEquation(coefficients[eqIndex], result[eqIndex]);
                if (currentSolution.IsEmpty)
                    return new UnionDomain(new List<VariableDomain>());

                // Логирование текущего решения
                if (log)
                {
                    Console.WriteLine($"Solution for equation {eqIndex + 1}:");
                    Console.WriteLine(currentSolution.ToString());
                }

                rez=rez.Intersect(currentSolution);

                if (rez.IsEmpty)
                    return new UnionDomain(new List<VariableDomain>());
            }

            return rez;
        }

    }
}
