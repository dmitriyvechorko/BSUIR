from CashRegister import CashRegister
from Product import Product
from Buyer import Buyer
from Store import Store
from Seller import Seller
from ShoppingGallery import ShoppingGallery
from ShoppingCenter import ShoppingCenter
from Inventory import Inventory
from Discount import Discount


def main():
    dmitry = Buyer("Dmitry", 19, 257.40)
    sergey = Seller("Sergey", 34)
    apple = Product("Apple", 1.30)
    banana = Product("Banana", 1.10)
    shoes = Product("Shoes", 130.00)
    discount = Discount()
    gel_for_shower = Product("Gel for shower", 19.90)
    evroopt = Store("Evroopt", 100, 420.00)
    evroopt_inventory = Inventory()
    evroopt_inventory.add_product(apple, 33)
    evroopt_inventory.add_product(banana, 21)
    evroopt_inventory.add_product(gel_for_shower, 4)
    handm = Store("H&M", 80, 360.00)
    handm_inventory = Inventory()
    handm_inventory.add_product(shoes, 7)
    stores_for_1st_floor_gallery = []
    shopping_gallery = ShoppingGallery("1st floor gallery", stores_for_1st_floor_gallery)
    shopping_gallery.add_store(handm)
    shopping_gallery.add_store(evroopt)
    shopping_galleries_list = []
    green = ShoppingCenter("Green City", "Prytycky 156", shopping_galleries_list)
    green.add_shopping_gallery(shopping_gallery)
    evroopt_cash_register = CashRegister(evroopt, evroopt_inventory)
    while True:
        print("""
        Input № of operation:
        0 - Exit
        1 - Show buyer info
        2 - Show seller info
        3 - Show shopping center info
        4 - Show shopping gallery info
        5 - Show store info   
        6 - Show cash register purchase process   
        7 - Show cash register info
        8 - Show product price including discount   
        """)
        i = int(input("Choose operation: "))
        match i:
            case 0:
                break
            case 1:
                dmitry.show_buyer_info()
            case 2:
                sergey.show_seller_info()
            case 3:
                green.get_shopping_center_info()
                green.list_shopping_galleries()
            case 4:
                print(f"Shopping gallery {shopping_gallery.get_shopping_gallery_name()} include: ")
                shopping_gallery.list_stores()
            case 5:
                print(f"{evroopt.store_name} total rent for 2 month is: {evroopt.calculate_total_rent(2)} ")
                print(f"{evroopt.evaluate_service(8)}")
                evroopt_inventory.display_inventory()
            case 6:
                evroopt_cash_register.process_purchase(dmitry, sergey, apple, 5)
                evroopt_inventory.display_inventory()
            case 7:
                evroopt_cash_register.get_cash_register_info()
            case 8:
                discount.set_discount(apple, 25)
                discount.participate_in_discount(apple)
                print(f"Price of {apple.get_product_name()} including discount: {apple.get_product_price()}")


if __name__ == "__main__":
    main()
