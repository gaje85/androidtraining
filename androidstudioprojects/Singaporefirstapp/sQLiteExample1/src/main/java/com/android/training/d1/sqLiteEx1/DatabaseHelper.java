package com.android.training.d1.sqLiteEx1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	final static String DATABASE_NAME = "UniverseDB";
	final String TABLE_NAME = "SolarSystem";
	final String TITLE = "Planet";
	final String VALUE = "Diameter";

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

		// This class is used to store a set of values
		ContentValues cv = new ContentValues();

		// Adds a value to the set.
		cv.put(TITLE, "MERCURY");
		cv.put(VALUE, "4,880 km");
		// Convenience method for inserting a row into the database.
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "VENUS");
		cv.put(VALUE, "12,103.6 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "EARTH");
		cv.put(VALUE, "12,756.3 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "MARS");
		cv.put(VALUE, "6,794 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "JUPITER");
		cv.put(VALUE, "142,984 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "SATURN");
		cv.put(VALUE, "120,536 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "URANUS");
		cv.put(VALUE, "51,118 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "NEPTUNE");
		cv.put(VALUE, "49,532 km");
		db.insert(TABLE_NAME, TITLE, cv);

		cv.put(TITLE, "PLUTO");
		cv.put(VALUE, "2274 km");
		db.insert(TABLE_NAME, TITLE, cv);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w(TABLE_NAME,
				"Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS SolarSystem");
		onCreate(db);
	}
}