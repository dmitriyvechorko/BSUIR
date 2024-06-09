class ShoppingGallery:
    def __init__(self, shopping_gallery_name, stores_for_1st_floor_gallery):
        self.shopping_gallery_name = shopping_gallery_name
        self.stores_for_1st_floor_gallery = []

    def get_shopping_gallery_name(self):
        return self.shopping_gallery_name

    def get_shopping_gallery_info(self):
        print(f"Shopping gallery {self.shopping_gallery_name}")

    def add_store(self, store):
        self.stores_for_1st_floor_gallery.append(store.get_store_name())

    def list_stores(self):
        print(f"The list of stores in {self.shopping_gallery_name}:")
        for stores in self.stores_for_1st_floor_gallery:
            print(stores)
