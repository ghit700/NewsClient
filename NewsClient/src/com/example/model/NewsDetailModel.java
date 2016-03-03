package com.example.model;

import com.alibaba.fastjson.JSON;
import com.example.bean.NewsDetail;
import com.example.dao.NewsDetailDao;
import com.example.net.HttpUtils;
import com.example.net.HttpUtils.httpCallback;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NewsDetailModel implements NewsDetailDao {

	Context mContext;
	HttpUtils mHttpUtils;

	public NewsDetailModel(Context mContext) {
		super();
		this.mContext = mContext;
		mHttpUtils = new HttpUtils();
	}

	@Override
	public void query(final Handler handler, String url) {
		
	}
}
