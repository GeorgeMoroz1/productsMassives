import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] listProducts = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {50, 100, 70};
        Basket basket = new Basket(listProducts, prices);
        ClientLog cl = new ClientLog(0, 0);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");

        XPath xPath = XPathFactory.newInstance().newXPath();

        boolean isLoadEnabled = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(doc));
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(doc);
        boolean isLogEnabled = Boolean.parseBoolean(xPath
                .compile("config/log/enabled")
                .evaluate(doc));
        String logFileName = xPath
                .compile("config/log/fileName")
                .evaluate(doc);
        File loadFile = new File(loadFileName);
        File txtFile = new File(logFileName);
        if (isLoadEnabled && loadFile.exists()) {
            switch (loadFormat) {
                case "text" -> basket = Basket.loadFromTxtFile(loadFile);
                case "json" -> basket = Basket.loadFromJson(loadFile);
            }
        } else {
            basket = new Basket(listProducts, prices);
        }

        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < listProducts.length; i++) {
            System.out.println((i + 1) + ". " + listProducts[i] + " " + prices[i] + " руб./шт.");
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`: ");
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            if (inputString.equals("end")) {
                break;
            }
            String[] parts = inputString.split(" ");
            String one = parts[0];
            int productNum = Integer.parseInt(one) - 1;
            String two = parts[1];
            int amount = Integer.parseInt(two);
            basket.addToCart(productNum, amount);
            if (isLogEnabled) {
                cl.log(productNum, amount);
            }
        }
        boolean isSaveEnabled = Boolean.parseBoolean(xPath
                .compile("config/save/enabled")
                .evaluate(doc));
        String saveFileName = xPath
                .compile("config/save/fileName")
                .evaluate(doc);
        String saveFormat = xPath
                .compile("config/save/format")
                .evaluate(doc);
        if (isSaveEnabled) {
            switch (saveFormat) {
                case "text" -> basket.saveTxt(new File(saveFileName));
                case "json" -> basket.saveJson(new File(saveFileName));
            }
        }
        basket.printCart();
        cl.exportAsCSV(txtFile);
    }
}
