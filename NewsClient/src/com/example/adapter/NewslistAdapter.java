package com.example.adapter;

import java.util.ArrayList;

import com.example.bean.News.PageMsg.NewsContent;
import com.example.newsclient.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewslistAdapter extends BaseAdapter {

	ArrayList<NewsContent> mNews;
	Context mContext;

	public NewslistAdapter(ArrayList<NewsContent> mNews, Context mContext) {
		super();
		this.mNews = mNews;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {

		return mNews.size();
	}

	@Override
	public Object getItem(int position) {

		return mNews.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	class ViewHolder {
		// 新闻列表的textview项
		TextView title, sourse, digest, ptime;
		LinearLayout ll_list_imgs;
		ImageView img;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		ViewHolder holder = null;

		if (convertView == null) {

			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_newslist, parent, false);
			convertView = view;
			holder = new ViewHolder();
			holder.digest = (TextView) view.findViewById(R.id.newslistDigest);
			holder.title = (TextView) view.findViewById(R.id.newslistTitle);
			holder.ptime = (TextView) view.findViewById(R.id.newslistPtime);
			holder.sourse = (TextView) view.findViewById(R.id.newslistSource);
			holder.img = (ImageView) view.findViewById(R.id.img_newslist);
			holder.ll_list_imgs = (LinearLayout) view
					.findViewById(R.id.ll_list_imgs);

			convertView.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}

		NewsContent news = mNews.get(position);

		if (news.imageurls.size() == 0) {

			holder.img.setVisibility(View.GONE);
			holder.ll_list_imgs.setVisibility(View.GONE);

		} else {

			holder.ll_list_imgs.setVisibility(View.GONE);
			holder.img.setVisibility(View.VISIBLE);
			BitmapUtils bitmapUtils = new BitmapUtils(mContext);
			bitmapUtils.display(holder.img, news.imageurls.get(0).url);
		}
		// } else {
		// holder.img.setVisibility(View.GONE);
		// holder.ll_list_imgs.setVisibility(View.VISIBLE);
		// BitmapUtils bitmapUtils = new BitmapUtils(mContext);
		//
		// for (int i = 0; i < news.imageurls.size(); i++) {
		//
		// ImageView imageView = new ImageView(mContext);
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(
		// holder.ll_list_imgs.getWidth() / news.imageurls.size(),
		// LayoutParams.MATCH_PARENT);
		// layoutParams.setMargins(10, 0, 10, 0);
		// imageView.setLayoutParams(layoutParams);
		// bitmapUtils.display(imageView, news.imageurls.get(i).url);
		//
		// holder.ll_list_imgs.addView(imageView);
		// }

		holder.digest.setText("  " + news.desc);
		holder.title.setText(news.title);
		holder.sourse.setText(news.source);
		holder.ptime.setText(news.pubDate);

		return view;
	}

}
