package api;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import util.Consts;

/**
 * API tests for CancelorderApi
 */
// @Disabled
public class CancelorderApiTest {

	private static String X_API_KEY;
	private static CancelorderApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new CancelorderApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

	@Test
	public void putTest() throws Exception {
    	String orderId = "20220317A02N52384855";
        OrderSuccess response = api.put(orderId);

        // TODO: test validations
        System.out.println(response);        
	}

}
