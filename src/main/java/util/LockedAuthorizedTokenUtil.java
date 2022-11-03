package util;

import java.lang.invoke.MethodHandles;

import api.ApisoftlimitApi;
import api.TokenApi;
import io.swagger.client.ApiException;
import logic.FileLockLogic;

/**
 * 認証済TOKENをファイルロック管理する。
 */
public class LockedAuthorizedTokenUtil extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 1行目にAPIパスワードを保存したファイルパス。事前に準備する。
	 */
	private static final String PWD_FILEPATH = HOME_PATH + "LockedAuthorizedToken.pwd";
	/**
	 * 1行目に認証済TOKENを保存したファイルパス。存在しなければ生成される。
	 */
	private static final String TXT_FILEPATH = HOME_PATH + "LockedAuthorizedToken.txt";
	/**
	 * ファイルロック管理用0バイトのファイルパス。存在しなければ生成される。
	 */
	private static final String LOCK_FILEPATH = HOME_PATH + "LockedAuthorizedToken.lock";
	/**
	 * ファイルロック管理ログのファイルパス。存在しなければ生成される。
	 */
	private static final String LOG_FILEPATH = SERVER_DIR_PATH + "LockedAuthorizedToken.log";

	/**
	 * シングルトンインスタンス。
	 */
	private static LockedAuthorizedTokenUtil singleton = new LockedAuthorizedTokenUtil();
	/**
	 * 認証済TOKENロックを管理する。
	 */
	private static FileLockLogic fileLockLogic = new FileLockLogic(LOCK_FILEPATH);

	/**
	 * シングルトンインスタンスを取得する。
	 * 
	 * @return シングルトンインスタンス。
	 */
	public static LockedAuthorizedTokenUtil getInstance() {
		return singleton;
	}

	/**
	 * ファイルロックを取得し、認証済TOKENを取得する。
	 * 
	 * @return 認証済TOKEN。
	 * @throws ApiException
	 */
	public static String lockToken() throws ApiException {
		fileLockLogic.lockFile();
		try {
			return singleton.initToken();
		} catch (ApiException e) {
			fileLockLogic.unlockFile();
			throw e;
		}
	}

	/**
	 * ファイルロックを解放する。
	 */
	public static void unlockToken() {
		fileLockLogic.unlockFile();
	}

	/**
	 * ファイルロックせず、認証済TOKENを取得する。有効かどうか保証しない。
	 * 
	 * @return 認証済TOKEN。
	 */
	public static String peekToken() {
		String token = FileUtil.readOneLine(TXT_FILEPATH);
		FileUtil.printLog(LOG_FILEPATH, "initToken", "peek  token=" + token);
		return token;
	}

	/**
	 * トークン発行API。
	 */
	private TokenApi tokenApi;

	/**
	 * コンストラクタ。
	 */
	private LockedAuthorizedTokenUtil() {
		this.tokenApi = new TokenApi();
	}

	/**
	 * ファイルからTOKENを取得し、有効かどうか調べ、無効なら認証APIを呼び出し、認証済TOKENを取得する。
	 * 
	 * @return 認証済TOKEN。
	 * @throws ApiException
	 */
	private String initToken() throws ApiException {
		String token = FileUtil.readOneLine(TXT_FILEPATH);
		boolean bAvailable = false;
		if (token != null && token.length() > 0) {
			bAvailable = ping(token);
		}
		String msg = "check token=" + token + ", bAvailable=" + bAvailable;
		StdoutLog.println(clazz, "initToken()", msg);
		FileUtil.printLog(LOG_FILEPATH, "initToken", msg);
		if (!bAvailable) {
			try {
				token = auth();
				msg = "save  token=" + token;
				System.out.println("  > auth " + msg);
				FileUtil.printLog(LOG_FILEPATH, "initToken", msg);
				FileUtil.writeOneLine(TXT_FILEPATH, token);
			} catch (ApiException e) {
				msg = "error token=" + token;
				System.out.println("  > auth " + msg);
				FileUtil.printLog(LOG_FILEPATH, "initToken", msg);
				FileUtil.writeOneLine(TXT_FILEPATH, "");
				throw e;
			}
		}
		return token;
	}

	/**
	 * 指定したTOKENが有効かどうか調べる。
	 * 
	 * @param token 認証済TOKEN。
	 * @return true:有効、false:無効。
	 */
	private boolean ping(String token) {
		String X_API_KEY = token;
		try {
			ApisoftlimitApi apisoftlimitApi = new ApisoftlimitApi(X_API_KEY);
			apisoftlimitApi.get();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	/**
	 * 認証APIを呼び出し、認証済TOKENを取得する。
	 * 
	 * @return 認証済TOKEN。
	 * @throws ApiException
	 */
	private String auth() throws ApiException {
		String pwd = FileUtil.readOneLine(PWD_FILEPATH);
		return tokenApi.postToken(pwd);
	}

}
