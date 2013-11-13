package com.team4.wtfparking;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	 private static ArrayList<JSONParse> searchArrayList;
	 
	 private LayoutInflater mInflater;

	 public MyCustomBaseAdapter(Context context, ArrayList<JSONParse> results) {
	  searchArrayList = results;
	  mInflater = LayoutInflater.from(context);
	 }

	 public int getCount() {
	  return searchArrayList.size();
	 }

	 public Object getItem(int position) {
	  return searchArrayList.get(position);
	 }

	 public long getItemId(int position) {
	  return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	  ViewHolder holder;
	  if (convertView == null) {
	   convertView = mInflater.inflate(R.layout.custom_row_view, null);
	   holder = new ViewHolder();
	   holder.tvLotName = (TextView) convertView.findViewById(R.id.lotName);
	   holder.tvCapacity = (TextView) convertView.findViewById(R.id.capacity);
	   holder.tvAvailable = (TextView) convertView.findViewById(R.id.available);
	   holder.tvStatus = (TextView) convertView.findViewById(R.id.lotStatus);
	   holder.maybe = (RelativeLayout) convertView.findViewById(R.id.custom_row);
	   
	   convertView.setTag(holder);
	  } else {
	   holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.tvLotName.setText(searchArrayList.get(position).getLotName());
	  holder.tvCapacity.setText(searchArrayList.get(position).getLotCapacity());
	  holder.tvAvailable.setText(searchArrayList.get(position).getLotAvailability());
	  holder.tvStatus.setText(searchArrayList.get(position).getStatus());
	  holder.tvLotName.setBackgroundColor(0xffcccccc);
	  
	  return convertView;
	 }

	 static class ViewHolder {
	  TextView tvLotName;
	  TextView tvCapacity;
	  TextView tvAvailable;
	  TextView tvStatus;
	  RelativeLayout maybe;
	 }
	}