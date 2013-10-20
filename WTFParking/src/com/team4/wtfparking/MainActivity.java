package com.team4.wtfparking;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button loginButton;
	Button guestLoginButton;
	Button createAccountButton;
	EditText userNameTextEdit;
	EditText passwordTextEdit;
	TextView testText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loginButton = (Button) findViewById(R.id.loginButton);
		guestLoginButton = (Button) findViewById(R.id.guestButton);
		createAccountButton = (Button) findViewById(R.id.createAccountButton);
		
		userNameTextEdit = (EditText) findViewById(R.id.userNameEditText);
		passwordTextEdit = (EditText) findViewById(R.id.passWordEditText);
		
		loginButton.setOnClickListener(loginButtonListener);
		
	}
	
	private OnClickListener loginButtonListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			testText = (TextView) findViewById(R.id.testButtonTextEdit);
			testText.setVisibility(View.VISIBLE);
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}