package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class ParkingList extends Activity {
	
	JSONParse jsParse;
	Button refresh;
	ArrayList<JSONParse> pkList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parking_list_layout);
		
		//run the thread that will automatically refresh the list of parking every 10 seconds
		//by calling the getParkingLot method, which will post a GET request to the application server,
		//parse the data, and start the listview again.
		autoRefresh.start();
		refresh = (Button) findViewById(R.id.list_refresh);
		refresh.setOnClickListener(refreshButtonListener);
	}
	
	//implement the button listener for the refresh button.
	private OnClickListener refreshButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			getData();
		}		
	};
	
	//implement the autorefresh thread 
	private Thread autoRefresh = new Thread(){
		@Override
		public void run() {
			while(!isInterrupted()){
				getData();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
			}
		}
		
	};
	
	//method from android volley api that will post a request to the application server to get the 
	//list of parking.
	public void getData(){
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://192.168.1.118:8000/api/ListOfParking/?format=json";
        
        //method to post a GET request to the url to get the parking list JSON object
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	//from the parking list json object obtained in the url, get the JSONArray object containing the list of
            	//all the parking.            	
            	JSONArray jsArray = new JSONArray();
				try {
					//the JSON array object inside the generated JSON Object (obtained from URL for parking list) is named "objects"
					jsArray = response.getJSONArray("objects");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
            	try {
            		//pass the jsArray to the JSONArray parsing class, the getPKList() method returns An array list of an
            		//object, this object contain all the methods to get the elements of the JSON Array (getID(), getLotName(), etc...)
					JSArrayParse jsArr = new JSArrayParse(jsArray);
					pkList = jsArr.getPkList();
					createList(pkList);
				} catch (JSONException e) {
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
	
	//method for creating the listview of the list of parking
	public void createList(ArrayList<JSONParse> pkList){
        //setting ListView and Adapter for displaying list of parking lots
        final ListView lv1 = (ListView) findViewById(R.id.ListView01);
        lv1.setAdapter(new MyCustomBaseAdapter(this, pkList));
        
        //sep OnItemClickListener to 
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
	             Object o = lv1.getItemAtPosition(position);
	             JSONParse fullObject = (JSONParse)o;
	             Intent pkLot = new Intent(ParkingList.this, ViewParking.class);
	             pkLot.putExtra("uri",fullObject.getUri());
	             startActivity(pkLot);
            }  
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}