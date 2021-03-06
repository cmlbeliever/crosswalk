package com.example.xwalktest;

import org.xwalk.core.XWalkExtension;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class JsInterface extends XWalkExtension {

	private Context context;

	private static Handler handler = new Handler();

	public JsInterface(Context context, String name, String jsApi) {
		super(name, jsApi);
		this.context = context;
	}

	private void send(final int id, final String msg) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "send" + id + "," + msg, Toast.LENGTH_LONG).show();
			}
		});

		postMessage(id, "java 处理后的msg：" + msg);
	}

	@Override
	public void onMessage(final int arg0, final String arg1) {
		// Looper.prepare();
		// Toast.makeText(context, "onMessage" + arg0 + "," + arg1,
		// Toast.LENGTH_LONG).show();
		// Looper.loop();

		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "onMessage" + arg0 + "," + arg1, Toast.LENGTH_LONG).show();
			}
		});

		postMessage(arg0, arg1);
	}
	
	@Override
	public void onInstanceCreated(final int instanceID) {
		super.onInstanceCreated(instanceID);
		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "onInstanceCreated" +instanceID, Toast.LENGTH_LONG).show();
			}
		});

	}
	
	@Override
	public void onInstanceDestroyed(final int instanceID) {
		super.onInstanceDestroyed(instanceID);
		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "onInstanceDestroyed" +instanceID, Toast.LENGTH_LONG).show();
			}
		});

	}

	@Override
	public String onSyncMessage(int arg0, String arg1) {
		return "from sync msg:" + arg1;
	}

}
