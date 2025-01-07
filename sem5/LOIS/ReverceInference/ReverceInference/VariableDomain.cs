///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//Содержит класс VariableDomain который хранит Одно решение для каждой из переменных
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

namespace ReverceInference
{
    public class VariableDomain
    {
        public List<IIntersectable> _variables;

        public VariableDomain(List<IIntersectable> variables)
        {
            _variables = variables;
        }

        public bool IsEmpty => _variables.Exists(v => v.IsEmpty);

        public VariableDomain Intersect(VariableDomain other)
        {
            if (_variables.Count != other._variables.Count) throw new InvalidOperationException("Mismatched variable counts");

            var intersections = new List<IIntersectable>();
            for (int i = 0; i < _variables.Count; i++)
            {
                var intersection = _variables[i].Intersect(other._variables[i]);
                if (intersection.IsEmpty) return new VariableDomain(new List<IIntersectable> { new EmptySet() });
                intersections.Add(intersection);
            }

            return new VariableDomain(intersections);
        }

        public override string ToString() => string.Join(" * ", _variables);
    }
}
