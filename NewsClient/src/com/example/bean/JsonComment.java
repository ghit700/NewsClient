package com.example.bean;

import java.util.ArrayList;

import android.R.integer;

/**
 * 解析从网络上取得的数据
 * 
 * @author nan
 * 
 */
class CommentDate {
	public ArrayList<Comment> commentslist;
	public int totalnum;
}

public class JsonComment {

	public int ret; //
	public CommentDate data;
	public String msg;

	//返回评论的数据
	public ArrayList<Comment> getData(){
		return data.commentslist;
	}
	//返回评论数
	public int getNum(){
		return data.totalnum;
	}
}
