# Индивидуальная практическая работа 1 по дисциплине ЛОИС
# Выполнена студентом группы 221703 БГУИР Фурс Алексей Михайлович и Вечорко Дмитрий Николаевич
# Основные методы для нахождения прямого нечёткого вывода
# Последние изменения: 03.11.2024, версия: 1
#
# Использованные источники:
# Логические основы интеллектуальных систем. Практикум: учебно-методическое пособие / В.В.Голенков, В.П.Ивашенко, Д.Г.Колб, К.А.Уваров. – Минск: БГУИР, 2011.
from fuzzy_data import Implication, Sets, Set
from typing import Set as TSet, Tuple, Dict, List, Optional
from parse import parse_file

count = 1

# Класс представляющий матрицу импликации
class ImplMatrix:
    def __init__(
            self, first_name: str, first_elem: str, second_name: str, second_elem: str
    ):
        self.first_elems_set: TSet[str] = set()
        self.second_elems_set: TSet[str] = set()
        self.matrix: Dict[Tuple[str, str], int] = {}
        self.first_name = first_name
        self.second_name = second_name
        self.first_elem = first_elem
        self.second_elem = second_elem

    def calc_cell(self, a: Tuple[str, int], b: Tuple[str, int]):  # Вычисляет значение импликации между двумя элементами и сохраняет его в матрицу
        self.matrix[(a[0], b[0])] = impl_func(a[1], b[1])
        self.first_elems_set.add(a[0])
        self.second_elems_set.add(b[0])

    def set_sets(self, other: "ImplMatrix") -> None:    # Копирует множества элементов из другой матрицы
        self.first_elems_set = other.first_elems_set
        self.second_elems_set = other.second_elems_set

    def is_appliable(self, set_name: str, sets: Sets) -> bool:      # Проверяет можно ли применить матрицу к заданному множеству
        return sets[set_name].elements.keys() == self.first_elems_set

    def __getitem__(self, index):
        return self.matrix[index]

    def __setitem__(self, index, item):
        self.matrix[index] = item


def t_norm(a: int, b: int) -> int:
    # Треугольная норма: минимум из двух входных значений
    return min(a, b)

def impl_func(a: int, b: int) -> int:
    # Импликация Гёделя
    return 10000 if a >= b else b


def get_impl_matrix(impl: Implication, sets: Sets) -> ImplMatrix:
    # Создаёт и заполняет матрицу импликации для заданных множеств и отношения импликации
    matrix: ImplMatrix = ImplMatrix(impl.first, impl.first_elem, impl.second, impl.second_elem)
    for elem1 in sets[impl.first]:
        for elem2 in sets[impl.second]:
            matrix.calc_cell(elem1, elem2)

    return matrix


def sup(matrix: ImplMatrix) -> Set:     # Функция суперпозиции
    set = Set()
    for elem2 in matrix.second_elems_set:
        max = 0
        for elem1 in matrix.first_elems_set:
            if matrix[(elem1, elem2)] > max:
                max = matrix[(elem1, elem2)]

        set.push_element(elem2, max)

    return set


def apply_set_impl(set_name: str, matrix: ImplMatrix, sets: Sets) -> Set: # Применяет матрицу импликации к множеству
    new_matrix: ImplMatrix = ImplMatrix(matrix.first_name, matrix.first_elem, matrix.second_name, matrix.second_elem)
    set = sets[set_name]
    new_matrix.set_sets(matrix)
    for elem1 in set:
        for elem2 in matrix.second_elems_set:
            new_matrix[(elem1[0], elem2)] = t_norm(elem1[1], matrix[(elem1[0], elem2)])

    return sup(new_matrix)


def is_unique_set(new_set: Set, sets: Sets) -> Optional[str]: # Проверка на уникальность множества
    for set_name, existing_set in sets.sets.items():
        if set(existing_set.elements.items()) == set(new_set.elements.items()):
            return set_name

    return None


def algorithm(file_name: str) -> None:

    global count
    (sets, impls) = parse_file(file_name)
    impl_matrices: List[ImplMatrix] = []

    for impl in impls:
        impl_matrices.append(get_impl_matrix(impl, sets))

    # Инициализация очереди со всеми множествами
    set_queue = [x for x in sets]

    while len(set_queue) != 0:
        current_set = set_queue.pop(0)
        for matrix in impl_matrices:
            if matrix.is_appliable(current_set, sets):
                new_set = apply_set_impl(current_set, matrix, sets)
                new_set_name = f"_{count}"
                count += 1
                same_set = is_unique_set(new_set, sets)
                if same_set is None:
                    sets.push_set(new_set_name, new_set)
                    set_queue.append(new_set_name)
                    print(
                        f"{{ {current_set}, {matrix.first_name}({matrix.first_elem})~>{matrix.second_name}({matrix.second_elem})}} |~ {new_set_name} = {{{str(new_set)}}}")
                else:
                    print(
                        f"{{ {current_set}, {matrix.first_name}({matrix.first_elem})~>{matrix.second_name}({matrix.second_elem})}} |~ {new_set_name} = {{{str(new_set)}}} = {same_set}")