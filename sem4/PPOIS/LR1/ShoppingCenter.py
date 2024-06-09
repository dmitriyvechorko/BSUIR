class ShoppingCenter:
    def __init__(self, name, location, shopping_galleries):
        self.name = name
        self.location = location
        self.shopping_galleries = []

    def get_name(self):
        return self.name

    def get_location(self):
        return self.location

    def add_shopping_gallery(self, shopping_gallery):
        self.shopping_galleries.append(shopping_gallery.get_shopping_gallery_name())

    def get_shopping_center_info(self):
        print(f"Shopping center {self.name} is located at {self.location}")

    def list_shopping_galleries(self):
        print(f"The list of shopping galleries in {self.name} shopping center:")
        for shopping_galleries in self.shopping_galleries:
            print(shopping_galleries)
