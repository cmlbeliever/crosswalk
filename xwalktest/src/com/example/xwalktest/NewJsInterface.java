package com.example.xwalktest;

import org.xwalk.core.XWalkExtension;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class NewJsInterface extends XWalkExtension {

	private Context context;
	private static Handler handler = new Handler();

	public NewJsInterface(Context context, String name, String jsApi) {
		super(name, jsApi);
		this.context = context;
	}

	@Override
	public void onInstanceCreated(int instanceID) {
		super.onInstanceCreated(instanceID);
	}

	@Override
	public void onMessage(final int arg0, final String arg1) {

		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "new onMessage" + arg0 + "," + arg1, Toast.LENGTH_LONG).show();
			}
		});

		postMessage(arg0, arg1);
	}

	@Override
	public String onSyncMessage(int arg0, String arg1) {
		return "from sync msg:" + arg1;
	}

	private void send(final int id, final String msg) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, "new send" + id + "," + msg, Toast.LENGTH_LONG).show();
			}
		});

		postMessage(id, "new java 处理后的msg：" + msg);
	}

}
