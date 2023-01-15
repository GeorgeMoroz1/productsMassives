import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.out;

public class Basket implements Serializable {
    private String[] products;
    private int[] prices;
    private int[] amounts;
    private int[] productSum = {0, 0, 0};
    private int sumProducts = 0;


    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.amounts = new int[products.length];
    }

    public Basket() {
    }

    public void addToCart(int productNum, int amount) {

        productSum[productNum] += prices[productNum] * amount;
        amounts[productNum] += amount;
    }

    public void printCart() {
        out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (amounts[i] != 0) {
                out.println(products[i] + " " + amounts[i] + " шт., " + prices[i] + " руб./шт., " + productSum[i]);
            }
            sumProducts += productSum[i];
        }
        out.println("Итого " + sumProducts + "руб.");
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            writer.println(products.length);
            for (int i = 0; i < products.length; i++) {
                writer.println(products[i] + "\t" + prices[i] + "\t" + amounts[i] + "\t" + productSum[i]);
            }
        }
    }

    public static Basket loadFromTxtFile(File txtFile) throws IOException {
        String[] products;
        int[] prices;
        int[] amounts;
        int[] productSum;
        try (Scanner scanner = new Scanner(new FileInputStream(txtFile))) {
            int size = Integer.parseInt(scanner.nextLine());
            products = new String[size];
            prices = new int[size];
            amounts = new int[size];
            productSum = new int[size];
            for (int i = 0; i < size; i++) {
                String inputString2 = scanner.nextLine();
                String[] parts = inputString2.split("\t");
                products[i] = parts[0];
                prices[i] = Integer.parseInt(parts[1]);
                amounts[i] = Integer.parseInt(parts[2]);
                productSum[i] = prices[i] * amounts[i];
            }
        }
        Basket basket = new Basket();
        basket.products = products;
        basket.prices = prices;
        basket.amounts = amounts;
        basket.productSum = productSum;
        return basket;
    }

    public void saveJson(File textFile) throws Exception {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.println(json);
        }
    }

    public static Basket loadFromJson(File loadFile) throws IOException {
        try (Scanner scanner = new Scanner(new FileInputStream(loadFile))) {

            Gson gson = new Gson();
            String json = scanner.nextLine();
            return gson.fromJson(json, Basket.class);
        }
    }
}




