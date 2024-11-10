package store;

import store.view.InputView;

import java.util.HashMap;

public class Application {
    static InputView inputView;
    static StockManager stockManager;
    static PromotionManager promotionManager;

    public static void main(String[] args) {
        inputView = new InputView();
        stockManager = new StockManager();
        promotionManager = new PromotionManager();

        boolean isContinue = true;

        while(isContinue) {
            stockManager.displayStock();
            HashMap<Product, Integer> orders = new OrderHandler(stockManager, inputView).receiveOrder();
            new PromotionHandler(promotionManager, inputView).checkPromotion(orders);
            //멤버쉽 할인 여부 체크
            //영수증 발행 + stock 반영
            isContinue = false; //진행 여부 확인
        }
    }
}
