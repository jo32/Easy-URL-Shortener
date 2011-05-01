package info.jo32.s.client;

import java.io.IOException;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("urlsvc")
public interface UrlService extends RemoteService {
	
	String shorten(String url);

	UrlWrapper getUrl(String code);

	List<UrlWrapper> get10Url() throws IOException;
}
