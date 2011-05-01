package info.jo32.s.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import info.jo32.s.client.UrlService;
import info.jo32.s.client.UrlWrapper;
import info.jo32.s.entity.TotalInformation;
import info.jo32.s.entity.UrlInformation;
import info.jo32.s.util.PMF;
import info.jo32.s.util.PropertiesGetter;
import info.jo32.s.util.UrlConverter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UrlServiceImpl extends RemoteServiceServlet implements UrlService {

	@Override
	public UrlWrapper getUrl(String code) {
		UrlInformation url = UrlConverter.getUrl(code);
		return url.getWrapper();
	}

	@Override
	public List<UrlWrapper> get10Url() throws IOException {
		ArrayList<UrlWrapper> urls = new ArrayList<UrlWrapper>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			TotalInformation total = pm.getObjectById(TotalInformation.class, PropertiesGetter.getProperty("administation.totalInfomation.id"));
			int codeNum = total.getUrlNumber() - 1;
			for (int i = 0; i < 10 && codeNum > 0; i++, codeNum--) {
				String code = UrlConverter.getStringByNumber(codeNum);
				Key key = KeyFactory.createKey(KeyFactory.createKey(TotalInformation.class.getSimpleName(), PropertiesGetter.getProperty("administation.totalInfomation.id")), UrlInformation.class.getSimpleName(), code);
				UrlWrapper temp = pm.getObjectById(UrlInformation.class, key).getWrapper();
				urls.add(temp);
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return urls;
	}

	@Override
	public String shorten(String url) {
		String code = "ERROR";
		try {
			code = UrlConverter.shorten(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

}
