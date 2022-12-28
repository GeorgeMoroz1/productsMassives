import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Basket basket = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{50, 100, 70});
        File file = new File("basket.txt");
      //  Basket basket2 = new Basket();
        if (file.exists()) {
            basket = Basket.loadFromTxtFile(file);
            basket.addToCart(basket.getProductNum(), basket.getAmount());
            basket.saveTxt(file);
            basket.printCart();
        } else {
            basket.addToCart(basket.getProductNum(), basket.getAmount());
            basket.saveTxt(file);
            basket.printCart();

        }

    }
}
