# Индивидуальная практическая работа 1 по дисциплине ЛОИС
# Выполнена студентом группы 221703 БГУИР Фурс Алексей Михайлович и Вечорко Дмитрий Николаевич
# Основные методы для нахождения прямого нечёткого вывода
# Последние изменения: 03.11.2024, версия: 1
#
# Использованные источники:
# Логические основы интеллектуальных систем. Практикум: учебно-методическое пособие / В.В.Голенков, В.П.Ивашенко, Д.Г.Колб, К.А.Уваров. – Минск: БГУИР, 2011.
from typing import List, Tuple
from fuzzy_data import Sets, Set, Implication


class ParseError(Exception):
    def __init__(self, message="Error in input file") -> None:
        super().__init__(message)


def get_set(line: str) -> Tuple[str, Set]:
    parts = [s.strip() for s in line.split("=")]
    set: Set = Set()
    if len(parts) != 2:
        raise ParseError("More than one equal sign in a line")

    set_name, pairs_str = parts[0], parts[1].strip("{}").strip()
    flag = True

    for pair in pairs_str.split("<"):
        pair = pair.strip()
        if not pair:
            if not flag:
                raise ParseError("Double < symbol")
            flag = False
            continue

        if ">" not in pair:
            raise ParseError("Should be closing > symbol")

        elem, _ = pair.split(">", 1)
        elem_parts = [e.strip() for e in elem.split(",")]

        if len(elem_parts) != 2:
            raise ParseError("Should be only two elements in the <> pair")

        name = elem_parts[0]

        try:
            value = float(elem_parts[1].strip())
        except ValueError:
            raise ParseError("Has to be a valid float number")

        if value < 0.0 or value > 1.0:
            raise ParseError("The float number has to be in range of [0, 1]")

        set.push_element(name, int(value * 10000))

    return (set_name, set)


def get_implication(line: str) -> Implication:
    parts = line.split("~>")

    if len(parts) != 2:
        raise ParseError("Not exactly one ~> contained")

    first = parts[0].strip()
    second = parts[1].strip()

    first_elem = ([first[first.find("(") + 1: first.find(")")]] if "(" in first and ")" in first else [])
    second_elem = ([second[second.find("(") + 1: second.find(")")]] if "(" in second and ")" in second else [])

    if len(first_elem) != 1 or len(second_elem) != 1:
        raise ParseError("Not appropriate bracket count")

    first_set = first.split("(")[0].strip()
    second_set = second.split("(")[0].strip()

    return Implication(first_set, first_elem[0], second_set, second_elem[0])


def parse_file(file_str: str) -> Tuple[Sets, List[Implication]]:
    sets: Sets = Sets()
    implications: List[Implication] = []
    file = open(file_str, mode="r")
    lines = file.readlines()
    file.close()
    for line in lines:
        line = line.strip()
        if not line:
            continue

        if "~>" in line:
            implications.append(get_implication(line))
        elif "=" in line:
            name, res_set = get_set(line)
            sets.push_set(name, res_set)
        else:
            raise ParseError("Invalid statement")

    return sets, implications
