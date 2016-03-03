package com.example.adapter;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bean.Category.Channel;
import com.example.bean.News.PageMsg;
import com.example.bean.News.PageMsg.NewsContent;
import com.example.model.NewsModel;
import com.example.newsclient.NewsActivity;
import com.example.newsclient.R;

public class VpNewslistAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Channel> mChannels;

	public VpNewslistAdapter(FragmentManager fragmentManager,
			ArrayList<Channel> mChannels) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
		this.mChannels = mChannels;
	}

	@Override
	public Fragment getItem(int arg0) {
		if (mChannels.size() != 0) {

			return NewslistFragment.newInstance(mChannels.get(arg0).channelId);
	
		} else {

			return null;
		}
	}

	@Override
	public int getCount() {
		return mChannels.size();
	}

	public static class NewslistFragment extends Fragment {

		private String mChannelId;

		public String getmChannelId() {
			return mChannelId;
		}

		public void setmChannelId(String mChannelId) {
			this.mChannelId = mChannelId;
		}

		private ProgressBar mBar;
		private SwipeRefreshLayout mSwipeRefreshLayout;
		private ListView mNewsListView; //
		private PageMsg page;
		private int lastItem;
		private ArrayList<NewsContent> mNews; // 新闻列表
		private NewslistAdapter mNewslistAdapter; // 新闻列表适配器
		private TextView mLoadText; // 尾列表
		private NewsModel mNewsModel;

		Handler mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				// 加载新闻列表
				case 2:

					page = (PageMsg) msg.obj;
					if (page != null && page.getContentlist() != null) {
						mNews.addAll(page.getContentlist());

						mNewslistAdapter.notifyDataSetChanged();
						// 取消刷新图标
						mSwipeRefreshLayout.setRefreshing(false);

					}
					break;

				case 100:
					break;
				default:
					break;
				}
			};
		};

		static NewslistFragment newInstance(String channelId) {
			NewslistFragment fragment = new NewslistFragment();
			fragment.setmChannelId(channelId);
			return fragment;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			mNewsModel = new NewsModel(getActivity());
			mNewsModel.queryNews(mHandler, mChannelId, 1);
			mNews = new ArrayList<NewsContent>();
			mNewslistAdapter = new NewslistAdapter(mNews, getActivity());
		}

		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub

			View view = inflater.inflate(R.layout.newslist, container, false);
			mSwipeRefreshLayout = (SwipeRefreshLayout) view
					.findViewById(R.id.swipeRefresh);
			mNewsListView = (ListView) view.findViewById(R.id.newslist);
			mBar = (ProgressBar) view.findViewById(R.id.pb_main_load);

			setNewslistView();

			initListener();

			return view;
		}

		private void setNewslistView() {

			// 设置空listview时展示的view
			mNewsListView.setEmptyView(mBar);

			// 设置适配器
			mNewsListView.setAdapter(mNewslistAdapter);

			// 添加尾列表
			View footer = LayoutInflater.from(getActivity()).inflate(
					R.layout.list_footer, null);
			mLoadText = (TextView) footer.findViewById(R.id.loadText);
			mNewsListView.addFooterView(footer);

		}

		private void initListener() {
			// 新闻列表跳转具体新闻详情
			mNewsListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),
							NewsActivity.class);
					intent.putExtra("News", mNews.get(position));
					startActivity(intent);
				}
			});

			// 新闻列表滑动事件
			mNewsListView.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					if (page.allPages == page.currentPage) {
						mLoadText.setText("该栏目下无更多新闻");
					} else if (lastItem == mNewslistAdapter.getCount()
							&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

						mNewsModel.queryNews(mHandler, mChannelId,
								page.currentPage + 1);
					}

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					lastItem = firstVisibleItem + visibleItemCount - 1;

					if (firstVisibleItem == 0)
						mSwipeRefreshLayout.setEnabled(true);
					else {
						mSwipeRefreshLayout.setEnabled(false);
					}
				}
			});

			// 设置刷新事件
			mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					mNewsModel.queryNews(mHandler, mChannelId, 1);
				}
			});
		}
	}

}
