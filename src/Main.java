import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Basket basket = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{50, 100, 70});
        File file = new File("basket.bin");
        if (file.exists()) {
            basket = Basket.loadFromBinFile(file);
            basket.addToCart(basket.getProductNum(), basket.getAmount());
            basket.saveBin(file);
            basket.printCart();

        } else {
            basket.addToCart(basket.getProductNum(), basket.getAmount());
            basket.printCart();
            basket.saveBin(file);
        }

    }
}
