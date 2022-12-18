package api;

import java.lang.invoke.MethodHandles;

import com.github.hiuchida.api.AuthApiWrapper;

import io.swagger.client.ApiException;
import io.swagger.client.model.TokenSuccess;

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
	private AuthApiWrapper authApi = new AuthApiWrapper();

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
			TokenSuccess ts = invoke(apiPassword);
			return ts;
		} catch (ApiException e) {
			ApiErrorLog.error(e, clazz, "post", apiPassword);
			throw e;
		}
	}

	/**
	 * トークン発行API。
	 * 
	 * @param apiPassword APIパスワード。
	 * @return トークン発行。
	 * @throws ApiException
	 */
	private TokenSuccess invoke(String apiPassword) throws ApiException {
		try {
			TokenSuccess ts = authApi.tokenPost(apiPassword);
			return ts;
		} finally {
		}
	}

}
