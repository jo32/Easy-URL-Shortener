package info.jo32.s.operation;

import java.io.IOException;

import info.jo32.s.util.PropertiesGetter;
import info.jo32.s.util.UrlConverter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Shorten extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String hint = "failed!";
		String domain = null;
		try {
			domain = PropertiesGetter.getProperty("domain");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		resp.setContentType("text/plain");
		String url = req.getParameter("url");
		String code = req.getParameter("code");
		if (url != null && url != "" && url.contains(".")) {
			if (!url.contains("http")) {
				url = "http://" + url;
			}
			try {
				hint = domain + "/" + UrlConverter.shorten(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (code != null) {
			hint = UrlConverter.getUrl(code).getUrl();
		}
		try {
			resp.getWriter().println(hint);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
