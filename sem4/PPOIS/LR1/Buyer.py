from Human import Human


class Buyer(Human):
    def __init__(self, name, age, budget):
        super().__init__(name, age)
        self.budget = budget

    def get_name(self):
        return super().get_name()

    def get_age(self):
        return super().get_age()

    def get_budget(self):
        return self.budget

    def show_buyer_info(self):
        print(f"Buyer name is: {self.get_name()}")
        print(f"Buyer age is: {self.get_age()}")
        print(f"Buyer budget is: {self.budget}")
