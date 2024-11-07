package store;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContentReader {
    private final String PRODUCTS_FILEPATH = "src/main/resources/products.md";
    private final String PROMOTION_FILEPATH = "src/main/resources/promotion.md";
    private final String ERROR_MESSAGE_FILE = "[ERROR] 파일을 읽는 도중 오류가 발생했습니다";

    public List<ProductDTO> readProducts() {
        List<ProductDTO> productList = new ArrayList<>();

        List<String> lines = readFileLines(PRODUCTS_FILEPATH);
        for (int i = 1; i < lines.size(); i++) {
            ProductDTO product = parseProduct(lines.get(i));
            productList.add(product);
        }

        return productList;
    }

    private List<String> readFileLines(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MESSAGE_FILE, e);
        }

        return lines;
    }

    private ProductDTO parseProduct(String line) {
        String[] fields = line.split(",");

        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int quantity = Integer.parseInt(fields[2]);
        String promotion = fields[3];

        return new ProductDTO(name, price, quantity, promotion);
    }

}
