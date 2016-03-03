package com.example.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class News implements Serializable {
	public int ret_code;
	public PageMsg pagebean;

	public class PageMsg implements Serializable {
		public int allPages;
		public int maxResult;
		public int currentPage;
		public int allNum;
		public List<NewsContent> contentlist;

		public class NewsContent implements Serializable {
			public String link;
			public String channelName;
			public String source;
			public String title;
			public String pubDate;
			public String channelId;
			public String desc;
			public List<Imageurls> imageurls;

			public class Imageurls implements Serializable {
				public int width;
				public String url;
				public int height;
			}
		}

		public List<NewsContent> getContentlist() {
			return contentlist;
		}
	}

	public PageMsg getPageMsg() {
		return pagebean;
	}
}
