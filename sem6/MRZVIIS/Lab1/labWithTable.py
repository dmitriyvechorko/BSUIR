#Лабораторная работа #1 по предмету "Методы решения задач в интеллектуальных системах"
#
#Авторы:
#    студенты гр. 221703
#    Вечорко Д.Н., Фурс А.М.
#
#Задание:
#    (4)  реализовать алгоритм вычисления произведения пары 4-разрядных чисел умножением со старших разрядов
#         со сдвигом частичной суммы влево
#    (16) реализовать алгоритм вычисления произведения пары 8-разрядных чисел умножением со старших разрядов
#         со сдвигом частичной суммы влево
#
#Источники:
#    1.	Формальные модели обработки информации и параллельные модели решения задач. Практикум: учебно-методическое пособие / В.П.Ивашенко. – Минск: БГУИР, 2020.
#    2.	Воеводин, В. В. Параллельные вычисления / В. В. Воеводин, Вл. В. Воеводин. – СПб. : БХВ-Петербург, 2002. – 608 с.
#    3.	https://ru.stackoverflow.com/questions/471548/Алгоритм-умножения-чисел-методом-сдвига


from tabulate import tabulate

ALFABET = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0']


class Utils:
    def __init__(self, bitness):
        self.bitness = bitness
        self.max_value = 2 ** bitness - 1

    def convert_direct(self, x_int) -> str:
        if x_int == 0:
            return "0" * self.bitness
        bin_str = ''
        temp = abs(x_int)
        while temp > 0:
            bin_str = str(temp % 2) + bin_str
            temp //= 2
        return bin_str.zfill(self.bitness)

    def check(self, x):
        for symbol in x:
            if symbol not in ALFABET:
                return "Неверный формат"
        if int(x) < 0 or int(x) > self.max_value:
            return f"Выход за пределы допустимых значений (0-{self.max_value})"
        return ""


class Pair:
    def __init__(self, x1, x2, n, bitness):
        self.bitness = bitness
        self.x1 = x1
        self.x2 = x2
        self.partial_sum = "0" * (2 * bitness)
        self.index = 0
        self.result = ""
        self.pair_num = n

    def sum(self, x1, x2) -> str:
        result = []
        carry = 0
        for a, b in zip(reversed(x1), reversed(x2)):
            total = int(a) + int(b) + carry
            result.append(str(total % 2))
            carry = total // 2
        return ''.join(reversed(result))

    def part_sum(self):
        if self.index >= self.bitness:
            return

        if self.index > 0:
            self.partial_sum = self.partial_sum[1:] + "0"

        current_bit = self.x2[self.index]
        if current_bit == "1":
            a_extended = self.x1.zfill(2 * self.bitness)
            self.partial_sum = self.sum(self.partial_sum, a_extended)

        self.index += 1

        if self.index == self.bitness:
            self.result = self.partial_sum


class Conveyor:
    def __init__(self):
        self.pairs = []
        self.queue_len = 0
        self.bitness = 4
        self.utils = None

    def select_bitness(self):
        while True:
            choice = input("Выберите разрядность чисел (4 или 8): ")
            if choice in ['4', '8']:
                self.bitness = int(choice)
                break
            else:
                print("Неверный выбор. Введите 4 или 8.")
        self.utils = Utils(self.bitness)

    def entry(self):
        self.select_bitness()

        while True:
            queue = input(f"Введите количество пар {self.bitness}-разрядных чисел для умножения: ")
            if all(c in ALFABET for c in queue):
                self.queue_len = int(queue)
                break
            else:
                print("Неверный формат")

        print(f'Формат чисел: целые числа от 0 до {self.utils.max_value}')
        while True:
            self.pairs.clear()
            for n in range(self.queue_len):
                while True:
                    x1 = input(f'Введите первое число {n + 1} пары: ')
                    if self.utils.check(x1) != "":
                        print(self.utils.check(x1))
                        break
                    x2 = input(f'Введите второе число {n + 1} пары: ')
                    if self.utils.check(x2) != "":
                        print(self.utils.check(x2))
                        break
                    self.pairs.append(Pair(
                        self.utils.convert_direct(int(x1)),
                        self.utils.convert_direct(int(x2)),
                        n,
                        self.bitness
                    ))
                    break
            if len(self.pairs) == self.queue_len:
                break

    def write_pairs(self):
        print("\nВходная очередь:")
        for pair in self.pairs:
            x1_dec = int(pair.x1, 2)
            x2_dec = int(pair.x2, 2)
            print(f"{x1_dec}={pair.x1} {x2_dec}={pair.x2}")

    def algorithm(self):
        self.write_pairs()

        total_tacts = self.bitness + self.queue_len - 1

        print(f"\nНачало конвейерной обработки")
        print(f"Количество тактов: {total_tacts}\n")

        for tact in range(total_tacts):
            table_data = []

            for stage in range(self.bitness):
                pair_index = tact - stage
                row = [f"Этап {stage + 1}"]

                if 0 <= pair_index < self.queue_len:
                    pair = self.pairs[pair_index]
                    if pair.index <= stage:
                        pair.part_sum()
                        row.append(pair.pair_num + 1)
                        row.append(pair.partial_sum)
                        row.append(pair.partial_sum)
                    else:
                        row.extend(["-", "-", "-"])
                else:
                    row.extend(["-", "-", "-"])

                table_data.append(row)

            print(f"\nТакт: {tact + 1}.")
            print(tabulate(table_data, headers=["Этап", "Номер пары", "Частичная сумма", "Частичное произведение"], tablefmt="grid"))
            input("\nНажмите Enter для продолжения...")

        print("\nФинальные результаты:")
        for j, pair in enumerate(self.pairs):
            print(f"Результат {j + 1}: {pair.result} = {int(pair.result, 2)}")


if __name__ == '__main__':
    conveyor = Conveyor()
    conveyor.entry()
    conveyor.algorithm()