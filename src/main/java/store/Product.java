package store;

import java.util.List;
import java.util.Objects;

import static store.view.FormatConverter.formatPrice;
import static store.view.FormatConverter.formatQuantity;

public class Product {
    private final String name;
    private final int price;
    private int vanillaQuantity;
    private String promotionInfo;
    private int promotionQuantity;

    public Product(String name, int price, int quantity, String promotionInfo) {
        this.name = name;
        this.price = price;

        if(promotionInfo.equals("null")) {
            this.vanillaQuantity = quantity;
        }
        if(!promotionInfo.equals("null")) {
            this.promotionInfo = promotionInfo;
            this.promotionQuantity = quantity;
        }
    }

    @Override
    public boolean equals(Object otherProduct) {
        if (this == otherProduct) {
            return true;
        }
        if (otherProduct == null || getClass() != otherProduct.getClass()) {
            return false;
        }

        Product product = (Product) otherProduct;
        return price == product.price &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public static void updateProduct(List<Product> productList, Product product) {
        Product prevProduct = findPrevProduct(productList, product);
        updateInfo(product, prevProduct);
    }

    public static Product findByName(List<Product> productList, String name) {
        for (Product product : productList) {
            if (product.name.equals(name)) {
                return product;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public int getStockPromotionQuantity() {
        return promotionQuantity;
    }

    public String getInfoForDisplay() {
        String info = "";
        if(promotionInfo != null) {
            info += "- " + name + " " + formatPrice(price) + " " + formatQuantity(promotionQuantity) + " " + promotionInfo + "\n";
        }
        info += "- " + name + " " + formatPrice(price) + " " + formatQuantity(vanillaQuantity);
        return info;
    }

    public boolean isAvailSaleQuantity(int orderQuantity) {
        return (this.vanillaQuantity + this.promotionQuantity) >= orderQuantity;
    }

    public void reduceQuantity(int orderQuantity) {
        int orderPromotionQuantity = Math.max(orderQuantity, this.promotionQuantity);
        reducePromotionQuantity(orderPromotionQuantity);
        if(orderPromotionQuantity < orderQuantity) {
            orderQuantity -= orderPromotionQuantity;
            reduceVanillaQuantity(orderQuantity);
        }
    }

    public int getPriceForQuantity(int quantity) {
        return quantity * price;
    }

    private static void updateInfo(Product product, Product prevProduct) {
        if(prevProduct.promotionInfo == null && product.promotionInfo != null) {
            prevProduct.promotionInfo = product.promotionInfo;
            prevProduct.promotionQuantity = product.promotionQuantity;
        }
        if(prevProduct.promotionInfo != null && product.promotionInfo == null) {
            prevProduct.vanillaQuantity = product.vanillaQuantity;
        }
    }

    private static Product findPrevProduct(List<Product> productList, Product targetProduct) {
        for (Product product : productList) {
            if (product.name.equals(targetProduct.name)) {
                return product;
            }
        }
        return null;
    }

    private void reducePromotionQuantity(int orderPromotionQuantity) {
        this.promotionQuantity -= orderPromotionQuantity;
    }

    private void reduceVanillaQuantity(int orderVanillaQuantity) {
        this.vanillaQuantity -= orderVanillaQuantity;
    }
}