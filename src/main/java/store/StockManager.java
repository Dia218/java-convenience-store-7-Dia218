package store;

import store.view.ServiceMessages;

import java.util.ArrayList;
import java.util.List;

public class StockManager {
    private static List<Product> productList;

    public StockManager() {
        productList = new ArrayList<>();;
        initStock();
    }

    public void displayStock() {
        StockView.printStock(productList);
    }

    public boolean containsProduct(String targetName) {
        return Product.findByName(productList, targetName) != null;
    }

    public Product findProductByName(String targetName) {
        return Product.findByName(productList, targetName);
    }

    private void initStock() {
        List<String> lines = new FileContentReader().readProductFile();
        List<Product> products = parseProducts(lines);
        productList.addAll(products);
    }

    private List<Product> parseProducts(List<String> lines) {
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            addProductList(parseProduct(lines.get(i)));
        }
        return products;
    }

    private void addProductList(Product product) {
        if(productList.contains(product)) {
            Product prevProduct = Product.findByName(productList, product.getName());
            prevProduct.updateQuantity(product);
        }
        productList.add(product);
    }

    private Product parseProduct(String line) {
        String[] fields = line.split(",");

        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int quantity = Integer.parseInt(fields[2]);
        String promotion = fields[3];

        return new Product(name, price, quantity, promotion);
    }
}

class StockView {
    public static void printStock(List<Product> productList) {
        printWelcome();
        printProductList(productList);
        System.out.println();
    }

    private static void printWelcome() {
        System.out.println(ServiceMessages.WELCOME.getInputMessage());
    }

    private static void printProductList(List<Product> productList) {
        for(Product product : productList) {
            printProduct(product);
        }
    }

    private static void printProduct(Product product) {
        System.out.println(product.getInfoForDisplay());
    }

}