package store;

import java.time.LocalDate;
import java.util.*;

public class PromotionManager {
    private static Set<Promotion> promotionList;

    public PromotionManager() {
        promotionList = new HashSet<>();;
        initPromotion();
    }

    public Promotion findPromotionByName(String targetName) {
        return Promotion.findByName(promotionList, targetName);
    }

    public int getPromotionFreeQuantity(Product product, int promotionQuantity) {
        return findPromotionByName(product.getPromotionInfo()).calculateFreeQuantity(promotionQuantity);
    }

    private void initPromotion() {
        List<String> lines = new FileContentReader().readPromotionFile();
        addAllPromotions(lines);
    }

    private void addAllPromotions(List<String> lines) {
        for (int i = 1; i < lines.size(); i++) {
            addPromotion(parsePromotion(lines.get(i)));
        }
    }

    private void addPromotion(Promotion promotion) {
        promotionList.add(promotion);
    }

    private Promotion parsePromotion(String line) {
        String[] fields = line.split(",");

        String name = fields[0];
        int buy = Integer.parseInt(fields[1]);
        int get = Integer.parseInt(fields[2]);
        LocalDate startDate = LocalDate.parse(fields[3]);
        LocalDate endDate = LocalDate.parse(fields[4]);

        return new Promotion(name, buy, get, startDate, endDate);
    }
}
