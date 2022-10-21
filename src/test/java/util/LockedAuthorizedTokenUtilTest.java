package util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import api.ApiErrorLog;
import io.swagger.client.ApiException;

public class LockedAuthorizedTokenUtilTest {

	@BeforeAll
	public static void beforeOnce() {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
	}

	@Test
	public void getInstanceTest() throws ApiException {
		LockedAuthorizedTokenUtil a1 = LockedAuthorizedTokenUtil.getInstance();
		assertNotNull(a1);
	}

	@Test
	public void lockTokenTest() throws ApiException {
		try {
			LockedAuthorizedTokenUtil.lockToken();
		} finally {
			LockedAuthorizedTokenUtil.unlockToken();
		}
	}

	@Test
	public void lockTokenTest2() throws ApiException {
		try {
			LockedAuthorizedTokenUtil.lockToken();
			try {
				LockedAuthorizedTokenUtil.lockToken();
				fail("must throw IllegalStateException.");
			} catch (IllegalStateException e) {
				assertTrue(true);
			}
		} finally {
			LockedAuthorizedTokenUtil.unlockToken();
		}
	}

	/**
	 * ログインエラーが前提となるテスト。
	 */
	@Disabled
	@Test
	public void lockTokenTest99() {
		try {
			LockedAuthorizedTokenUtil.lockToken();
			fail("must throw ApiException.");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		try {
			LockedAuthorizedTokenUtil.lockToken();
			fail("must throw ApiException.");
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void unlockTokenTest() throws ApiException {
		try {
			LockedAuthorizedTokenUtil.lockToken();
		} finally {
			LockedAuthorizedTokenUtil.unlockToken();
		}
	}

	@Test
	public void unlockTokenTest2() throws ApiException {
		LockedAuthorizedTokenUtil.unlockToken();
	}

	@Test
	public void peekTokenTest() throws ApiException {
		String a1 = LockedAuthorizedTokenUtil.peekToken();
		assertNotNull(a1);
	}

}
