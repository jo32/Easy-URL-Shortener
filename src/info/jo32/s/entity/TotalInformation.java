package info.jo32.s.entity;

import info.jo32.s.client.TotalWrapper;
import info.jo32.s.util.PropertiesGetter;

import java.io.IOException;
import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class TotalInformation implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private int urlNumber;

	@Persistent
	private boolean isProxyBlocked;

	public TotalInformation() throws IOException {
		Key key = KeyFactory.createKey(TotalInformation.class.getSimpleName(), PropertiesGetter.getProperty("administation.totalInfomation.id"));
		this.key = key;
	}

	public int getUrlNumber() {
		return urlNumber;
	}

	public void setUrlNumber(int urlNumber) {
		this.urlNumber = urlNumber;
	}

	public boolean isProxyBlocked() {
		return isProxyBlocked;
	}

	public void setProxyBlocked(boolean isProxyBlocked) {
		this.isProxyBlocked = isProxyBlocked;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public TotalWrapper getWrapper() {
		TotalWrapper wrapper = new TotalWrapper();
		wrapper.setUrlNumber(this.getUrlNumber());
		wrapper.setProxyBocked(this.isProxyBlocked);
		return wrapper;
	}
}
