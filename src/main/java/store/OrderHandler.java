package store;

import store.view.*;

import java.util.HashMap;

import static store.view.ErrorMessages.*;
import static store.view.ExceptionHandler.throwException;
import static store.view.ServiceMessages.REQUEST_ORDER;

public class OrderHandler {
    private final StockManager stockManager;
    private final InputView inputView;

    public OrderHandler(StockManager stockManager, InputView inputView) {
        this.stockManager = stockManager;
        this.inputView = inputView;
    }

    public HashMap<Product, Integer> receiveOrder() {
        HashMap<Product, Integer> orders = new HashMap<>();

        boolean checkOrdersComplete = false;
        while(!checkOrdersComplete) {
            String inputData = inputView.requireInput(REQUEST_ORDER);
            checkOrdersComplete = addOrdersList(inputData, orders);
        }
        return orders;
    }

    private boolean addOrdersList(String inputData, HashMap<Product, Integer> orders) {
        String[] rawOrders = inputData.split(",");
        for (String rawOrder : rawOrders) {
            if(!addOrderList(rawOrder, orders)) { return false; }
        }
        return true;
    }

    private boolean addOrderList(String rawOrder, HashMap<Product, Integer> orders) {
        try {
            OrderValidator.checkOrderForm(rawOrder);
            String[] order = rawOrder.substring(1,rawOrder.length()-1).split("-");
            OrderValidator.checkOrderName(stockManager, order[0]);
            Product orderProduct = stockManager.findProductByName(order[0]);
            int orderQuantity = Integer.parseInt(order[1]);
            OrderValidator.checkOrderQuantity(orderProduct, orderQuantity);
            orders.put(orderProduct, orderQuantity);
        } catch (IllegalArgumentException e) {
            orders.clear();
            return false;
        }
        return true;
    }
}

class OrderValidator {
    final static String ORDER_REGEX = "\\[[a-zA-Z가-힣0-9]+-\\d+\\]";

    static void checkOrderForm(String order) {
        if(!order.matches(ORDER_REGEX)) {
            throwException(ERROR_ORDER_FORM);
        }
    }

    static void checkOrderName(StockManager stockManager, String name) {
        if(!stockManager.containsProduct(name)) {
            throwException(ERROR_ORDER_NAME);
        }
    }

    static void checkOrderQuantity(Product product, int orderQuantity) {
        if(!product.isAvailSaleQuantity(orderQuantity)) {
            throwException(ERROR_ORDER_QUANTITY);
        }
    }
}