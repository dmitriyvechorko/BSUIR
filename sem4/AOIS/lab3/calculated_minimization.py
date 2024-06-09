from typing import List, NoReturn, Dict
from function import OperationSigns


def make_calculated_minimization(expression: str, operator=None) -> str:
    if not operator:
        conjunction_amount = expression.count(OperationSigns.CONJUNCTION.value)
        disjunction_amount = expression.count(OperationSigns.DISJUNCTION.value)
        operator = OperationSigns.CONJUNCTION if disjunction_amount > conjunction_amount else OperationSigns.DISJUNCTION
    return make_dead_end_form_conjunction(concatenation(expression, operator), operator)


def are_neighbor_implicates(implicate1: str, implicate2: str, operator: OperationSigns) -> bool:
    opposite_arguments_counter = 0
    if len(implicate1.split(operator.value)) != len(implicate2.split(operator.value)):
        return False
    for argument1 in implicate1.split(operator.value):
        for argument2 in implicate2.split(operator.value):
            if argument1 == argument2:
                break
            elif argument1 == f"!{argument2}" or f"!{argument1}" == argument2:
                opposite_arguments_counter += 1
                break
        else:
            return False
    return opposite_arguments_counter == 1


def define_opposite_argument_in_neighbor_implicates(implicate1: str, implicate2: str, operator: OperationSigns) -> str:
    if not are_neighbor_implicates(implicate1, implicate2, operator):
        raise ValueError("Not neighbours")
    for argument1 in implicate1.split(operator.value):
        for argument2 in implicate2.split(operator.value):
            if argument1 == argument2:
                break
            elif argument1 == f"!{argument2}":
                return argument2
            elif f"!{argument1}" == argument2:
                return argument1


def neighbor_implicates_concatenation_result(implicate1: str, implicate2: str, operator: OperationSigns) -> str:
    if not are_neighbor_implicates(implicate1, implicate2, operator):
        raise ValueError("Not neighbours")
    arg_to_delete = define_opposite_argument_in_neighbor_implicates(implicate1, implicate2, operator)
    return operator.value.join(
        (arg for arg in implicate1.split(operator.value) if arg.replace("!", "") != arg_to_delete))


def define_inner_operator(form_operator: OperationSigns) -> OperationSigns:
    if form_operator == OperationSigns.CONJUNCTION:
        return OperationSigns.DISJUNCTION
    elif form_operator == OperationSigns.DISJUNCTION:
        return OperationSigns.CONJUNCTION
    else:
        raise ValueError("Can't concatenate implicates of this normal_form")


def concatenation_of_neighbours(implicates: List[str], concatenated_implicates: List[str],
                                concatenated_indexes: List[int], i, j, inner_operator: OperationSigns) -> NoReturn:
    to_append = neighbor_implicates_concatenation_result(implicates[i], implicates[j], operator=inner_operator)
    for index in concatenated_indexes:
        if index == i:
            break
    else:
        concatenated_indexes.append(i)
    for index in concatenated_indexes:
        if index == j:
            break
    else:
        concatenated_indexes.append(j)
    for implicate in concatenated_implicates:
        if implicate == to_append:
            break
    else:
        concatenated_implicates.append(to_append)


def add_unused_implicate(implicates: List[str], used_implicates: List[str], used_indexes: List[int]) -> NoReturn:
    for i in range(len(implicates)):
        if i not in used_indexes:
            for j in range(len(implicates)):
                if i != j and implicates[i] == implicates[j]:
                    break
            else:
                if implicates[i] not in used_implicates:
                    used_implicates.append(implicates[i])


def concatenation(normal_form: str, form_operator: OperationSigns) -> str:
    inner_operator = define_inner_operator(form_operator)
    implicates = [_ for _ in normal_form.replace("(", "").replace(")", "").split(form_operator.value)]
    if len(implicates) == 1:
        return implicates[0]
    while True:
        concatenated_implicates: List = []
        concatenated_indexes: List = []
        neighbors_counter = 0
        for i in range(len(implicates) - 1):
            for j in range(i + 1, len(implicates)):
                if are_neighbor_implicates(implicates[i], implicates[j], operator=inner_operator):
                    concatenation_of_neighbours(implicates, concatenated_implicates, concatenated_indexes, i, j,
                                                inner_operator)
                    neighbors_counter += 1
        if neighbors_counter == 0:
            break
        add_unused_implicate(implicates, concatenated_implicates, concatenated_indexes)
        implicates = concatenated_implicates
    return form_operator.value.join(implicates)


def replace_implicate_with_negation(expression: str, implicate: str, useless_implicates_indexes: List,
                                    args_values: Dict, expr_to_check_list: List, operator: OperationSigns) -> NoReturn:
    replaceable_implicate_index = 0
    for replaceable_implicate in expression.replace("(", "").replace(")", "").split(operator.value):
        if replaceable_implicate == implicate:
            replaceable_implicate_index += 1
            continue
        if replaceable_implicate_index in useless_implicates_indexes:
            replaceable_implicate_index += 1
            continue
        for key, value in args_values.items():
            replaceable_implicate = replaceable_implicate.replace(key, str(value))
            replaceable_implicate = replaceable_implicate.replace("!0", "1").replace("!1", "0")
        expr_to_check_list.append(replaceable_implicate)
        replaceable_implicate_index += 1


def make_dead_end_form_conjunction(expression: str, operator: OperationSigns) -> str:
    inner_sign = OperationSigns.DISJUNCTION if operator == OperationSigns.CONJUNCTION else OperationSigns.CONJUNCTION
    negative_replace, positive_replace = (0, 1) if operator == OperationSigns.DISJUNCTION else (1, 0)
    new_expression_list = []
    useless_implicates_indexes = []
    index = 0
    for implicate in expression.replace("(", "").replace(")", "").split(operator.value):
        args_values = {}
        for argument in implicate.split(inner_sign.value):
            if argument.startswith("!"):
                args_values[argument.replace("!", "")] = negative_replace
            else:
                args_values[argument.replace("!", "")] = positive_replace
        expr_to_check_list = []
        replace_implicate_with_negation(expression, implicate, useless_implicates_indexes, args_values,
                                        expr_to_check_list, operator)
        if not implicate_is_useless_in_calculated_method(expr_to_check_list, inner_sign):
            new_expression_list.append(implicate)
        else:
            useless_implicates_indexes.append(index)
        index += 1
    return operator.value.join(new_expression_list)


def implicate_is_useless_in_calculated_method(split_expression: List[str], inner_operator: OperationSigns) -> bool:
    res = []
    skip_implicate_argument_value = "0" if inner_operator == OperationSigns.CONJUNCTION else "1"
    useless_value = "1" if inner_operator == OperationSigns.CONJUNCTION else "0"
    for implicate in split_expression:
        if skip_implicate_argument_value in implicate.split(inner_operator.value):
            continue
        res_implicate_list = [
            implicate_arg for implicate_arg in implicate.split(inner_operator.value) if implicate_arg != useless_value]
        res.append(inner_operator.value.join(res_implicate_list))
    to_remove = []
    for i in range(len(res) - 1):
        for j in range(i + 1, len(res)):
            if (res[i] == f"!{res[j]}" or f"!{res[i]}" == res[j]) and inner_operator.value not in res[i]:
                return True
    res = [implicate for implicate in res if implicate not in to_remove]

    return res.count(useless_value) >= 1
