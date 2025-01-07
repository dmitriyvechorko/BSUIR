///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит класс Point хранящий точку из области определения решения
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

using System.Globalization;
namespace ReverceInference
{
    public class Point : IIntersectable
    {
        public double Value { get; }
        public bool IsEmpty => Value < 0 || Value > 1;

        public Point(double value)
        {
            Value = value;
        }

        public IIntersectable Intersect(IIntersectable other)
        {
            if (other is Point point)
                return Math.Abs(Value - point.Value) < double.Epsilon ? this : new EmptySet();

            if (other is Segment segment)
                return segment.Intersect(this);

            return new EmptySet();
        }

        public override string ToString() => $"{{{Value.ToString("F5", CultureInfo.InvariantCulture)}}}";
    }
}
