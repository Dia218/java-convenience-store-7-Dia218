package store;

public class Receipt {
    private int quantityAll;
    private int priceAll;
    private int freePriceAll;
    private int membershipPrice;
    private int finalPrice;

    public Receipt(int quantityAll, int priceAll, int freePriceAll, int membershipPrice, int finalPrice) {
        this.quantityAll = quantityAll;
        this.priceAll = priceAll;
        this.freePriceAll = freePriceAll;
        this.membershipPrice = membershipPrice;
        this.finalPrice = finalPrice;
    }
}

class ReceiptPrinter {
    private static final String CONVENIENCE_LINE = "\n==============W 편의점================\n";
    private static final String PROMOTION_LINE = "=============증\t정===============\n";
    private static final String PLAIN_LINE = "====================================\n";

    static void printReceipt(Receipt receipt) {
        StringBuilder stringBuilder = new StringBuilder();

        appendTop(stringBuilder);
        appendMiddle(stringBuilder);
        appendBottom(stringBuilder);

        printReceiptContent(stringBuilder);
    }

    static private void appendTop(StringBuilder stringBuilder) {
        stringBuilder.append(CONVENIENCE_LINE);
        //
    }
    static private void appendMiddle(StringBuilder stringBuilder) {
        stringBuilder.append(PROMOTION_LINE);
        //
    }
    static private void appendBottom(StringBuilder stringBuilder) {
        stringBuilder.append(PLAIN_LINE);
        //
    }

    static private void printReceiptContent(StringBuilder stringBuilder) {
        System.out.println(stringBuilder.toString());
    }

}