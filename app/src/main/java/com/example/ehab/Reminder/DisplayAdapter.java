package com.example.ehab.Reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	//list fields to be displayed
	private ArrayList<String> taskid;
	private ArrayList<String> task;
	private ArrayList<String> time;
	

	public DisplayAdapter(Context c, ArrayList<String> taskid, ArrayList<String> task, ArrayList<String> time) {
		this.mContext = c;
		//transfer content from database to temporary memory
		this.taskid = taskid;
		this.task = task;
		this.time = time;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return taskid.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			
			//link to TextView
			mHolder.taskid = (TextView) child.findViewById(R.id.taskidcell);
			mHolder.task = (TextView) child.findViewById(R.id.taskcell);
			mHolder.time = (TextView) child.findViewById(R.id.timecell);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		//transfer to TextView in screen
		mHolder.taskid.setText(taskid.get(pos));
		mHolder.task.setText(task.get(pos));
		mHolder.time.setText(time.get(pos));
		

		return child;
	}

	public class Holder {
		TextView taskid;
		TextView task;
		TextView time;
		TextView date;
	}

}

