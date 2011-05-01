package info.jo32.s.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PropertyServiceAsync {

	void getProperty(String name, AsyncCallback<String> callback);

}
