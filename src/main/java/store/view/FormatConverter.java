package store.view;

import java.text.DecimalFormat;
public class FormatConverter {
    public static String formatPrice(int price) {
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        return decimalFormatter.format(price) + "원";
    }

    public static String formatQuantity(int quantity) {
        if(quantity == 0) { return "재고 없음"; }
        return quantity + "개";
    }
}
