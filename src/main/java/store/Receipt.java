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

    public int getQuantityAll() {
        return quantityAll;
    }

    public int getPriceAll() {
        return priceAll;
    }

    public int getFreePriceAll() {
        return freePriceAll;
    }

    public int getMembershipPrice() {
        return membershipPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

}
