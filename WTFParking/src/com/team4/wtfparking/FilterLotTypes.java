package com.team4.wtfparking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class FilterLotTypes extends Activity implements OnClickListener{
	CheckBox commuterCheckBox;
	CheckBox facultyCheckBox;
	CheckBox residentCheckBox;
	CheckBox visitorCheckBox;
	CheckBox handicappedCheckBox;
	CheckBox meterCheckBox;
	
	Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.filter_lot_type_layout);
		
		commuterCheckBox = (CheckBox) findViewById(R.id.commuter_check);
		facultyCheckBox = (CheckBox) findViewById(R.id.faculty_check);
		residentCheckBox = (CheckBox) findViewById(R.id.resident_check);
		visitorCheckBox = (CheckBox) findViewById(R.id.visitor_check);
		handicappedCheckBox = (CheckBox) findViewById(R.id.handicapped_check);
		meterCheckBox = (CheckBox) findViewById(R.id.meter_check);		
		submit = (Button) findViewById(R.id.submit_filter);
		submit.setOnClickListener(this);
		loadSavedPreferences();
	}
	
	private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean filterCommuter = sharedPreferences.getBoolean("commuter_value", false);
		boolean filterFaculty = sharedPreferences.getBoolean("faculty_value", false);
		boolean filterResident = sharedPreferences.getBoolean("resident_value", false);
		boolean filterVisitor = sharedPreferences.getBoolean("visitor_value", false);
		boolean filterHandicapped = sharedPreferences.getBoolean("handicapped_value", false);
		boolean filterMeter = sharedPreferences.getBoolean("meter_value", false);
		
		if (filterCommuter){
			commuterCheckBox.setChecked(true);
		}else{
			commuterCheckBox.setChecked(false);
		}
		
		if (filterFaculty){
			facultyCheckBox.setChecked(true);
		}else{
			facultyCheckBox.setChecked(false);
		}
		
		if (filterResident){
			residentCheckBox.setChecked(true);
		}else{
			residentCheckBox.setChecked(false);
		}
		
		if (filterVisitor){
			visitorCheckBox.setChecked(true);
		}else{
			visitorCheckBox.setChecked(false);
		}
		
		if (filterHandicapped){
			handicappedCheckBox.setChecked(true);
		}else{
			handicappedCheckBox.setChecked(false);
		}
		
		if (filterMeter){
			meterCheckBox.setChecked(true);
		}else{
			meterCheckBox.setChecked(false);
		}
	}
	
	public void savePreferences(String key, boolean value){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public void onClick(View v) {
		savePreferences("commuter_value", commuterCheckBox.isChecked());
		savePreferences("faculty_value", facultyCheckBox.isChecked());
		savePreferences("resident_value", residentCheckBox.isChecked());
		savePreferences("visitor_value", visitorCheckBox.isChecked());
		savePreferences("handicapped_value", handicappedCheckBox.isChecked());
		savePreferences("meter_value", meterCheckBox.isChecked());
		Toast.makeText(this,"Your preferences have been saved.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
