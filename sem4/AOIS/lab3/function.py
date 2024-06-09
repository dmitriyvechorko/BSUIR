from enum import Enum
from prettytable import PrettyTable
from typing import List, NoReturn, Dict
import re

SINGLE_ELEMENT_REGEX = re.compile(r"^x\d+$")


class OperationSigns(Enum):
    IMPLICATION = ">"
    CONJUNCTION = "^"
    DISJUNCTION = "v"
    EQUIVALENT = "~"
    NEGATION = "!"


class Function:
    class FunctionValue:
        def __init__(self, arguments: Dict, value: int):
            self._arguments = arguments
            self._value = value

        def __str__(self):
            arguments = "("
            for key, value in self._arguments.items():
                arguments += f"{key}={value}, "
            arguments = arguments.rstrip(", ") + ")"
            return f"function{arguments} = {self._value}"

        def __repr__(self):
            return f"FV[arguments: {self._arguments}, value: {self._value}]"

        @property
        def arguments(self) -> Dict:
            return self._arguments

        @property
        def value(self) -> int:
            return self._value

        """
        @value.setter
        def value(self, new_value) -> NoReturn:
            self._value = new_value
        """

    class LogicalElement:
        def __init__(self, name: str, value: int = 0):
            self._name = name
            self._value = value

        def __str__(self):
            return f"({self._name}: {self._value})"

        def __repr__(self):
            return f"LE({self._name}: {self._value})"

        @property
        def name(self) -> str:
            return self._name

        @property
        def value(self) -> int:
            return self._value

        @value.setter
        def value(self, new_value) -> NoReturn:
            self._value = new_value

    @property
    def elements(self) -> List[LogicalElement]:
        return self._elements

    @property
    def function_values(self) -> List[FunctionValue]:
        return self._function_values

    @property
    def expression(self) -> str:
        return self._expression.replace(">", "->")

    @property
    def index(self) -> int:
        return self._index

    @property
    def sdnf(self) -> str:
        return self._sdnf

    @property
    def bin_sdnf(self) -> str:
        bin_sdnf = ""
        members_amount = self._true_results_amount()
        for function_value in self._function_values:
            if function_value.value == 1:
                for _, value in function_value.arguments.items():
                    bin_sdnf += str(value)
                if members_amount != 1:
                    bin_sdnf += "+"
                members_amount -= 1
        return bin_sdnf

    @property
    def scnf(self) -> str:
        return self._scnf

    @property
    def bin_scnf(self) -> str:
        bin_scnf = ""
        members_amount = len(self._function_values) - self._true_results_amount()
        for function_value in self._function_values:
            if function_value.value == 0:
                for _, value in function_value.arguments.items():
                    bin_scnf += str(value)
                if members_amount != 1:
                    bin_scnf += "*"
                members_amount -= 1
        return bin_scnf

    @property
    def dec_sdnf(self):
        bin_sdnf_members: List[str] = self.bin_sdnf.split("+")
        dec_sdnf = "+("
        for bin_sdnf_member in bin_sdnf_members:
            dec_number = 0
            power = len(self._elements) - 1
            for element in bin_sdnf_member:
                dec_number += int(element) * (2 ** power)
                power -= 1
            dec_sdnf += f"{dec_number}, "
        dec_sdnf = dec_sdnf.rstrip(", ") + ")"
        return dec_sdnf

    @property
    def dec_scnf(self):
        bin_scnf_members: List[str] = self.bin_scnf.split("*")
        dec_scnf = "*("
        for bin_sdnf_member in bin_scnf_members:
            dec_number = 0
            power = len(self._elements) - 1
            for element in bin_sdnf_member:
                dec_number += int(element) * (2 ** power)
                power -= 1
            dec_scnf += f"{dec_number}, "
        dec_scnf = dec_scnf.rstrip(", ") + ")"
        return dec_scnf

    @property
    def table(self):
        return self._table

    def _find_elements(self) -> NoReturn:
        self._elements = []
        for element_name in set(re.findall(r"(x\d+)", self._expression)):
            self._elements.append(self.LogicalElement(element_name))
        self._elements.sort(key=lambda element: element.name)

    def _change_elements_values(self) -> NoReturn:
        elements_amount = len(self._elements)
        index_offset = 0
        while index_offset <= elements_amount - 1:
            if self._elements[elements_amount - 1 - index_offset].value == 0:
                self._elements[elements_amount - 1 - index_offset].value = 1
                return
            else:
                self._elements[elements_amount - 1 - index_offset].value = 0
                index_offset += 1
        raise ValueError

    def _make_function_values(self) -> NoReturn:
        self._function_values = []
        for iteration in range(2 ** len(self._elements)):
            arguments = {}
            for element in self._elements:
                arguments[element.name] = element.value
            function_value = Function.FunctionValue(
                arguments, self._define_formula_value(self._expression, self._elements))
            self._function_values.append(function_value)
            if iteration == 2 ** len(self._elements) - 1:
                break
            self._change_elements_values()
        self._function_values.sort(key=lambda func_value: str(function_value))

    def _make_index(self) -> NoReturn:
        self._index = 0
        power = 0
        for function_value in self._function_values[::-1]:
            self._index += function_value.value * (2 ** power)
            power += 1

    def _true_results_amount(self) -> int:
        counter: int = 0
        for function_value in self._function_values:
            if function_value.value == 1:
                counter += 1
        return counter

    @staticmethod
    def _make_snf_member(function_value: FunctionValue, args_amount: int, type_of_form: OperationSigns) -> List[str]:
        if type_of_form == OperationSigns.CONJUNCTION:  # For scnf
            sign_between_members = OperationSigns.DISJUNCTION
            not_negative_value = 0  # Value of element that doesn't require negation for scnf
        elif type_of_form == OperationSigns.DISJUNCTION:  # For sdnf
            sign_between_members = OperationSigns.CONJUNCTION
            not_negative_value = 1  # Value of element that doesn't require negation for sdnf
        else:
            raise ValueError("Unknown type of snf")
        snf_member = ["(" for _ in range(args_amount - 1)]
        element_number = 0
        for element_name, element_value in function_value.arguments.items():
            snf_member.append(f"{element_name}" if element_value == not_negative_value else f"(!{element_name})")
            if element_number == 0:
                snf_member.append(sign_between_members.value)
            elif element_number == args_amount - 1:
                snf_member.append(")")
            else:
                snf_member.append(f"){sign_between_members.value}")
            element_number += 1
        return snf_member

    def _make_sdnf(self) -> NoReturn:
        sdnf_members_amount = self._true_results_amount()
        brackets_amount = 0
        self._sdnf = f"{'(' * (sdnf_members_amount - 1)}"
        args_amount = len(self._elements)
        for function_value in self._function_values:
            if function_value.value == 0:
                continue
            self._sdnf += "".join(self._make_snf_member(function_value, args_amount, OperationSigns.DISJUNCTION))
            if brackets_amount == 0:
                self._sdnf += OperationSigns.DISJUNCTION.value
            elif brackets_amount == sdnf_members_amount - 1:
                self._sdnf += ")"
            else:
                self._sdnf += f"){OperationSigns.DISJUNCTION.value}"
            brackets_amount += 1

    def _make_scnf(self) -> NoReturn:
        scnf_members_amount = len(self._function_values) - self._true_results_amount()
        brackets_amount = 0
        self._scnf = f"{'(' * (scnf_members_amount - 1)}"
        args_amount = len(self._elements)
        for function_value in self._function_values:
            if function_value.value == 1:
                continue
            self._scnf += "".join(self._make_snf_member(function_value, args_amount, OperationSigns.CONJUNCTION))
            if brackets_amount == 0:
                self._scnf += OperationSigns.CONJUNCTION.value
            elif brackets_amount == scnf_members_amount - 1:
                self._scnf += ")"
            else:
                self._scnf += f"){OperationSigns.CONJUNCTION.value}"
            brackets_amount += 1

    def _make_table(self) -> NoReturn:
        fields_names = [element.name for element in self._elements]
        fields_names.append("function")
        fields_names.append("i")
        self._table = PrettyTable(fields_names)
        index_power = len(self._function_values) - 1
        for function_value in self._function_values:
            row = [x_value for x_value in function_value.arguments.values()]
            row.append(function_value.value)
            row.append(2 ** index_power)
            self._table.add_row(row)
            index_power -= 1

    def __init__(self, expression: str):
        expression = re.sub("->", ">", expression).replace(" ", "")
        if not Function._formula_is_correct(expression):
            raise ValueError(f"Unknown Syntax in expression: {expression}")
        self._expression = expression
        self._find_elements()
        self._make_function_values()
        self._make_index()
        self._make_scnf()
        self._make_sdnf()
        self._make_table()

    def __str__(self):
        return f"Expression: {self.expression}\n" \
               f"{str(self._table)}\n" \
               f"Index: {self._index}\n" \
               f"SCNF: {self._scnf}\n" \
               f"Binary SCNF: {self.bin_scnf}\n" \
               f"Decimal SCNF: {self.dec_scnf}\n" \
               f"SDNF: {self._sdnf}\n" \
               f"Binary SDNF: {self.bin_sdnf}\n" \
               f"Decimal SDNF: {self.dec_sdnf}"

    def __repr__(self):
        return f"Function[{self._function_values}]"

    @staticmethod
    def _brackets_and_operators_amount_is_correct(expression: str) -> bool:
        signs = 0
        for symbol in expression:
            if symbol == "!" or Function._define_logical_operation(symbol):
                signs += 1
        if expression.count("(") != expression.count(")") or expression.count("(") != signs:
            return False
        return True

    @staticmethod
    def _formula_is_correct(expression: str) -> bool:
        if not Function._brackets_and_operators_amount_is_correct(expression):
            return False
        if SINGLE_ELEMENT_REGEX.match(expression):  # If single elem
            return True
        elif expression[0] == "(" and expression[-1] == ")" and expression[1] == OperationSigns.NEGATION.value:
            return Function._formula_is_correct(expression[2:-1])
        inner_brackets = 0
        index = 1
        operation = None
        while index < len(expression) - 1:
            if expression[index] == "(":
                inner_brackets += 1
            elif expression[index] == ")":
                inner_brackets -= 1
            if inner_brackets == 0:
                operation = Function._define_logical_operation(expression[index])
                while not operation and index < len(expression) - 1:
                    index += 1
                    operation = Function._define_logical_operation(expression[index])
                break
            index += 1
        if not operation or index > len(expression) - 2:
            return False
        return Function._formula_is_correct(expression[1:index]) and\
            Function._formula_is_correct(expression[index + 1:-1])

    @staticmethod
    def _find_element_value_by_key(element_values: List, key_to_find: str) -> int:
        for element in element_values:
            if element.name == key_to_find:
                return element.value
        raise ValueError(f"No such single element: {key_to_find}")

    @staticmethod
    def _define_logical_operation(expression: str) -> [OperationSigns, None]:
        if expression == OperationSigns.DISJUNCTION.value:
            return OperationSigns.DISJUNCTION
        elif expression == OperationSigns.CONJUNCTION.value:
            return OperationSigns.CONJUNCTION
        elif expression == OperationSigns.EQUIVALENT.value:
            return OperationSigns.EQUIVALENT
        elif expression == OperationSigns.IMPLICATION.value:
            return OperationSigns.IMPLICATION
        else:
            return None

    @staticmethod
    def _operation_distributor(first_expr_value: int, second_expr_value: int, operation: OperationSigns) -> int:
        if operation == OperationSigns.CONJUNCTION:
            return Function._conjunction(first_expr_value, second_expr_value)
        elif operation == OperationSigns.DISJUNCTION:
            return Function._disjunction(first_expr_value, second_expr_value)
        elif operation == OperationSigns.IMPLICATION:
            return Function._implication(first_expr_value, second_expr_value)
        elif operation == OperationSigns.EQUIVALENT:
            return Function._equivalent(first_expr_value, second_expr_value)
        else:
            raise ValueError(f"Unknown syntax: {first_expr_value} {operation} {second_expr_value}")

    @staticmethod
    def _define_formula_value(expression: str, elements_values: List) -> int:
        if SINGLE_ELEMENT_REGEX.match(expression):  # If single elem
            return Function._find_element_value_by_key(elements_values, SINGLE_ELEMENT_REGEX.match(expression).group(0))
        elif expression[1] == OperationSigns.NEGATION.value:  # If negation
            return Function._negation(Function._define_formula_value(expression[2:-1], elements_values))
        inner_brackets = 0
        index = 1
        operation = None
        while index < len(expression) - 1:
            if expression[index] == "(":
                inner_brackets += 1
            elif expression[index] == ")":
                inner_brackets -= 1
            if inner_brackets == 0:
                operation = Function._define_logical_operation(expression[index])
                while not operation:
                    index += 1
                    operation = Function._define_logical_operation(expression[index])
                break
            index += 1
        first_expr_value = Function._define_formula_value(expression[1:index], elements_values)
        second_expr_value = Function._define_formula_value(expression[index + 1:-1], elements_values)
        return Function._operation_distributor(first_expr_value, second_expr_value, operation)

    @staticmethod
    def _conjunction(x1_value: int, x2_value: int) -> int:
        return x1_value and x2_value

    @staticmethod
    def _disjunction(x1_value: int, x2_value: int) -> int:
        return x1_value or x2_value

    @staticmethod
    def _implication(x1_value: int, x2_value: int) -> int:
        return 0 if x1_value == 1 and x2_value == 0 else 1

    @staticmethod
    def _equivalent(x1_value: int, x2_value: int) -> int:
        return 1 if x1_value == x2_value else 0

    @staticmethod
    def _negation(x_value: int) -> int:
        return 1 if x_value == 0 else 0
