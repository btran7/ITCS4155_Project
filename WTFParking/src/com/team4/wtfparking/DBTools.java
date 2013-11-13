package com.team4.wtfparking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTools extends SQLiteOpenHelper {

	public DBTools(Context applicationContext) {

		super(applicationContext, "WTFParking.db", null, 1);
		}
		 
		String table3 = "preference";
		 
		final String CREATE_TABLE_3 =
		"CREATE TABLE IF NOT EXISTS " + table3 + " (lotName INTEGER PRIMARY KEY, capacity TEXT," 
		+ "available TEXT, event_time INTEGER, author_name TEXT, track TEXT)";

		 
		@Override
		public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int olderversion, int newerversion) {
		// TODO Auto-generated method stub
		
	}
	
	

}
