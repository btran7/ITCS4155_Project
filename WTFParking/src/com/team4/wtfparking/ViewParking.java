package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ViewParking extends Activity {
	ArrayList<String> pk;
	JSONParse pkLotObj;
	String lotName;
	View view;
	RelativeLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lot_view_layout);
		
		/*get the URI passed over from the ParkingList class, the URI is a part of the JSONParse object, it's basically a string that looks like this:
		api/ListOfParking/1/
		add that string inbetween the url below will give:
		http://localhost:8000api/ListOfParking/1/?format=json
		
		with the url above, you will get this JSON object:
		
			{
				"comment": "", "commuter_avail": 34, "commuter_max": 434, "faculty_avail": 53, "faculty_max": 393, 
				"handicapped_avail": 0, "handicapped_max": 0, "id": 1, "lot_name": "Cone Deck", "meter_avail": 0, 
				"meter_max": 0, "resident_avail": 0, "resident_max": 0, "resource_uri": "/api/ListOfParking/1/", 
				"status": "Open", "visitor_avail": 54, "visitor_max": 473
			}
		
		now pass that object to JSONParse.java to get the class and use it to display on screen
		
		*/
		
		
		Intent i = getIntent();
		String uri = i.getStringExtra("uri");
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://localhost:8000"+uri+"?format=json";
        //method to post a GET request to the url to get the parking list JSON object
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	try {
					pkLotObj = new JSONParse(response);
					String nameTitle = pkLotObj.getLotName();
					String capTitle = pkLotObj.getLotCapacity();
					String availTitle = pkLotObj.getLotAvailability();
					String statusTitle = pkLotObj.getStatus();
					setText(nameTitle, capTitle, availTitle, statusTitle);
					startListView();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }, new Response.ErrorListener() {
 
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
 
            }
        });
        queue.add(jsObjRequest);
	}
	
	public void setText(String n, String c, String a, String s){
		
		setContentView(R.layout.lot_view_layout);
		layout = (RelativeLayout) findViewById(R.id.lot_layout);
		
		TextView nameTitle = (TextView) findViewById(R.id.lot_name_title);
		
		TextView capTitle = (TextView) findViewById(R.id.lot_capacity_title);
		TextView availTitle = (TextView) findViewById(R.id.lot_available_title);
		TextView statusTitle = (TextView)findViewById(R.id.lot_status_title);
		TextView availValue = (TextView) findViewById(R.id.lot_available_value);
		
		//***********************************************************************************************************
		//the name of the parking, the total capacity, total available and status will be display in their separate textview
		//the rest of the information (maximum capacity and available of each type: commuter, resident, meter, etc... will be 
		//displayed in a listview, since they are different for each parking lot.
		
		
		//you can  set text, color, background color, margin, shadow and whatever in the Textview with the codes below, 
		//you can also hardcode them in the layout, but these codes will overwrite whatever default in the layout.
		
		capTitle.setText("Total Capacity:  "+c);
		availTitle.setText("Available:");
		availValue.setText(a);
		statusTitle.setText("Status:  "+s);
		nameTitle.setText(n);
		nameTitle.setTextColor(Color.WHITE);
		nameTitle.setBackgroundColor(Color.rgb(0, 50, 0));
		nameTitle.setShadowLayer(2, 1, 1, Color.BLACK);
		capTitle.setTextColor(Color.WHITE);
		availTitle.setTextColor(Color.WHITE);
		availValue.setTextColor(Color.WHITE);
		statusTitle.setTextColor(Color.WHITE);
		double availInt = Double.parseDouble(a);
		double capInt = Double.parseDouble(c);
		
		if (availInt <= 5 && capInt >= 50){
			layout.setBackgroundColor(Color.rgb(255,150,125));
			capTitle.setBackgroundColor(Color.rgb(155, 10, 5));
			availValue.setBackgroundColor(Color.rgb(155, 10, 5));
			availTitle.setBackgroundColor(Color.rgb(155, 10, 5));
			statusTitle.setBackgroundColor(Color.rgb(155, 10, 5));
		} else if (availInt <= 25 && capInt >=100){
			layout.setBackgroundColor(Color.rgb(232,255,132));
			capTitle.setBackgroundColor(Color.rgb(100, 90, 1));
			availValue.setBackgroundColor(Color.rgb(100, 90, 1));
			availTitle.setBackgroundColor(Color.rgb(100, 90, 1));
			statusTitle.setBackgroundColor(Color.rgb(100, 90, 1));
		}else{
			layout.setBackgroundColor(Color.rgb(190,255,150));
			capTitle.setBackgroundColor(Color.rgb(10, 100, 5));
			availTitle.setBackgroundColor(Color.rgb(10, 100, 5));
			availValue.setBackgroundColor(Color.rgb(10, 100, 5));
			statusTitle.setBackgroundColor(Color.rgb(10, 100, 5));
		}
	}
	//********************************************************************************************************************
	public void startListView(){
		pk = pkLotObj.getList();
		//this is a simple listview with a simple adapter (android built in), it only display 1 line per row.
		ListView pkLV = (ListView) findViewById(R.id.lotView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pk);
		pkLV.setAdapter(adapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}