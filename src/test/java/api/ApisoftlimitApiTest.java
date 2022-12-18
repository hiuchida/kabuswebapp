package api;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.swagger.client.ApiException;
import io.swagger.client.model.ApiSoftLimitResponse;
import util.Consts;

/**
 * API tests for ApisoftlimitApi
 */
// @Disabled
public class ApisoftlimitApiTest {

	private static String X_API_KEY;
	private static ApisoftlimitApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		X_API_KEY = AuthorizedToken.getToken();
		api = new ApisoftlimitApi(X_API_KEY);
	}

	@BeforeEach
	public void beforeEach() {
	}

    @Test
    public void getTest() throws Exception {
        ApiSoftLimitResponse response = api.get();

        // TODO: test validations
        System.out.println(response);        
    }

}
