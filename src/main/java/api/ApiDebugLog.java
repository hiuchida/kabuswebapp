package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import util.DateUtil;
import util.FileUtil;

/**
 * APIデバッグログを管理する。
 */
public class ApiDebugLog {
	/**
	 * 基準パス。
	 */
	private static final String SERVER_DIRPATH = "/tmp/server/debug/";
	/**
	 * APIエラーログのファイルパス。存在しなければ生成される。
	 */
	private static final String LOG_FILEPATH = SERVER_DIRPATH + "ApiDebug_%s_%02d.log";

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
		ApiDebugLog.clazz = clazz;
//		ApiDebugLog.version = version;
//		ApiDebugLog.printVersion();
	}

	/**
	 * モジュールバージョン情報を表示する。
	 */
//	public static void printVersion() {
//		String now = DateTimeUtil.nowToString();
//		System.out.println("--- " + now + " " + clazz.getName() + " " + version + " ---");
//	}

	/**
	 * デバッグログを出力する。
	 * 
	 * @param msg    メッセージ。
	 * @param clazz2 API実行クラス。
	 * @param method メソッド名。
	 * @param param  パラメータ値。
	 */
	public static void debug(String msg, Class<?> clazz2, String method, String param) {
		Calendar c = Calendar.getInstance();
		String filepath = String.format(LOG_FILEPATH, DateUtil.toString(c.getTime()).replaceAll("/", ""), c.get(Calendar.HOUR_OF_DAY));
		try (PrintWriter pw = FileUtil.writer(filepath, FileUtil.UTF8, true)) {
			pw.println("-----------------------------------------------------------------------------------");
			FileUtil.printLogLine(pw, clazz.getName() + "/" + clazz2.getName() + "/" + method + "/" + param);
			pw.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----");
			pw.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ApiDebugLog() {
	}

}
