package api;

import java.lang.invoke.MethodHandles;

import com.github.hiuchida.api.OrderApiWrapper;

import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import util.SendOrderConfigUtil;

/**
 * 注文取消API。
 */
public class CancelorderApi {
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
	private OrderApiWrapper orderApi = new OrderApiWrapper();

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public CancelorderApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 注文取消API。
	 * 
	 * @param orderId 注文番号(ID)。
	 * @return 注文取消情報。
	 * @throws ApiException
	 */
	public OrderSuccess put(String orderId) throws ApiException {
		try {
			OrderSuccess os = invoke(orderId);
			return os;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "put", orderId);
			throw e;
		}
	}

	/**
	 * 注文取消API。
	 * 
	 * @param orderId 注文番号(ID)。
	 * @return 注文取消情報。
	 * @throws ApiException
	 */
	private OrderSuccess invoke(String orderId) throws ApiException {
		try {
			OrderSuccess os = orderApi.cancelorderPut(orderId, TRADE_PASSWORD, X_API_KEY);
			return os;
		} finally {
			try {
				Thread.sleep(240); // 4.2req/sec
			} catch (Exception e) {
			}
		}
	}

}
