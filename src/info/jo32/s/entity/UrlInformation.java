package info.jo32.s.entity;

import info.jo32.s.client.UrlWrapper;
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
public class UrlInformation implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String url = new String();

	@Persistent
	private String code = new String();

	@Persistent
	private boolean isBlocked = false;

	@Persistent
	private int clickTimes;

	public UrlInformation(String code) throws IOException {
		Key key = new KeyFactory.Builder(TotalInformation.class.getSimpleName(), PropertiesGetter.getProperty("administation.totalInfomation.id")).addChild(UrlInformation.class.getSimpleName(), code).getKey();
		this.clickTimes = 0;
		this.key = key;
	}

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

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public int getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(int clickTimes) {
		this.clickTimes = clickTimes;
	}

	public UrlWrapper getWrapper() {
		UrlWrapper wrapper = new UrlWrapper();
		wrapper.setBlocked(this.isBlocked);
		wrapper.setCode(this.getCode());
		wrapper.setTimes(this.getClickTimes());
		wrapper.setUrl(this.getUrl());
		return wrapper;
	}
}
