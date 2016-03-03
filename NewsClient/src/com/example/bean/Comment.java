package com.example.bean;

import android.R.integer;

public class Comment {

	public String region; // 地址
	public String content; // 评论内容
	public int supportCount; // 支持数
	public int cid; // 评论的id
	public String ptime; // 时间
	public int opposeCount; // 反对数

	public Comment(String region, String content, int supportCount, int cid,
			String ptime, int opposeCount) {
		super();
		this.region = region;
		this.content = content;
		this.supportCount = supportCount;
		this.cid = cid;
		this.ptime = ptime;
		this.opposeCount = opposeCount;
	}

}
