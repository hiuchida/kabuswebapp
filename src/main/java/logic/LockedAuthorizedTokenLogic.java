package logic;

import io.swagger.client.ApiException;
import util.LockedAuthorizedTokenUtil;

/**
 * 認証済TOKENをファイルロック管理する。
 */
public class LockedAuthorizedTokenLogic implements AutoCloseable {

	private String X_API_KEY;

	public LockedAuthorizedTokenLogic() throws ApiException {
		this.X_API_KEY = LockedAuthorizedTokenUtil.lockToken();
	}

	public String getApiKey() {
		return X_API_KEY;
	}

	@Override
	public void close() {
		X_API_KEY = null;
		LockedAuthorizedTokenUtil.unlockToken();
	}

	@Override
	protected void finalize() throws Throwable {
		LockedAuthorizedTokenUtil.unlockToken();
	}

}
