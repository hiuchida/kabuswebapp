package tool;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import api.CancelorderApi;
import io.swagger.client.ApiException;
import io.swagger.client.model.OrderSuccess;
import util.AppCommon;
import util.StdoutLog;

/**
 * 注文取り消しするツール。
 */
public class ToolCancelOrder extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	/**
	 * 注文取消API。
	 */
	private CancelorderApi cancelorderApi;

	/**
	 * コンストラクタ。
	 * 
	 * @param X_API_KEY 認証済TOKEN。
	 */
	public ToolCancelOrder(String X_API_KEY) {
		this.cancelorderApi = new CancelorderApi(X_API_KEY);
	}

	/**
	 * 注文取り消しする。
	 * 
	 * @param orderId 取り消す注文番号。
	 * @throws ApiException 
	 */
	public List<String> execute(String orderId) throws ApiException {
		StdoutLog.println(clazz, "execute()", "orderId=" + orderId);
		List<String> lines = new ArrayList<>();

		OrderSuccess os = cancelorderApi.put(orderId);
		lines.add(os.toString());
		return lines;
	}

}
