package api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.swagger.client.ApiException;
import io.swagger.client.model.TokenSuccess;
import util.Consts;

/**
 * API tests for TokenApi
 */
// @Disabled
public class TokenApiTest {

	private static TokenApi api;

	@BeforeAll
	public static void beforeOnce() throws ApiException {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
		api = new TokenApi();
	}

	@BeforeEach
	public void beforeEach() {
	}

	@Test
	public void tokenPostTokenTest() throws Exception {
		String response = api.postToken(TestConsts.API_PASSWORD);

		// TODO: test validations
		System.out.println(response);
		assertNotNull(response);
	}

	@Test
	public void tokenPostTest() throws Exception {
		TokenSuccess response = api.post(TestConsts.API_PASSWORD);

		// TODO: test validations
		System.out.println(response);
		assertEquals(0, (int) response.getResultCode());
		assertNotNull(response.getToken());
	}

}
