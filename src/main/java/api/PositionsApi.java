package api;

import java.lang.invoke.MethodHandles;
import java.util.List;

import com.github.hiuchida.api.InfoApiWrapper;
import com.github.hiuchida.api.consts.ProductCode;
import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.model.PositionsSuccessWrapper;

import io.swagger.client.ApiException;

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
	private InfoApiWrapper infoApi = new InfoApiWrapper();

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
	public List<PositionsSuccessWrapper> get() throws ApiException {
		try {
			ProductCode product = null;
			String symbol = null;
			SideCode side = null;
			String addinfo = null;
			List<PositionsSuccessWrapper> lps = invoke(product, symbol, side, addinfo);
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
	public List<PositionsSuccessWrapper> getByProduct(ProductCode product) throws ApiException {
		try {
			String symbol = null;
			SideCode side = null;
			String addinfo = null;
			List<PositionsSuccessWrapper> lps = invoke(product, symbol, side, addinfo);
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
	public List<PositionsSuccessWrapper> getBySymbol(String symbol) throws ApiException {
		try {
			ProductCode product = null;
			SideCode side = null;
			String addinfo = null;
			List<PositionsSuccessWrapper> lps = invoke(product, symbol, side, addinfo);
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
	public List<PositionsSuccessWrapper> getByAll(ProductCode product, String symbol, SideCode side, String addinfo) throws ApiException {
		try {
			List<PositionsSuccessWrapper> lps = invoke(product, symbol, side, addinfo);
			return lps;
		} catch (ApiException e) {
			String param = product + "," + symbol + "," + side + "," + addinfo;
			ApiErrorLog.error(e, clazz, "getByAll", param);
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
	private List<PositionsSuccessWrapper> invoke(ProductCode product, String symbol, SideCode side, String addinfo)
			throws ApiException {
		try {
			List<PositionsSuccessWrapper> lps = infoApi.positionsGet(X_API_KEY, product, symbol, side, addinfo);
			return lps;
		} finally {
			try {
				Thread.sleep(120); // 8.3req/sec
			} catch (Exception e) {
			}
		}
	}

}
