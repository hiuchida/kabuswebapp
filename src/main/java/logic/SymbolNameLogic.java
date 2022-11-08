package logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.github.hiuchida.api.consts.FutureCode;
import com.github.hiuchida.api.consts.PutOrCallCode;

import api.SymbolNameApi;
import io.swagger.client.ApiException;
import io.swagger.client.model.SymbolNameSuccess;
import util.AppCommon;
import util.FileUtil;
import util.StdoutLog;
import util.StringUtil;

/**
 * キャッシュ付き銘柄コード取得API。
 */
public class SymbolNameLogic extends AppCommon implements AutoCloseable {
	/**
	 * API実行クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 設定ファイルのカラム数。
	 */
	public static final int MAX_COLS = 2;
	/**
	 * 先物銘柄コードファイル名。
	 */
	private static final String FUTURE_DB_FILENAME = "FutureSymbolName.db";
	/**
	 * 先物銘柄コードファイルパス。存在しなければ生成される。
	 */
	private static final String FUTURE_DB_FILEPATH = SERVER_DB_DIR_PATH + FUTURE_DB_FILENAME;
	/**
	 * OP銘柄コードファイル名。
	 */
	private static final String OPTION_DB_FILENAME = "OptionSymbolName.db";
	/**
	 * OP銘柄コードファイルパス。存在しなければ生成される。
	 */
	private static final String OPTION_DB_FILEPATH = SERVER_DB_DIR_PATH + OPTION_DB_FILENAME;
	/**
	 * 先物ヘッダ文字列。
	 */
	private static final String FUTURE_HEADER = "type_yyyyMM code,name";
	/**
	 * OPヘッダ文字列。
	 */
	private static final String OPTION_HEADER = "yyyyMM_PorC_price code,name";

	/**
	 * 銘柄コード取得API。
	 */
	private SymbolNameApi symbolNameApi;

	/**
	 * 先物銘柄コードが更新されたか？
	 */
	private boolean bFutureUpdate = false;
	/**
	 * 先物銘柄コードのマップ。
	 */
	private Map<String, SymbolNameSuccess> futureMap;
	/**
	 * OP銘柄コードが更新されたか？
	 */
	private boolean bOptionUpdate = false;
	/**
	 * OP銘柄コードのマップ。
	 */
	private Map<String, SymbolNameSuccess> optionMap;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public SymbolNameLogic(String X_API_KEY) {
		symbolNameApi = new SymbolNameApi(X_API_KEY);
		readFutureFile();
		readOptionFile();
	}

	/**
	 * キャッシュ付き先物銘柄コード取得API。
	 * 
	 * @param futureCode 先物コード。
	 * @param derivMonth 限月（yyyyMM形式）。
	 * @return 銘柄コード。
	 * @throws ApiException
	 */
	public SymbolNameSuccess getFuture(FutureCode futureCode, int derivMonth) throws ApiException {
		String key = getFutureKey(futureCode, derivMonth);
		SymbolNameSuccess sns = futureMap.get(key);
		if (sns != null) {
			return sns;
		}
		sns = symbolNameApi.getFuture(futureCode, derivMonth);
		futureMap.put(key, sns);
		bFutureUpdate = true;
		return sns;
	}

	/**
	 * キャッシュ付きOP銘柄コード取得API。
	 * 
	 * @param derivMonth  限月（yyyyMM形式）。
	 * @param putOrCall   コール or プット。
	 * @param strikePrice 権利行使価格。
	 * @return 銘柄コード。
	 * @throws ApiException
	 */
	public SymbolNameSuccess getOption(int derivMonth, PutOrCallCode putOrCall, int strikePrice) throws ApiException {
		String key = getOptionKey(derivMonth, putOrCall, strikePrice);
		SymbolNameSuccess sns = optionMap.get(key);
		if (sns != null) {
			return sns;
		}
		sns = symbolNameApi.getOption(derivMonth, putOrCall, strikePrice);
		optionMap.put(key, sns);
		bOptionUpdate = true;
		return sns;
	}

	/**
	 * 先物銘柄コードのキーを取得する。
	 * 
	 * @param futureCode 先物コード。
	 * @param derivMonth 限月（yyyyMM形式）。
	 * @return 先物銘柄コードのキー。
	 */
	private String getFutureKey(FutureCode futureCode, int derivMonth) {
		return futureCode.toString() + "_" + derivMonth;
	}

	/**
	 * OP銘柄コードのキーを取得する。
	 * 
	 * @param derivMonth  限月（yyyyMM形式）。
	 * @param putOrCall   コール or プット。
	 * @param strikePrice 権利行使価格。
	 * @return OP銘柄コードのキー。
	 */
	private String getOptionKey(int derivMonth, PutOrCallCode putOrCall, int strikePrice) {
		return "" + derivMonth + "_" + putOrCall.toString() + "_" + strikePrice;
	}

	/**
	 * 先物銘柄コードを読み込む。
	 */
	private void readFutureFile() {
		futureMap = new TreeMap<>();
		List<String> lines = FileUtil.readAllLines(FUTURE_DB_FILEPATH);
		System.out.println("--- " + FUTURE_DB_FILEPATH + " ---");
		StdoutLog.println(clazz, "readConfig()", "lines.size=" + lines.size());
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitTab(s);
			if (cols.length != MAX_COLS) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			String[] flds = StringUtil.splitComma(cols[1]);
			SymbolNameSuccess sns = new SymbolNameSuccess();
			sns.setSymbol(flds[0]);
			sns.setSymbolName(flds[1]);
			futureMap.put(cols[0], sns);
		}
	}

	/**
	 * OP銘柄コードを読み込む。
	 */
	private void readOptionFile() {
		optionMap = new TreeMap<>();
		List<String> lines = FileUtil.readAllLines(OPTION_DB_FILEPATH);
		System.out.println("--- " + OPTION_DB_FILEPATH + " ---");
		StdoutLog.println(clazz, "readConfig()", "lines.size=" + lines.size());
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitTab(s);
			if (cols.length != MAX_COLS) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			String[] flds = StringUtil.splitComma(cols[1]);
			SymbolNameSuccess sns = new SymbolNameSuccess();
			sns.setSymbol(flds[0]);
			sns.setSymbolName(flds[1]);
			optionMap.put(cols[0], sns);
		}
	}

	/**
	 * 先物銘柄コードを保存する。
	 */
	private void writeFutureFile() {
		if (bFutureUpdate) {
			StdoutLog.println(clazz, "writeFutureFile()", "futureMap.size=" + futureMap.size());
			List<String> lines = new ArrayList<>();
			lines.add("# " + FUTURE_HEADER);
			for (String key : futureMap.keySet()) {
				SymbolNameSuccess sns = futureMap.get(key);
				String val = StringUtil.joinComma(sns.getSymbol(), sns.getSymbolName());
				String line = StringUtil.joinTab(key, val);
				lines.add(line);
			}
			FileUtil.writeAllLines(FUTURE_DB_FILEPATH, lines);
		}
	}

	/**
	 * OP銘柄コードを保存する。
	 */
	private void writeOptionFile() {
		if (bOptionUpdate) {
			StdoutLog.println(clazz, "writeOptionFile()", "optionMap.size=" + optionMap.size());
			List<String> lines = new ArrayList<>();
			lines.add("# " + OPTION_HEADER);
			for (String key : optionMap.keySet()) {
				SymbolNameSuccess sns = optionMap.get(key);
				String val = StringUtil.joinComma(sns.getSymbol(), sns.getSymbolName());
				String line = StringUtil.joinTab(key, val);
				lines.add(line);
			}
			FileUtil.writeAllLines(OPTION_DB_FILEPATH, lines);
		}
	}

	@Override
	public void close() {
		writeFutureFile();
		writeOptionFile();
	}

}
