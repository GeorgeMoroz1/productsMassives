import java.io.*;
import java.util.Scanner;

public class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private int[] amounts;
    private int[] productSum = {0, 0, 0};
    private int productNum;
    private int sumProducts = 0;
    private int amount;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.amounts = new int[products.length];
    }

    public Basket() {
    }

    public void addToCart(int productNum, int amount) {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб./шт.");
        }
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Выберите товар и количество или введите `end`: ");
            String inputString = scanner.nextLine();
            if (inputString.equals("end")) {
                break;
            }
            String[] parts = inputString.split(" ");
            String one = parts[0];
            productNum = Integer.parseInt(one) - 1;
            String two = parts[1];
            amount = Integer.parseInt(two);
            productSum[productNum] += prices[productNum] * amount;
            amounts[productNum] += amount;
        }
        System.out.println(" ");
    }

    public int getProductNum() {
        return productNum;
    }

    public int getAmount() {
        return amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (amounts[i] != 0) {
                System.out.println(products[i] + " " + amounts[i] + " шт., " + prices[i] + " руб./шт., " + productSum[i]);
            }
            sumProducts += productSum[i];
        }
        System.out.println("Итого " + sumProducts + "руб.");
    }

    public void saveBin(File file) {
        Basket basket = new Basket();
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static Basket loadFromBinFile(File file) {
        Basket basket = null;

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return basket;
    }
}