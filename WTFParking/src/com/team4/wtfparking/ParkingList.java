package com.team4.wtfparking;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class ParkingList extends Activity {
	
	JSONParse jsParse;
	ArrayList<JSONParse> pkList = new ArrayList<JSONParse>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_list_layout);
		
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://10.0.2.2:8000/api/ListOfParking/?format=json";
        
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