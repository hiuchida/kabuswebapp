package tool;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.deliv.ExchangeDCode;
import com.github.hiuchida.api.consts.deliv.FrontOrderTypeDCode;
import com.github.hiuchida.api.consts.deliv.TimeInForceCode;
import com.github.hiuchida.api.consts.deliv.TradeTypeCode;
import com.github.hiuchida.api.model.SendOrderFutureRequestWrapper;

import api.SendoderFutureApi;
import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import io.swagger.client.model.RequestSendOrderDerivFuture;
import logic.LockedAuthorizedTokenLogic;
import util.AppCommon;
import util.GlobalConfigUtil;
import util.StdoutLog;
import util.StringUtil;

/**
 * 先物を新規注文するツール。
 */
public class ToolEntryOrderFuture extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 銘柄コード(Symbol)。 
	 */
	private static final String SYMBOL = GlobalConfigUtil.get("Symbol"); // "167060019";

	/**
	 * 先物を新規注文する。
	 */
	public static void main(String[] args) throws ApiException {
//		init(clazz);
		String param1 = args[0];
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
		if (args.length > 2) {
			qty = StringUtil.parseInt(args[1]);
		}
		int expire = 0;
		if (args.length > 3) {
			expire = StringUtil.parseInt(args[2]);
		}
		try (LockedAuthorizedTokenLogic login = new LockedAuthorizedTokenLogic()) {
			String X_API_KEY = login.getApiKey();
			new ToolEntryOrderFuture(X_API_KEY).execute(side, qty, price, expire);
		}
	}

	/**
	 * 注文発注（先物）API。
	 */
	private SendoderFutureApi sendoderFutureApi;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolEntryOrderFuture(String X_API_KEY) {
		this.sendoderFutureApi = new SendoderFutureApi(X_API_KEY);
	}

	/**
	 * 先物を新規注文する。
	 * 
	 * @param side   売買区分。
	 * @param qty    注文数量。
	 * @param price  注文価格。
	 * @param expire 注文有効期限。
	 * @return stdoutのリスト。
	 * @throws ApiException 
	 */
	public List<String> execute(SideCode side, int qty, double price, int expire) throws ApiException {
		StdoutLog.println(clazz, "execute()", "SYMBOL=" + SYMBOL);
		StdoutLog.println(clazz, "execute()", "side=" + side + ", qty=" + qty + ", price=" + price + ", expire=" + expire);
		List<String> lines = new ArrayList<>();

		SendOrderFutureRequestWrapper req = new SendOrderFutureRequestWrapper();
		req.setSymbol(SYMBOL);
		req.setExchange(ExchangeDCode.日通し);
		req.setTradeType(TradeTypeCode.新規);
		req.setTimeInForce(TimeInForceCode.FAS);
		req.setSide(side);
		req.setQty(qty);
		req.setFrontOrderType(FrontOrderTypeDCode.指値);
		req.setPrice(price);
		req.setExpireDay(expire);

		OrderSuccess os = sendoderFutureApi.post(req);
		lines.add(os.toString());
		return lines;
	}

	/**
	 * 先物を新規注文する。
	 * 
	 * @param side   売買区分。
	 * @param qty    注文数量。
	 * @param price  注文価格。
	 * @param expire 注文有効期限。
	 * @return stdoutのリスト。
	 * @throws ApiException 
	 */
	public List<String> executeV1(SideCode side, int qty, double price, int expire) throws ApiException {
		StdoutLog.println(clazz, "execute()", "SYMBOL=" + SYMBOL);
		StdoutLog.println(clazz, "execute()", "side=" + side + ", qty=" + qty + ", price=" + price + ", expire=" + expire);
		List<String> lines = new ArrayList<>();

		RequestSendOrderDerivFuture body = new RequestSendOrderDerivFuture();
		body.setSymbol(SYMBOL);
		body.setExchange(ExchangeDCode.日通し.intValue());
		body.setTradeType(TradeTypeCode.新規.intValue());
		body.setTimeInForce(TimeInForceCode.FAS.intValue());
		body.setSide(side.toString());
		body.setQty(qty);
		body.setFrontOrderType(FrontOrderTypeDCode.指値.intValue());
		body.setPrice(price);
		body.setExpireDay(expire);

		OrderSuccess os = sendoderFutureApi.post(body);
		lines.add(os.toString());
		return lines;
	}

}
