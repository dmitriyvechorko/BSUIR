class Discount:
    def __init__(self):
        self.discounts = {}

    def set_discount(self, product, discount_percentage):
        self.discounts[product] = discount_percentage

    def get_discount(self, product):
        return self.discounts.get(product, 0)

    def calculate_discounted_price(self, product):
        discount = self.get_discount(product)
        return product.product_price * (1 - discount / 100)

    def participate_in_discount(self, product):
        if product in self.discounts:
            product.product_price = self.calculate_discounted_price(product)
