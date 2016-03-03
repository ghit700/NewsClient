package com.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.bean.News;
import com.example.bean.News.PageMsg;
import com.example.bean.News.PageMsg.NewsContent;

import com.example.dao.NewsDao;
import com.example.net.HttpUtils;
import com.example.net.HttpUtils.httpCallback;

import android.R.array;
import android.R.integer;
import android.content.Context;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NewsModel implements NewsDao {

	Context mContext;
	HttpUtils mHttpUtils;

	public NewsModel(Context mContext) {
		super();
		this.mContext = mContext;
		mHttpUtils = new HttpUtils();
		

	}

	// 查找新闻列表数据
	@Override
	public void queryNews(final Handler handler, final String categoryId,
			final int start) {
		
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
		StringBuilder httpArg = new StringBuilder("channelId=");
		httpArg = httpArg.append(categoryId).append("&page=").append(start);

		HttpUtils httpUtils= new HttpUtils();
		httpUtils.request(httpUrl, httpArg.toString(), new httpCallback() {

			@Override
			public void onSuccess(String result) {

				News news = JSON.parseObject(result, News.class);

				Message msg = new Message();
				msg.obj = news.getPageMsg();
				msg.what = 2;
				handler.sendMessage(msg);

			}

		});

	}

}
