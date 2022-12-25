package api;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.hiuchida.api.consts.CashmarginCode;
import com.github.hiuchida.api.consts.ProductCode;
import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.StateCode;
import com.github.hiuchida.api.model.OrdersSuccessWrapper;

import io.swagger.client.ApiException;
import util.Consts;

/**
 * API tests for OrdersApi
 */
// @Disabled
public class OrdersApiTest {

	private static String X_API_KEY;
	private static OrdersApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new OrdersApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

    @Test
    public void getTest() throws Exception {
        List<OrdersSuccessWrapper> response = api.get();

        // TODO: test validations
//        System.out.println(response);
        System.out.println("List<OrdersSuccess>.size=" + response.size());
        for (int i = 0; i < response.size(); i++) {
        	OrdersSuccessWrapper order = response.get(i);
        	if (order.getState() == StateCode.終了) {
        		continue;
        	}
        	System.out.println((i + 1) + ": " + order);
        }
    }

    @Test
    public void getByAllTest() throws Exception {
        ProductCode product = ProductCode.先物;
        String id = null;
        String updtime = null;
        String details = null;
        String symbol = "167110019";
        StateCode state = StateCode.処理済;
        SideCode side = SideCode.買;
        CashmarginCode cashmargin = CashmarginCode.新規;
        List<OrdersSuccessWrapper> response = api.getByAll(product, id, updtime, details, symbol, state, side, cashmargin);

        // TODO: test validations
//        System.out.println(response);
        System.out.println("List<OrdersSuccess>.size=" + response.size());
        for (int i = 0; i < response.size(); i++) {
        	OrdersSuccessWrapper order = response.get(i);
        	if (order.getState() == StateCode.終了) {
        		continue;
        	}
        	System.out.println((i + 1) + ": " + order);
        }
    }

}
