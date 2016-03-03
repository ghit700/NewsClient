package com.example.newsclient;

import java.util.ArrayList;
import java.util.Iterator;


import com.example.manager.NetManager;
import com.example.newsclient.R.array;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	 Context mContext;
	public NetManager mNetManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mContext = this;
		mNetManager=new NetManager(mContext);
	
	}

}
