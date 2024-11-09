package store;

import store.view.InputView;

import java.util.HashMap;

public class Application {
    static StockManager stockManager;
    static InputView inputView;

    public static void main(String[] args) {
        stockManager = new StockManager();
        inputView = new InputView();

        boolean isContinue = true;

        while(isContinue) {
            stockManager.displayStock();
            HashMap<Product, Integer> orders = new OrderHandler(stockManager, inputView).receiveOrder();
            //프로모션 확인
            //멤버쉽 할인 여부 체크
            //영수증 발행 + stock 반영
            isContinue = false; //진행 여부 확인
        }
    }
}
