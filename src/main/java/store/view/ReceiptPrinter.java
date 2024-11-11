package store.view;

import store.Product;
import store.Receipt;

import java.util.HashMap;

import static store.view.FormatConverter.formatPrice;

public class ReceiptPrinter {
    private static final String CONVENIENCE_LINE = "\n==============W 편의점================\n";
    private static final String PROMOTION_LINE = "=============증\t정===============\n";
    private static final String PLAIN_LINE = "====================================\n";

    private Receipt receipt;
    private HashMap<Product, int[]> promotionOrders;

    public ReceiptPrinter(Receipt receipt, HashMap<Product, int[]> promotionOrders) {
        this.receipt = receipt;
        this.promotionOrders = promotionOrders;
    }

    public void printReceipt() {
        StringBuilder stringBuilder = new StringBuilder();

        appendTop(stringBuilder);
        appendMiddle(stringBuilder);
        appendBottom(stringBuilder);

        printReceiptContent(stringBuilder);
    }

    private void appendTop(StringBuilder stringBuilder) {
        stringBuilder.append(CONVENIENCE_LINE);
        stringBuilder.append("상품명\t\t수량\t금액\n");

        for (Product product : promotionOrders.keySet()) {
            stringBuilder.append(product.getName()).append("\t\t").append(promotionOrders.get(product)[0])
                    .append(formatPrice(product.getPriceForQuantity(promotionOrders.get(product)[0]))).append("\n");
        }
    }

    private void appendMiddle(StringBuilder stringBuilder) {
        stringBuilder.append(PROMOTION_LINE);

        for (Product product : promotionOrders.keySet()) {
            stringBuilder.append(product.getName()).append("\t\t").append(promotionOrders.get(product)[1]).append("\n");
        }
    }

    private void appendBottom(StringBuilder stringBuilder) {
        stringBuilder.append(PLAIN_LINE);
        stringBuilder.append("총구매액\t\t").append(receipt.getQuantityAll()).append("\t").append(formatPrice(receipt.getPriceAll())).append("\n");
        stringBuilder.append("행사할인" + "\t\t\t" + "-").append(formatPrice(receipt.getFreePriceAll())).append("\n");
        stringBuilder.append("멤버십할인" + "\t\t\t" + "-").append(formatPrice(receipt.getMembershipPrice())).append("\n");
        stringBuilder.append("내실돈" + "\t\t\t" + " ").append(formatPrice(receipt.getFinalPrice())).append("\n");
    }

    private void printReceiptContent(StringBuilder stringBuilder) {
        System.out.println(stringBuilder.toString());
    }
}
