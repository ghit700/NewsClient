package com.example.dao;

import java.util.ArrayList;

import android.os.Handler;

public interface NewsDao {

	void queryNews(Handler handler, String categoryId, int start);
}
