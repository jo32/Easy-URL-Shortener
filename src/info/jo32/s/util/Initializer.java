package info.jo32.s.util;

import info.jo32.s.entity.TotalInformation;
import info.jo32.s.entity.UrlInformation;

import java.io.IOException;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class Initializer {

	public static String initialize(String customConfirmKey) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Transaction tx = pm.currentTransaction();
		String confirmKey = PropertiesGetter.getProperty("administration.initializer.confirmKey");
		String info = PropertiesGetter.getProperty("language.initializer.failure");
		if (customConfirmKey.equals(confirmKey)) {
			try {
				tx.begin();
				TotalInformation totalInfo = null;
				try {
					totalInfo = pm.getObjectById(TotalInformation.class, PropertiesGetter.getProperty("administation.totalInfomation.id"));
				} catch (Exception e) {
					e.toString();
				} finally {
				}
				if (totalInfo == null) {
					totalInfo = new TotalInformation();
					totalInfo.setUrlNumber(1);
					try {
						pm.makePersistent(totalInfo);
					} catch (Exception e) {
						e.toString();
					}
					info = PropertiesGetter.getProperty("language.initializer.firstInitialized");
				} else {
					int urlNum = totalInfo.getUrlNumber();
					if (urlNum < 0) {
						totalInfo.setUrlNumber(1);
					}
					info = PropertiesGetter.getProperty("language.initializer.Initialized");
				}
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
			}
		} else {
			info = PropertiesGetter.getProperty("language.initializer.wrongParameter");
		}
		pm.close();
		return info;
	}

	public static String reset(String customConfirmKey) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Transaction tx = pm.currentTransaction();
		String confirmKey = PropertiesGetter.getProperty("administration.initializer.confirmKey");
		String info = PropertiesGetter.getProperty("language.initializer.failure");
		if (customConfirmKey.equals(confirmKey)) {
			try {
				tx.begin();
				TotalInformation totalInfo = null;
				try {
					totalInfo = pm.getObjectById(TotalInformation.class, PropertiesGetter.getProperty("administation.totalInfomation.id"));
				} catch (Exception e) {
				} finally {
				}
				for (int i = totalInfo.getUrlNumber() - 1; i > 0; i--) {
					UrlInformation nextUrl = null;
					try {
						Key key = KeyFactory.createKey(KeyFactory.createKey(TotalInformation.class.getSimpleName(), PropertiesGetter.getProperty("administation.totalInfomation.id")), UrlInformation.class.getSimpleName(), UrlConverter.getStringByNumber(i));
						nextUrl = pm.getObjectById(UrlInformation.class, key);
					} catch (Exception e) {
						System.out.println();
					}
					if (nextUrl != null)
						pm.deletePersistent(nextUrl);
				}
				totalInfo.setUrlNumber(1);
				info = PropertiesGetter.getProperty("language.initializer.resetSuccessfully");
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
			}
		} else {
			info = PropertiesGetter.getProperty("language.initializer.wrongParameter");
		}
		pm.close();
		return info;
	}
}
