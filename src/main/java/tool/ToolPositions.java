package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import api.PositionsApi;
import io.swagger.client.ApiException;
import io.swagger.client.model.PositionsSuccess;
import util.AppUtil;
import util.DateTimeUtil;
import util.DoubleUtil;
import util.FileUtil;
import util.IntegerUtil;
import util.StdoutLog;
import util.StringUtil;

/**
 * 建玉情報を管理する。
 */
public class ToolPositions {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 基準パス。
	 */
	private static final String SERVER_DIRPATH = "/tmp/server/";
	/**
	 * 建玉情報を保存したファイルパス。存在しなければ生成される。
	 */
	private static final String TXT_FILEPATH = SERVER_DIRPATH + "ToolPositions.txt";
	/**
	 * 建玉情報の削除情報を保存したファイルパス。存在しなければ生成される。
	 */
	private static final String DEL_FILEPATH = SERVER_DIRPATH + "ToolPositions.del";
	/**
	 * 建玉情報ログのファイルパス。存在しなければ生成される。
	 */
	private static final String LOG_FILEPATH = SERVER_DIRPATH + "ToolPositions.log";

	/**
	 * 建玉情報クラス
	 */
	public static class PosInfo implements Comparable<PosInfo> {
		/**
		 * 建玉情報ファイルのカラム数。
		 */
		public static final int MAX_COLS = 10;
		/**
		 * 約定番号（ExecutionID）。
		 */
		public String executionId;
		/**
		 * 銘柄コード(Symbol)。
		 */
		public String code;
		/**
		 * 銘柄名(SymbolName)。
		 */
		public String name;
		/**
		 * 値段(Price)。
		 */
		public int price;
		/**
		 * 残数量（保有数量）(LeavesQty)。
		 */
		public int leavesQty;
		/**
		 * 拘束数量（返済のために拘束されている数量）(HoldQty)。
		 */
		public int holdQty;
		/**
		 * 売買区分(Side)。
		 */
		public String side;
		/**
		 * 現在値(CurrentPrice)。
		 */
		public int curPrice;
		/**
		 * 生成日時。
		 */
		public long createDate;
		/**
		 * 更新日時。
		 */
		public long updateDate;

		/**
		 * コンストラクタ（新規作成）。
		 * 
		 * @param executionId 約定番号(ID)。
		 * @param code        銘柄コード(Symbol)。
		 * @param name        銘柄名(SymbolName)。
		 * @param price       値段(Price)。
		 * @param leavesQty   残数量（保有数量）(LeavesQty)。
		 * @param holdQty     拘束数量（返済のために拘束されている数量）(HoldQty)。
		 * @param side        売買区分(Side)。
		 * @param curPrice    現在値(CurrentPrice)。
		 */
		public PosInfo(String executionId, String code, String name, int price, int leavesQty, int holdQty, String side,
				int curPrice) {
			this.executionId = executionId;
			this.code = code;
			this.name = name;
			this.price = price;
			this.leavesQty = leavesQty;
			this.holdQty = holdQty;
			this.side = side;
			this.curPrice = curPrice;
			this.createDate = System.currentTimeMillis();
		}

		/**
		 * コンストラクタ（建玉情報レコード）。
		 * 
		 * @param cols 建玉情報ファイルの1レコードの全てのカラム文字列。
		 */
		public PosInfo(String[] cols) {
			int i = 0;
			this.executionId = cols[i++];
			this.code = cols[i++];
			this.name = cols[i++];
			this.price = StringUtil.parseInt(cols[i++]);
			this.leavesQty = StringUtil.parseInt(cols[i++]);
			this.holdQty = StringUtil.parseInt(cols[i++]);
			this.side = "" + StringUtil.parseInt(cols[i++]);
			this.curPrice = StringUtil.parseInt(cols[i++]);
			this.createDate = StringUtil.parseLong(cols[i++]);
			this.updateDate = StringUtil.parseLong(cols[i++]);
		}

		/**
		 * 建玉情報ファイルのヘッダ文字列を生成する。
		 * 
		 * @return ヘッダ文字列。
		 */
		public static String toHeaderString() {
			String[] sa = new String[MAX_COLS];
			int i = 0;
			sa[i++] = "executionId ";
			sa[i++] = "code     ";
			sa[i++] = "name             ";
			sa[i++] = "price";
			sa[i++] = "leaves";
			sa[i++] = "holdQty";
			sa[i++] = "side";
			sa[i++] = "curPric";
			sa[i++] = "createDate                            ";
			sa[i++] = "updateDate                            ";
			String val = "# " + StringUtil.joinTab(sa);
			return val;
		}

		/**
		 * インスタンスの主キー(executionId)を取得する。
		 * 
		 * @return 主キー。
		 */
		public String getKey() {
			return executionId;
		}

		/**
		 * 建玉情報ファイルのレコード文字列を生成する。
		 * 
		 * @return レコード文字列。
		 */
		public String toLineString() {
			String[] sa = new String[MAX_COLS];
			int i = 0;
			sa[i++] = executionId;
			sa[i++] = code;
			sa[i++] = name;
			sa[i++] = "" + price;
			sa[i++] = "" + leavesQty;
			sa[i++] = "" + holdQty;
			sa[i++] = side + "(" + AppUtil.sideStr(side) + ")";
			sa[i++] = "" + curPrice;
			sa[i++] = createDate + "(" + DateTimeUtil.toString(createDate) + ")";
			sa[i++] = updateDate + "(" + DateTimeUtil.toString(updateDate) + ")";
			String val = StringUtil.joinTab(sa);
			return val;
		}

		@Override
		public int compareTo(PosInfo that) {
			String key1 = this.executionId;
			String key2 = that.executionId;
			return key1.compareTo(key2);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{name=").append(name);
			sb.append(", price=").append(price);
			sb.append(", leavesQty=").append(leavesQty);
			sb.append("}");
			return sb.toString();
		}
	}

	/**
	 * 残高照会API。
	 */
	private PositionsApi positionsApi;

	/**
	 * 建玉情報のマップ。
	 */
	private Map<String, PosInfo> posMap;

	/**
	 * 削除対象の建玉情報キーのセット。
	 */
	private Set<String> posKeySet;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolPositions(String X_API_KEY) {
		this.positionsApi = new PositionsApi(X_API_KEY);
	}

	/**
	 * 建玉情報を更新し、含み益を更新したリストを返す。
	 * 
	 * @return 建玉情報のリスト。
	 * @throws ApiException
	 */
	public List<String> execute() throws ApiException {
		readPositions();
		List<PositionsSuccess> response = positionsApi.get();
		StdoutLog.println(clazz, "execute()", "response.size=" + response.size());
		for (int i = 0; i < response.size(); i++) {
			PositionsSuccess pos = response.get(i);
			String id = pos.getExecutionID();
			String key = id;
			if (key != null) {
				posKeySet.remove(key);
			}
			String code = pos.getSymbol();
			String name = pos.getSymbolName();
			name = replaceOpName(name);
			int price = (int) (double) pos.getPrice();
			int leavesQty = (int) (double) pos.getLeavesQty();
			int holdQty = (int) (double) pos.getHoldQty();
			String side = pos.getSide();
			int curPrice = DoubleUtil.intValue(pos.getCurrentPrice());
			int type = IntegerUtil.intValue(pos.getSecurityType());
			if ((type != 901 && type != 103) || curPrice == 0) {
				continue;
			}
			PosInfo pi = posMap.get(key);
			if (pi == null) {
				pi = new PosInfo(id, code, name, price, leavesQty, holdQty, side, curPrice);
				posMap.put(key, pi);
				String msg = "create " + key + " " + pi;
				System.out.println("  > " + msg);
				FileUtil.printLog(LOG_FILEPATH, "execute", msg);
			} else {
				pi.name = name; // TODO replaceOpName()を後から追加
				int oldLeavesQty = pi.leavesQty;
				int oldHoldQty = pi.holdQty;
				pi.leavesQty = leavesQty;
				pi.holdQty = holdQty;
				pi.curPrice = curPrice;
				if (oldLeavesQty != pi.leavesQty || oldHoldQty != pi.holdQty) {
					String msg = "changeQty " + key + " " + oldLeavesQty + "->" + pi.leavesQty + " " + oldHoldQty + "->"
							+ pi.holdQty;
					System.out.println("  > execute " + msg);
					FileUtil.printLog(LOG_FILEPATH, "execute", msg);
				}
			}
			pi.updateDate = System.currentTimeMillis();
		}
		deletePositions();
		List<String> lines = writePositions();
		return lines;
	}

	/**
	 * ＯＰ銘柄名を省略する。
	 * 
	 * @param name ＯＰ銘柄名(日経平均オプション 22/05 プット 25000)。
	 * @return ＯＰ銘柄名(日経OP22/05P25000)。
	 */
	private String replaceOpName(String name) {
		if (name.indexOf("日経平均オプション") >= 0) {
			name = name.replace("日経平均オプション ", "日経OP");
			name = name.replace(" プット ", "P");
			name = name.replace(" コール ", "C");
		}
		return name;
	}

	/**
	 * 建玉情報ファイルを読み込む。不正なレコードは無視される。
	 */
	private void readPositions() {
		posMap = new TreeMap<>();
		posKeySet = new TreeSet<>();
		List<String> lines = FileUtil.readAllLines(TXT_FILEPATH);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitTab(s);
			if (cols.length != PosInfo.MAX_COLS) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			PosInfo pi = new PosInfo(cols);
			String key = pi.getKey();
			posMap.put(key, pi);
			posKeySet.add(key);
		}
		StdoutLog.println(clazz, "readPositions()", "posMap.size=" + posMap.size());
//		for (String key : posMap.keySet()) {
//			PosInfo pi = posMap.get(key);
//			System.out.println("  " + key + ": " + pi);
//		}
	}

	/**
	 * 決済済の建玉を削除する。
	 */
	private void deletePositions() {
		if (posKeySet.size() > 0) {
			try (PrintWriter pw = FileUtil.writer(DEL_FILEPATH, FileUtil.UTF8, true)) {
				StdoutLog.println(clazz, "deletePositions()", "posKeySet.size=" + posKeySet.size());
				for (String key : posKeySet) {
					PosInfo pi = posMap.get(key);
					FileUtil.printLogLine(pw, pi.toLineString());
					String msg = "delete " + key + " " + pi;
					System.out.println("  > deletePositions " + msg);
					FileUtil.printLog(LOG_FILEPATH, "deletePositions", msg);
					posMap.remove(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 建玉情報ファイルを書き込む。
	 * 
	 * @return 建玉情報のリスト。
	 */
	private List<String> writePositions() {
		StdoutLog.println(clazz, "writePositions()", "posMap.size=" + posMap.size());
		List<String> lines = new ArrayList<>();
		lines.add(PosInfo.toHeaderString());
		List<PosInfo> list = new ArrayList<>();
		list.addAll(posMap.values());
		Collections.sort(list);
		for (PosInfo pi : list) {
			lines.add(pi.toLineString());
//			String key = pi.getKey();
//			System.out.println("  " + key + ": " + pi);
		}
		FileUtil.writeAllLines(TXT_FILEPATH, lines);
		return lines;
	}

}
