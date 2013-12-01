package com.team4.wtfparking;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	 private static ArrayList<JSONParse> searchArrayList;
	 
	 private LayoutInflater mInflater;
	 
	 Context applicationContext = MainActivity.getContextOfApplication();
	 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
	 
	 public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
		 final String name = searchArrayList.get(position).getLotName();
		 if (convertView == null) {
			 convertView = mInflater.inflate(R.layout.custom_row_view, null);
			 holder = new ViewHolder();
			 holder.tvLotName = (TextView) convertView.findViewById(R.id.lotName);
			 holder.tvCapacity = (TextView) convertView.findViewById(R.id.capacity);
			 holder.tvAvailable = (TextView) convertView.findViewById(R.id.available);
			 holder.tvStatus = (TextView) convertView.findViewById(R.id.lotStatus);
			 holder.tvComment = (TextView) convertView.findViewById(R.id.lotComment);
			 holder.favorite = (CheckBox) convertView.findViewById(R.id.star);
			 holder.maybe = (RelativeLayout) convertView.findViewById(R.id.custom_row);
			 
			 holder.favorite.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) { 
					 CheckBox box = (CheckBox) v;
					 savePreferences(name, box.isChecked());
				 }
			 });
		 } 
		 else {
			 holder = (ViewHolder) convertView.getTag();
		 }
   
		 convertView.setTag(holder);
		 String avail = searchArrayList.get(position).getLotAvailability();
		 String cap = searchArrayList.get(position).getLotCapacity();
		 String status = searchArrayList.get(position).getStatus();
	  
		 holder.tvLotName.setText(searchArrayList.get(position).getLotName());
		 holder.tvCapacity.setText(cap);
		 holder.tvAvailable.setText(avail);
		 holder.tvComment.setText(searchArrayList.get(position).getComment());
		 holder.tvComment.setSelected(true);
		 holder.tvComment.requestFocus();
		 
		 boolean isFavorite = sharedPreferences.getBoolean(name, false);
		 if (isFavorite){
			 holder.favorite.setChecked(true);
		 }
		 else{
			 holder.favorite.setChecked(false);
		 }
	  
		 int availInt = Integer.parseInt(avail);
		 int capInt = Integer.parseInt(cap);
		 if (status.equals("Closed")){
			 holder.tvAvailable.setText("NONE");
			 holder.tvStatus.setText("Lot is Closed");
			 convertView.setBackgroundColor(Color.rgb(255,150,125));
		 }
		 else {
			 if (availInt == 0){
				 holder.tvAvailable.setText("FULL");
				 holder.tvStatus.setText("No Slot Available");
				 convertView.setBackgroundColor(Color.rgb(255,150,125));
			 }
			 else if (availInt <= 5 && capInt >= 50){
				 holder.tvStatus.setText("Low Availability");
				 convertView.setBackgroundColor(Color.rgb(255,150,125));
	//			 holder.tvLotName.setTextColor(Color.WHITE);
	//			 holder.tvLotName.setBackgroundColor(Color.rgb(155, 10, 5));
			 } 
			 else if (availInt <= 25 && capInt >=100){
				 holder.tvStatus.setText("Medium Availability");
				 convertView.setBackgroundColor(Color.rgb(232,255,132));
	//			 holder.tvLotName.setTextColor(Color.WHITE);
	//			 holder.tvLotName.setBackgroundColor(Color.rgb(100, 90, 1));
				
			 }
			 else{
				 holder.tvStatus.setText("High Availability");
				 convertView.setBackgroundColor(Color.rgb(190,255,150));
	//			 holder.tvLotName.setTextColor(Color.WHITE);
	//			 holder.tvLotName.setBackgroundColor(Color.rgb(10, 100, 5));
			 }
		 }
		 
	  	return convertView;
	 }
	 
	 public void savePreferences(String key, boolean value){
		 Editor editor = sharedPreferences.edit();
		 editor.putBoolean(key, value);
		 editor.commit();
	 }

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
	 
	 static class ViewHolder {
	  TextView tvLotName;
	  TextView tvCapacity;
	  TextView tvAvailable;
	  TextView tvStatus;
	  TextView tvComment;
	  CheckBox favorite;
	  RelativeLayout maybe;
	 }
	}