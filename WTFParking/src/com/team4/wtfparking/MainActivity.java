package com.team4.wtfparking;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
	
	//declare buttons and edit text field
	Button pkListButton;
	Button favButton;
	Button filterButton;
	
	public static Context contextOfApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		getAdminComment();
		
		//create a context of application to use shared preference in a non-activity class
		contextOfApplication = getApplicationContext();
		
		//initialize buttons and text edit fields and link them to the xml layouts using ID from the layout objects
		pkListButton = (Button) findViewById(R.id.pkListButton);
		favButton = (Button) findViewById(R.id.favButton);
		filterButton = (Button) findViewById(R.id.filterButton);
		
		//set action listener to the login button, the guest button and the create account button
		pkListButton.setOnClickListener(pkListButtonListener);
		favButton.setOnClickListener(favButtonListener);
		filterButton.setOnClickListener(filterButtonListener);
		 
	}
	
	//method to get the admin comment from the application server to display on main page.
	private void getAdminComment(){
		RequestQueue queue = Volley.newRequestQueue(this);
		//url where we get the json object to be parsed
        String url = "http://192.168.1.118:8000/api/AdminComment/1/?format=json";
        
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
	}
	
	//after getting admin comment with the getAdminComment method, set it to the admin_comment textview
	//also set properties (if alert is checked, display text in red, etc...
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
	
	//borrowing the context of application from this method in order to use the shared preference features
	//in classes that do not extend Activity class (such as the JSONParse class)
	public static Context getContextOfApplication(){
		return contextOfApplication;
	}
	
	//implement actions for the parking list button which starts the parking list activity
	private OnClickListener pkListButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			Intent pkList = new Intent(getApplicationContext(), ParkingList.class);
			startActivity(pkList);
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

	//default function from the Activity class, I have no idea what it does, but I think its for 
	//modifying the action menu, which we do not use in this application.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}