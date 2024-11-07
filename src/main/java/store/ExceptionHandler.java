package store;

import store.view.ErrorMessages;

public class ExceptionHandler {
    public void throwException(ErrorMessages errorMessages) {
        throw new IllegalArgumentException(errorMessages.toString());
    }
}
