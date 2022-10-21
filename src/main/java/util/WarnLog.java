package util;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 警告ログを管理する。
 */
public class WarnLog {
	/**
	 * 基準パス。
	 */
	private static final String SERVER_DIRPATH = "/tmp/server/";
	/**
	 * 警告ログのファイルパス。存在しなければ生成される。
	 */
	private static final String LOG_FILEPATH = SERVER_DIRPATH + "Warn.log";

	/**
	 * 実行アプリクラス。
	 */
	private static Class<?> clazz;

	/**
	 * モジュールバージョン情報。
	 */
//	private static String version;

	/**
	 * 初期化する。
	 * 
	 * @param clazz   実行アプリクラス。
	 * @param version モジュールバージョン情報。
	 */
	public static void init(Class<?> clazz, String version) {
		WarnLog.clazz = clazz;
//		WarnLog.version = version;
//		WarnLog.printVersion();
	}

	/**
	 * モジュールバージョン情報を表示する。
	 */
//	public static void printVersion() {
//		String now = DateTimeUtil.nowToString();
//		System.out.println("--- " + now + " " + clazz.getName() + " " + version + " ---");
//	}

	/**
	 * 警告ログを出力する。
	 * 
	 * @param msg    メッセージ。
	 * @param clazz2 API実行クラス。
	 * @param method メソッド名。
	 * @param param  パラメータ値。
	 */
	public static void warn(String msg, Class<?> clazz2, String method, String param) {
		try (PrintWriter pw = FileUtil.writer(LOG_FILEPATH, FileUtil.UTF8, true)) {
			FileUtil.printLogLine(pw,
					msg + " " + clazz.getName() + "/" + clazz2.getName() + "/" + method + "/" + param);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private WarnLog() {
	}

}
