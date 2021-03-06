package com.example.xwalktest;

import java.lang.reflect.Method;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkAutofillClient;
import org.xwalk.core.internal.XWalkSettings;
import org.xwalk.core.internal.XWalkViewBridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Toast;

public class MainActivity extends XWalkActivity {

	private XWalkView view;
	private static String name = "nativeApp";
	private JsInterface js;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		js = new JsInterface(this, name, getString(R.string.jsapi));
		new NewJsInterface(this, "testApp", getString(R.string.jsapi_new));
		view = (XWalkView) findViewById(R.id.test);
	}

	public void callJs(View v) {
		view.load("javascript:show()", null);
		startActivity(new Intent(this, MainActivity.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (view != null) {
			view.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (view != null) {
			view.onNewIntent(intent);
		}
	}

	@Override
	protected void onXWalkReady() {

		// 配置调试功能
		XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, true);
		XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
		try {
			Method _getBridge = XWalkView.class.getDeclaredMethod("getBridge");
			_getBridge.setAccessible(true);
			XWalkViewBridge xWalkViewBridge = (XWalkViewBridge) _getBridge.invoke(view);
			XWalkSettings xWalkSettings = xWalkViewBridge.getSettings();
			xWalkSettings.setJavaScriptEnabled(true);
			xWalkSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		view.setResourceClient(new XWalkResourceClient(view) {
			@Override
			public void onLoadFinished(XWalkView view, String url) {
				super.onLoadFinished(view, url);
			}

			@Override
			public void onLoadStarted(XWalkView view, String url) {
				super.onLoadStarted(view, url);
			}
		});

		XWalkAutofillClient client = XWalkAutofillClient.create(1);

		view.setUIClient(new XWalkUIClient(view) {
			@Override
			public boolean onJavascriptModalDialog(XWalkView view, JavascriptMessageType type, String url,
					String message, String defaultValue, XWalkJavascriptResult result) {
				Toast.makeText(getApplicationContext(), "onJavascriptModalDialog", Toast.LENGTH_LONG).show();
				return super.onJavascriptModalDialog(view, type, url, message, defaultValue, result);
			}

			@Override
			public void onPageLoadStarted(XWalkView view, String url) {
				Toast.makeText(getApplicationContext(), "onPageLoadStarted", Toast.LENGTH_LONG).show();
				super.onPageLoadStarted(view, url);
			}

		});

		// view.addJavascriptInterface(new JsInterface(this, null, "alert(11)"),
		// "nativeApp");

		view.load("file:///android_asset/test.html", null);
		view.load("javascript:show()", null);
	}

}
