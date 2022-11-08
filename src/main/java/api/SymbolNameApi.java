package api;

import java.lang.invoke.MethodHandles;

import com.github.hiuchida.api.InfoApiWrapper;
import com.github.hiuchida.api.consts.FutureCode;
import com.github.hiuchida.api.consts.PutOrCallCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.SymbolNameSuccess;

/**
 * 銘柄コード取得API。
 */
public class SymbolNameApi {
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
	public SymbolNameApi(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
	}

	/**
	 * 先物銘柄コード取得API。
	 * 
	 * @param futureCode 先物コード。
	 * @param derivMonth 限月（yyyyMM形式）。
	 * @return 銘柄コード。
	 * @throws ApiException
	 */
	public SymbolNameSuccess getFuture(FutureCode futureCode, int derivMonth) throws ApiException {
		try {
			SymbolNameSuccess sns = invokeFuture(derivMonth, futureCode);
			return sns;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "getFuture", futureCode + "," + derivMonth);
			throw e;
		}
	}

	/**
	 * 先物銘柄コード取得API。
	 * 
	 * @param derivMonth 限月（yyyyMM形式）。
	 * @param futureCode 先物コード。
	 * @return 銘柄コード。
	 * @throws ApiException
	 */
	private SymbolNameSuccess invokeFuture(int derivMonth, FutureCode futureCode) throws ApiException {
		try {
			SymbolNameSuccess sns = infoApi.symbolnameFutureGet(X_API_KEY, derivMonth, futureCode);
			return sns;
		} finally {
			try {
				Thread.sleep(120); // 8.3req/sec
			} catch (Exception e) {
			}
		}
	}

	/**
	 * オプション銘柄コード取得API。
	 * 
	 * @param derivMonth  限月（yyyyMM形式）。
	 * @param putOrCall   コール or プット。
	 * @param strikePrice 権利行使価格。
	 * @return 銘柄コード。
	 * @throws ApiException
	 */
	public SymbolNameSuccess getOption(int derivMonth, PutOrCallCode putOrCall, int strikePrice) throws ApiException {
		try {
			SymbolNameSuccess sns = invokeOption(derivMonth, putOrCall, strikePrice);
			return sns;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "getOption", derivMonth + "," + putOrCall + "," + strikePrice);
			throw e;
		}
	}

	/**
	 * オプション銘柄コード取得API。
	 * 
	 * @param derivMonth  限月（yyyyMM形式）。
	 * @param putOrCall   コール or プット。
	 * @param strikePrice 権利行使価格。
	 * @return 銘柄コード。
	 * @throws ApiException
	 */
	private SymbolNameSuccess invokeOption(int derivMonth, PutOrCallCode putOrCall, int strikePrice) throws ApiException {
		try {
			SymbolNameSuccess sns = infoApi.symbolnameOptionGet(X_API_KEY, derivMonth, putOrCall, strikePrice);
			return sns;
		} finally {
			try {
				Thread.sleep(120); // 8.3req/sec
			} catch (Exception e) {
			}
		}
	}

}
