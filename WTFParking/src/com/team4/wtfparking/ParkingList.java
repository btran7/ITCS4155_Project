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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.View;

public class ParkingList extends Activity {
	
	JSONParse jsParse;
	ArrayList<JSONParse> pkList = new ArrayList<JSONParse>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parking_list_layout);
		//******************************************************************************************************************************
		/*Volley API visits http://localhost:8000/api/ListOfParking/?format=json and get the JSONObject of the list 
		of all the parking lots in that page.  
		
		The Object looks like this: 
		{"meta": {"limit": 20, "next": null, "offset": 0, "previous": null, "total_count": 9}, 
			"objects": [
							{
								"comment": "", "commuter_avail": 34, "commuter_max": 434, "faculty_avail": 53, "faculty_max": 393, 
								"handicapped_avail": 0, "handicapped_max": 0, "id": 1, "lot_name": "Cone Deck", "meter_avail": 0, 
								"meter_max": 0, "resident_avail": 0, "resident_max": 0, "resource_uri": "/api/ListOfParking/1/", 
								"status": "Open", "visitor_avail": 54, "visitor_max": 473
							},
						
							{
								"comment": "", "commuter_avail": 80, "commuter_max": 980, "faculty_avail": 0, "faculty_max": 0, 
								"handicapped_avail": 0, "handicapped_max": 0, "id": 2, "lot_name": "CRI Deck", "meter_avail": 0, 
								"meter_max": 0, "resident_avail": 0, "resident_max": 0, "resource_uri": "/api/ListOfParking/2/", 
								"status": "Open", "visitor_avail": 0, "visitor_max": 0
							}
						]
		}
		
		(notice the [], and inside the object is a JSON object that has key/value pairs of variables.
		
		What the Volley below does:
		1) parse out the Json Array "objects"
		2) pass the Array object to the JSArrayParse.java class
		3) JSArrayParse create an Array List of each JSONParse object (refer to JSONParse.java for more detail)
		4) call the getPKList() to get the Array List containing JSONParse objects
		5) call the createList() method to create the listView for the list of parkings
		*/
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://localhost:8000/api/ListOfParking/?format=json";
        
        //method to post a GET request to the url to get the parking list JSON object
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	//from the parking list json object obtained in the url, get the JSONArray object containing the list of
            	//all the parking.            	
            	JSONArray jsArray = new JSONArray();
				try {
					//get the JSON array called "objects" from the website"
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
        //********************************************************************************************************************************
	}
	
	//a listView is a type of layout that display an entire list of data on screen, each list view has an adapter that is responsible 
	//for displaying every individual item.
	public void createList(ArrayList<JSONParse> pkList){
        //setting ListView and Adapter for displaying list of parking lots
        final ListView lv1 = (ListView) findViewById(R.id.ListView01);
        //there are many types of listview adapters (basic listview, array listview)
        //this is a custom listview I created extending the basic listView so that I could modify the listview
        //to display more than one lines for each item (the basic adapter only let you display 1 line per row)
        //refer to MyCustomBaseAdapter.java for more detail.
        lv1.setAdapter(new MyCustomBaseAdapter(this, pkList));
        
        //everytime you click on any individual item in the list of parking, this method is called to switch to the ViewParking.java
        //that will display the content of individual parking.
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
            	
            	//for each item click, get the item position in the array
	             Object o = lv1.getItemAtPosition(position);
	             //access the object in that position of the array
	             JSONParse fullObject = (JSONParse)o;
	             //from that object, get the uri and pass the uri to the ViewParking.java, ViewParking.java will use that uri
	             //as a reference to which parking it needs to display
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