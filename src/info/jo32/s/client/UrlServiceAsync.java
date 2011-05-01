package info.jo32.s.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UrlServiceAsync {

	void getUrl(String code, AsyncCallback<UrlWrapper> callback);

	void shorten(String url, AsyncCallback<String> callback);

	void get10Url(AsyncCallback<List<UrlWrapper>> callback);

}
