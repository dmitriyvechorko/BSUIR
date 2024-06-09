class Store:
    def __init__(self, store_name, area, rent_price):
        self.store_name = store_name
        self.area = area
        self.rent_price = rent_price

    def get_store_name(self):
        return self.store_name

    def calculate_total_rent(self, duration):
        total_rent = self.rent_price * duration
        return total_rent

    def evaluate_service(self, rating):
        if rating >= 8:
            return f"Service {self.get_store_name()} is evaluated as Excelent"
        elif 5 <= rating < 8:
            return f"Service {self.get_store_name()} is evaluated as Normal"
        else:
            return f"Service {self.get_store_name()} is evaluated as Awful"
