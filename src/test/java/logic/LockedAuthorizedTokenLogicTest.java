package logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import api.ApiErrorLog;
import io.swagger.client.ApiException;
import util.Consts;

public class LockedAuthorizedTokenLogicTest {

	@BeforeAll
	public static void beforeOnce() {
		ApiErrorLog.init(MethodHandles.lookup().lookupClass(), Consts.VERSION);
	}

	@Test
	public void lockTokenTest1() throws ApiException {
		try (LockedAuthorizedTokenLogic lock = new LockedAuthorizedTokenLogic()) {
			assertNotNull(lock);
			assertNotNull(lock.getApiKey());
		}
		try (LockedAuthorizedTokenLogic lock2 = new LockedAuthorizedTokenLogic()) {
			assertNotNull(lock2);
			assertNotNull(lock2.getApiKey());
		}
	}

	@Test
	public void lockTokenTest2() throws ApiException {
		try (LockedAuthorizedTokenLogic lock = new LockedAuthorizedTokenLogic()) {
			assertNotNull(lock);
			assertNotNull(lock.getApiKey());
			try (LockedAuthorizedTokenLogic lock2 = new LockedAuthorizedTokenLogic()) {
				fail("must throw IllegalStateException.");
			} catch (IllegalStateException e) {
				assertTrue(true);
			}
		}
	}

	@Test
	public void lockTokenTest11() throws ApiException {
		{
			LockedAuthorizedTokenLogic lock = null;
			try {
				lock = new LockedAuthorizedTokenLogic();
				assertNotNull(lock);
				assertNotNull(lock.getApiKey());
			} finally {
				if (lock != null) {
					lock.close();
				}
			}
		}
		{
			LockedAuthorizedTokenLogic lock2 = null;
			try {
				lock2 = new LockedAuthorizedTokenLogic();
				assertNotNull(lock2);
				assertNotNull(lock2.getApiKey());
			} finally {
				if (lock2 != null) {
					lock2.close();
				}
			}
		}
	}

	@Test
	public void lockTokenTest22() throws ApiException {
		LockedAuthorizedTokenLogic lock = null;
		try {
			lock = new LockedAuthorizedTokenLogic();
			assertNotNull(lock);
			assertNotNull(lock.getApiKey());
			LockedAuthorizedTokenLogic lock2 = null;
			try {
				lock2 = new LockedAuthorizedTokenLogic();
				fail("must throw IllegalStateException.");
			} catch (IllegalStateException e) {
				assertTrue(true);
			} finally {
				if (lock2 != null) {
					lock2.close();
				}
			}
		} finally {
			if (lock != null) {
				lock.close();
			}
		}
	}

}
