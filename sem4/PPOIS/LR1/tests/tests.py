from CashRegister import CashRegister
from Product import Product
from Buyer import Buyer
from Store import Store
from Seller import Seller
from ShoppingGallery import ShoppingGallery
from ShoppingCenter import ShoppingCenter
from Inventory import Inventory

dmitry = Buyer("Dmitry", 19, 257.40)
sergey = Seller("Sergey", 34)
apple = Product("Apple", 1.30)
banana = Product("Banana", 1.10)
shoes = Product("Shoes", 130.00)
gel_for_shower = Product("Gel for shower", 19.90)
evroopt = Store("Evroopt", 100, 420.00)
evroopt_inventory = Inventory()
evroopt_inventory.add_product(apple, 33)
evroopt_inventory.add_product(banana, 21)
evroopt_inventory.add_product(gel_for_shower, 4)
handm = Store("H&M", 80, 360.00)
handm_inventory = Inventory()
handm_inventory.add_product(shoes, 7)
stores_for_1st_floor_gallery = [evroopt.store_name, handm.store_name]
shopping_gallery = ShoppingGallery("1st floor gallery", stores_for_1st_floor_gallery)
shopping_galleries_list = [shopping_gallery.shopping_gallery_name]
green = ShoppingCenter("Green City", "Prytycky 156", shopping_galleries_list)
evroopt_cash_register = CashRegister(evroopt, evroopt_inventory)
evroopt_cash_register.process_purchase(dmitry, sergey, apple, 5)


def test_buyer():
    assert dmitry.get_name() == "Dmitry"
    assert dmitry.get_age() == 19
    assert dmitry.get_budget() == 257.40


def test_seller():
    assert sergey.get_name() == "Sergey"
    assert sergey.get_age() == 34


def test_product():
    assert apple.get_product_name() == "Apple"
    assert apple.get_product_price() == 1.30


def test_store():
    assert evroopt.get_store_name() == "Evroopt"
    assert evroopt.evaluate_service(4) == "Service Evroopt is evaluated as Awful"
    assert evroopt.calculate_total_rent(2) == 840.0


def test_shopping_gallery():
    assert shopping_gallery.get_shopping_gallery_name() == "1st floor gallery"


def test_shopping_center():
    assert green.get_name() == "Green City"
    assert green.get_location() == "Prytycky 156"




