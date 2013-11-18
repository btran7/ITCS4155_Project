package com.team4.wtfparking;

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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//declare buttons and edit text field
	Button pkListButton;
	Button pkMapButton;
	Button favButton;
	Button filterButton;
	
	public static Context contextOfApplication;
	
	//onCreate() method is an auto-generated method (part of the Activity class).  This class is the first to load when the activity start.  Whatever method
	//you want to run, put in here in the onCreate() method.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		//create a context of application to use shared preference in a non-activity class
		contextOfApplication = getApplicationContext();
		
		//initialize buttons and text edit fields and link them to the xml layouts using ID from the layout objects
		pkListButton = (Button) findViewById(R.id.pkListButton);
		pkMapButton = (Button) findViewById(R.id.pkMapButton);
		favButton = (Button) findViewById(R.id.favButton);
		filterButton = (Button) findViewById(R.id.filterButton);
		
		//set action listener to the login button, the guest button and the create account button
		//Each button has to have an action listener set to it, basically what it does is that it sits and wait for the button to 
		//be clicked, then it does something, in this context, the button listener starts a new activity(refer to the button listener classes
		//codes below
		pkListButton.setOnClickListener(pkListButtonListener);
		pkMapButton.setOnClickListener(pkMapButtonListener);
		favButton.setOnClickListener(favButtonListener);
		filterButton.setOnClickListener(filterButtonListener);
		
		//********************************************************************************************************************************
		//this whole chunk implements the volley API, basically what it does is visit http://localhost:8000/api/AdminComment/1/?format=json, 
		//get the JSON object from the page that contains the admin comment, and parse the comment into the String comment, then call the 
		//setAdminComment() method, pass the comment over to be pass on to the textview(in the layout that has the id admin_comment).
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://localhost:8000/api/AdminComment/1/?format=json";
        
        //method to post a GET request to the url to get the parking list JSON object
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	//from the parking list json object obtained in the url, get the JSONArray object containing the list of
            	//all the parking.            	
				try {
					//the JSON array object inside the generated JSON Object (obtained from URL for parking list) is named "objects"
					String comment = response.getString("comment");
					Boolean alert = response.getBoolean("alert");
					setAdminComment(comment, alert);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
            }
        }, new Response.ErrorListener() {
 
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
 
            }
        });
        queue.add(jsObjRequest); 
        //*********************************************************************************************************************************
	}
	
	//this method 
	public void setAdminComment(String comment, Boolean alert){
		TextView adminComment = (TextView) findViewById(R.id.admin_comment);
		adminComment.setSelected(true);
		adminComment.requestFocus();
		if (!comment.equals("")){
			adminComment.setText(comment);
		}
		if (alert==true){
			adminComment.setTextColor(Color.RED);
		}
	}
	
	//for shared preference to work, it has to have a context of application as a parameter, unfortunately only Activity classes can have 
	//context of application, therefore this method gets the context of application from main activity, so non-activity classes like JSONParse.java 
	//can borrow this context and gain access to the shared preference (useful for getting preference for favorite list or filter list)
	public static Context getContextOfApplication(){
		return contextOfApplication;
	}
	
	//implement actions for the parking list button which starts the parking list activity
	//basically the button listener is a class itself (notice the { at beginning and }; at the end), methods dont have that, it should be declared 
	//in a .java file on its own, but since its relatively small, I put it here instead.  the class has only one method, that is onClick(View v)
	//it determines what the button does, in this case, it creates and Intent, an Intent tells the Activity to do something.
	private OnClickListener pkListButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			//this intent gets the application context and the ParkingList class for the ParkingList Activity
			//then start the activity, this is how your link one activity to another.
			//refer to ParkingList.java on how to pass variable to another Activity.
			Intent pkList = new Intent(getApplicationContext(), ParkingList.class);
			startActivity(pkList);
		}		
	};
	
	private OnClickListener pkMapButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			Intent pkMap = new Intent(getApplicationContext(), ParkingMap.class);
			startActivity(pkMap);
		}		
	};
	
private OnClickListener favButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			Intent fav = new Intent(getApplicationContext(), Favorite.class);
			startActivity(fav);
		}		
	};
	
private OnClickListener filterButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			Intent filter = new Intent(getApplicationContext(), FilterLotTypes.class);
			startActivity(filter);
		}		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}