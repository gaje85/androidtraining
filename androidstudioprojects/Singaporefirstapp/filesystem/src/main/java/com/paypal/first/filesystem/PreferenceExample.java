package com.paypal.first.filesystem;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PreferenceExample extends Activity implements OnClickListener {
	/** Called when the activity is first createdy. */

	final String PREF_NAME = "Pref";
	SharedPreferences preferences; 

	EditText ed1, ed2; 
	Button button;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpref);

		ed1 = (EditText) findViewById(R.id.EditText01);
		ed2 = (EditText) findViewById(R.id.EditText02);

		button = (Button) findViewById(R.id.Button01);

		button.setOnClickListener(this);

		// Retrieve and hold the contents of the preferences file 'name',
		// returning a SharedPreferences through which you can retrieve and
		// modify its values.
		preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

		loadFromSharedPref();

	}

	private void loadFromSharedPref() {
//		Loading the data from SharedPreferences
		ed1.setText(preferences.getString("name", ""));
		ed2.setText(preferences.getString("phone", ""));
	}

	@Override
	public void onClick(View v) {
		savePreferences();
	}

	private void savePreferences() {
//		Save the data into the SharedPreferencesjkk
		Editor editor = preferences.edit();
		editor.putString("name", ed1.getText().toString());
		editor.putString("phone", ed2.getText().toString());
		editor.commit();
		Toast.makeText(this, "Preference saved!", Toast.LENGTH_LONG).show();
	}

}