package com.example.dao;

import android.os.Handler;

public interface CommentDao {
	void query(Handler handler,int nid,int startnid,int state);
	void postComment(Handler handler,int nid,String region,String content);
}
