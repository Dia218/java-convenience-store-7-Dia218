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
        addAllProducts(lines);
    }

    private void addAllProducts(List<String> lines) {
        for (int i = 1; i < lines.size(); i++) {
            addProduct(parseProduct(lines.get(i)));
        }
    }

    private void addProduct(Product product) {
        if(productList.contains(product)) {
            Product.updateProduct(productList, product);
        }
        productList.add(product);
    }

    private Product parseProduct(String line) {
        String[] fields = line.split(",");

        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int quantity = Integer.parseInt(fields[2]);
        String promotionInfo = fields[3];

        return new Product(name, price, quantity, promotionInfo);
    }
}

class StockView {
    static void printStock(List<Product> productList) {
        printWelcome();
        printProductList(productList);
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