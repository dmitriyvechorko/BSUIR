class Product:
    def __init__(self, product_name, product_price):
        self.product_name = product_name
        self.product_price = product_price

    def get_product_name(self):
        return self.product_name

    def get_product_price(self):
        return self.product_price
