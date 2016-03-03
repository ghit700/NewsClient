package com.example.adapter;

import java.util.ArrayList;

import com.example.bean.Category.Channel;
import com.example.newsclient.R;

import android.R.integer;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CategoryAdapter extends
		RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

	public interface OnItemClickListener {
		void OnItemClick(ViewHolder viewHolder, int position);
	}

	ArrayList<Channel> mCategories;
	Context mContext;
	private OnItemClickListener mOnItemClickListener;

	public OnItemClickListener getmOnItemClickListener() {
		return mOnItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;
	}

	public CategoryAdapter(ArrayList<Channel> mCategories, Context mContext) {
		super();
		this.mCategories = mCategories;
		this.mContext = mContext;
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements
			View.OnClickListener {

		private TextView textView;
		CategoryAdapter mCategoryAdapter;
		View view;

		public TextView getTextView() {
			return textView;
		}

		public ViewHolder(View arg0, CategoryAdapter categoryAdapter) {
			super(arg0);
			view = arg0;
			arg0.setOnClickListener(this);
			mCategoryAdapter = categoryAdapter;
			textView = (TextView) arg0.findViewById(R.id.text1);
		}

		public void setText(String text) {
			textView.setText(text);
		}

		@Override
		public void onClick(View v) {
			final OnItemClickListener listener = mCategoryAdapter
					.getmOnItemClickListener();
			if (listener != null) {
				listener.OnItemClick(this, getPosition());
			}
		}

	}

	@Override
	public int getItemCount() {

		return mCategories.size();

	}

	boolean isFirst=true;
	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		Channel channel = mCategories.get(arg1);

		arg0.setText(channel.name);
		if (arg1 == 0&&isFirst) {
			arg0.getTextView().setBackgroundResource(R.drawable.category_shape);
			arg0.getTextView().getPaint().setFakeBoldText(true);
			arg0.getTextView().setSelected(true);
			isFirst=false;
		} else {
			arg0.getTextView().setBackgroundDrawable(null);
			arg0.getTextView().getPaint().setFakeBoldText(false);
			arg0.getTextView().setSelected(false);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view;

		view = LayoutInflater.from(mContext).inflate(R.layout.item_categorybar,
				null);
		return new ViewHolder(view, this);
	}

}
