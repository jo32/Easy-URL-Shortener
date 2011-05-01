package info.jo32.s.operation;

import info.jo32.s.util.Initializer;
import info.jo32.s.util.PropertiesGetter;

import java.io.IOException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String type = req.getParameter("type");
		String confirmKey = req.getParameter("confirmKey");
		resp.setContentType("text/plain");
		if (confirmKey != null && type.equals("initialize")) {
			resp.getWriter().println(Initializer.initialize(confirmKey));
		} else if (confirmKey != null && type.equals("reset")) {
			resp.getWriter().println(Initializer.reset(confirmKey));
		} else {
			resp.getWriter().println(PropertiesGetter.getProperty("language.initializer.noParameter"));
		}
	}
}
