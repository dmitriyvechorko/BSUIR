///////////////////////////////////////////////
//Индивидуальная практическая работа 2 по дисциплине ЛОИС
//Выполнена студентами группы 221703 Вечорко Дмитрий и Фурс Алексей
//содержит класс UnionDomain который хранит объеденение решений 
//21.12.2024
//Использованные материалы:
//Голенков, В. В. Логические основы интеллектуальных систем.
//Практикум: учебное методическое пособие БГУИР, 2011.

namespace ReverceInference
{
    public class UnionDomain
    {
        private readonly List<VariableDomain> _domains;

        public UnionDomain()
        {
            _domains = new List<VariableDomain>();
        }

        public UnionDomain(List<VariableDomain> domains)
        {
            _domains = new List<VariableDomain>();
            foreach (var domain in domains)
            {
                AddWithStrictEndIfNeeded(domain);
            }
        }
        public bool IsEmpty => !_domains.Any();

        public void AddWithStrictEndIfNeeded(VariableDomain domain)
        {
            foreach (var existingDomain in _domains)
                AreDomainsOverlapping(existingDomain, domain); // Используем AreDomainsOverlapping для проверки на дублирование
            _domains.Add(domain);
        }

        public UnionDomain Intersect(UnionDomain other)
        {
            var result = new UnionDomain();
            foreach (var domain1 in _domains)
            {
                foreach (var domain2 in other._domains)
                {
                    var intersection = domain1.Intersect(domain2);
                    if ( !intersection.IsEmpty)
                    {
                        result.AddWithStrictEndIfNeeded(intersection);
                    }
                }
            }
            return result;
        }

        private bool AreDomainsOverlapping(VariableDomain domain1, VariableDomain domain2)
        {
            for (int i = 0; i < domain1._variables.Count; i++)
            {
                var var1 = domain1._variables[i];
                var var2 = domain2._variables[i];

                if (var1 is Segment seg1 && var2 is Segment seg2)
                {
                    if (seg1.End == seg2.End && seg1.IsInclusiveEnd && seg2.IsInclusiveEnd)
                    {
                        return true;
                    }
                }
                else if (var1 is Point pt1 && var2 is Segment seg)
                {
                    if (pt1.Value == seg.End && seg.IsInclusiveEnd)
                    {
                        domain2._variables[i] = new Segment(seg.End, false); // Сделать конец строгим
                        return true;
                    }
                }
                else if (var2 is Point pt2 && var1 is Segment segg)
                {
                    if (pt2.Value == segg.End && segg.IsInclusiveEnd)
                    {
                        domain1._variables[i] = new Segment(segg.End, false); // Сделать конец строгим
                        return true;
                    }
                }
            }
            return false;
        }

        public override string ToString()
        {
            return string.Join(" U ", _domains.Select(d => $"({d})"));
        }
    }
}
