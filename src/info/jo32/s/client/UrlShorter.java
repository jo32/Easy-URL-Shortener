package info.jo32.s.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class UrlShorter implements EntryPoint {

	private VerticalPanel vpanel = new VerticalPanel();
	private Button convertButton = new Button();
	private TextBox urlInput = new TextBox();
	private DialogBox errorDialog = new DialogBox();

	private String domain = null;

	private PropertyServiceAsync propertySvc = GWT.create(PropertyService.class);
	private UrlServiceAsync urlSvc = GWT.create(UrlService.class);

	@Override
	public void onModuleLoad() {
		initialize();
		convertButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleSubmit();
			}
		});
		urlInput.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					handleSubmit();
				}
			}
		});
	}

	public void initialize() {
		StatusView.showStatus("Loading...");

		// get the domain
		if (propertySvc == null) {
			propertySvc = GWT.create(PropertyService.class);
		}
		AsyncCallback<String> propCB = new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				showError("Error when retrieving the domain information.");
			}

			@Override
			public void onSuccess(String result) {
				setDomain(result);
			}
		};
		propertySvc.getProperty("domain", propCB);
		// -----

		if (urlSvc == null) {
			urlSvc = GWT.create(UrlService.class);
		}
		AsyncCallback<List<UrlWrapper>> tenCB = new AsyncCallback<List<UrlWrapper>>() {

			@Override
			public void onFailure(Throwable caught) {
				StatusView.hideLastStatus();
				showError("Error when getting latest shortened urls");
			}

			@Override
			public void onSuccess(List<UrlWrapper> result) {
				ArrayList<UrlWrapper> tenUrls = (ArrayList<UrlWrapper>) result;
				if (result != null) {
					for (int i = 0; i < tenUrls.size(); i++) {
						String url = tenUrls.get(i).getUrl();
						String urlContent = url;
						if (url.length() > 88) {
							urlContent = url.substring(0, 88) + "...";
						}
						HTML link = new HTML("<div class=\"urlLink\"><a target=\"_blank\" href=\"" + url + "\">" + urlContent + "</a></div>", true);
						RootPanel.get("recentUrlTable").add(link);
					}
				} else {
					showError("No url shortened now!");
				}
				StatusView.hideLastStatus();
			}
		};
		urlSvc.get10Url(tenCB);

		// urlInput.setWidth(RootPanel.get("sendingForm").getOffsetWidth() - 100
		// + "");
		convertButton.setText("submit");
		convertButton.setStyleName("submit");
		urlInput.setStyleName("inputbox");
		// hpanel.add(urlInput);
		// hpanel.add(convertButton);

		// RootPanel.get("sendingForm").add(hpanel);
		RootPanel.get("sendingForm").setStyleName("sendingForm");
		RootPanel.get("sendingForm").add(urlInput);
		RootPanel.get("sendingForm").add(convertButton);
		RootPanel.get("recentUrlTable").setStyleName("recentUrlTable");
		// RootPanel.get("recentUrlTable").add(ftable);
	}

	public void handleSubmit() {

		StatusView.showStatus("processing...");
		String unknow = urlInput.getText();
		if (unknow == null || unknow.equals("")) {
			StatusView.hideLastStatus();
			showError("no input found!");
		} else {
			if (unknow.contains(domain)) {
				String code = unknow.replaceAll(".*" + domain + "/", "");
				if (urlSvc == null) {
					urlSvc = GWT.create(UrlService.class);
				}
				AsyncCallback<UrlWrapper> getUrlCB = new AsyncCallback<UrlWrapper>() {

					@Override
					public void onFailure(Throwable caught) {
						StatusView.hideLastStatus();
						showError("Error when getting the shotened Url");
					}

					@Override
					public void onSuccess(UrlWrapper result) {
						urlInput.setText(result.getUrl());
						StatusView.hideLastStatus();
					}
				};
				urlSvc.getUrl(code, getUrlCB);
			} else if (unknow.contains(".")) {
				if (!unknow.contains("http")) {
					unknow = "http://" + unknow;
				}
				if (urlSvc == null) {
					urlSvc = GWT.create(UrlService.class);
				}
				AsyncCallback<String> shortenCB = new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						showError("Error when shortening the url.");
						StatusView.hideLastStatus();
					}

					@Override
					public void onSuccess(String result) {
						String shortenedUrl = domain + "/" + result;
						urlInput.setText(shortenedUrl);
						StatusView.hideLastStatus();
					}
				};
				urlSvc.shorten(unknow, shortenCB);
			} else {
				StatusView.hideLastStatus();
				showError("U gotta input the right url.");
			}
		}
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void showError(String errorInfo) {
		errorDialog.setText("ERROR");
		errorDialog.setAnimationEnabled(true);
		errorDialog.setGlassEnabled(true);

		Button ok = new Button("OK");
		ok.setStyleName("button");
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				errorDialog.hide();
			}
		});

		vpanel = new VerticalPanel();
		HTML info = new HTML("<br><b>" + errorInfo + "</b><br><br>");
		info.setStyleName("error");
		vpanel.add(info);
		vpanel.add(ok);
		errorDialog.setWidget(vpanel);
		errorDialog.center();
		errorDialog.show();
	}

	private static class StatusView {

		private final static DialogBox statusBox = new DialogBox();

		public static void showStatus(String status) {
			statusBox.setText("STATUS");
			statusBox.setAnimationEnabled(true);
			statusBox.setGlassEnabled(true);
			SimplePanel panel = new SimplePanel();
			HTML info = new HTML("<br>" + status + "<br><br>");
			info.setStyleName("status");
			panel.add(info);
			statusBox.setWidget(panel);
			statusBox.center();
			statusBox.show();
		}

		public static void hideLastStatus() {
			statusBox.hide();
		}
	}
}
