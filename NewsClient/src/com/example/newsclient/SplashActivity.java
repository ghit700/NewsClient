package com.example.newsclient;

import java.util.ArrayList;

import com.example.bean.Category.Channel;
import com.example.bean.News.PageMsg;
import com.example.bean.News.PageMsg.NewsContent;
import com.example.manager.NetManager;
import com.example.model.CategoryModel;
import com.example.model.NewsModel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends BaseActivity {

	ArrayList<Channel> channels;
	ArrayList<NewsContent> newsContents;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 4:
				channels = (ArrayList<Channel>) msg.obj;
				NewsModel newsModel = new NewsModel(mContext);
				newsModel.queryNews(handler, channels.get(0).channelId, 1);
				break;
			case 2:
				PageMsg pageMsg = (PageMsg) msg.obj;
				Intent intent = new Intent(mContext, MainActivity.class);
				intent.putExtra("channels", channels);
				intent.putExtra("page", pageMsg);
				startActivity(intent);
				finish();

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		if (!mNetManager.hasNetworkConnection()) {

			Intent intent = new Intent(mContext, MainActivity.class);
			Toast.makeText(mContext, "没有网络连接", 1).show();
			startActivity(intent);
			
			
		}
		
		
		CategoryModel mCategoryModel = new CategoryModel(mContext);
		mCategoryModel.getNewsCategory(handler);
	}

}
