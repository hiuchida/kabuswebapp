package api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.hiuchida.api.consts.FutureCode;
import com.github.hiuchida.api.consts.PutOrCallCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.SymbolNameSuccess;
import util.Consts;

/**
 * API tests for SymbolNameApi
 */
// @Disabled
public class SymbolNameApiTest {

	private static String X_API_KEY;
	private static SymbolNameApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new SymbolNameApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

	@Test
	public void getFutureTest() throws Exception {
		FutureCode futureCode = FutureCode.日経225mini先物;
		Integer derivMonth = 202211;
		SymbolNameSuccess response = api.getFuture(futureCode, derivMonth);

		// TODO: test validations
//        System.out.println(response);
		assertEquals("167110019", response.getSymbol());
		assertEquals("日経225mini 22/11", response.getSymbolName());
	}

    @Test
    public void getOptionTest() throws Exception {
        Integer derivMonth = 202211;
        PutOrCallCode putOrCall = PutOrCallCode.CALL;
        Integer strikePrice = 26000;
        SymbolNameSuccess response = api.getOption(derivMonth, putOrCall, strikePrice);

        // TODO: test validations
//        System.out.println(response);
        assertEquals("147116018", response.getSymbol());
        assertEquals("日経平均オプション 22/11 コール 26000", response.getSymbolName());
    }

}
