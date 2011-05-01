package info.jo32.s.server;

import java.io.IOException;

import info.jo32.s.client.PropertyService;
import info.jo32.s.util.PropertiesGetter;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PropertyServiceImpl extends RemoteServiceServlet implements PropertyService {

	@Override
	public String getProperty(String name) {
		try {
			return PropertiesGetter.getProperty(name);
		} catch (IOException e) {
			return "ERROR IN PropertyServiceImpl";
		}
	}

}
