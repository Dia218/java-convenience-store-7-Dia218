package store.view;

public enum ErrorMessages {
    ERROR_ORDER_FORM("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ERROR_ORDER_NAME("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    ERROR_ORDER_QUANTITY("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    ERROR_ORDER_ETC("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
