package store;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class Product {
    private final String name;
    private final int price;
    private int vanillaQuantity;
    private String promotion;
    private int promotionQuantity;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;

        if(promotion == null) {
            this.vanillaQuantity = quantity;
        }
        if(promotion != null) {
            this.promotion = promotion;
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

    public static Product findByName(List<Product> productList, String name) {
        for (Product product : productList) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String checkPromotion() {
        return promotion;
    }

    public String getInfoForDisplay() {
        String info = "";
        if(promotion != null) {
            info += "- " + name + " " + formatPrice() + " " + formatQuantity(promotionQuantity) + " " + promotion  + "\n";
        }
        info += "- " + name + " " + formatPrice() + " " + formatQuantity(vanillaQuantity);
        return info;
    }

    public boolean availSaleQuantity(int orderQuantity) {
        return (this.vanillaQuantity + this.promotionQuantity) >= orderQuantity;
    }

    public void updateQuantity(Product product) {
        if(promotion == null && product.promotion != null) {
            this.promotion = product.promotion;
            this.promotionQuantity = product.promotionQuantity;
        }
        if(promotion != null && product.promotion == null) {
            this.vanillaQuantity = product.vanillaQuantity;
        }
    }

    public void reduceQuantity(int orderQuantity) {
        int orderPromotionQuantity = Math.max(orderQuantity, this.promotionQuantity);
        reducePromotionQuantity(orderPromotionQuantity);
        if(orderPromotionQuantity < orderQuantity) {
            orderQuantity -= orderPromotionQuantity;
            reduceVanillaQuantity(orderQuantity);
        }
    }

    private String formatPrice() {
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        return decimalFormatter.format(price) + "원";
    }

    private String formatQuantity(int quantity) {
        if(quantity == 0) { return "재고 없음"; }
        return quantity + "개";
    }

    private void reducePromotionQuantity(int orderPromotionQuantity) {
        this.promotionQuantity -= orderPromotionQuantity;
    }

    private void reduceVanillaQuantity(int orderVanillaQuantity) {
        this.vanillaQuantity -= orderVanillaQuantity;
    }

}