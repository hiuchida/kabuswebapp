package api;

import java.lang.invoke.MethodHandles;

import api.model.SendOrderOptionRequestWrapper;
import io.swagger.client.ApiException;
import io.swagger.client.api.OrderApi;
import io.swagger.client.model.OrderSuccess;
import io.swagger.client.model.RequestSendOrderDerivOption;
import util.SendOrderConfigUtil;

/**
 * 注文発注（オプション）API。
 */
public class SendoderOptionApi {
	/**
	 * API実行クラス。
	 */
	private Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 注文パスワード。
	 */
	private static final String TRADE_PASSWORD = SendOrderConfigUtil.getPassword();

	/**
	 * 認証済TOKEN。
	 */
	private String X_API_KEY;

	/**
	 * 注文API。
	 */
	private OrderApi orderApi = new OrderApi();

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public SendoderOptionApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 注文発注（オプション）API。
	 * 
	 * @param body 注文発注（オプション）情報。
	 * @return 注文発注（オプション）情報。
	 * @throws ApiException
	 */
	public OrderSuccess post(RequestSendOrderDerivOption body) throws ApiException {
		body.setPassword(TRADE_PASSWORD);
		try {
			OrderSuccess os = invoke(body);
			return os;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "post", toString(body));
			throw e;
		}
	}

	/**
	 * 注文発注（オプション）API。
	 * 
	 * @param req 注文発注（オプション）情報。
	 * @return 注文発注（オプション）情報。
	 * @throws ApiException
	 */
	public OrderSuccess post(SendOrderOptionRequestWrapper req) throws ApiException {
		RequestSendOrderDerivOption body = req.toRequestSendOrderDerivOption();
		body.setPassword(TRADE_PASSWORD);
		try {
			OrderSuccess os = invoke(body);
			return os;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "post", toString(body));
			throw e;
		}
	}

	/**
	 * 注文発注（オプション）API。
	 * 
	 * @param body 注文発注（オプション）情報。
	 * @return 注文発注（オプション）情報。
	 * @throws ApiException
	 */
	private OrderSuccess invoke(RequestSendOrderDerivOption body) throws ApiException {
		try {
			OrderSuccess os = orderApi.sendorderOptionPost(body, X_API_KEY);
			return os;
		} finally {
			try {
				Thread.sleep(240); // 4.2req/sec
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 文字列表現を取得する。
	 * 
	 * @param body 注文発注（オプション）情報。
	 * @return 文字列表現。
	 */
	private String toString(RequestSendOrderDerivOption body) {
		return body.toString();
	}

}
