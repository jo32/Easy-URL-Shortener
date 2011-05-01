package info.jo32.s.client;

public class UrlWrapper implements java.io.Serializable {

	private static final long serialVersionUID = -4429669346726613729L;
	
	private String url = null;
	private String code = null;
	private boolean isBlocked = false;
	private int times = 0;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
