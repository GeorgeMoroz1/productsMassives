import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Basket basket = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{50, 100, 70});

        basket.addToCart(basket.getProductNum(), basket.getAmount());
        basket.printCart();
        basket.saveTxt(new File("basket.txt"));
        Basket basket2 = basket.loadFromTxtFile(new File("basket.txt"));
        basket2.printCart();

    }
}
