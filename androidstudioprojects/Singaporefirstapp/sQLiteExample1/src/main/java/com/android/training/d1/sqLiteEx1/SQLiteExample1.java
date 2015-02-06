package com.android.training.d1.sqLiteEx1;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SQLiteExample1 extends Activity {
	LinearLayout layout;
	private SQLiteDatabase db = null;
	private Cursor cur = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		layout = (LinearLayout) findViewById(R.id.linlay);

		// Create and/or open a database that will be used for reading and
		// writing. Once opened successfully, the database is cached, so you can
		// call this method every time you need to write to the database.
		db = (new DatabaseHelper(this)).getWritableDatabase();

		// Runs the provided SQL and returns a Cursor over the result set.
		cur = db.rawQuery("SELECT _ID, Planet, Diameter " + "FROM SolarSystem",
				null);

		// Returns the numbers of rows in the cursor.
		int count = cur.getCount();

		if (count > 0) {
			// Move the cursor to the first row.
			cur.moveToFirst();

			for (int i = 0; i < count; i++) {
				TextView textView = new TextView(this);
				

				// Returns the value of the requested column as a String.
				textView.setText(cur.getString(0) + "  -  " + cur.getString(1)
						+ "  -  " + cur.getString(2));

				// Move the cursor to the next row.
				cur.moveToNext();

				layout.addView(textView);
			}

		}
		
	   // Closes the Cursor, releasing all of its resources and making it
		// completely invalid.
		cur.close();

		// Close the database.
		db.close();
		
		for (int i = 0; i < layout.getChildCount(); i++) {
			View v = layout.getChildAt(i);
			if(v instanceof TextView){
				Log.i("sqllite",((TextView) v).getText().toString());
			}
		}
		
		
	}
}





