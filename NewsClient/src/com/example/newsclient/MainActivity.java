package com.example.newsclient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CategoryAdapter;
import com.example.adapter.CategoryAdapter.ViewHolder;
import com.example.adapter.NewslistAdapter;
import com.example.adapter.VpNewslistAdapter;
import com.example.bean.Category.Channel;
import com.example.bean.News.PageMsg;
import com.example.bean.News.PageMsg.NewsContent;
import com.example.manager.UpdateManager;
import com.example.model.CategoryModel;
import com.example.model.NewsModel;

public class MainActivity extends BaseActivity implements OnClickListener {

	ArrayList<Channel> mCategorybars; // 新闻类型表

	MenuDrawer mDrawer;
	TextView mTv_CheckUpdate;

	// 界面

	RecyclerView rv_main_categorybars;
	CategoryAdapter mCategoryAdapter;

	ProgressBar mProgressBar, mPB_main_load, mPB_main_loadMore;

	ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDrawer = mDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT,
				Position.TOP);
		mDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_BEZEL);
		mDrawer.setContentView(R.layout.activity_main);
		mDrawer.setMenuView(R.layout.menu_update);
		mDrawer.setMenuSize(160);

		// 初始化界面
		initView();

		// 初始化新闻类型表
		initCategoryBar();
		// 添加点击事件
		setListener();

	}

	private void initCategoryBar() {
		// 新闻类型条目 从资源文件中获取,并初始化新闻类型条目
		// mCategoryModel = new CategoryModel();
		// mCategoryModel.getNewsCategory(mHandler);

		if (getIntent().getSerializableExtra("channels") == null) {
			mCategorybars=new ArrayList<Channel>();
			FileInputStream fis;
			try {
				fis = openFileInput("Categorys.txt");
				StringBuffer sb = new StringBuffer();
				byte[] data = new byte[2861];
				while ((fis.read(data,0,data.length)) != -1) {
					
					String st=new String(data);
					sb.append(st);
					Log.i("wu",sb.toString().length()+"------"+st.length() );
				}
				mCategorybars = CategoryModel.getChannels(sb.toString().trim());
				
				fis.close();

			} catch (Exception e) {
				Log.i("zhen", e.toString());
				Log.i("wu", e.getMessage());
				e.printStackTrace();
			}
			mCategoryAdapter = new CategoryAdapter(mCategorybars, mContext);
			rv_main_categorybars.setAdapter(mCategoryAdapter);

			mPager.setAdapter(new VpNewslistAdapter(
					getSupportFragmentManager(), mCategorybars));
		} else {

			mCategorybars = (ArrayList<Channel>) getIntent()
					.getSerializableExtra("channels");
			try {

				FileOutputStream fos = openFileOutput("Categorys.xml",
						MODE_PRIVATE);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mCategoryAdapter = new CategoryAdapter(mCategorybars, mContext);
			rv_main_categorybars.setAdapter(mCategoryAdapter);

			mPager.setAdapter(new VpNewslistAdapter(
					getSupportFragmentManager(), mCategorybars));
		}

	}

	// 初始化界面
	private void initView() {
		rv_main_categorybars = (RecyclerView) findViewById(R.id.rv_main_categorybar);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
				mContext, LinearLayoutManager.HORIZONTAL, false);

		rv_main_categorybars.setLayoutManager(linearLayoutManager);

		mPager = (ViewPager) findViewById(R.id.vp_newslist);
		mTv_CheckUpdate = (TextView) findViewById(R.id.tv_checkUpdate);

	}

	private int statepage = 0;

	private void changeCategorybarsSelected(int position, boolean isSelected) {
		View itemView = rv_main_categorybars.getChildAt(position);
		TextView title = (TextView) itemView.findViewById(R.id.text1);
		changeTextViewState(title, isSelected);

	}

	private void changeTextViewState(TextView title, boolean isSelected) {
		if (isSelected) {
			title.setBackgroundResource(R.drawable.category_shape);
		} else {
			title.setBackgroundDrawable(null);
		}

		title.getPaint().setFakeBoldText(isSelected);
		title.setSelected(isSelected);
	};

	private int scrollState;

	// 设置监听
	private void setListener() {

		mTv_CheckUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				UpdateManager updateManager = new UpdateManager(mContext);
				updateManager.checkUpdate();
				mDrawer.closeMenu();
			}
		});

		rv_main_categorybars
				.setOnScrollListener(new RecyclerView.OnScrollListener() {

					@Override
					public void onScrolled(RecyclerView recyclerView, int dx,
							int dy) {
						// TODO Auto-generated method stub
						super.onScrolled(recyclerView, dx, dy);
						for (int i = 0; i < rv_main_categorybars
								.getChildCount(); i++) {
							View itemView = rv_main_categorybars.getChildAt(i);

							TextView title = (TextView) itemView
									.findViewById(R.id.text1);
							if (title.getText().equals(
									mCategorybars.get(statepage).name)) {
								changeTextViewState(title, true);
							}
						}
					}

					@Override
					public void onScrollStateChanged(RecyclerView recyclerView,
							int newState) {
						// TODO Auto-generated method stub
						super.onScrollStateChanged(recyclerView, newState);
						scrollState = newState;
					}

				});

		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				statepage = arg0;
				rv_main_categorybars.smoothScrollToPosition(arg0);

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					String name = mCategorybars.get(arg0).name;
					for (int i = 0; i < rv_main_categorybars.getChildCount(); i++) {

						View itemView = rv_main_categorybars.getChildAt(i);

						TextView title = (TextView) itemView
								.findViewById(R.id.text1);
						if (title.getText().equals(name)) {
							changeTextViewState(title, true);

						} else {

							changeTextViewState(title, false);
						}
					}
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		mCategoryAdapter
				.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {

					public void OnItemClick(ViewHolder viewHolder, int position) {

						// // 将所有item设置为未选中状态
						for (int i = 0; i < rv_main_categorybars
								.getChildCount(); i++) {
							changeCategorybarsSelected(i, false);
						}

						TextView title = viewHolder.getTextView();
						changeTextViewState(title, true);

						mPager.setCurrentItem(position);
					}

				});

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.check:
			UpdateManager updateManager = new UpdateManager(mContext);
			updateManager.checkUpdate();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
