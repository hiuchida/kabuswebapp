package api;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.hiuchida.api.consts.ExchangeCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.BoardSuccess;
import util.Consts;

/**
 * API tests for BoardApi
 */
// @Disabled
public class BoardApiTest {

	private static String X_API_KEY;
	private static BoardApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new BoardApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

    @Test
    public void getCurPriceTest() throws Exception {
        String symbol = "9433"; // ＫＤＤＩ
        ExchangeCode ec = ExchangeCode.東証;
        int response = api.getCurPrice(symbol, ec);

        // TODO: test validations
        System.out.println(response);        
    }

    @Test
    public void getTest() throws Exception {
        String symbol = "9433"; // ＫＤＤＩ
        ExchangeCode ec = ExchangeCode.東証;
        BoardSuccess response = api.get(symbol, ec);

        // TODO: test validations
        System.out.println(response);        
    }

}
