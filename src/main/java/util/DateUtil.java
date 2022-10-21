package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日付に関するユーティリティクラス。
 */
public class DateUtil {

	/**
	 * 現在の日付を文字列で取得する。
	 * 
	 * @return 日付文字列(yyyy/MM/dd)。
	 */
	public static String nowToString() {
		return toString(new Date());
	}

	/**
	 * 日付文字列から日付型を取得する。
	 * 
	 * @param s 日付文字列(yyyy/MM/dd)。
	 * @return 日付型。
	 */
	public static Date parseString(String s) {
		if (s == null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.parse(s);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日付型から日付を文字列で取得する。
	 * 
	 * @param date 日付型。
	 * @return 日付文字列(yyyy/MM/dd)。
	 */
	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date);
	}

	/**
	 * 時刻long値から日付を文字列で取得する。
	 * 
	 * @param time 時刻long値。
	 * @return 日付文字列(yyyy/MM/dd)。
	 */
	public static String toString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(time);
	}

	/**
	 * 前日の日付文字列を検索する。
	 * 
	 * @param date 今日の日付文字列(yyyy/MM/dd)。
	 * @return 前日の日付文字列(yyyy/MM/dd)。
	 */
	public static String prevDay(String date) {
		Date now = DateUtil.parseString(date);
		if (now != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			String next = DateUtil.toString(cal.getTime());
			return next;
		}
		return null;
	}

	/**
	 * 翌日の日付文字列を検索する。
	 * 
	 * @param date 今日の日付文字列(yyyy/MM/dd)。
	 * @return 翌日の日付文字列(yyyy/MM/dd)。
	 */
	public static String nextDay(String date) {
		Date now = DateUtil.parseString(date);
		if (now != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			String next = DateUtil.toString(cal.getTime());
			return next;
		}
		return null;
	}

	private DateUtil() {
	}

}
