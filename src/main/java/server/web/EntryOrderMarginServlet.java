package server.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hiuchida.api.consts.SideCode;

import io.swagger.client.ApiException;
import logic.LockedAuthorizedTokenLogic;
import tool.ToolEntryOrderMargin;
import util.StringUtil;

/**
 * Servlet implementation class EntryOrderMarginServlet
 */
public class EntryOrderMarginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntryOrderMarginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String symbol = request.getParameter("symbol");
		String param1 = request.getParameter("order");
		SideCode side = null;
		if (param1.endsWith("L")) {
			side = SideCode.買;
		} else if (param1.endsWith("S")) {
			side = SideCode.売;
		} else {
			throw new IllegalArgumentException("side is unknown. param1=" + param1);
		}
		double price = StringUtil.parseDouble(param1.substring(0, param1.length() - 1));
		int qty = 100;
		String qtyStr = request.getParameter("qty");
		if (qtyStr != null) {
			qty = StringUtil.parseInt(qtyStr);
		}
		int expire = 0;
		String expireStr = request.getParameter("expire");
		if (expireStr != null) {
			expire = StringUtil.parseInt(expireStr);
		}
		try (LockedAuthorizedTokenLogic login = new LockedAuthorizedTokenLogic()) {
			String X_API_KEY = login.getApiKey();
			List<String> lines = new ToolEntryOrderMargin(X_API_KEY).execute(symbol, side, qty, price, expire);
			PrintWriter pw = response.getWriter();
			for (String s : lines) {
				pw.println(s);
			}
		} catch (ApiException e) {
			e.printStackTrace();
			response.getWriter().append("Error: ").append(e.getResponseBody());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
