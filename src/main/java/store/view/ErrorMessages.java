package store.view;

public enum ErrorMessages {
    ERROR_ORDER_FORM("\n[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.\n"),
    ERROR_ORDER_NAME("\n[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.\n"),
    ERROR_ORDER_QUANTITY("\n[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.\n"),
    ERROR_INPUT_ETC("\n[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.\n");

    private final String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
