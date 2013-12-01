package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


public class ViewParking extends Activity {
	ArrayList<String> pk;
	JSONParse pkLotObj;
	String lotName;
	View view;
	String uri = "";
	String nameTitle, capTitle, availTitle, statusTitle, statusComment;
	RelativeLayout layout;
	Button refresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lot_view_layout);
		Intent i = getIntent();
		uri = i.getStringExtra("uri");
		
		//run the thread that will automatically refresh the list of parking every 10 seconds
		//by calling the getParkingLot method, which will post a GET request to the application server,
		//parse the data, and start all the textviews and the listview again.
		autoRefresh.start();
		
		//the refresh button is similar to the thread right above, except that it will immediately refresh the 
		//view.
		refresh = (Button) findViewById(R.id.view_lot_refresh);
		refresh.setOnClickListener(refreshButtonListener);
	}
	
	//implement the button listener for the refresh button.
	private OnClickListener refreshButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			getParkingLot();
		}		
	};
	
	//implement the thread 
	private Thread autoRefresh = new Thread(){

		@Override
		public void run() {
			while(!isInterrupted()){
				getParkingLot();
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
				}
			}
		}
		
	};
	
	public void getParkingLot(){
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://192.168.1.118:8000"+uri+"?format=json";
        //method to post a GET request to the url to get the parking list JSON object
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	try {
					pkLotObj = new JSONParse(response);
					nameTitle = pkLotObj.getLotName();
					capTitle = pkLotObj.getLotCapacity();
					availTitle = pkLotObj.getLotAvailability();
					statusTitle = pkLotObj.getStatus();
					statusComment = pkLotObj.getComment();
					setText(nameTitle, capTitle, availTitle, statusTitle, statusComment);
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
	//set Textview and color depending on the number of available and status of parking lot
	public void setText(String n, String c, String a, String s, String cmt){
		layout = (RelativeLayout) findViewById(R.id.lot_layout);
		
		TextView nameTitle = (TextView) findViewById(R.id.lot_name_title);
		
		TextView capTitle = (TextView) findViewById(R.id.lot_capacity_title);
		TextView availTitle = (TextView) findViewById(R.id.lot_available_title);
		TextView statusTitle = (TextView) findViewById(R.id.lot_status_title);
		TextView statusValue = (TextView)findViewById(R.id.lot_status);
		TextView availValue = (TextView) findViewById(R.id.lot_available_value);
		
		availTitle.setText("Total Available:");
		availValue.setText(a+"/"+c);
		statusValue.setText(cmt);
		nameTitle.setText(n);
		nameTitle.setTextColor(Color.WHITE);
		nameTitle.setBackgroundColor(Color.rgb(0, 50, 0));
		capTitle.setTextColor(Color.WHITE);
		availTitle.setTextColor(Color.WHITE);
		availValue.setTextColor(Color.WHITE);
		statusValue.setTextColor(Color.WHITE);
		double availInt = Double.parseDouble(a);
		double capInt = Double.parseDouble(c);
		
		availValue.setShadowLayer(100, 0, 0, Color.BLACK);
		statusTitle.setShadowLayer(50, 0, 0, Color.BLACK);
		if (s.equals("Closed")){
			availValue.setText("CLOSED");
			statusTitle.setTextColor(Color.rgb(255, 200, 200));
			layout.setBackgroundColor(Color.rgb(255,150,125));
			capTitle.setBackgroundColor(Color.rgb(155, 10, 5));
			availValue.setBackgroundColor(Color.rgb(155, 10, 5));
			availTitle.setBackgroundColor(Color.rgb(155, 10, 5));
			statusTitle.setBackgroundColor(Color.rgb(155, 10, 5));
			statusValue.setBackgroundColor(Color.rgb(155, 10, 5));
		}
		else {
			if (availInt==0){
				availValue.setText("FULL");
				layout.setBackgroundColor(Color.rgb(255,150,125));
				capTitle.setBackgroundColor(Color.rgb(155, 10, 5));
				availValue.setBackgroundColor(Color.rgb(155, 10, 5));
				availTitle.setBackgroundColor(Color.rgb(155, 10, 5));
				statusTitle.setBackgroundColor(Color.rgb(155, 10, 5));
				statusValue.setBackgroundColor(Color.rgb(155, 10, 5));
			}	
			else if (availInt <= 5 && capInt >= 50){
				statusTitle.setText("Low Availability");
				layout.setBackgroundColor(Color.rgb(255,150,125));
				capTitle.setBackgroundColor(Color.rgb(155, 10, 5));
				availValue.setBackgroundColor(Color.rgb(155, 10, 5));
				availTitle.setBackgroundColor(Color.rgb(155, 10, 5));
				statusTitle.setBackgroundColor(Color.rgb(155, 10, 5));
				statusValue.setBackgroundColor(Color.rgb(155, 10, 5));
			} else if (availInt <= 25 && capInt >=100){
				statusTitle.setText("Medium Availability");
				layout.setBackgroundColor(Color.rgb(232,255,132));
				capTitle.setBackgroundColor(Color.rgb(100, 90, 1));
				availValue.setBackgroundColor(Color.rgb(100, 90, 1));
				availTitle.setBackgroundColor(Color.rgb(100, 90, 1));
				statusTitle.setBackgroundColor(Color.rgb(100, 90, 1));
				statusValue.setBackgroundColor(Color.rgb(100, 90, 1));
			}else{
				statusTitle.setText("High Availability");
				layout.setBackgroundColor(Color.rgb(190,255,150));
				capTitle.setBackgroundColor(Color.rgb(10, 100, 5));
				availTitle.setBackgroundColor(Color.rgb(10, 100, 5));
				availValue.setBackgroundColor(Color.rgb(10, 100, 5));
				statusTitle.setBackgroundColor(Color.rgb(10, 100, 5));
				statusValue.setBackgroundColor(Color.rgb(10, 100, 5));
			} 
		}		
	}
	
	public void startListView(){
		pk = pkLotObj.getList();
		ListView pkLV = (ListView) findViewById(R.id.lotView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lot_row_view_layout, R.id.lot_row, pk);
		pkLV.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}