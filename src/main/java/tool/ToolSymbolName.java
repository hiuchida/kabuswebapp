package tool;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.github.hiuchida.api.consts.FutureCode;
import com.github.hiuchida.api.consts.PutOrCallCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.SymbolNameSuccess;
import logic.SymbolNameLogic;
import util.AppCommon;
import util.GlobalConfigUtil;
import util.StdoutLog;

/**
 * PUSH API受信ツールと同じ銘柄コードのキャッシュを登録するツール。
 */
public class ToolSymbolName extends AppCommon {
	/**
	 * API実行クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 銘柄登録する先物miniの開始年月(yyyymm)。
	 */
	public static final int MINI_START = GlobalConfigUtil.getInt("MiniStart", -1);
	/**
	 * 銘柄登録する先物miniの終了年月(yyyymm)。開始年月から毎月登録される。
	 */
	public static final int MINI_END = GlobalConfigUtil.getInt("MiniEnd", -1);
	/**
	 * 銘柄登録する先物ラージの開始年月(yyyymm)。
	 */
	public static final int LARGE_START = GlobalConfigUtil.getInt("LargeStart", -1);
	/**
	 * 銘柄登録する先物ラージの終了年月(yyyymm)。開始年月から3ヵ月ごと連続して登録される。
	 */
	public static final int LARGE_END = GlobalConfigUtil.getInt("LargeEnd", -1);
	/**
	 * 銘柄登録するOPの開始価格(ppppp)。
	 */
	public static final int OPTION_START = GlobalConfigUtil.getInt("OptionStart", -1);
	/**
	 * 銘柄登録するOPの終了価格(ppppp)。開始価格から250円ごと連続してCall/Put共に登録される。
	 */
	public static final int OPTION_END = GlobalConfigUtil.getInt("OptionEnd", -1);

	/**
	 * 認証済TOKEN。
	 */
	private String X_API_KEY;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolSymbolName(String X_API_KEY) {
		this.X_API_KEY = X_API_KEY;
		if (MINI_START < 0 || MINI_END < 0 || LARGE_START < 0 || LARGE_END < 0 || OPTION_START < 0 || OPTION_END < 0) {
			throw new RuntimeException("Global.cfg setting ERROR");
		}
	}

	/**
	 * PUSH API受信ツールと同じ銘柄コードのキャッシュを登録する。
	 * 
	 * @return stdoutのリスト。
	 */
	public List<String> execute() {
		StdoutLog.println(clazz, "execute()", "MINI_START=" + MINI_START + ", MINI_END=" + MINI_END);
		StdoutLog.println(clazz, "execute()", "LARGE_START=" + LARGE_START + ", LARGE_END=" + LARGE_END);
		StdoutLog.println(clazz, "execute()", "OPTION_START=" + OPTION_START + ", OPTION_END=" + OPTION_END);
		List<String> lines = new ArrayList<>();

		try (SymbolNameLogic symbolNameLogic = new SymbolNameLogic(X_API_KEY)) {
			int yyyymm = MINI_START;
			for (int ym = yyyymm; ym <= MINI_END; ym++) {
				ym = normalizationDerivMonth(ym);
				try {
					SymbolNameSuccess sns = symbolNameLogic.getFuture(FutureCode.日経225mini先物, ym);
					String code = sns.getSymbol();
					lines.add("getFuture: " + code + " (F" + ym + ")");
				} catch (ApiException e) {
					e.printStackTrace();
				}
			}
			yyyymm = LARGE_START;
			for (int ym = yyyymm; ym <= LARGE_END; ym += 3) {
				ym = normalizationDerivMonth(ym);
				try {
					SymbolNameSuccess sns = symbolNameLogic.getFuture(FutureCode.日経平均先物, ym);
					String code = sns.getSymbol();
					lines.add("getFuture: " + code + " (FL" + ym + ")");
				} catch (ApiException e) {
					e.printStackTrace();
				}
			}
			yyyymm = MINI_START;
			int basePrice = OPTION_START;
			for (int d = 0 ; true; d++) {
				int price = basePrice + d * 250;
				if (price > OPTION_END) {
					break;
				}
				try {
					SymbolNameSuccess sns = symbolNameLogic.getOption(yyyymm, PutOrCallCode.CALL, price);
					String code = sns.getSymbol();
					lines.add("getOption: " + code + " (" + "C" + price + ")");
				} catch (ApiException e) {
					e.printStackTrace();
				}
				try {
					SymbolNameSuccess sns = symbolNameLogic.getOption(yyyymm, PutOrCallCode.PUT, price);
					String code = sns.getSymbol();
					lines.add("getOption: " + code + " (" + "P" + price + ")");
				} catch (ApiException e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	/**
	 * 限月（yyyyMM形式）を正規化する。
	 * 
	 * @param derivMonth 限月（yyyyMM形式）。
	 * @return 限月（yyyyMM形式）。
	 */
	private int normalizationDerivMonth(int derivMonth) {
		int yyyy = derivMonth / 100;
		int mm = derivMonth % 100;
		if (mm > 12) {
			mm -= 12;
			yyyy++;
		}
		return yyyy * 100 + mm;
	}

}
