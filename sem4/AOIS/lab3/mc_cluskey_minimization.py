from typing import Tuple, List
from prettytable import PrettyTable
from calculated_minimization import concatenation
from function import OperationSigns


def make_mac_cluskey_table_form(expression: str) -> PrettyTable:
    conjunction_amount = expression.count(OperationSigns.CONJUNCTION.value)
    disjunction_amount = expression.count(OperationSigns.DISJUNCTION.value)
    operator = OperationSigns.CONJUNCTION if disjunction_amount > conjunction_amount else OperationSigns.DISJUNCTION
    inner_sign = OperationSigns.DISJUNCTION if operator == OperationSigns.CONJUNCTION else OperationSigns.CONJUNCTION
    constituents = expression.replace("(", "").replace(")", "").split(operator.value)
    implicates = concatenation(expression, operator).split(operator.value)
    mc_cluskey_matrix = make_mc_cluskey_matrix(constituents, implicates, inner_sign)
    field_names = [" "]
    field_names.extend(constituents)
    table_form = PrettyTable(field_names)
    for i in range(len(implicates)):
        temp_row = [implicates[i]]
        temp_row.extend(mc_cluskey_matrix[i])
        table_form.add_row(temp_row)
    return table_form


def quine_mc_cluskey_result_and_table(expression: str) -> Tuple[str, PrettyTable]:
    conjunction_amount = expression.count(OperationSigns.CONJUNCTION.value)
    disjunction_amount = expression.count(OperationSigns.DISJUNCTION.value)
    operator = OperationSigns.CONJUNCTION if disjunction_amount > conjunction_amount else OperationSigns.DISJUNCTION
    inner_sign = OperationSigns.DISJUNCTION if operator == OperationSigns.CONJUNCTION else OperationSigns.CONJUNCTION
    constituents = expression.replace("(", "").replace(")", "").split(operator.value)
    implicates = concatenation(expression, operator).split(operator.value)
    table_form = make_mac_cluskey_table_form(expression)
    mc_cluskey_matrix = make_mc_cluskey_matrix(constituents, implicates, inner_sign)
    removed_indexes = remove_unnecessary_mc_cluskey(mc_cluskey_matrix)
    for removed_index in removed_indexes:
        implicates.remove(implicates[removed_index])
    return operator.value.join(implicates), table_form


def make_mc_cluskey_matrix(constituents: List[str], implicates: List[str], inner_operator: OperationSigns):
    mc_cluskey_matrix = []
    for implicate in implicates:
        implicate_str = []
        for constituent in constituents:
            for implicate_arg in implicate.split(inner_operator.value):
                if implicate_arg not in constituent.split(inner_operator.value):
                    implicate_str.append("-")
                    break
            else:
                implicate_str.append("+")
        mc_cluskey_matrix.append(implicate_str)
    return mc_cluskey_matrix


def remove_unnecessary_mc_cluskey(mc_cluskey_matrix: List[List[str]]) -> List[int]:
    removed_indexes = []
    while True:
        for implicate_index in range(len(mc_cluskey_matrix)):
            if can_be_removed(mc_cluskey_matrix, implicate_index):
                mc_cluskey_matrix.remove(mc_cluskey_matrix[implicate_index])
                removed_indexes.append(implicate_index)
                break
        else:
            return removed_indexes


def can_be_removed(mc_cluskey_matrix: List[List[str]], implicate_index: int) -> bool:
    for column_index in range(len(mc_cluskey_matrix[0])):
        if mc_cluskey_matrix[implicate_index][column_index] != "+":
            continue
        intersection_amount = 0
        for string_index in range(len(mc_cluskey_matrix)):
            if string_index == implicate_index:
                continue
            if mc_cluskey_matrix[string_index][column_index] == "+":
                intersection_amount += 1
        if intersection_amount == 0:
            return False
    return True
