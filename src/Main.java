import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Basket basket = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{50, 100, 70});
        File file = new File("basket.bin");
        if (file.exists()) {
            Basket basket2 = Basket.loadFromBinFile(file);
            basket2.addToCart(basket.getProductNum(), basket.getAmount());
            basket.saveBin(file);
            basket2.printCart();

        } else {
            basket.addToCart(basket.getProductNum(), basket.getAmount());
            basket.printCart();
            basket.saveBin(file);
        }

    }
}
