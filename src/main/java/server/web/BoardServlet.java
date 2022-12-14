package server.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hiuchida.api.consts.ExchangeCode;
import com.github.hiuchida.api.model.BoardSuccessWrapper;

import api.BoardApi;
import io.swagger.client.ApiException;
import logic.LockedAuthorizedTokenLogic;

/**
 * Servlet implementation class BoardServlet
 */
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (LockedAuthorizedTokenLogic login = new LockedAuthorizedTokenLogic()) {
			String X_API_KEY = login.getApiKey();
	        String symbol = "9433"; // ＫＤＤＩ
	        BoardApi boardApi = new BoardApi(X_API_KEY);
	        BoardSuccessWrapper board = boardApi.get(symbol, ExchangeCode.東証);
//	        InfoApi infoApi = new InfoApi();
//	        BoardSuccess board = infoApi.boardGet(X_API_KEY, symbol);
	        System.out.println(board);
			response.getWriter().append(board.toString());
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
