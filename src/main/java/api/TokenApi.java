package api;

import java.lang.invoke.MethodHandles;

import io.swagger.client.ApiException;
import io.swagger.client.api.AuthApi;
import io.swagger.client.model.RequestToken;
import io.swagger.client.model.TokenSuccess;
import util.WarnLog;

/**
 * トークン発行API。
 */
public class TokenApi {
	/**
	 * API実行クラス。
	 */
	private Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 認証API。
	 */
	private AuthApi authApi = new AuthApi();

	/**
	 * コンストラクタ。
	 */
	public TokenApi() {
	}

	/**
	 * トークン発行API。
	 * 
	 * @param apiPassword APIパスワード。
	 * @return APIトークン。
	 * @throws ApiException
	 */
	public String postToken(String apiPassword) throws ApiException {
		TokenSuccess ts = this.post(apiPassword);
		return ts.getToken();
	}

	/**
	 * トークン発行API。
	 * 
	 * @param apiPassword APIパスワード。
	 * @return トークン発行。
	 * @throws ApiException
	 */
	public TokenSuccess post(String apiPassword) throws ApiException {
		try {
			RequestToken body = new RequestToken();
			body.setApIPassword(apiPassword);
			TokenSuccess ts = invoke(body);
			return ts;
		} catch (ApiException e) {
			WarnLog.warn("Login Error. " + e.getResponseBody(), clazz, "post", apiPassword);
//			ApiErrorLog.error(e, clazz, "post", apiPassword);
			throw e;
		}
	}

	/**
	 * トークン発行API。
	 * 
	 * @param body RequestToken。
	 * @return トークン発行。
	 * @throws ApiException
	 */
	private TokenSuccess invoke(RequestToken body) throws ApiException {
		try {
			TokenSuccess ts = authApi.tokenPost(body);
			String param = body.toString();
			debug(ts, param);
			return ts;
		} finally {
		}
	}

	/**
	 * デバッグログを出力する。
	 * 
	 * @param ts    トークン発行。
	 * @param param パラメータ値。
	 */
	private void debug(TokenSuccess ts, String param) {
		ApiDebugLog.debug(ts.toString(), clazz, "invoke", param);
	}

}
