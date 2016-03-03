package com.example.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.bean.Category;
import com.example.bean.Category.Channel;
import com.example.dao.CategoryDao;
import com.example.net.HttpUtils;
import com.example.net.Response;
import com.example.net.HttpUtils.httpCallback;

public class CategoryModel implements CategoryDao {
	
	Context mContext;
	

	public CategoryModel(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public void getNewsCategory(final Handler handler) {
		String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/channel_news";
		String httpArg = "";
		HttpUtils httpUtils = new HttpUtils();

		httpUtils.request(httpUrl, httpArg, new httpCallback() {

			@Override
			public void onSuccess(String result) {
				
				ArrayList<Channel> channels = getChannels(result);
				
				try {

					FileOutputStream fos=mContext.openFileOutput("Categorys.txt",Activity.MODE_PRIVATE);
					fos.write(result.getBytes());
					
					Log.i("wu", result.getBytes().length+"----");
					
					fos.flush();
					fos.getFD().sync();
					fos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				
				Message msg = new Message();
				msg.obj = channels;
				msg.what = 4;
				handler.sendMessage(msg);
			}
		});

	}
	
	public static ArrayList<Channel> getChannels(String result){
		
		
		Category category = JSON.parseObject(result, Category.class);
		
	
		ArrayList<Channel> channels = (ArrayList<Channel>) category
				.getChannelList();
		
		Log.i("wu", category.totalNum+"--------------------------"+result );
		return channels;
	}

}
