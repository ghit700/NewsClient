package com.example.newsclient;

import java.util.IllegalFormatCodePointException;

import com.example.bean.News.PageMsg.NewsContent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsActivity extends BaseActivity {

	WebView mWeb;
	NewsContent mNewsContent;
	ProgressBar mProgressBar;
	
	private final static int MIN_MOVE_X=200;
	private final static int MIN_MOVE_Y=50;
	
	private GestureDetector mDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		MyGestureListener	mlistener=new MyGestureListener();
		mDetector=new GestureDetector(this, mlistener);
		
	
		initView();
		initDate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mDetector.onTouchEvent(event);
	}
	
	
	
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			
			if (e2.getX()-e1.getX()>MIN_MOVE_X&&Math.abs(e1.getY()-e2.getY())<MIN_MOVE_Y) {
				finish();
			}
			return true;
		}
		
	}
	
	
	private void initDate() {
		Intent intent=getIntent();
		mNewsContent=(NewsContent) intent.getSerializableExtra("News");
		mWeb.getSettings().setJavaScriptEnabled(true);
		mWeb.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return mDetector.onTouchEvent(event);
			}
		});
		mWeb.loadUrl(mNewsContent.link);
		mWeb.setWebViewClient(new WebViewClient(){
			
			
			
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				
				Toast.makeText(mContext, "载入页面出现错误,请重新点击"	, 1).show();
				finish();
				
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mProgressBar.setVisibility(View.GONE);

				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
		
	}
	private void initView() {
		mWeb=(WebView)findViewById(R.id.web);
		mProgressBar=(ProgressBar)findViewById(R.id.pb_news_load);
	}

}
