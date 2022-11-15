package api;

import java.lang.invoke.MethodHandles;

import com.github.hiuchida.api.OrderApiWrapper;
import com.github.hiuchida.api.model.SendOrderFutureRequestWrapper;

import io.swagger.client.ApiException;
import io.swagger.client.api.OrderApi;
import io.swagger.client.model.OrderSuccess;
import io.swagger.client.model.RequestSendOrderDerivFuture;
import util.SendOrderConfigUtil;

/**
 * 注文発注（先物）API。
 */
public class SendoderFutureApi {
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
	private OrderApi orderApiOld = new OrderApi();

	/**
	 * 注文API。
	 */
	private OrderApiWrapper orderApi = new OrderApiWrapper();

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public SendoderFutureApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 注文発注（先物）API。
	 * 
	 * @param body 注文発注（先物）情報。
	 * @return 注文発注（先物）情報。
	 * @throws ApiException
	 */
	public OrderSuccess post(RequestSendOrderDerivFuture body) throws ApiException {
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
	 * 注文発注（先物）API。
	 * 
	 * @param req 注文発注（先物）情報。
	 * @return 注文発注（先物）情報。
	 * @throws ApiException
	 */
	public OrderSuccess post(SendOrderFutureRequestWrapper req) throws ApiException {
		try {
			OrderSuccess os = invoke(req);
			return os;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "post", toString(req));
			throw e;
		}
	}

	/**
	 * 注文発注（先物）API。
	 * 
	 * @param body 注文発注（先物）情報。
	 * @return 注文発注（先物）情報。
	 * @throws ApiException
	 */
	private OrderSuccess invoke(RequestSendOrderDerivFuture body) throws ApiException {
		try {
			OrderSuccess os = orderApiOld.sendoderFuturePost(body, X_API_KEY);
			return os;
		} finally {
			try {
				Thread.sleep(240); // 4.2req/sec
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 注文発注（先物）API。
	 * 
	 * @param req 注文発注（先物）情報。
	 * @return 注文発注（先物）情報。
	 * @throws ApiException
	 */
	private OrderSuccess invoke(SendOrderFutureRequestWrapper req) throws ApiException {
		try {
			OrderSuccess os = orderApi.sendoderFuturePost(req, TRADE_PASSWORD, X_API_KEY);
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
	 * @param body 注文発注（先物）情報。
	 * @return 文字列表現。
	 */
	private String toString(RequestSendOrderDerivFuture body) {
		return body.toString();
	}

	/**
	 * 文字列表現を取得する。
	 * 
	 * @param req 注文発注（先物）情報。
	 * @return 文字列表現。
	 */
	private String toString(SendOrderFutureRequestWrapper req) {
		return req.toString();
	}

}
