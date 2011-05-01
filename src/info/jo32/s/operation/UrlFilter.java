package info.jo32.s.operation;

import info.jo32.s.entity.TotalInformation;
import info.jo32.s.entity.UrlInformation;
import info.jo32.s.util.PMF;
import info.jo32.s.util.PropertiesGetter;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class UrlFilter implements javax.servlet.Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getRequestURI();
		boolean go = false;
		String fbdW = PropertiesGetter.getProperty("urlConverter.shorten.forbiddenwords");
		String[] forbiddenW = fbdW.split(";");
		for (int i = 0; i < forbiddenW.length; i++) {
			if (path.contains(forbiddenW[i])) {
				go = true;
				break;
			}
		}
		if (go == true) {
			chain.doFilter(req, resp);
		} else if (path.equals("/") || path.equals("") || path == null) {
			request.getRequestDispatcher("UrlShorter.html").forward(req, resp);
		} else {
			String code = path.replaceAll("/", "");
			try {
				PersistenceManager pm = PMF.get().getPersistenceManager();
				Key key = KeyFactory.createKey(KeyFactory.createKey(TotalInformation.class.getSimpleName(), PropertiesGetter.getProperty("administation.totalInfomation.id")), UrlInformation.class.getSimpleName(), code);
				UrlInformation url = null;
				try {
					url = pm.getObjectById(UrlInformation.class, key);
				} catch (Exception e) {
				}
				HttpServletResponse response = (HttpServletResponse) resp;
				if (url != null) {
					response.sendRedirect(url.getUrl());
				} else {
					request.getRequestDispatcher("UrlShorter.html").forward(req, resp);
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
