package info.jo32.s.client;

public class TotalWrapper implements java.io.Serializable {

	private static final long serialVersionUID = -4669433771835565165L;
	
	private int urlNumber = 0;
	private boolean isProxyBocked = false;

	public int getUrlNumber() {
		return urlNumber;
	}

	public void setUrlNumber(int urlNumber) {
		this.urlNumber = urlNumber;
	}

	public boolean isProxyBocked() {
		return isProxyBocked;
	}

	public void setProxyBocked(boolean isProxyBocked) {
		this.isProxyBocked = isProxyBocked;
	}

}
