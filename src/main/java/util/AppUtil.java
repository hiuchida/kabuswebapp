package util;

import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.UnderOverCode;
import com.github.hiuchida.api.consts.deliv.ExchangeDCode;

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
	public static String exchangeStr(ExchangeDCode exchange) {
		switch (exchange) {
		case 日通し:
			return "日通し";
		case 日中:
			return " 日中 ";
		case 夜間:
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
	public static int sign(SideCode side) {
		switch (side) {
		case 売:
			return -1;
		case 買:
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
	public static String sideStr(SideCode side) {
		switch (side) {
		case 売:
			return "S";
		case 買:
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
	public static SideCode sideCode(String side) {
		switch (side) {
		case "S":
			return SideCode.売;
		case "L":
			return SideCode.買;
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
	public static SideCode sideReturn(SideCode side) {
		switch (side) {
		case 売:
			return SideCode.買;
		case 買:
			return SideCode.売;
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
	public static UnderOverCode underOver(SideCode side) {
		switch (side) {
		case 売:
			return UnderOverCode.以下;
		case 買:
			return UnderOverCode.以上;
		default:
			throw new RuntimeException();
		}
	}

}
