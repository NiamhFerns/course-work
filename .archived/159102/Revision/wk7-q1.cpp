#include <iostream>
#include <string>

class ShopItem {
    private:
        std::string itemName;
        int stock;
        float price;

    public:
        void setName(std::string itemName);
        void setStock(int stock);
        void setPrice(float price);

        std::string getName() { return itemName; }
        int getStock() { return stock; }
        float getPrice() { return price; }
};

int main() {
    ShopItem list[1000];
    list[0].setName("Apple");
    list[0].setPrice(2.59f);
    list[0].setStock(5);
    std::cout << "You have an " << list[0].getName() << " that costs " << list[0].getPrice() << " and there are " << list[0].getStock() << " of them." << std::endl;
    return 0;
}

void ShopItem::setName(std::string itemName) {
    this->itemName = itemName;
}

void ShopItem::setStock(int stock) {
    this->stock = stock;
}

void ShopItem::setPrice(float price) {
    this->price = price;
}
