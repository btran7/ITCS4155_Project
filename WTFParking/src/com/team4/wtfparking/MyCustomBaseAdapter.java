package com.team4.wtfparking;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
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
	  
	  String avail = searchArrayList.get(position).getLotAvailability();
	  String cap = searchArrayList.get(position).getLotCapacity();
	  
	  holder.tvLotName.setText(searchArrayList.get(position).getLotName());
	  holder.tvCapacity.setText(cap);
	  holder.tvAvailable.setText(avail);
	  holder.tvStatus.setText(searchArrayList.get(position).getStatus());
	  
	  int availInt = Integer.parseInt(avail);
	  int capInt = Integer.parseInt(cap);
	  if (availInt <= 5 && capInt >= 50){
			convertView.setBackgroundColor(Color.rgb(255,150,125));
			holder.tvLotName.setTextColor(Color.WHITE);
			holder.tvLotName.setBackgroundColor(Color.rgb(155, 10, 5));
		} else if (availInt <= 25 && capInt >=100){
			convertView.setBackgroundColor(Color.rgb(232,255,132));
			holder.tvLotName.setTextColor(Color.WHITE);
			holder.tvLotName.setBackgroundColor(Color.rgb(100, 90, 1));
			
		}else{
			convertView.setBackgroundColor(Color.rgb(190,255,150));
			holder.tvLotName.setTextColor(Color.WHITE);
			holder.tvLotName.setBackgroundColor(Color.rgb(10, 100, 5));
		}
	 
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