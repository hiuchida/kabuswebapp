package api;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.stock.AccountTypeCode;
import com.github.hiuchida.api.consts.stock.CashmarginStockCode;
import com.github.hiuchida.api.consts.stock.DelivTypeCode;
import com.github.hiuchida.api.consts.stock.ExchangeSCode;
import com.github.hiuchida.api.consts.stock.FrontOrderTypeSCode;
import com.github.hiuchida.api.consts.stock.FundTypeCode;
import com.github.hiuchida.api.consts.stock.MarginTradeTypeCode;
import com.github.hiuchida.api.consts.stock.SecurityTypeSCode;
import com.github.hiuchida.api.model.SendOrderRequestWrapper;

import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import util.Consts;

/**
 * API tests for SendoderApi
 */
// @Disabled
public class SendoderApiTest {

	private static String X_API_KEY;
	private static SendoderApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new SendoderApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

	@Test
	public void postTest() throws Exception {
    	SendOrderRequestWrapper req = new SendOrderRequestWrapper();
        req.setSymbol("9433@" + ExchangeSCode.東証.toString()); // ＫＤＤＩ
        req.setExchange(ExchangeSCode.東証);
        req.setSecurityType(SecurityTypeSCode.株式);
        req.setSide(SideCode.買);
        req.setCashMargin(CashmarginStockCode.現物);
        req.setMarginTradeType(MarginTradeTypeCode.制度信用);
        req.setDelivType(DelivTypeCode.指定なし);
        req.setFundType(FundTypeCode.信用代用);
        req.setAccountType(AccountTypeCode.特定);
        req.setQty(100); // 注文数量
        req.setFrontOrderType(FrontOrderTypeSCode.指値);
        req.setPrice(100.0); // 注文価格
        req.setExpireDay(0); // 注文有効期限
        OrderSuccess response = api.post(req);

        // TODO: test validations
        System.out.println(response);        
	}

}
