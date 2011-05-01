package info.jo32.s.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {
	public static String[] getProperties(String[] args) throws IOException {
		File file = new File("WEB-INF/lang.properties");
		InputStream in = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(in);
		String[] props = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			props[i] = properties.getProperty(args[i]);
		}
		in.close();
		return props;
	}

	public static String getProperty(String arg) throws IOException {
		File file = new File("WEB-INF/lang.properties");
		InputStream in = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(in);
		String prop = null;
		prop = properties.getProperty(arg);
		in.close();
		return prop;
	}
}
