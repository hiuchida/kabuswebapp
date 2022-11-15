package api;

import java.lang.invoke.MethodHandles;

import com.github.hiuchida.api.InfoApiWrapper;
import com.github.hiuchida.api.consts.ExchangeCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.BoardSuccess;
import util.DoubleUtil;

/**
 * 時価情報・板情報API。
 */
public class BoardApi {
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
	public BoardApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 時価情報・板情報APIの現値。
	 * 
	 * @param symbol 銘柄コード(Symbol)。
	 * @param ec     市場コード。
	 * @return 現値。
	 * @throws ApiException
	 */
	public int getCurPrice(String symbol, ExchangeCode ec) throws ApiException {
		BoardSuccess bs = this.get(symbol, ec);
		int curPrice = DoubleUtil.intValue(bs.getCurrentPrice());
		return curPrice;
	}

	/**
	 * 時価情報・板情報API。
	 * 
	 * @param symbol 銘柄コード(Symbol)。
	 * @param ec     市場コード。
	 * @return 時価情報・板情報。
	 * @throws ApiException
	 */
	public BoardSuccess get(String symbol, ExchangeCode ec) throws ApiException {
		try {
			BoardSuccess bs = invoke(symbol, ec);
			return bs;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "get", symbol);
			throw e;
		}
	}

	/**
	 * 時価情報・板情報API。
	 * 
	 * @param symbol 銘柄コード(Symbol)。
	 * @param ec     市場コード。
	 * @return 時価情報・板情報。
	 * @throws ApiException
	 */
	private BoardSuccess invoke(String symbol, ExchangeCode ec) throws ApiException {
		try {
			BoardSuccess bs = infoApi.boardGet(X_API_KEY, symbol, ec);
			return bs;
		} finally {
			try {
				Thread.sleep(120); // 8.3req/sec
			} catch (Exception e) {
			}
		}
	}

}
