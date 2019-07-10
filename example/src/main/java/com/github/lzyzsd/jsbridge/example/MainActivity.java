package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class MainActivity extends Activity implements OnClickListener {

	BridgeWebView webView;

	Button button;

	TextView tv1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        webView = (BridgeWebView) findViewById(R.id.webView);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);

		tv1 = (TextView)findViewById(R.id.tv1);

		webView.setDefaultHandler(new DefaultHandler());
		webView.loadUrl("file:///android_asset/demo.html");
		//供给js调用
		webView.registerHandler("submitFromWeb", new BridgeHandler() {
			@Override
			public void handler(String data, CallBackFunction function) {
                function.onCallBack("这是Java被调用的结果");
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (button.equals(v)) {
			//调用js方法，并接收回调
            webView.callHandler("functionInJs", "native有数据发给js", new CallBackFunction() {
				@Override
				public void onCallBack(String data) {
					tv1.setText("接收到数据啦：from js:"+data);
				}
			});
		}
	}

}
