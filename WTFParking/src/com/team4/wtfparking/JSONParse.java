package com.team4.wtfparking;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class JSONParse {
	
	int id;
	int residentMax=0, commuterMax=0, facultyMax=0, visitorMax=0, handicappedMax=0, meterMax=0;
	int totalMax = 0;
	int residentAvail=0, commuterAvail=0, facultyAvail=0, visitorAvail=0,handicappedAvail=0, meterAvail=0;
	int totalAvail=0;
	String lotName, status, uri, comment;
	boolean isFav= false;
	ArrayList<String> pk = new ArrayList<String>();
	
	//in the constructor, the received json object will be parsed into all the variables declared earlier
	public JSONParse(JSONObject json) throws JSONException{
		
		Context applicationContext = MainActivity.getContextOfApplication();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
		
		lotName = json.getString("lot_name");
		uri = json.getString("resource_uri");
		status = json.getString("status");
		comment = json.getString("comment");
		
		id = json.getInt("id");
		
		boolean filterCommuter = sharedPreferences.getBoolean("commuter_value", false);
		boolean filterFaculty = sharedPreferences.getBoolean("faculty_value", false);
		boolean filterResident = sharedPreferences.getBoolean("resident_value", false);
		boolean filterVisitor = sharedPreferences.getBoolean("visitor_value", false);
		boolean filterHandicapped = sharedPreferences.getBoolean("handicapped_value", false);
		boolean filterMeter = sharedPreferences.getBoolean("meter_value", false);
		boolean filterFav = sharedPreferences.getBoolean(lotName, false);
		
		if (filterFav){
			isFav=true;
		}
			
		
		if (!filterMeter){
			meterMax = json.getInt("meter_max");
			meterAvail = json.getInt("meter_avail");
		}
		
		if (!filterCommuter){
			commuterMax = json.getInt("commuter_max");
			commuterAvail = json.getInt("commuter_avail");
		}
		
		if (!filterResident){
			residentMax = json.getInt("resident_max");
			residentAvail = json.getInt("resident_avail");
		}
		
		if (!filterFaculty){
			facultyMax = json.getInt("faculty_max");
			facultyAvail = json.getInt("faculty_avail");
		}
		
		if (!filterVisitor){
			visitorMax = json.getInt("visitor_max");
			visitorAvail = json.getInt("visitor_avail");
		}
		
		if (!filterHandicapped){
			handicappedMax = json.getInt("handicapped_max");
			handicappedAvail = json.getInt("handicapped_avail");
		}
			
		totalMax += residentMax+commuterMax+facultyMax+visitorMax+handicappedMax+meterMax;
		totalAvail+=residentAvail+commuterAvail+facultyAvail+visitorAvail+handicappedAvail;
	}
	
	//hashmap containing all the values for each individual parking, the ViewParking Activity will use this 
	//method to display all the information of the parking in a listview.
	ArrayList <String> getList(){
		
		if (residentMax !=0){
			pk.add("Resident:               "+Integer.toString(residentAvail)+"/"+Integer.toString(residentMax));
		}
		
		if (commuterMax != 0){
			pk.add("Commuter:            "+Integer.toString(commuterAvail)+"/"+Integer.toString(commuterMax));
		}
		
		if (facultyMax != 0){
			pk.add("Faculty:                  "+Integer.toString(facultyAvail)+"/"+Integer.toString(facultyMax));
		}
		
		if (visitorMax !=0){
			pk.add("Visitor:                   "+Integer.toString(visitorAvail)+"/"+Integer.toString(visitorMax));
		}
		
		if (handicappedMax != 0){
			pk.add("Handicapped:       "+Integer.toString(handicappedAvail)+"/"+Integer.toString(handicappedMax));
		}
		
		if (meterMax != 0){
			pk.add("Meter:                     "+Integer.toString(meterAvail)+"/"+Integer.toString(meterMax));
		}
			
		return pk;
	}
	
	//return the id of the parking
	int getID(){
		return id;
	}
	
	//return lot name
	String getLotName(){
		return lotName;
	}
	
	String getUri(){
		return uri;
	}
	
	String getStatus(){
		if (status.equals("OP")){
			status="Open";
		}else if (status.equals("CL")){
			status="Closed";
		}
		return status;
	}
	
	String getLotCapacity(){
		return Integer.toString(totalMax);
	}
	
	String getLotAvailability(){
		return Integer.toString(totalAvail);
	}
	
	String getComment(){
		return comment;
	}
	boolean getFav(){
		return isFav;
	}
}