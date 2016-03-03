package com.example.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;

import android.os.AsyncTask;
import android.util.Log;

public class HttpUtils {

	public interface httpCallback {
		void onSuccess(String result);
	}

	private  WeakReference<httpCallback> mHttpCallback;

	public  void request(final String httpUrl, final String httpArg,
			final httpCallback httpCallback) {

		new Thread() {
			public void run() {
				BufferedReader reader = null;
				String StrResponse = null;
				StringBuffer sbf = new StringBuffer();
				String httpUrl1 = httpUrl + "?" + httpArg;

				try {

					URL url = new URL(httpUrl1);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setRequestMethod("GET");
					// 填入apikey到HTTP header
					connection.setRequestProperty("apikey",
							"6325d3eaa9f0b5d323a4b54030be7b07");
					connection.connect();

					InputStream is = connection.getInputStream();
					reader = new BufferedReader(new InputStreamReader(is,
							"UTF-8"));
					String strRead = null;
					while ((strRead = reader.readLine()) != null) {
						sbf.append(strRead);
						sbf.append("\r\n");
					}
					reader.close();
					StrResponse = sbf.toString();

				} catch (Exception e) {
					e.printStackTrace();

				}
				if (StrResponse != null) {

					Response response = JSON.parseObject(StrResponse,
							Response.class);
					mHttpCallback = new WeakReference<HttpUtils.httpCallback>(
							httpCallback);
					mHttpCallback.get().onSuccess(response.getResult());

				}
			};
		}.start();

	}

}
