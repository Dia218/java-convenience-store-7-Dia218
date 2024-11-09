package store.view;

public class ExceptionHandler {
    public static void throwException(ErrorMessages errorOrderQuantity) {
        printException(errorOrderQuantity);
        throw new IllegalArgumentException();
    }

    private static void printException(ErrorMessages errorMessage) {
        System.out.println(errorMessage.getErrorMessage());
    }
}
