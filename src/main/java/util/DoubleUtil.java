package util;

/**
 * Doubleに関するユーティリティクラス。
 */
public class DoubleUtil {

	/**
	 * nullチェック付きのDoubleインスタンスをdouble値に変換する。
	 * 
	 * @param o Doubleインスタンス。
	 * @return double値。nullの場合は0.0。
	 */
	public static double doubleValue(Double o) {
		double v = 0.0;
		if (o != null) {
			v = o.doubleValue();
		}
		return v;
	}

	/**
	 * nullチェック付きのDoubleインスタンスをint値に変換する。
	 * 
	 * @param o Doubleインスタンス。
	 * @return int値。nullの場合は0。
	 */
	public static int intValue(Double o) {
		int v = 0;
		if (o != null) {
			v = o.intValue();
		}
		return v;
	}

	private DoubleUtil() {
	}

}
