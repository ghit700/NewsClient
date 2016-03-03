package com.example.model;

import com.example.bean.JsonComment;
import com.example.dao.CommentDao;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class CommentModel implements CommentDao {

	Context mContext;
	HttpUtils mHttpUtils;

	public CommentModel(Context mContext) {
		super();
		this.mContext = mContext;
		mHttpUtils=new HttpUtils();
	}

	@Override
	public void query(final Handler handler, int nid, int startnid,final int state) {
		StringBuffer url = new StringBuffer(
				"http://115.28.152.201:8080/znews/getComments?count=10&nid=");
		url = url.append(nid).append("&startnid=").append(startnid);
		mHttpUtils.send(HttpMethod.GET, url.toString(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
//						JsonComment jsonComment = gson.fromJson(arg0.result,
//								JsonComment.class);

//						if (jsonComment.getNum()==0) {
//							handler.sendEmptyMessage(100);
//						}else if(state==1){
//							Message msg = new Message();
//							msg.what = 1;
//							msg.obj = jsonComment.getData();
//							handler.sendMessage(msg);
//						}else if(state==2) {
//							Message msg = new Message();
//							msg.arg1=jsonComment.getNum();
//							msg.what=3;
//							handler.sendMessage(msg);
//						}
						
					}
				});

	}

	@Override
	public void postComment(final Handler handler, final int nid, String region,
			String content) {
		String url="http://115.28.152.201:8080/znews/postComment";
		
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("nid", String.valueOf(nid));
		params.addQueryStringParameter("region", region);
		params.addQueryStringParameter("content", content);
		
		mHttpUtils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				handler.sendEmptyMessage(2);
			
			}
		});
	};

}
