package api;

import java.lang.invoke.MethodHandles;
import java.util.List;

import api.consts.ProductCode;
import io.swagger.client.ApiException;
import io.swagger.client.api.InfoApi;
import io.swagger.client.model.PositionsSuccess;

/**
 * 残高照会API。
 */
public class PositionsApi {
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
	private InfoApi infoApi = new InfoApi();

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public PositionsApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 残高照会API。
	 * 
	 * @return 残高照会のリスト。
	 * @throws ApiException
	 */
	public List<PositionsSuccess> get() throws ApiException {
		try {
			String product = null;
			String symbol = null;
			String side = null;
			String addinfo = null;
			List<PositionsSuccess> lps = invoke(product, symbol, side, addinfo);
			return lps;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "get", "");
			throw e;
		}
	}

	/**
	 * 残高照会API。
	 * 
	 * @param product 取得する商品。
	 * @return 残高照会のリスト。
	 * @throws ApiException
	 */
	public List<PositionsSuccess> getByProduct(ProductCode product) throws ApiException {
		try {
			String productStr = (product != null) ? product.toString() : null;
			String symbol = null;
			String side = null;
			String addinfo = null;
			List<PositionsSuccess> lps = invoke(productStr, symbol, side, addinfo);
			return lps;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "getByProduct", "" + product);
			throw e;
		}
	}

	/**
	 * 残高照会API。
	 * 
	 * @param symbol 銘柄コード。
	 * @return 残高照会のリスト。
	 * @throws ApiException
	 */
	public List<PositionsSuccess> getBySymbol(String symbol) throws ApiException {
		try {
			String product = null;
			String side = null;
			String addinfo = null;
			List<PositionsSuccess> lps = invoke(product, symbol, side, addinfo);
			return lps;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "getBySymbol", symbol);
			throw e;
		}
	}

	/**
	 * 残高照会API。
	 * 
	 * @param product 取得する商品。
	 * @param symbol  銘柄コード。
	 * @param side    売買区分。
	 * @param addinfo 追加情報出力フラグ（未指定時：true）。
	 * @return 残高照会のリスト。
	 * @throws ApiException
	 */
	private List<PositionsSuccess> invoke(String product, String symbol, String side, String addinfo)
			throws ApiException {
		try {
			List<PositionsSuccess> lps = infoApi.positionsGet(X_API_KEY, product, symbol, side, addinfo);
			return lps;
		} finally {
			try {
				Thread.sleep(120); // 8.3req/sec
			} catch (Exception e) {
			}
		}
	}

}
