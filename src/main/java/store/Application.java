package store;

import store.view.InputView;
import store.view.ReceiptPrinter;

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
            HashMap<Product, int[]> promotionOrders = new PromotionHandler(promotionManager, inputView).checkPromotion(orders);
            int membershipDiscountRate = new MembershipHandler(inputView).membershipDiscountRate();
            Receipt receipt = new BillingCalculator(promotionOrders).calculateAllProduct(promotionManager, membershipDiscountRate);
            new ReceiptPrinter(receipt, promotionOrders).printReceipt();
            isContinue = false; //진행 여부 확인
        }
    }
}
