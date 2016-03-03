package com.example.adapter;

import java.util.ArrayList;

import com.example.bean.Comment;
import com.example.newsclient.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	ArrayList<Comment> mComments;
	Context mContext;

	@Override
	public int getCount() {

		return mComments.size();
	}

	public CommentAdapter(ArrayList<Comment> mComments, Context mContext) {
		super();
		this.mComments = mComments;
		this.mContext = mContext;
	}

	@Override
	public Object getItem(int position) {

		return mComments.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	class ViewHolder{
		TextView region,content,ptime;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view ;
		ViewHolder holder =null;
		if (convertView==null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
			convertView=view;
			
			holder=new ViewHolder();
			holder.region=(TextView)view.findViewById(R.id.comment_region);
			holder.content=(TextView)view.findViewById(R.id.comment_content);
			holder.ptime=(TextView)view.findViewById(R.id.comment_ptime);
			
			convertView.setTag(holder);
		}else {
			view=convertView;
			holder=(ViewHolder) convertView.getTag();
		}
		
		Comment comment = mComments.get(position);
		
		holder.region.setText(comment.region);
		holder.content.setText(comment.content);
		holder.ptime.setText(comment.ptime);
		
		return view;
	}

}
