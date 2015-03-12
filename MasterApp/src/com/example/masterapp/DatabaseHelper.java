package com.example.masterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	final static String DATABASE_NAME = "smsreceived";
	final String TABLE_NAME = "incomingsms";
	final String TITLE = "number";
	final String VALUE = "message";

	public DatabaseHelper(Context context) {

		// Create a helper object to create, open, and/or manage a database.
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Execute a single SQL statement that is not a query. For example,
		// CREATE TABLE, DELETE, INSERT, etc. Multiple statements separated by
		// ;s are not supported. It takes a write lock
		db
				.execSQL("CREATE TABLE "
						+ TABLE_NAME
						+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" TEXT, "+VALUE+" TEXT);");

		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w(TABLE_NAME,
				"Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS incomingsms");
		onCreate(db);
	}
}