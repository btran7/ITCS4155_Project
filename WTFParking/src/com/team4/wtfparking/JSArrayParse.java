package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class JSArrayParse {
	ArrayList<JSONParse> pkList= new ArrayList<JSONParse>();
	/*this class accept an array of JSON objects
		1) iterate over the array, 
		2) pass each JSON object to the JSONParse class 
		3) create instances of JSONParse.java
		4) add each instance to an Array List
		5) the ParkingList Activity will call the ArrayList() method to get the Array List back.
	 */
	public JSArrayParse(JSONArray jsArray) throws JSONException{
		for (int i=0; i<jsArray.length(); i++){
			JSONParse obj = new JSONParse(jsArray.getJSONObject(i));
			if (!obj.getLotCapacity().equals("0")){
				pkList.add(obj);
			}
		}
	}
	
	ArrayList<JSONParse> getPkList(){
		return pkList;
	}
}
