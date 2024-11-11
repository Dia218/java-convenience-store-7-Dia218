package store;

import java.time.LocalDate;
import java.util.Set;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion findByName(Set<Promotion> promotionList, String name) {
        for (Promotion promotion : promotionList) {
            if (promotion.name.equals(name)) {
                return promotion;
            }
        }
        return null;
    }

    public boolean isNowOn() {
        LocalDate localDate = camp.nextstep.edu.missionutils.DateTimes.now().toLocalDate();
        return localDate.isAfter(startDate) && localDate.isBefore(endDate);
    }

    public int calculateFreeQuantity(int targetQuantity) {
        return (targetQuantity / getPromotionQuantitySet()) * get;
    }

    public int calculatePromotionQuantity(int targetQuantity) {
        return targetQuantity - calculateUnPromotionQuantity(targetQuantity);
    }

    public int calculateUnPromotionQuantity(int targetQuantity) {
        return targetQuantity % getPromotionQuantitySet();
    }

    public int calculateMisQuantity(int targetQuantity) {
        int unPromotionQuantity = calculateUnPromotionQuantity(targetQuantity);
        if(unPromotionQuantity == 0 || targetQuantity < buy) { return 0; }

        if(unPromotionQuantity < buy) { return buy - unPromotionQuantity; }
        return unPromotionQuantity - getPromotionQuantitySet();
    }

    private int getPromotionQuantitySet() {
        return buy + get;
    }

}
