class CashRegister:
    def __init__(self, store, inventory):
        self.total_sales = 0
        self.inventory = inventory
        self.store = store
        self.store_name = store.store_name

    def process_purchase(self, buyer, seller, product, quantity):
        if product in self.inventory.get_inventory():
            if quantity < self.inventory.get_inventory()[product]:
                product_price = product.product_price
                if buyer.budget >= product_price * quantity:
                    buyer.budget -= product_price * quantity
                    self.total_sales += product_price * quantity
                    seller.products_sold += quantity
                    self.inventory.get_inventory()[product] -= quantity
                    print(f"{buyer._name} have bought {product.product_name} from {seller._name} at {product_price}")
                else:
                    print(f"{buyer._name} can't buy {product.quantity} {product.product_name} cause of his budget")
            else:
                print(f"There is no {quantity} {product.product_name} in the inventory")

    def get_cash_register_info(self):
        print(f"Total sales of {self.store_name} for this CR is/are: {self.total_sales}")
