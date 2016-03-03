package com.example.bean;

import java.io.Serializable;

public class NewsDetail implements Serializable {

	public String pic;
	public String body;
	public String title;

	public String getBody() {
		return body;
	}

	public String getPic() {
		return pic;
	}

}