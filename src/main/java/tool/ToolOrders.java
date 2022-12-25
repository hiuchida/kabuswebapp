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

import com.github.hiuchida.api.consts.SideCode;

import api.OrdersApi;
import io.swagger.client.ApiException;
import io.swagger.client.model.OrdersSuccess;
import io.swagger.client.model.OrdersSuccessDetails;
import util.AppCommon;
import util.AppUtil;
import util.DateTimeUtil;
import util.FileUtil;
import util.StdoutLog;
import util.StringUtil;

/**
 * 新規注文情報を管理する。
 */
public class ToolOrders extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 新規注文情報を保存したファイルパス。事前に準備し、uniqIdが振られて更新される。
	 */
	private static final String TXT_FILEPATH = SERVER_DIR_PATH + "ToolOrders.txt";
	/**
	 * 新規注文情報の削除情報を保存したファイルパス。事前に準備し、uniqIdが振られて更新される。
	 */
	private static final String DEL_FILEPATH = SERVER_DIR_PATH + "ToolOrders.del";
	/**
	 * 新規注文ログのファイルパス。存在しなければ生成される。
	 */
	private static final String LOG_FILEPATH = SERVER_DIR_PATH + "ToolOrders.log";

	/**
	 * 新規注文情報クラス。
	 */
	public static class OrderInfo implements Comparable<OrderInfo> {
		/**
		 * 登録済、未発注。
		 */
		public static final int STATE_NOT_ORDER = -1;
		/**
		 * 発注済、注文ステータス不明。
		 */
		public static final int STATE_UNKNOWN = 0;
		/**
		 * 発注済。API:待機（発注待機）。
		 */
		public static final int STATE_WAIT = 1;
		/**
		 * 発注済。API:処理中（発注送信中）。
		 */
		public static final int STATE_SEND_ORDER = 2;
		/**
		 * 発注済。API:処理済（発注済・訂正済）。
		 */
		public static final int STATE_ORDER = 3;
		/**
		 * 発注済。API:訂正取消送信中。
		 */
		public static final int STATE_SEND_CANCEL = 4;
		/**
		 * 約定済。API:終了（発注エラー・取消済・全約定・失効・期限切れ）。
		 */
		public static final int STATE_FINISH = 5;
		/**
		 * 注文情報からキャンセル済。
		 */
		public static final int STATE_CANCEL = 6;
		/**
		 * 注文情報から返済済。
		 */
		public static final int STATE_CLOSE = 7;
		/**
		 * 注文情報から削除済（終了）。
		 */
		public static final int STATE_FINISH_DELETE = 95;
		/**
		 * 注文情報から削除済（キャンセル）。
		 */
		public static final int STATE_CANCEL_DELETE = 96;
		/**
		 * 注文情報から削除済（返済）。
		 */
		public static final int STATE_CLOSE_DELETE = 97;
		/**
		 * 新規注文情報ファイルのカラム数。
		 */
		public static final int MAX_COLS = 11;
		/**
		 * 注文番号(ID)。
		 */
		public String orderId;
		/**
		 * 銘柄コード(Symbol)。
		 */
		public String code;
		/**
		 * 銘柄名(SymbolName)。
		 */
		public String name;
		/**
		 * 状態(State)。
		 */
		public int state;
		/**
		 * 取引区分(CashMargin)。
		 */
		public int cashMargin;
		/**
		 * 値段(Price)。
		 */
		public int price;
		/**
		 * 発注数量(OrderQty)。
		 */
		public int orderQty;
		/**
		 * 売買区分(Side)。
		 */
		public SideCode side;
		/**
		 * 生成日時。
		 */
		public long createDate;
		/**
		 * 更新日時。
		 */
		public long updateDate;
		/**
		 * 約定番号(ExecutionID)と値段(Price)と数量(Qty)のリスト。
		 */
		public String executionIds;

		/**
		 * コンストラクタ（新規作成）。
		 * 
		 * @param orderId    注文番号(ID)。
		 * @param code       銘柄コード(Symbol)。
		 * @param name       銘柄名(SymbolName)。
		 * @param state      状態(State)。
		 * @param cashMargin 取引区分(CashMargin)。
		 * @param price      値段(Price)。
		 * @param qty        発注数量(OrderQty)。
		 * @param side       売買区分(Side)。
		 */
		public OrderInfo(String orderId, String code, String name, int state, int cashMargin, int price, int qty, SideCode side) {
			this.orderId = orderId;
			this.code = code;
			this.name = name;
			this.state = state;
			this.cashMargin = cashMargin;
			this.price = price;
			this.orderQty = qty;
			this.side = side;
			this.createDate = System.currentTimeMillis();
			this.updateDate = System.currentTimeMillis();
			this.executionIds = "";
		}

		/**
		 * コンストラクタ（新規注文情報レコード）。
		 * 
		 * @param cols 新規注文情報ファイルの1レコードの全てのカラム文字列。
		 */
		public OrderInfo(String[] cols) {
			int i = 0;
			this.orderId = cols[i++];
			this.code = cols[i++];
			this.name = cols[i++];
			this.state = StringUtil.parseInt(cols[i++]);
			this.cashMargin = StringUtil.parseInt(cols[i++]);
			this.price = StringUtil.parseInt(cols[i++]);
			this.orderQty = StringUtil.parseInt(cols[i++]);
			this.side = SideCode.valueOf(StringUtil.parseInt(cols[i++]));
			this.createDate = StringUtil.parseLong(cols[i++]);
			this.updateDate = StringUtil.parseLong(cols[i++]);
			this.executionIds = cols[i++];
		}

		/**
		 * 新規注文情報ファイルのヘッダ文字列を生成する。
		 * 
		 * @return ヘッダ文字列。
		 */
		public static String toHeaderString() {
			String[] sa = new String[MAX_COLS];
			int i = 0;
			sa[i++] = "orderId             ";
			sa[i++] = "code     ";
			sa[i++] = "name             ";
			sa[i++] = "state";
			sa[i++] = "cashMar";
			sa[i++] = "price";
			sa[i++] = "qty";
			sa[i++] = "side";
			sa[i++] = "createDate                            ";
			sa[i++] = "updateDate                            ";
			sa[i++] = "executionIds";
			String val = "# " + StringUtil.joinTab(sa);
			return val;
		}

		/**
		 * インスタンスの主キー(orderId)を取得する。
		 * 
		 * @return 主キー。
		 */
		public String getKey() {
			return orderId;
		}

		/**
		 * 新規注文情報ファイルのレコード文字列を生成する。
		 * 
		 * @return レコード文字列。
		 */
		public String toLineString() {
			String[] sa = new String[MAX_COLS];
			int i = 0;
			sa[i++] = orderId;
			sa[i++] = code;
			sa[i++] = name;
			sa[i++] = "" + state;
			sa[i++] = "" + cashMargin;
			sa[i++] = "" + price;
			sa[i++] = "" + orderQty;
			sa[i++] = side + "(" + AppUtil.sideStr(side) + ")";
			sa[i++] = createDate + "(" + DateTimeUtil.toString(createDate) + ")";
			sa[i++] = updateDate + "(" + DateTimeUtil.toString(updateDate) + ")";
			sa[i++] = executionIds;
			String val = StringUtil.joinTab(sa);
			return val;
		}

		@Override
		public int compareTo(OrderInfo that) {
			String key1 = this.orderId;
			String key2 = that.orderId;
			return key1.compareTo(key2);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{name=").append(name);
			sb.append(", state=").append(state);
			sb.append(", price=").append(price);
			sb.append("}");
			return sb.toString();
		}
	}

	/**
	 * 新規注文情報のマップ。
	 */
	private Map<String, OrderInfo> orderMap;

	/**
	 * 削除対象の新規注文情報キーのセット。
	 */
	private Set<String> orderKeySet;

	/**
	 * 注文約定照会API。
	 */
	private OrdersApi ordersApi;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolOrders(String X_API_KEY) {
		this.ordersApi = new OrdersApi(X_API_KEY);
	}

	/**
	 * 新規注文情報を更新する。
	 * 
	 * @return 新規注文情報のリスト。
	 * @throws ApiException
	 */
	public List<String> execute() throws ApiException {
		readOrders();
		List<OrdersSuccess> response = ordersApi.get();
		StdoutLog.println(clazz, "execute()", "response.size=" + response.size());
		for (int i = 0; i < response.size(); i++) {
			OrdersSuccess order = response.get(i);
			String orderId = order.getID();
			String code = order.getSymbol();
			String name = order.getSymbolName();
			name = replaceOpName(name);
			int price = (int) (double) order.getPrice();
			int orderQty = (int) (double) order.getOrderQty();
			SideCode side = SideCode.valueOfCode(order.getSide());
			int state = order.getState();
			int exchange = order.getExchange();
			Integer cashMargin = order.getCashMargin();
			String executionIds = "";
			for (OrdersSuccessDetails osd : order.getDetails()) {
				String executionId = osd.getExecutionID();
				if (executionId != null) {
					int executionPrice = (int) (double) osd.getPrice();
					int executionQty = (int) (double) osd.getQty();
					if (executionIds.length() > 0) {
						executionIds = executionIds + ",";
					}
					executionIds = executionIds + executionId + ":" + executionPrice + "x" + executionQty;
				}
			}
			if (exchange != 2 && exchange != 23 && exchange != 24) { // 先物OP以外
				continue;
			}
//			if (cashMargin != 2) { // 新規以外
//				continue;
//			}
			String key = orderId;
			OrderInfo oi = orderMap.get(key);
			if (oi == null) {
				oi = new OrderInfo(orderId, code, name, state, cashMargin, price, orderQty, side);
				oi.executionIds = executionIds;
				orderMap.put(key, oi);
				String msg = "create " + key + " " + oi;
				System.out.println("  > execute " + msg);
				FileUtil.printLog(LOG_FILEPATH, "execute", msg);
			} else {
				oi.name = name; // TODO replaceOpName()を後から追加
				int oldState = oi.state;
				String oldExecutionIds = executionIds;
				oi.state = state;
				oi.executionIds = executionIds;
				if (oldState != oi.state) {
					String msg = "changeState " + key + " " + oldState + "->" + oi.state;
					System.out.println("  > execute " + msg);
					FileUtil.printLog(LOG_FILEPATH, "execute", msg);
				}
				if (!oldExecutionIds.equals(oi.executionIds)) {
					String msg = "changeState " + key + " " + oldExecutionIds + "->" + oi.executionIds;
					System.out.println("  > execute " + msg);
					FileUtil.printLog(LOG_FILEPATH, "execute", msg);
				}
			}
			oi.updateDate = System.currentTimeMillis();
			orderKeySet.remove(key);
		}
		deleteOrders();
		List<String> lines = writeOrders();
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
	 * 新規注文情報ファイルを読み込む。不正なレコードは無視される。
	 */
	private void readOrders() {
		orderMap = new TreeMap<>();
		orderKeySet = new TreeSet<>();
		List<String> lines = FileUtil.readAllLines(TXT_FILEPATH);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitTab(s);
			if (cols.length != OrderInfo.MAX_COLS) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			OrderInfo oi = new OrderInfo(cols);
			String key = oi.getKey();
			orderMap.put(key, oi);
			orderKeySet.add(key);
		}
		StdoutLog.println(clazz, "readOrders()", "orderMap.size=" + orderMap.size());
//		for (String key : orderMap.keySet()) {
//			OrderInfo oi = orderMap.get(key);
//			System.out.println("  " + key + ": " + oi);
//		}
	}

	/**
	 * 終了済の注文を論理削除する。
	 */
	private void deleteOrders() {
		if (orderKeySet.size() > 0) {
			try (PrintWriter pw = FileUtil.writer(DEL_FILEPATH, FileUtil.UTF8, true)) {
				StdoutLog.println(clazz, "deleteOrders()", "orderKeySet.size=" + orderKeySet.size());
				for (String key : orderKeySet) {
					OrderInfo oi = orderMap.get(key);
					FileUtil.printLogLine(pw, oi.toLineString());
					String msg = "delete " + key + " " + oi;
					System.out.println("  > deleteOrders " + msg);
					FileUtil.printLog(LOG_FILEPATH, "deleteOrders", msg);
					orderMap.remove(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 新規注文情報ファイルを書き込む。
	 * 
	 * @return 新規注文情報のリスト。
	 */
	private List<String> writeOrders() {
		StdoutLog.println(clazz, "writeOrders()", "orderMap.size=" + orderMap.size());
		List<String> lines = new ArrayList<>();
		lines.add(OrderInfo.toHeaderString());
		List<OrderInfo> list = new ArrayList<>();
		list.addAll(orderMap.values());
		Collections.sort(list);
		for (OrderInfo oi : list) {
			lines.add(oi.toLineString());
//			String key = oi.getKey();
//			System.out.println("  " + key + ": " + oi);
		}
		FileUtil.writeAllLines(TXT_FILEPATH, lines);
		return lines;
	}

}
