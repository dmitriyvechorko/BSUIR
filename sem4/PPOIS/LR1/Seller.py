from Human import Human


class Seller(Human):
    def __init__(self, name, age):
        super().__init__(name, age)
        self.products_sold = 0

    def get_name(self):
        return super().get_name()

    def get_age(self):
        return super().get_age()

    def show_seller_info(self):
        print(f"Seller name is: {self.get_name()}")
        print(f"Seller age is: {self.get_age()}")
        print(f"The amount of sold products is: {self.products_sold}")
