package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.RequestToken;
import io.swagger.client.model.TokenSuccess;

/**
 * 認証済TOKENを管理する。
 */
public class AuthorizedToken {
	/**
	 * シングルトンインスタンス。
	 */
	private static AuthorizedToken singleton = new AuthorizedToken();

	/**
	 * シングルトンインスタンスを取得する。
	 * @return シングルトンインスタンス。
	 */
	public static AuthorizedToken getInstance() {
		return singleton;
	}

	/**
	 * 認証済TOKENを取得する。
	 * @return 認証済TOKEN。
	 * @throws ApiException
	 */
	public static String getToken() throws ApiException {
		return singleton.initToken();
	}

	/**
	 * 認証済TOKEN。
	 */
	private String token = null;
	
	private AuthorizedToken() {
	}

	/**
	 * メモリ上のTOKENが存在しなければ認証APIを呼び出し、認証済TOKENを取得する。
	 * @return 認証済TOKEN。
	 * @throws ApiException
	 */
	private String initToken() throws ApiException {
		if (token == null) {
			AuthApi authApi = new AuthApi();
			RequestToken body = new RequestToken();
	        body.setApIPassword(TestConsts.API_PASSWORD);
	        TokenSuccess response = authApi.tokenPost(body);
	        System.out.println(response);
	        token = response.getToken();
		}
		return token;
	}

}
