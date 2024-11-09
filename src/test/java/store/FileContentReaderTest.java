package store;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileContentReaderTest {

    @Test
    void testReadProducts() {
        FileContentReader fileContentReader = new FileContentReader();
        List<String> lines = fileContentReader.readProductFile();

        StringBuilder stringBuilder = new StringBuilder();
        for(String line : lines) {
            stringBuilder.append(line);
        }

        String actualValue = stringBuilder.toString();
        String expectedValue = "name,price,quantity,promotion"
                + "콜라,1000,10,탄산2+1"
                + "콜라,1000,10,null"
                + "사이다,1000,8,탄산2+1"
                + "사이다,1000,7,null"
                + "오렌지주스,1800,9,MD추천상품"
                + "탄산수,1200,5,탄산2+1"
                + "물,500,10,null"
                + "비타민워터,1500,6,null"
                + "감자칩,1500,5,반짝할인"
                + "감자칩,1500,5,null"
                + "초코바,1200,5,MD추천상품"
                + "초코바,1200,5,null"
                + "에너지바,2000,5,null"
                + "정식도시락,6400,8,null"
                + "컵라면,1700,1,MD추천상품"
                + "컵라면,1700,10,null";

        assertEquals(expectedValue, actualValue, "실제값이 기대값과 다름");
    }

}