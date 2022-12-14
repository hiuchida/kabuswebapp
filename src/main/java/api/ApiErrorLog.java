package api;

import java.io.IOException;
import java.io.PrintWriter;

import io.swagger.client.ApiException;
import util.AppCommon;
import util.DateTimeUtil;
import util.FileUtil;

/**
 * APIエラーログを管理する。
 */
public class ApiErrorLog extends AppCommon {
	/**
	 * APIエラーログのファイルパス。存在しなければ生成される。
	 */
	private static final String LOG_FILEPATH = SERVER_DIR_PATH + "ApiError.log";

	/**
	 * 実行アプリクラス。
	 */
	private static Class<?> clazz;

	/**
	 * モジュールバージョン情報。
	 */
	private static String version;

	/**
	 * 初期化する。
	 * 
	 * @param clazz   実行アプリクラス。
	 * @param version モジュールバージョン情報。
	 */
	public static void init(Class<?> clazz, String version) {
		ApiErrorLog.clazz = clazz;
		ApiErrorLog.version = version;
		ApiErrorLog.printVersion();
	}

	/**
	 * モジュールバージョン情報を表示する。
	 */
	public static void printVersion() {
		String now = DateTimeUtil.nowToString();
		System.out.println("--- " + now + " " + clazz.getName() + " " + version + " ---");
	}

	/**
	 * エラーログを出力する。
	 * 
	 * @param err    発生した例外。
	 * @param clazz2 API実行クラス。
	 * @param method メソッド名。
	 * @param param  パラメータ値。
	 */
	public static void error(ApiException err, Class<?> clazz2, String method, String param) {
		try (PrintWriter pw = FileUtil.writer(LOG_FILEPATH, FileUtil.UTF8, true)) {
			FileUtil.printLogLine(pw,
					err.getResponseBody() + " " + clazz.getName() + "/" + clazz2.getName() + "/" + method + "/" + param);
			err.printStackTrace(pw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ApiErrorLog() {
	}

}
