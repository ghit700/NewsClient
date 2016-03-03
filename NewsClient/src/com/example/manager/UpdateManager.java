package com.example.manager;

import java.io.File;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsclient.BaseActivity;
import com.example.newsclient.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 软件更新的操作 1.显示是否更新的对话框 2.从服务器端获取version 3.获取自己本身apk的版本号
 * 4.两者进行比较，如果服务器的版本号比较新，那就下载下来 5.下载apk 6，下载apk之后，进行安装
 * 
 * @author nan
 * 
 */
public class UpdateManager {

	Context mContext;
	HttpUtils mHttpUtils;
	String target;// 存放的路径地址
	int serverVersion; // 服务器的版本号
	double rate; // 进度
	ProgressBar progressBar;// 进度条
	HttpHandler mHandler;
	AlertDialog dialog;

	public UpdateManager(Context mContext) {
		super();
		this.mContext = mContext;
		mHttpUtils = new HttpUtils();
	}

	// 获取本身apk的版本号
	public int getAPKVersion() {
		int apkVersionCode = 0;

		try {
			apkVersionCode = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}
		return apkVersionCode;

	}

	// 获取服务器端的版本号
	public void getServerVersion() {
		serverVersion = 0;

		String url = "http://115.28.152.201:8080/znews/version.json";

		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(mContext, "网络请求失败", 1).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				serverVersion = Integer.parseInt(arg0.result);

				int i = getAPKVersion();
				if (serverVersion > i) {
					new AlertDialog.Builder(mContext)
							.setMessage("检查到有新版本，是否更新?")
							.setPositiveButton("确认", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									if (((BaseActivity) mContext).mNetManager
											.isWifiReachable()) {
										// 显示对话框
										showDialog();
										// 开始下载
										downloadAPK();
									} else {
										new AlertDialog.Builder(mContext)
												.setMessage("不是处于wifi环境是否还要下载?")
												.setPositiveButton("取消", null)
												.setNegativeButton("确认",
														new OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																// 显示对话框
																showDialog();
																// 开始下载
																downloadAPK();

															}
														}).create().show();
									}

								}
							}).setNegativeButton("取消", null).create().show();
				} else {
					Toast.makeText(mContext, "当前版本已经是最新版本", 1).show();
				}
			}
		});

	}

	// 下载apk
	public void downloadAPK() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// 获得存储卡的路径
			String sdpath = Environment.getExternalStorageDirectory() + "/";
			target = sdpath + "download/News.apk";

			mHandler = mHttpUtils.download(
					"http://115.28.152.201:8080/znews/NewsClient.apk", target,
					new RequestCallBack<File>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {

						}

						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							dialog.dismiss();

							new AlertDialog.Builder(mContext)
									.setMessage("是否要安装")
									.setPositiveButton("确认",
											new OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													installAPK();
												}
											}).setNegativeButton("取消", null)
									.create().show();

						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {

							// total 总的大小
							// current 当前下载的进度

							rate = (double) current / total;

							progressBar.setProgress((int) (rate * 100));

						}
					});
		}
	}

	//

	// 安装apk
	public void installAPK() {

		File file = new File(target);

		if (!file.exists()) {
			return;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + file.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}

	public void checkUpdate() {

		getServerVersion();

	}

	// 进度条的dialog
	public void showDialog() {
		dialog = new AlertDialog.Builder(mContext).create();

		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_progress, null);

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);

		dialog.setButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mHandler.cancel();
			}
		});

		dialog.setView(view);

		dialog.show();

	}

}
