package info.jo32.s.util;

import info.jo32.s.entity.*;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class UrlConverter {
	public static String shorten(String url) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String code = null;
		TotalInformation total = null;
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			try {
				total = pm.getObjectById(TotalInformation.class, PropertiesGetter.getProperty("administation.totalInfomation.id"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			code = UrlConverter.getStringByNumber(total.getUrlNumber());
			//to see whether it is conflict with existed url
			int urlAmount = total.getUrlNumber();
			String fbdW = PropertiesGetter.getProperty("urlConverter.shorten.forbiddenwords");
			boolean up = true;
			int i = 0;
			String[] fbdWs = fbdW.split(";");
			while (up) {
				for (i = 0; i < fbdWs.length; i++) {
					if (fbdWs[i].matches(code)) {
						urlAmount++;
						code = UrlConverter.getStringByNumber(urlAmount);
						break;
					}
				}
				if (i == fbdWs.length) {
					up = false;
				}
			}
			//<------------->
			total.setUrlNumber(urlAmount + 1);
			UrlInformation thisUrl = new UrlInformation(code);
			thisUrl.setUrl(url);
			thisUrl.setCode(code);
			pm.makePersistent(thisUrl);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		pm.close();
		return code;
	}

	public static UrlInformation getUrl(String code) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		UrlInformation urlInfo = null;
		try {
			tx.begin();
			try {
				Key key = KeyFactory.createKey(KeyFactory.createKey(TotalInformation.class.getSimpleName(), PropertiesGetter.getProperty("administation.totalInfomation.id")), UrlInformation.class.getSimpleName(), code);
				urlInfo = pm.getObjectById(UrlInformation.class, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		pm.close();
		return urlInfo;
	}

	public static String getStringByNumber(int amount) {
		String signals = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		char[] chars = signals.toCharArray();
		String code = "";
		while (amount != 0) {
			code += chars[amount % 63];
			amount = amount / 63;
		}
		return code;
	}
}
