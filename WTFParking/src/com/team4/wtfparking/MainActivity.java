package com.team4.wtfparking;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	//declare buttons and edit text field
	Button pkListButton;
	Button pkMapButton;
	Button favButton;
	Button filterButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize buttons and text edit fields and link them to the xml layouts using ID from the layout objects
		pkListButton = (Button) findViewById(R.id.pkListButton);
		pkMapButton = (Button) findViewById(R.id.pkMapButton);
		favButton = (Button) findViewById(R.id.favButton);
		filterButton = (Button) findViewById(R.id.filterButton);
		
		//set action listener to the login button, the guest button and the create account button
		pkListButton.setOnClickListener(pkListButtonListener);
		pkMapButton.setOnClickListener(pkMapButtonListener);
		favButton.setOnClickListener(favButtonListener);
		filterButton.setOnClickListener(filterButtonListener);
	
	}
	
	//implement actions for the parking list button which starts the parking list activity
	private OnClickListener pkListButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
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