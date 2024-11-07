package store;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileContentReaderTest {

    @Test
    void testReadProducts() {
        FileContentReader fileContentReader = new FileContentReader();
        List<ProductDTO> productList = fileContentReader.readProducts();

        String filePath = "src/main/resources/products.md";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String actualLine;
            int index = 0;

            while ((actualLine = reader.readLine()) != null) {
                if (index == 0) {
                    index++;
                    continue;
                }

                ProductDTO product = productList.get(index - 1);
                String expectedLine = productToString(product);

                assertEquals(expectedLine, actualLine, index + "번째 상품이 파일과 다릅니다");

                index++;
            }

            assertEquals(index - 1, productList.size(), "저장된 상품의 수가 파일과 다릅니다");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String productToString(ProductDTO product) {
        return product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getPromotion();
    }

}