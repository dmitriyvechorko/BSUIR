///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит класс Segment который хранит отрезок из области определения решения
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

using System.Globalization;
namespace ReverceInference
{
    public class Segment : IIntersectable
    {
        public double End { get; }
        public bool IsInclusiveEnd { get; } // Флаг для включения/исключения конца
        public bool IsEmpty => End <= 0 || End > 1;

        public Segment(double end, bool isInclusiveEnd = true)
        {
            End = Math.Min(1, Math.Max(0, end));
            IsInclusiveEnd = isInclusiveEnd;
        }

        public IIntersectable Intersect(IIntersectable other)
        {
            if (other is Point point)
            {
                // Проверка с учетом строгого и нестрогого включения конца
                return (point.Value < End || (IsInclusiveEnd && point.Value == End)) ? point : new EmptySet();
            }

            if (other is Segment segment)
            {
                double newEnd = Math.Min(End, segment.End);
                bool newInclusiveEnd = newEnd == End ? IsInclusiveEnd : segment.IsInclusiveEnd;
                return newEnd >= 0 ? new Segment(newEnd, newInclusiveEnd) : new EmptySet();
            }

            return new EmptySet();
        }

        public override string ToString() => IsInclusiveEnd ? $"[0, {End.ToString("F5", CultureInfo.InvariantCulture)}]" : $"[0, {End.ToString("F5", CultureInfo.InvariantCulture)})";
    }

}
