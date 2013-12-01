package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class JSArrayParse {
	ArrayList<JSONParse> pkList= new ArrayList<JSONParse>();
	ArrayList<JSONParse> favList = new ArrayList<JSONParse>();
	
	public JSArrayParse(JSONArray jsArray) throws JSONException{
		for (int i=0; i<jsArray.length(); i++){
			JSONParse obj = new JSONParse(jsArray.getJSONObject(i));
			if (!obj.getLotCapacity().equals("0")){
				pkList.add(obj);
			}
			if (obj.getFav()==true){
				favList.add(obj);
			}
		}
	}
	
	ArrayList<JSONParse> getPkList(){
		return pkList;
	}
	
	ArrayList<JSONParse> getFavList(){
		return favList;
	}
}
