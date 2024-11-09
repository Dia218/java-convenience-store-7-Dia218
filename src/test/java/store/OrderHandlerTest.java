package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.view.ErrorMessages;
import store.view.InputView;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static store.view.ErrorMessages.*;

class OrderHandlerTest extends NsTest {
    private StockManager stockManager;
    private InputView inputView;
    private OrderHandler orderHandler;

    @BeforeEach
    void setUp() {
        stockManager = new StockManager();
        inputView = new InputView();
        orderHandler = new OrderHandler(stockManager, inputView);
    }

    @Test
    void testCheckOrderForm() {
        runOrderExceptionAssert("12345678", ERROR_ORDER_FORM);
    }

    @Test
    void testCheckOrderName() {
        runOrderExceptionAssert("[코크-2]", ERROR_ORDER_NAME);
    }

    @Test
    void testCheckOrderQuantity() {
        runOrderExceptionAssert("[콜라-30]", ERROR_ORDER_QUANTITY);
    }

    private void runOrderExceptionAssert(String testingInput, ErrorMessages errorMessages) {
        assertSimpleTest(() -> {
            runException(testingInput, "[콜라-2]");
            assertThat(output()).contains(errorMessages.getErrorMessage());
        });
    }

    @Override
    protected void runMain() {
        orderHandler.receiveOrder();
    }
}