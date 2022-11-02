package util;

/**
 * Integerに関するユーティリティクラス。
 */
public class IntegerUtil {

	/**
	 * nullチェック付きのIntegerインスタンスをint値に変換する。
	 * 
	 * @param o Integerインスタンス。
	 * @return int値。nullの場合は0。
	 */
	public static int intValue(Integer o) {
		int v = 0;
		if (o != null) {
			v = o.intValue();
		}
		return v;
	}

	private IntegerUtil() {
	}

}
