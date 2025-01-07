# Индивидуальная практическая работа 1 по дисциплине ЛОИС
# Выполнена студентом группы 221703 БГУИР Фурс Алексей Михайлович и Вечорко Дмитрий Николаевич
# Основные методы для нахождения прямого нечёткого вывода
# Последние изменения: 03.11.2024, версия: 1
#
# Использованные источники:
# Логические основы интеллектуальных систем. Практикум: учебно-методическое пособие / В.В.Голенков, В.П.Ивашенко, Д.Г.Колб, К.А.Уваров. – Минск: БГУИР, 2011.
from typing import Dict

class Set:
    def __init__(self):
        self.elements: Dict[str, int] = {}

    def push_element(self, name: str, value: int):
        if name in self.elements:
            raise Exception("Tried to insert an already existing element")

        self.elements[name] = value

    def __iter__(self):
        return iter(self.elements.items())

    def __str__(self):
        return ", ".join(f"<{name}, {float(val) / 10000}>" for name, val in self.elements.items())


class Sets:
    def __init__(self):
        self.sets: Dict[str, Set] = {}

    def push_set(self, name: str, set: Set):
        if name in self.sets:
            raise Exception("Tried to insert an already existing element")

        self.sets[name] = set

    def __getitem__(self, index):
        return self.sets[index]

    def __iter__(self):
        return iter(self.sets.keys())


class Implication:
    def __init__(
            self, first_set: str, first_elem: str, second_set: str, second_elem: str
    ):
        self.first: str = first_set
        self.first_elem: str = first_elem
        self.second: str = second_set
        self.second_elem: str = second_elem
