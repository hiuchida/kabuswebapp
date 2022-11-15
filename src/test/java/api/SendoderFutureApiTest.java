package api;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.deliv.ExchangeDCode;
import com.github.hiuchida.api.consts.deliv.FrontOrderTypeDCode;
import com.github.hiuchida.api.consts.deliv.TimeInForceCode;
import com.github.hiuchida.api.consts.deliv.TradeTypeCode;
import com.github.hiuchida.api.model.SendOrderFutureRequestWrapper;

import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import util.Consts;

/**
 * API tests for SendoderFutureApi
 */
// @Disabled
public class SendoderFutureApiTest {

	private static String X_API_KEY;
	private static SendoderFutureApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new SendoderFutureApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

	@Test
	public void postTest() throws Exception {
    	SendOrderFutureRequestWrapper req = new SendOrderFutureRequestWrapper();
        req.setSymbol("167060019"); // 日経225mini 22/06
        req.setExchange(ExchangeDCode.日通し);
        req.setTradeType(TradeTypeCode.新規);
        req.setTimeInForce(TimeInForceCode.FAS);
        req.setSide(SideCode.買);
        req.setQty(1); // 注文数量
        req.setFrontOrderType(FrontOrderTypeDCode.指値);
        req.setPrice(24500.0); // 注文価格
        req.setExpireDay(0); // 注文有効期限
        OrderSuccess response = api.post(req);

        // TODO: test validations
        System.out.println(response);        
	}

}
