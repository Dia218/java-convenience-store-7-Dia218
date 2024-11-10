package store;

import store.view.ExceptionHandler;
import store.view.InputView;

import java.util.HashMap;

import static store.view.ErrorMessages.ERROR_ORDER_ETC;
import static store.view.ServiceMessages.CHECK_IGNORE_PROMOTION;
import static store.view.ServiceMessages.CHECK_OFFER_PROMOTION;

public class PromotionHandler {
    private final PromotionManager promotionManager;
    private final InputView inputView;

    public PromotionHandler(PromotionManager promotionManager, InputView inputView) {
        this.promotionManager = promotionManager;
        this.inputView = inputView;
    }

    public void checkPromotion(HashMap<Product, Integer> orders) {
        for(Product product : orders.keySet()) {
            checkOrderPromotion(product, orders);
        }
    }

    private void checkOrderPromotion(Product product, HashMap<Product, Integer> orders) {
        Promotion promotion = getValidPromotion(product);
        if (promotion == null) return;

        adjustOrderQuantity(product, orders, promotion);
    }

    private void adjustOrderQuantity(Product product, HashMap<Product, Integer> orders, Promotion promotion) {
        int misQuantity = getMisQuantity(orders.get(product), product.getStockPromotionQuantity(), promotion);
        if(misQuantity < 0) {
            adjustLessOrder(product, orders.get(product), promotion, misQuantity, orders);
        }
        if(misQuantity > 0) {
            adjustMuchOrder(product, misQuantity, orders);
        }
    }

    private void adjustLessOrder(Product product, int orderQuantity, Promotion promotion, int misQuantity, HashMap<Product, Integer> orders) {
        if(isAvailAddOrder(product, orderQuantity, -misQuantity)) {
            updateOrder(PromotionAsker.askReceiveDecision(inputView, product.getName(), -misQuantity),
                    orders, product, orderQuantity + -misQuantity); return;
        }
        updateOrder(!PromotionAsker.askPurchaseDecision(inputView, product.getName(), promotion.calculateUnPromotionQuantity(orderQuantity)),
                orders, product, 0);
    }

    private void adjustMuchOrder(Product product, int misQuantity, HashMap<Product, Integer> orders) {
        updateOrder(!PromotionAsker.askPurchaseDecision(inputView, product.getName(),
                misQuantity), orders, product, 0);
    }

    private boolean isAvailAddOrder(Product product, int orderQuantity, int addQuantity) {
        return product.getStockPromotionQuantity() >= addQuantity + orderQuantity;
    }

    private void updateOrder(boolean requireChange, HashMap<Product, Integer> orders, Product product, int quantity) {
        if (requireChange) {
            orders.put(product, quantity);
        }
    }

    private int getMisQuantity(int orderQuantity, int stockPromotionQuantity, Promotion promotion) {
        if(stockPromotionQuantity < orderQuantity) {
            return orderQuantity - promotion.calculatePromotionQuantity(stockPromotionQuantity);
        }
        return promotion.calculateMisQuantity(orderQuantity);
    }

    private Promotion getValidPromotion(Product product) {
        if(product.getPromotionInfo() == null) {
            return null;
        }

        Promotion promotion = promotionManager.findPromotionByName(product.getPromotionInfo());
        if(!promotion.isNowOn()) {
            return null;
        }
        return promotion;
    }
}

class PromotionAsker {
    private static final String YES = "Y";
    private static final String NO = "N";

    static boolean askReceiveDecision(InputView inputView, String productName, int addQuantity) {
        String answer = inputView.requireInput(CHECK_OFFER_PROMOTION, productName, addQuantity);
        boolean isRequireUpdate = false;
        try {
            isRequireUpdate = validAnswer(answer);
        } catch (IllegalArgumentException e) {
            askReceiveDecision(inputView, productName, addQuantity);
        }
        return isRequireUpdate;
    }

    static boolean askPurchaseDecision(InputView inputView, String productName, int misQuantity) {
        String answer = inputView.requireInput(CHECK_IGNORE_PROMOTION, productName, misQuantity);
        boolean isRequireUpdate = false;
        try {
            isRequireUpdate = validAnswer(answer);
        } catch (IllegalArgumentException e) {
            askPurchaseDecision(inputView, productName, misQuantity);
        }
        return isRequireUpdate;
    }

    private static boolean validAnswer(String answer) {
        if(!answer.equals(YES) && !answer.equals(NO)) {
            ExceptionHandler.throwException(ERROR_ORDER_ETC);
        }
        return answer.equals(YES);
    }
}