package tool;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.github.hiuchida.api.consts.SideCode;

import api.SendoderApi;
import api.consts.ClosePositionOrderCode;
import api.consts.stock.AccountTypeCode;
import api.consts.stock.CashmarginStockCode;
import api.consts.stock.DelivTypeCode;
import api.consts.stock.ExchangeSCode;
import api.consts.stock.FrontOrderTypeSCode;
import api.consts.stock.MarginTradeTypeCode;
import api.consts.stock.SecurityTypeCode;
import api.model.SendOrderRequestWrapper;
import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import io.swagger.client.model.RequestSendOrder;
import logic.LockedAuthorizedTokenLogic;
import util.AppCommon;
import util.StdoutLog;
import util.StringUtil;

/**
 * 信用株式を返済注文するツール。
 */
public class ToolCloseOrderMargin extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 信用株式を返済注文する。
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
		int qty = 100;
		if (args.length > 3) {
			qty = StringUtil.parseInt(args[2]);
		}
		int expire = 0;
		if (args.length > 4) {
			expire = StringUtil.parseInt(args[3]);
		}
		try (LockedAuthorizedTokenLogic login = new LockedAuthorizedTokenLogic()) {
			String X_API_KEY = login.getApiKey();
			new ToolCloseOrderMargin(X_API_KEY).execute(symbol, side, qty, price, expire);
		}
	}

	/**
	 * 注文発注（株式）API。
	 */
	private SendoderApi sendoderApi;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolCloseOrderMargin(String X_API_KEY) {
		this.sendoderApi = new SendoderApi(X_API_KEY);
	}

	/**
	 * 信用株式を返済注文する。
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

		SendOrderRequestWrapper req = new SendOrderRequestWrapper();
		req.setSymbol(symbol);
		req.setExchange(ExchangeSCode.東証);
		req.setSecurityType(SecurityTypeCode.株式);
		req.setSide(side);
		req.setCashMargin(CashmarginStockCode.返済);
		req.setMarginTradeType(MarginTradeTypeCode.制度信用);
		req.setDelivType(DelivTypeCode.お預り金);
		req.setAccountType(AccountTypeCode.特定);
		req.setQty(qty);
		req.setClosePositionOrder(ClosePositionOrderCode.日付_古い順_損益_高い順);
		req.setFrontOrderType(FrontOrderTypeSCode.指値);
		req.setPrice(price);
		req.setExpireDay(expire);

		OrderSuccess os = sendoderApi.post(req);
		lines.add(os.toString());
		return lines;
	}

	/**
	 * 信用株式を返済注文する。
	 * 
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

		RequestSendOrder body = new RequestSendOrder();
		body.setSymbol(symbol);
		body.setExchange(ExchangeSCode.東証.intValue());
		body.setSecurityType(SecurityTypeCode.株式.intValue());
		body.setSide(side.toString());
		body.setCashMargin(CashmarginStockCode.返済.intValue());
		body.setMarginTradeType(MarginTradeTypeCode.制度信用.intValue());
		body.setDelivType(DelivTypeCode.お預り金.intValue());
		body.setAccountType(AccountTypeCode.特定.intValue());
		body.setQty(qty);
		body.setClosePositionOrder(ClosePositionOrderCode.日付_古い順_損益_高い順.intValue());
		body.setFrontOrderType(FrontOrderTypeSCode.指値.intValue());
		body.setPrice(price);
		body.setExpireDay(expire);

		OrderSuccess os = sendoderApi.post(body);
		lines.add(os.toString());
		return lines;
	}

}
