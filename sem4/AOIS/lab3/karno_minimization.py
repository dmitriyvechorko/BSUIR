from typing import List, Dict, NoReturn, Tuple

from prettytable import PrettyTable

from function import Function, OperationSigns


class KarnoLacuna:

    def __init__(self, lacuna_area: List[Function.FunctionValue]):
        if not lacuna_area:
            raise ValueError("Lacuna area is empty")
        self._lacuna_area = lacuna_area
        self._not_changing_arguments = self._find_not_changing_arguments(self._lacuna_area)
        self._function_value_of_lacuna = lacuna_area[0].value

    @property
    def not_changing_arguments(self) -> Dict[str, int]:
        return self._not_changing_arguments

    @property
    def lacuna_area(self) -> List[Function.FunctionValue]:
        return self._lacuna_area

    @property
    def function_value_of_lacuna(self) -> int:
        return self._function_value_of_lacuna

    @staticmethod
    def _find_not_changing_arguments(lacuna_area: List[Function.FunctionValue]) -> Dict[str, int]:
        if not lacuna_area:
            raise ValueError("Area is empty")
        not_changing_arguments = {}
        for argument_name, argument_value in lacuna_area[0].arguments.items():
            for lacuna_cell in lacuna_area[1:]:
                if lacuna_cell.arguments[argument_name] != argument_value:
                    break
            else:
                not_changing_arguments[argument_name] = argument_value
        return not_changing_arguments


def make_karno_map_matrix(expression: str) -> List[List[Function.FunctionValue]]:
    function_values = Function(expression).function_values
    if len(function_values) == 8:
        function_values[2], function_values[3] = function_values[3], function_values[2]
        function_values[6], function_values[7] = function_values[7], function_values[6]
    return [function_values[:2 ** (len(function_values[0].arguments) - 1)],
            function_values[2 ** (len(function_values[0].arguments) - 1):]]


def find_lacunas(karno_matrix: List[List[Function.FunctionValue]], form_operator: OperationSigns) -> List[KarnoLacuna]:
    import math
    value_of_lacuna = 1 if form_operator == OperationSigns.DISJUNCTION else 0
    vertical_lacunas = []
    horizontal_lacunas = []
    max_horizontal_power = int(math.log2(len(karno_matrix[0])))
    max_vertical_power = int(math.log2(len(karno_matrix)))
    for horizontal_power in range(max_horizontal_power, -1, -1):
        for vertical_power in range(max_vertical_power, -1, -1):
            find_lacunas_of_size(karno_matrix, vertical_lacunas, value_of_lacuna, vertical_power, horizontal_power)
    for vertical_power in range(max_vertical_power, -1, -1):
        for horizontal_power in range(max_horizontal_power, -1, -1):
            find_lacunas_of_size(karno_matrix, horizontal_lacunas, value_of_lacuna, vertical_power,
                                 horizontal_power)
    if len(vertical_lacunas) <= len(horizontal_lacunas):
        return vertical_lacunas
    else:
        return horizontal_lacunas


def find_lacunas_of_size(karno_matrix: List[List[Function.FunctionValue]], karno_lacunas_buffer: List[KarnoLacuna],
                         value_of_lacuna: int, vertical_size_power: int, horizontal_size_power: int) -> NoReturn:
    for vertical_index in range(len(karno_matrix)):
        for horizontal_index in range(len(karno_matrix[vertical_index])):
            lacuna_area = []
            if karno_matrix[vertical_index][horizontal_index].value == value_of_lacuna:
                lacuna_area = make_lacuna_of_area(karno_matrix, value_of_lacuna, vertical_index, horizontal_index,
                                                  vertical_size_power, horizontal_size_power)
            if lacuna_area:
                if not elements_of_lacuna_are_used(karno_lacunas_buffer, lacuna_area):
                    karno_lacunas_buffer.append(KarnoLacuna(lacuna_area))


def make_lacuna_of_area(karno_matrix: List[List[Function.FunctionValue]], value_of_lacuna: int, vertical_index: int,
                        horizontal_index: int, vertical_size_power: int, horizontal_size_power: int) -> List:
    lacuna_area = []
    checked = False
    for vertical_shift in range(2 ** vertical_size_power):
        checked = True
        for horizont_shift in range(2 ** horizontal_size_power):
            checking_vertical_index = rollup_index(len(karno_matrix), vertical_index + vertical_shift)
            checking_horizontal_index = rollup_index(len(karno_matrix[0]), horizont_shift + horizontal_index)
            if karno_matrix[checking_vertical_index][checking_horizontal_index].value != value_of_lacuna:
                break
            lacuna_area.append(
                karno_matrix[checking_vertical_index][checking_horizontal_index])
        else:
            continue
        lacuna_area = []
        break
    else:
        if not checked:
            lacuna_area = []
    return lacuna_area


def rollup_index(direction_length: int, direction_index_with_shift: int) -> int:
    if direction_index_with_shift < direction_length:
        return direction_index_with_shift
    else:
        return direction_index_with_shift - direction_length


def elements_of_lacuna_are_used(karno_lacunas_buffer: List[KarnoLacuna],
                                lacuna_area: List[Function.FunctionValue]) -> bool:
    for element_to_add in lacuna_area:
        for added_elements in karno_lacunas_buffer:
            for function_value in added_elements.lacuna_area:
                if element_to_add is function_value:
                    return True
    return False


def make_karno_minimization(expression: str) -> Dict[str, Tuple[str, PrettyTable]]:
    karno_dnf_lacunas = find_lacunas(make_karno_map_matrix(expression), OperationSigns.DISJUNCTION)
    karno_cnf_lacunas = find_lacunas(make_karno_map_matrix(expression), OperationSigns.CONJUNCTION)
    return {"dnf": (make_expression_from_lacuna(karno_dnf_lacunas), karno_table_form(expression)),
            "cnf": (make_expression_from_lacuna(karno_cnf_lacunas), karno_table_form(expression))}


def make_expression_from_lacuna(karno_lacunas: List[KarnoLacuna]):
    if not karno_lacunas:
        raise ValueError("Lacuna is empty")
    if karno_lacunas[0].function_value_of_lacuna == 1:
        form_operator = OperationSigns.DISJUNCTION
        inner_operator = OperationSigns.CONJUNCTION
    else:
        form_operator = OperationSigns.CONJUNCTION
        inner_operator = OperationSigns.DISJUNCTION
    new_expression_implicates = []
    for lacuna in karno_lacunas:
        arguments_of_implicate = []
        for argument_name, argument_value in lacuna.not_changing_arguments.items():
            if argument_value == lacuna.function_value_of_lacuna:
                arguments_of_implicate.append(argument_name)
            else:
                arguments_of_implicate.append(f"!({argument_name})")
        new_expression_implicates.append(inner_operator.value.join(arguments_of_implicate))
    return form_operator.value.join(new_expression_implicates)


def karno_table_form(expression: str) -> PrettyTable:
    function_values = Function(expression).function_values
    if len(function_values) == 8:
        function_values[2], function_values[3] = function_values[3], function_values[2]
        function_values[6], function_values[7] = function_values[7], function_values[6]
    karno_matrix = [function_values[:2 ** (len(function_values[0].arguments) - 1)],
                    function_values[2 ** (len(function_values[0].arguments) - 1):]]
    vertical_line_arguments = []
    fill_vertical_line_arguments(function_values, vertical_line_arguments)
    table_form = PrettyTable(make_table_form_horizontal_field_names(karno_matrix, function_values))
    fill_table_rows(table_form, karno_matrix, vertical_line_arguments)
    return table_form


def make_table_form_horizontal_field_names(karno_matrix, function_values: List[Function.FunctionValue]) -> List[str]:
    arguments_amount = len(function_values[0].arguments.keys())
    vertical_line_arguments = []
    horizontal_line_arguments = []
    argument_counter = 0
    for argument in function_values[0].arguments.keys():
        if argument_counter < arguments_amount // 2:
            vertical_line_arguments.append(argument)
        else:
            horizontal_line_arguments.append(argument)
        argument_counter += 1
    field_names = ["{}\\{}".format("".join(vertical_line_arguments), "".join(horizontal_line_arguments))]
    for function_element in karno_matrix[0]:
        horizontal_line_values_label = ""
        for argument_name, argument_value in function_element.arguments.items():
            for horizontal_argument in horizontal_line_arguments:
                if argument_name == horizontal_argument:
                    horizontal_line_values_label += str(argument_value)
        field_names.append(horizontal_line_values_label)
    return field_names


def fill_vertical_line_arguments(function_values: List[Function.FunctionValue], vertical_arguments: List) -> NoReturn:
    argument_counter = 0
    arguments_amount = len(function_values[0].arguments.keys())
    for argument in function_values[0].arguments.keys():
        if argument_counter < arguments_amount // 2:
            vertical_arguments.append(argument)
        argument_counter += 1


def fill_table_rows(table_form: PrettyTable, karno_matrix, vertical_line_arguments: List[str]) -> NoReturn:
    for function_elements in karno_matrix:
        table_line = []
        vertical_line_values_label = ""
        for argument_name, argument_value in function_elements[0].arguments.items():
            for vertical_line_argument in vertical_line_arguments:
                if vertical_line_argument == argument_name:
                    vertical_line_values_label += str(argument_value)
        table_line.append(str(vertical_line_values_label))
        table_line.extend(function_elements)
        table_form.add_row(table_line)
