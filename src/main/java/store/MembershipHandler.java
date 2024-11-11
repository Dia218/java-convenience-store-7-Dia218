package store;

import store.view.ExceptionHandler;
import store.view.InputView;

import static store.view.ErrorMessages.ERROR_INPUT_ETC;
import static store.view.ServiceMessages.CHECK_MEMBERSHIP;

public class MembershipHandler {
    private static final String YES = "Y";
    private static final String NO = "N";
    private static int DISCOUNT_RATE = 30;
    private final InputView inputView;

    public MembershipHandler(InputView inputView) {
        this.inputView = inputView;
    }

    public int membershipDiscountRate() {
        String answer = inputView.requireInput(CHECK_MEMBERSHIP);
        boolean yesDiscount = false;
        try {
            yesDiscount = validAnswer(answer);
        } catch (IllegalArgumentException e) {
            membershipDiscountRate();
        }
        if(yesDiscount) { return DISCOUNT_RATE; }
        return 0;
    }

    private boolean validAnswer(String answer) {
        if(!answer.equals(YES) && !answer.equals(NO)) {
            ExceptionHandler.throwException(ERROR_INPUT_ETC);
        }
        return answer.equals(YES);
    }
}