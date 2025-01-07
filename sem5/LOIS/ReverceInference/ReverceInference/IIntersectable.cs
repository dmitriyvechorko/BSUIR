///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит Интерфейс IIntersectable и реализацию интерфейса которая является пустым множеством
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

namespace ReverceInference
{
    public interface IIntersectable
    {
        IIntersectable Intersect(IIntersectable other);
        bool IsEmpty { get; }
        string ToString();
    }
    public class EmptySet : IIntersectable
    {
        public bool IsEmpty => true;
        public IIntersectable Intersect(IIntersectable other) => this;

        public override string ToString() => "{}";
    }
}
