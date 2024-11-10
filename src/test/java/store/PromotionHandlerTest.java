package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.view.InputView;

import java.util.HashMap;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class PromotionHandlerTest extends NsTest {
    private final Product COKE10 = new Product("콜라", 1000, 10, "탄산2+1");
    private final Product COKE5 = new Product("콜라", 1000, 5, "탄산2+1");
    private final Product COKE1 = new Product("콜라", 1000, 1, "탄산2+1");
    private final Product ANERGYBAR = new Product("에너지바", 2000, 5, null);
    private final Product CHEESECAT = new Product("치즈고양이", 0, 5, "새해할인");

    private PromotionManager promotionManager;
    private InputView inputView;
    private PromotionHandler promotionHandler;
    private HashMap<Product, Integer> orders;

    @BeforeEach
    void setUp() {
        promotionManager = new PromotionManager();
        inputView = new InputView();
        promotionHandler = new PromotionHandler(promotionManager, inputView);

        orders = new HashMap<>();
    }

    @Test
    void testNonPromotionProduct() {
        orders.put(ANERGYBAR, 5);
        run();
        Assertions.assertEquals(5, orders.get(ANERGYBAR), "주문서가 잘못 변경됨");
    }

    @Test
    void testMisPromotionPeriod() {
        orders.put(CHEESECAT, 3);
        run();
        Assertions.assertEquals(3, orders.get(CHEESECAT), "주문서가 잘못 변경됨");
    }

    @Test
    void testMatchPromotionQuantity() {
        orders.put(COKE10, 3);
        run();
        Assertions.assertEquals(3, orders.get(COKE10), "주문서가 잘못 변경됨");
    }

    @Test
    void testLessThanPromotionBuy() {
        orders.put(COKE10, 1);
        run();
        Assertions.assertEquals(1, orders.get(COKE10), "주문서가 잘못 변경됨");
    }

    @Test
    void testOutOfStock() {
        orders.put(COKE1, 3);

        assertSimpleTest(() -> {
            run("N");
            assertThat(output()).contains("현재 콜라 3개는 프로모션 할인이 적용되지 않습니다.");
        });

        Assertions.assertEquals(0, orders.get(COKE1), "주문서 수량이 제대로 변경되지 않음");
    }

    @Test
    void testOutOfStockForAdd() {
        orders.put(COKE5, 5);

        assertSimpleTest(() -> {
            run("N");
            assertThat(output()).contains("현재 콜라 2개는 프로모션 할인이 적용되지 않습니다.");
        });

        Assertions.assertEquals(0, orders.get(COKE5), "주문서 수량이 제대로 변경되지 않음");
    }

    @Test
    void testAddFreeQuantity() {
        orders.put(COKE10, 5);

        assertSimpleTest(() -> {
            run("Y");
            assertThat(output()).contains("현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다.");
        });

        Assertions.assertEquals(6, orders.get(COKE10), "주문서 수량이 제대로 변경되지 않음");
    }

    @Override
    protected void runMain() {
        promotionHandler.checkPromotion(orders);
    }
}