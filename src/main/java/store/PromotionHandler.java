package store;

import store.view.ExceptionHandler;
import store.view.InputView;

import java.util.HashMap;

import static store.view.ErrorMessages.ERROR_INPUT_ETC;
import static store.view.ServiceMessages.CHECK_IGNORE_PROMOTION;
import static store.view.ServiceMessages.CHECK_OFFER_PROMOTION;

public class PromotionHandler {
    private final PromotionManager promotionManager;
    private final InputView inputView;

    public PromotionHandler(PromotionManager promotionManager, InputView inputView) {
        this.promotionManager = promotionManager;
        this.inputView = inputView;
    }

    public HashMap<Product, int[]> checkPromotion(HashMap<Product, Integer> orders) {
        HashMap<Product, int[]> promotionOrders = new HashMap<>();

        for(Product product : orders.keySet()) {
            listOrderPromotion(product, orders, promotionOrders);
        }

        return promotionOrders;
    }

    private void listOrderPromotion(Product product, HashMap<Product, Integer> orders, HashMap<Product, int[]> promotionOrders) {
        Promotion promotion = getValidPromotion(product);
        if(promotion == null) {
            promotionOrders.put(product, new int[]{orders.get(product), 0});
            return;
        }
        promotionOrders.put(product, createQuantityList(orders, product, promotion));
    }

    private int[] createQuantityList(HashMap<Product, Integer> orders, Product product, Promotion promotion) {
        int[] quantityList = new int[]{promotion.calculateUnPromotionQuantity(orders.get(product)),
                promotion.calculatePromotionQuantity(orders.get(product))};

        int misQuantity = getMisQuantity(orders.get(product), product.getStockPromotionQuantity(), promotion);
        if(misQuantity < 0) { //buy 만족, get 불만족
            adjustLessOrder(product, orders.get(product), promotion, misQuantity, quantityList);
        }
        if(misQuantity > 0) { //1 set 이상 만족, buy 불만족
            adjustMuchOrder(product, misQuantity, quantityList);
        }

        return quantityList;
    }

    private void adjustLessOrder(Product product, int orderQuantity, Promotion promotion, int misQuantity, int[] quantityList) {
        if(isAvailAddOrder(product, orderQuantity, -misQuantity)) {
            adjustLessQuantity(product, misQuantity, quantityList); //모자란 get 추가하기
            return;
        }
        int muchQuantity = promotion.calculateUnPromotionQuantity(orderQuantity);
        adjustMuchQuantity(product, muchQuantity, quantityList); //남는 buy 버리기
    }

    private void adjustMuchOrder(Product product, int muchQuantity, int[] quantityList) {
        adjustMuchQuantity(product, muchQuantity, quantityList); //남는 buy 버리기
    }

    private void adjustMuchQuantity(Product product, int muchQuantity, int[] quantityList) {
        quantityList[0] += muchQuantity; quantityList[1] -= muchQuantity;
        if (!PromotionAsker.askPurchaseDecision(inputView, product.getName(), muchQuantity)) {
            quantityList[0] = 0; quantityList[1] = 0;
        }
    }

    private void adjustLessQuantity(Product product, int misQuantity, int[] quantityList) {
        if(PromotionAsker.askReceiveDecision(inputView, product.getName(), -misQuantity)) {
            quantityList[1] += -misQuantity;
        }
    }

    private boolean isAvailAddOrder(Product product, int orderQuantity, int addQuantity) {
        return product.getStockPromotionQuantity() >= addQuantity + orderQuantity;
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
            ExceptionHandler.throwException(ERROR_INPUT_ETC);
        }
        return answer.equals(YES);
    }
}