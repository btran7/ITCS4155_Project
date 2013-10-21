package com.team4.wtfparking;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	//declare buttons and edit text field
	Button loginButton;
	Button guestLoginButton;
	Button createAccountButton;
	
	EditText userNameTextEdit;
	EditText passwordTextEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize buttons and text edit fields and link them to the xml layouts using ID from the layout objects
		loginButton = (Button) findViewById(R.id.loginButton);
		guestLoginButton = (Button) findViewById(R.id.guestButton);
		createAccountButton = (Button) findViewById(R.id.createAccountButton);
		
		userNameTextEdit = (EditText) findViewById(R.id.userNameEditText);
		passwordTextEdit = (EditText) findViewById(R.id.passWordEditText);
		
		//set action listener to the login button, the guest button and the create account button
		loginButton.setOnClickListener(loginButtonListener);
		guestLoginButton.setOnClickListener(guestLoginButtonListener);
		createAccountButton.setOnClickListener(createAccountButtonListener);
		
	}
	
	//implement actions for the login button, which will take user to the main menu
	private OnClickListener loginButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
		
		}		
	};
	
	//implement actions for the guest login button, which will take user to the main menu
	//this main menu will have all the features available only to registered user set as invisible
	private OnClickListener guestLoginButtonListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
		}		
	};
	
	//implement actions for the create account button, this action will take user to the create account page.
	private OnClickListener createAccountButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			
		}		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}