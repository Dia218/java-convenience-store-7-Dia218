package store.view;

public enum InputMessages {
    ORDER("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    IGNORE_PROMOTION(" 그래도 구매하시겠습니까? (Y/N)"),
    MEMBERSHIP("멤버십 할인을 받으시겠습니까? (Y/N)"),
    CONTINUE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String inputMessage;

    InputMessages(String massage) {
        this.inputMessage = massage;
    }
}
