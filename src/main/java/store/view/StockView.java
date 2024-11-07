package store.view;

import store.FileContentReader;
import store.ProductDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StockView {
    private final String WELCOME_MASSAGE = "안녕하세요. W편의점입니다.\n" +
                                                                        "현재 보유하고 있는 상품입니다.\n";
    public List<ProductDTO> printStock() {
        List<ProductDTO> productList = new ArrayList<>();
        FileContentReader fileContentReader = new FileContentReader();
        productList = fileContentReader.readProducts();

        for(ProductDTO productDTO : productList) {
            printProduct(productDTO);
        }

        return productList;
    }

    private void printProduct(ProductDTO product) {
        System.out.println("- " +  product.getName() + " "
                + formatPrice(product.getPrice()) + " "
                + formatQuantity(product.getQuantity()) + " "
                + product.getPromotion());
    }

    private String formatPrice(int originalPrice) {
        DecimalFormat decimalFormatter = new DecimalFormat("#,###");
        return decimalFormatter.format(originalPrice) + "원";
    }

    private String formatQuantity(int originalQuantity) {
        if(originalQuantity == 0) { return "재고 없음"; }
        return originalQuantity + "개";
    }

}
