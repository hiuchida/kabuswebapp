package util;

import java.util.ArrayList;
import java.util.List;

/**
 * 文字列に関するユーティリティクラス。
 */
public class StringUtil {

	/**
	 * "("以降を除外して、数値文字列を数値に変換する。
	 * 
	 * @param s 数値文字列。
	 * @return 数値。
	 */
	public static int parseInt(String s) {
		if (s == null) {
			return 0;
		}
		int idx = s.indexOf("(");
		if (idx >= 0) {
			s = s.substring(0, idx);
		}
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * "("以降を除外して、数値文字列を数値に変換する。
	 * 
	 * @param s 数値文字列。
	 * @return 数値。
	 */
	public static long parseLong(String s) {
		if (s == null) {
			return 0;
		}
		int idx = s.indexOf("(");
		if (idx >= 0) {
			s = s.substring(0, idx);
		}
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 数値文字列を数値に変換する。
	 * 
	 * @param s 数値文字列。
	 * @return 数値。
	 */
	public static double parseDouble(String s) {
		if (s == null) {
			return 0;
		}
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 指定した文字以降を除外して、文字列を取得する。
	 * 
	 * @param s 文字列。
	 * @param d 区切り文字列。
	 * @return 文字列。
	 */
	public static String parseString(String s, String d) {
		if (s == null || d == null) {
			return s;
		}
		int idx = s.indexOf(d);
		if (idx >= 0) {
			s = s.substring(0, idx);
		}
		return s;
	}

	/**
	 * 指定した文字列から、左右2つの文字列に挟まれた文字列を取得する。
	 * 
	 * @param s  全体の文字列。
	 * @param sl 左側の文字列。
	 * @param sr 右側の文字列。
	 * @return 切り出した文字列。左右の文字列が見つからない場合はnull。
	 */
	public static String parseString(String s, String sl, String sr) {
		if (s == null || sl == null || sr == null) {
			return s;
		}
		int idxL = s.indexOf(sl);
		if (idxL < 0) {
			return null;
		}
		idxL += sl.length();
		int idxR = s.indexOf(sr, idxL);
		if (idxR < 0) {
			return null;
		}
		String val = s.substring(idxL, idxR);
		return val;
	}

	/**
	 * タブ文字で分割する。String.split()と異なり、行末の空文字列にも対応する。
	 * 
	 * @param s 文字列。
	 * @return 分割した文字列の配列。
	 */
	public static String[] splitTab(String s) {
		List<String> list = new ArrayList<>();
		while (true) {
			int idx = s.indexOf('\t');
			if (idx < 0) {
				list.add(s);
				break;
			}
			list.add(s.substring(0, idx));
			s = s.substring(idx + 1);
		}
		String[] ary = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ary[i] = list.get(i);
		}
		return ary;
	}

	/**
	 * タブ文字で2つの文字列を結合する。
	 * 
	 * @param s1 文字列1。
	 * @param s2 文字列2。
	 * @return 結合した文字列。
	 */
	public static String joinTab(String s1, String s2) {
		String val = s1 + "\t" + s2;
		return val;
	}

	/**
	 * タブ文字で結合する。
	 * 
	 * @param sa 文字列の配列。
	 * @return 結合した文字列。
	 */
	public static String joinTab(String[] sa) {
		String val = String.join("\t", sa);
		return val;
	}

	/**
	 * カンマ文字で分割する。String.split()と異なり、行末の空文字列にも対応する。
	 * 
	 * @param s 文字列。
	 * @return 分割した文字列の配列。
	 */
	public static String[] splitComma(String s) {
		List<String> list = new ArrayList<>();
		while (true) {
			int idx = s.indexOf(',');
			if (idx < 0) {
				list.add(s);
				break;
			}
			list.add(s.substring(0, idx));
			s = s.substring(idx + 1);
		}
		String[] ary = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ary[i] = list.get(i);
		}
		return ary;
	}

	/**
	 * タブ文字で2つの文字列を結合する。
	 * 
	 * @param s1 文字列1。
	 * @param s2 文字列2。
	 * @return 結合した文字列。
	 */
	public static String joinComma(String s1, String s2) {
		String val = s1 + "," + s2;
		return val;
	}

	/**
	 * タブ文字で結合する。
	 * 
	 * @param sa 文字列の配列。
	 * @return 結合した文字列。
	 */
	public static String joinComma(String[] sa) {
		String val = String.join(",", sa);
		return val;
	}

	private StringUtil() {
	}

}
