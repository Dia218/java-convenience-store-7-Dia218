package store;

import store.view.InputMessages;
import store.view.InputView;
import store.view.StockView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static store.view.ErrorMessages.*;

public class SalesService {
    static List<ProductDTO> productList = new ArrayList<>();

    public void executeSales() {
        StockView stockView = new StockView();
        productList = stockView.printStock();

        InputView inputView = new InputView();
        HashMap<ProductDTO, Integer> orders = requireOrder(inputView);

        updateStock(orders);
    }

    private HashMap<ProductDTO, Integer> requireOrder(InputView inputView) {
        HashMap<ProductDTO, Integer> orders = new HashMap<>();
        boolean checkOrdersComplete = false;
        while(!checkOrdersComplete) {
            String inputData = inputView.requireInput(InputMessages.ORDER);
            checkOrdersComplete = isCorrectOrders(inputData.split(","), orders);
        }
        return orders;
    }

    private boolean isCorrectOrders(String[] rawOrders, HashMap<ProductDTO, Integer> orders) {
        for(String rawOrder : rawOrders) {
            validateOrder(rawOrder, orders);
        }
        return true;
    }

    private void validateOrder(String rawOrder, HashMap<ProductDTO, Integer> orders) {
        String[] order = parseOrder(rawOrder);
        if(order.length != 2) {
            new ExceptionHandler().throwException(ERROR_ORDER_FORM);
        }
        
        ProductDTO product = findProductByName(order[0]);
        if(product == null) {
            new ExceptionHandler().throwException(ERROR_ORDER_NAME);
        }

        int changeQuantity = Integer.parseInt(order[1]);
        if(!availQuantity(product, changeQuantity)) {
            new ExceptionHandler().throwException(ERROR_ORDER_QUANTITY);
        }

        orders.put(product, changeQuantity);
    }

    private String[] parseOrder(String originalForm) {
        return originalForm.substring(1, originalForm.length()-1).split("-");
    }

    private ProductDTO findProductByName(String productName) {
        for (ProductDTO product : productList) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    private boolean availQuantity(ProductDTO product, int changeQuantity) {
        return product.getQuantity() >= changeQuantity;
    }

    private void updateStock(HashMap<ProductDTO, Integer> orders) {
        for(ProductDTO product : orders.keySet()) {
            product.setQuantity(product.getQuantity() - orders.get(product));
        }
    }
}
