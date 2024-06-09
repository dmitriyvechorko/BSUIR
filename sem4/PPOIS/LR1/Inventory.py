class Inventory:
    def __init__(self):
        self.items = {}

    def add_product(self, product, quantity):
        if quantity <= 0:
            print("The quantity must be a positive number.")
            return
        if product in self.items:
            self.items[product] += quantity
        else:
            self.items[product] = quantity
        print(f"Added {quantity} units of '{product.product_name}'. Total in stock: {self.items[product]}")

    def remove_product(self, product, quantity):
        if product not in self.items:
            print(f"There is/are no'{product}' in inventory.")
            return
        if quantity <= 0:
            print("The quantity must be a positive number.")
            return
        if self.items[product] <= quantity:
            removed_quantity = self.items[product]
            del self.items[product]
            print(f"Removed {removed_quantity} units of '{product}'. The product is completely exhausted.")
        else:
            self.items[product] -= quantity
            print(f"Removed {quantity} units of '{product}'. Left in stock: {self.items[product]}")

    def get_inventory(self):
        return self.items

    def display_inventory(self):
        if not self.items:
            print("The inventory is empty.")
        else:
            print("The inventory contains:")
            for product, quantity in self.items.items():
                print(f"{product.product_name}: {quantity} ед.")