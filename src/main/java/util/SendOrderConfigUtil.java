package util;

import java.lang.invoke.MethodHandles;

/**
 * 注文発注に関する設定情報を管理する。
 */
public class SendOrderConfigUtil extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 1行目に注文パスワードを保存したファイルパス。事前に準備する。
	 */
	private static final String PWD_FILEPATH = HOME_PATH + "SendOrderConfig.pwd";

	/**
	 * シングルトンインスタンス。
	 */
	private static SendOrderConfigUtil singleton = new SendOrderConfigUtil();

	/**
	 * シングルトンインスタンスを取得する。
	 * @return シングルトンインスタンス。
	 */
	public static SendOrderConfigUtil getInstance() {
		return singleton;
	}

	/**
	 * 注文パスワードを取得する。
	 * @return 注文パスワード。
	 */
	public static String getPassword() {
		return singleton.initPassword();
	}

	private SendOrderConfigUtil() {
	}

	/**
	 * 設定ファイルを読み込んで、注文パスワードを取得する。
	 * @return 注文パスワード。
	 */
	private String initPassword() {
		String pwd = FileUtil.readOneLine(PWD_FILEPATH);
		return pwd;
	}

}
