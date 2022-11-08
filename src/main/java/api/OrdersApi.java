package api;

import java.lang.invoke.MethodHandles;
import java.util.List;

import com.github.hiuchida.api.InfoApiWrapper;
import com.github.hiuchida.api.consts.CashmarginCode;
import com.github.hiuchida.api.consts.ProductCode;
import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.StateCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.OrdersSuccess;

/**
 * 注文約定照会API。
 */
public class OrdersApi {
	/**
	 * API実行クラス。
	 */
	private Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 認証済TOKEN。
	 */
	private String X_API_KEY;

	/**
	 * 情報API。
	 */
	private InfoApiWrapper infoApi = new InfoApiWrapper();

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public OrdersApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 注文約定照会API。
	 * 
	 * @return 注文約定照会のリスト。
	 * @throws ApiException
	 */
	public List<OrdersSuccess> get() throws ApiException {
		try {
			ProductCode product = null;
			String id = null;
			String updtime = null;
			String details = null;
			String symbol = null;
			StateCode state = null;
			SideCode side = null;
			CashmarginCode cashmargin = null;
			List<OrdersSuccess> los = invoke(product, id, updtime, details, symbol, state, side, cashmargin);
			return los;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "get", "");
			throw e;
		}
	}

	/**
	 * 注文約定照会API。
	 * 
	 * @param product    取得する商品。
	 * @param id         注文番号。
	 * @param updtime    更新日時。
	 * @param details    注文詳細抑止。
	 * @param symbol     銘柄コード。
	 * @param state      状態。
	 * @param side       売買区分。
	 * @param cashmargin 取引区分。
	 * @return 注文約定照会のリスト。
	 * @throws ApiException
	 */
	public List<OrdersSuccess> getByAll(ProductCode product, String id, String updtime, String details, String symbol,
			StateCode state, SideCode side, CashmarginCode cashmargin)
			throws ApiException {
		try {
			List<OrdersSuccess> los = invoke(product, id, updtime, details, symbol, state, side, cashmargin);
			return los;
		} catch (ApiException e) {
			String param = product + "," + id + "," + updtime + "," + details + "," + symbol + "," + state + "," + side
					+ "," + cashmargin;
			ApiErrorLog.error(e, clazz, "getByAll", param);
			throw e;
		}
	}

	/**
	 * 注文約定照会API。
	 * 
	 * @param product    取得する商品。
	 * @param id         注文番号。
	 * @param updtime    更新日時。
	 * @param details    注文詳細抑止。
	 * @param symbol     銘柄コード。
	 * @param state      状態。
	 * @param side       売買区分。
	 * @param cashmargin 取引区分。
	 * @return 注文約定照会のリスト。
	 * @throws ApiException
	 */
	private List<OrdersSuccess> invoke(ProductCode product, String id, String updtime, String details, String symbol,
			StateCode state, SideCode side, CashmarginCode cashmargin) throws ApiException {
		try {
			List<OrdersSuccess> los = infoApi.ordersGet(X_API_KEY, product, id, updtime, details, symbol, state, side,
					cashmargin);
			return los;
		} finally {
			try {
				Thread.sleep(120); // 8.3req/sec
			} catch (Exception e) {
			}
		}
	}

}
