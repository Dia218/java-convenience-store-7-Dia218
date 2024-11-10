package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContentReader {
    private final String PRODUCTS_FILEPATH = "src/main/resources/products.md";
    private final String PROMOTION_FILEPATH = "src/main/resources/promotions.md";
    private final String ERROR_MESSAGE_FILE = "[ERROR] 파일을 읽는 도중 오류가 발생했습니다";

    public List<String> readProductFile() {
        return readFileLines(PRODUCTS_FILEPATH);
    }

    public List<String> readPromotionFile() {
        return readFileLines(PROMOTION_FILEPATH);
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

}
