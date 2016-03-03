package com.example.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;





public class Category implements Serializable {
	
		public int totalNum;
		public int ret_code;
		public List<Channel> channelList;

		public static class Channel implements Serializable {
			public String name;
			public String channelId;
		}
	
		public List<Channel> getChannelList() {
			return channelList;
		}
}