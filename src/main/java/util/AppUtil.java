package util;

import com.github.hiuchida.api.consts.UnderOverCode;

/**
 * アプリ向けユーティリティクラス。
 */
public class AppUtil {

	/**
	 * インデックス番号から2桁の文字列を生成する。
	 * 
	 * @param idx インデックス番号。
	 * @return 文字列。
	 */
	public static String index(int idx) {
		return String.format("%02d", idx);
	}

	/**
	 * 市場名を取得する。
	 * 
	 * @param exchange 市場コード(Exchange)。
	 * @return 市場名。
	 */
	public static String exchangeStr(int exchange) {
		switch (exchange) {
		case 2:
			return "日通し";
		case 23:
			return " 日中 ";
		case 24:
			return " 夜間 ";
		default:
			throw new RuntimeException();
		}
	}

	/**
	 * 数量の符号を取得する。
	 * 
	 * @param side 売買区分(Side)。
	 * @return 符号
	 */
	public static int sign(String side) {
		switch (side) {
		case "1":
			return -1;
		case "2":
			return 1;
		default:
			throw new RuntimeException();
		}
	}

	/**
	 * ポジション文字列(L or S)を取得する。
	 * 
	 * @param side 売買区分(Side)。
	 * @return ポジション(L or S)。
	 */
	public static String sideStr(String side) {
		switch (side) {
		case "1":
			return "S";
		case "2":
			return "L";
		default:
			throw new RuntimeException();
		}
	}

	/**
	 * ポジション(1 or 2)を取得する。
	 * 
	 * @param side ポジション(L or S)。
	 * @return 売買区分(Side)。
	 */
	public static String sideCode(String side) {
		switch (side) {
		case "S":
			return "1";
		case "L":
			return "2";
		default:
			throw new RuntimeException();
		}
	}

	/**
	 * 反対売買を取得する。
	 * 
	 * @param side 売買区分(Side)。
	 * @return 反対の売買区分(1 or 2)。
	 */
	public static String sideReturn(String side) {
		switch (side) {
		case "1":
			return "2";
		case "2":
			return "1";
		default:
			throw new RuntimeException();
		}
	}
	
	/**
	 * 以上／以下を取得する。
	 * 
	 * @param side 売買区分(Side)。
	 * @return 以上／以下(1 or 2)。
	 */
	public static int underOver(String side) {
		switch (side) {
		case "1":
			return UnderOverCode.以下.intValue();
		case "2":
			return UnderOverCode.以上.intValue();
		default:
			throw new RuntimeException();
		}
	}

}
