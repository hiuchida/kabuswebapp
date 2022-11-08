package api;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.hiuchida.api.consts.ProductCode;
import com.github.hiuchida.api.consts.SideCode;

import io.swagger.client.ApiException;
import io.swagger.client.model.PositionsSuccess;
import util.Consts;

/**
 * API tests for PositionsApi
 */
// @Disabled
public class PositionsApiTest {

	private static String X_API_KEY;
	private static PositionsApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new PositionsApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

    @Test
    public void getTest() throws Exception {
        List<PositionsSuccess> response = api.get();

        // TODO: test validations
        System.out.println(response);        
    }

    @Test
    public void getByProductTest() throws Exception {
        ProductCode product = ProductCode.先物;
        List<PositionsSuccess> response = api.getByProduct(product);

        // TODO: test validations
        System.out.println(response);        
    }

    @Test
    public void getBySymbolTest() throws Exception {
        String symbol = "167110019";
        List<PositionsSuccess> response = api.getBySymbol(symbol);

        // TODO: test validations
        System.out.println(response);        
    }

    @Test
    public void getByAllTest() throws Exception {
        ProductCode product = ProductCode.先物;
        String symbol = "167110019";
        SideCode side = SideCode.買;
        String addinfo = null;
        List<PositionsSuccess> response = api.getByAll(product, symbol, side, addinfo);

        // TODO: test validations
        System.out.println(response);        
    }

}
