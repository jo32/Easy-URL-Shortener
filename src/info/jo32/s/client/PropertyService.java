package info.jo32.s.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("propertysvc")
public interface PropertyService extends RemoteService{
	String getProperty(String name);
}
