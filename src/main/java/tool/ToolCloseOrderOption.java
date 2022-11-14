package tool;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.github.hiuchida.api.consts.ClosePositionOrderCode;
import com.github.hiuchida.api.consts.SideCode;

import api.SendoderOptionApi;
import api.consts.deliv.ExchangeDCode;
import api.consts.deliv.FrontOrderTypeDCode;
import api.consts.deliv.TimeInForceCode;
import api.consts.deliv.TradeTypeCode;
import api.model.SendOrderOptionRequestWrapper;
import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import io.swagger.client.model.RequestSendOrderDerivOption;
import logic.LockedAuthorizedTokenLogic;
import util.AppCommon;
import util.StdoutLog;
import util.StringUtil;

/**
 * OPを返済注文するツール。
 */
public class ToolCloseOrderOption extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * OPを返済注文する。
	 */
	public static void main(String[] args) throws ApiException {
//		init(clazz);
		String symbol = args[0];
		String param1 = args[1];
		SideCode side = null;
		if (param1.endsWith("L")) {
			side = SideCode.買;
		} else if (param1.endsWith("S")) {
			side = SideCode.売;
		} else {
			throw new IllegalArgumentException("side is unknown. param1=" + param1);
		}
		double price = StringUtil.parseDouble(param1.substring(0, param1.length() - 1));
		int qty = 1;
		if (args.length > 3) {
			qty = StringUtil.parseInt(args[2]);
		}
		int expire = 0;
		if (args.length > 4) {
			expire = StringUtil.parseInt(args[3]);
		}
		try (LockedAuthorizedTokenLogic login = new LockedAuthorizedTokenLogic()) {
			String X_API_KEY = login.getApiKey();
			new ToolCloseOrderOption(X_API_KEY).execute(symbol, side, qty, price, expire);
		}
	}

	/**
	 * 注文発注（OP）API。
	 */
	private SendoderOptionApi sendoderOptionApi;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolCloseOrderOption(String X_API_KEY) {
		this.sendoderOptionApi = new SendoderOptionApi(X_API_KEY);
	}

	/**
	 * OPを返済注文する。
	 * 
	 * @param symbol 銘柄コード。
	 * @param side   売買区分。
	 * @param qty    注文数量。
	 * @param price  注文価格。
	 * @param expire 注文有効期限。
	 * @return stdoutのリスト。
	 * @throws ApiException
	 */
	public List<String> execute(String symbol, SideCode side, int qty, double price, int expire) throws ApiException {
		StdoutLog.println(clazz, "execute()", "symbol=" + symbol);
		StdoutLog.println(clazz, "execute()", "side=" + side + ", qty=" + qty + ", price=" + price + ", expire=" + expire);
		List<String> lines = new ArrayList<>();

		SendOrderOptionRequestWrapper req = new SendOrderOptionRequestWrapper();
		req.setSymbol(symbol);
		req.setExchange(ExchangeDCode.日通し);
		req.setTradeType(TradeTypeCode.返済);
		req.setTimeInForce(TimeInForceCode.FAS);
		req.setSide(side);
		req.setQty(qty);
		req.setClosePositionOrder(ClosePositionOrderCode.日付_古い順_損益_高い順);
		req.setFrontOrderType(FrontOrderTypeDCode.指値);
		req.setPrice(price);
		req.setExpireDay(expire);

		OrderSuccess os = sendoderOptionApi.post(req);
		lines.add(os.toString());
		return lines;
	}

	/**
	 * OPを返済注文する。
	 * 
	 * @param symbol 銘柄コード。
	 * @param side   売買区分。
	 * @param qty    注文数量。
	 * @param price  注文価格。
	 * @param expire 注文有効期限。
	 * @return stdoutのリスト。
	 * @throws ApiException 
	 */
	public List<String> executeV1(String symbol, SideCode side, int qty, double price, int expire) throws ApiException {
		StdoutLog.println(clazz, "execute()", "symbol=" + symbol);
		StdoutLog.println(clazz, "execute()", "side=" + side + ", qty=" + qty + ", price=" + price + ", expire=" + expire);
		List<String> lines = new ArrayList<>();

		RequestSendOrderDerivOption body = new RequestSendOrderDerivOption();
		body.setSymbol(symbol);
		body.setExchange(ExchangeDCode.日通し.intValue());
		body.setTradeType(TradeTypeCode.返済.intValue());
		body.setTimeInForce(TimeInForceCode.FAS.intValue());
		body.setSide(side.toString());
		body.setQty(qty);
		body.setClosePositionOrder(ClosePositionOrderCode.日付_古い順_損益_高い順.intValue());
		body.setFrontOrderType(FrontOrderTypeDCode.指値.intValue());
		body.setPrice(price);
		body.setExpireDay(expire);

		OrderSuccess os = sendoderOptionApi.post(body);
		lines.add(os.toString());
		return lines;
	}

}
