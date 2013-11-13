package com.team4.wtfparking;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse {
	
	int id;
	int residentMax, commuterMax, facultyMax, visitorMax, handicappedMax, meterMax;
	int totalMax = 0;
	int residentAvail, commuterAvail, facultyAvail, visitorAvail,handicappedAvail, meterAvail;
	int totalAvail=0;
	String lotName, status, uri;
	String realStatus="";
	ArrayList<String> pk = new ArrayList<String>();
	//in the constructor, the received json object will be parsed into all the variables declared earlier
	public JSONParse(JSONObject json) throws JSONException{
		
		id = json.getInt("id");
		
		lotName = json.getString("lot_name");
		uri = json.getString("resource_uri");
		status = json.getString("status");
		residentMax = json.getInt("resident_max");
		commuterMax = json.getInt("commuter_max");
		facultyMax = json.getInt("faculty_max");
		visitorMax = json.getInt("visitor_max");
		handicappedMax = json.getInt("handicapped_max");
		meterMax = json.getInt("meter_max");
		totalMax += residentMax+commuterMax+facultyMax+visitorMax+handicappedMax+meterMax;
		
		residentAvail = json.getInt("resident_avail");
		commuterAvail = json.getInt("commuter_avail");
		facultyAvail = json.getInt("faculty_avail");
		visitorAvail = json.getInt("visitor_avail");
		handicappedAvail = json.getInt("handicapped_avail");
		meterAvail = json.getInt("meter_avail");
		totalAvail+=residentAvail+commuterAvail+facultyAvail+visitorAvail+handicappedAvail;
		
		if (status.equals("OP")){
			realStatus = "Open";
		}
		else if (status.equals("CL")){
			realStatus ="Closed";
		}
	}
	
	//hashmap containing all the values for each individual parking, the ViewParking Activity will use this 
	//method to display all the information of the parking in a listview.
	ArrayList <String> getList(){
		pk.add(lotName);
		pk.add("Status: "+realStatus);
		pk.add("Total Capacity:     "+Integer.toString(totalMax));
		pk.add("Total Available:     "+Integer.toString(totalAvail));
		
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
			pk.add("Visitor:                  "+Integer.toString(visitorAvail)+"/"+Integer.toString(visitorMax));
		}
		
		if (handicappedMax != 0){
			pk.add("Handicapped:       "+Integer.toString(handicappedAvail)+"/"+Integer.toString(handicappedMax));
		}
		
		if (meterMax != 0){
			pk.add("Meter:                    "+Integer.toString(meterAvail)+"/"+Integer.toString(meterMax));
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
		return realStatus;
	}
	
	String getLotCapacity(){
		return Integer.toString(totalMax);
	}
	
	String getLotAvailability(){
		return Integer.toString(totalAvail);
	}
}