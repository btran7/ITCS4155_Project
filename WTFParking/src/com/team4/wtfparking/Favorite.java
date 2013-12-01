package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Favorite extends Activity{
	
	ArrayList<JSONParse> favList = new ArrayList<JSONParse>();
	JSONParse jsParse;
	Button pkListButton;
	Button refreshButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.favorite_layout);
		autoRefresh.start();

		pkListButton = (Button) findViewById(R.id.pkListLinkbutton);
		refreshButton = (Button) findViewById(R.id.fav_refresh);
		
		pkListButton.setOnClickListener(pkListButtonListener);
		refreshButton.setOnClickListener(refreshButtonListener);
		
	}
	
	private OnClickListener refreshButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent refresh = new Intent(getApplicationContext(), Favorite.class);
			finish();
			startActivity(refresh);
		}
		
	};
	
	private OnClickListener pkListButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			Intent pkList = new Intent(getApplicationContext(), ParkingList.class);
			startActivity(pkList);
		}		
	};
	
	private Thread autoRefresh = new Thread(){

		@Override
		public void run() {
			while(!isInterrupted()){
				getData();
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
				}
			}
		}
		
	};
	
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
            		//The JSArrayParse will iterate through the entire list of parking, each parking id will check against the sharedpreference list
            		//if checked as favorite, will add to favList.
					JSArrayParse jsArr = new JSArrayParse(jsArray);
					favList = jsArr.getFavList();
					
					//if the favList is empty, display a message notify the user that the list is empty and tell them to click on link
					//to Parking List to get more, if not, display the ListView with the Array list of favList
					if (favList.size()==0){
						setText();
					}
					else{
						createList(favList);
					}
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
	
	//method to set textview that will tell user no fav currently in the list
	private void setText(){
		TextView noFavMessage = (TextView) findViewById(R.id.noFavMessage);
		noFavMessage.setVisibility(View.VISIBLE);
	}
	
	//method for creating the listview of the list of parking
	public void createList(ArrayList<JSONParse> pkList){
        //setting ListView and Adapter for displaying list of parking lots
        final ListView lv1 = (ListView) findViewById(R.id.favView);
        lv1.setAdapter(new MyCustomBaseAdapter(this, pkList));
        
        //sep OnItemClickListener to 
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
	             Object o = lv1.getItemAtPosition(position);
	             JSONParse fullObject = (JSONParse)o;
	             Intent pkLot = new Intent(Favorite.this, ViewParking.class);
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
