package com.example.masterapp;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SQLiteDatabase db = null;
	private Cursor cur = null;
	LinearLayout layout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
	
        Button but = (Button)findViewById(R.id.smsbut);
        Button callBut = (Button)findViewById(R.id.calllog);
        layout = (LinearLayout) findViewById(R.id.linlay);
        
        TextView iButOne = (TextView)findViewById(R.id.one);
        TextView iButTwo = (TextView)findViewById(R.id.two);
        iButOne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("com.example.appone", "com.example.appone.MainActivityOne"));
				startActivity(intent);
			}
		});
        iButTwo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("com.example.apptwo", "com.example.apptwo.MainActivityTwo"));
				startActivity(intent);
			}
		});
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("htt://www.google.com"));
		PackageManager manager = getPackageManager();
		List<ResolveInfo> listActivities = manager.queryIntentActivities(intent, 0);
		int count = 0;
		for(ResolveInfo info : listActivities){
			Log.i("master", "app name "+info.loadLabel(manager).toString());
			if(count == 0){
				iButOne.setText(info.loadLabel(manager).toString());
				iButOne.setBackgroundResource(info.getIconResource());
			}else{
				iButTwo.setText(info.loadLabel(manager).toString());
				iButTwo.setBackgroundResource(info.getIconResource());
			}
			count++;
		}
		
        but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db = (new DatabaseHelper(MainActivity.this)).getWritableDatabase();

				// Runs the provided SQL and returns a Cursor over the result set.
				cur = db.rawQuery("SELECT _ID, number, message " + "FROM incomingsms",
						null);
				int count = cur.getCount();

				if (count > 0) {
					// Move the cursor to the first row.
					cur.moveToFirst();

					for (int i = 0; i < count; i++) {
						TextView textView = new TextView(MainActivity.this);
						

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
			}
		});
        callBut.setOnClickListener(new OnClickListener() {
			
     			@Override
     			public void onClick(View v) {
     				db = (new DatabaseHelperIncomingCall(MainActivity.this)).getWritableDatabase();

     				// Runs the provided SQL and returns a Cursor over the result set.
     				cur = db.rawQuery("SELECT _ID, number, date " + "FROM incomingcallone",
     						null);
     				int count = cur.getCount();

     				if (count > 0) {
     					// Move the cursor to the first row.
     					cur.moveToFirst();

     					for (int i = 0; i < count; i++) {
     						TextView textView = new TextView(MainActivity.this);
     						

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
     			}
     		});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
