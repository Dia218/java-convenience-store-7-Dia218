package store;

import java.util.HashMap;

public class BillingCalculator {
    HashMap<Product, int[]> promotionOrders;

    public BillingCalculator(HashMap<Product, int[]> promotionOrders) {
        this.promotionOrders = promotionOrders;
    }

    public Receipt calculateAllProduct(PromotionManager promotionManager, int membershipDiscountRate) {
        int quantityAll = 0;
        int priceAll = 0;
        int freePriceAll = 0;
        int unPromotionPriceAll = 0;
        int membershipPrice;
        int finalPrice;

        for(Product product : promotionOrders.keySet()) {
            int productAllQuantity = 0;
            int[] quantityList = promotionOrders.get(product);

            quantityAll += (productAllQuantity = calculateProductAllQuantity(quantityList));
            priceAll += product.getPriceForQuantity(productAllQuantity);
            if(promotionManager.findPromotionByName(product.getPromotionInfo()) != null) {
                freePriceAll += product.getPriceForQuantity(promotionManager.getPromotionFreeQuantity(product, quantityList[1]));
            }

            unPromotionPriceAll += product.getPriceForQuantity(quantityList[0]);
        }

        membershipPrice = unPromotionPriceAll * 3 / 10;
        finalPrice = priceAll - freePriceAll - membershipPrice;

        return new Receipt(quantityAll, priceAll, freePriceAll, membershipPrice, finalPrice);
    }

    private int calculateProductAllQuantity(int[] quantityList) {
        return quantityList[0] + quantityList[1];
    }
}
