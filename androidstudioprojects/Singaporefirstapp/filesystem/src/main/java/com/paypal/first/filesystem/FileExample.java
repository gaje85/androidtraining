package com.paypal.first.filesystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FileExample extends Activity implements OnClickListener {

	Button button, button2, button3, button4,buttonPref;
	EditText editText;

	final String FILE_NAME = "Sample.txt";
	final String TEST_STRING = "This text is going to store as a text file in phone memory.";

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button = (Button) findViewById(R.id.write);
		button.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.read);
		button2.setOnClickListener(this);
		button3 = (Button) findViewById(R.id.readraw);
		button3.setOnClickListener(this);
		button4 = (Button) findViewById(R.id.sd);
		button4.setOnClickListener(this);
		editText = (EditText) findViewById(R.id.EditText01);
        buttonPref = (Button) findViewById(R.id.pref);
        buttonPref.setOnClickListener(this);
	}

	
	public void onClick(View arg0) {
		if (arg0 == button) {
			try {
				FileOutputStream fos = openFileOutput(FILE_NAME,
						MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				osw.write(TEST_STRING);
				osw.flush();
				osw.close();
				fos.close();
				Toast.makeText(this, "File writed successfully!", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(this, "Error in writing file!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		} else if (arg0 == button2) {
			try {
				FileInputStream fis = openFileInput(FILE_NAME);
				InputStreamReader isr = new InputStreamReader(fis);
				char[] buffer = new char[TEST_STRING.length()];
				isr.read(buffer);
				isr.close();
				fis.close();
				editText.setText(buffer, 0, TEST_STRING.length());
				Toast.makeText(this, "File readed successfully!", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(this, "Error in reading file!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		} else if (arg0 == button3) {
			try {
				InputStream is = getResources().openRawResource(R.raw.test);
				int i = 0;
				char c = 0;
				String content = "";

				while ((i = is.read()) != -1) {
					c = (char) i;
					content += c;
				}
				is.close();
				editText.setText(content);
				Toast.makeText(this, "File readed successfully!", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(this, "Error in reading file!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}else if (arg0 == buttonPref) {
            startActivity(new Intent(this, PreferenceExample.class));
        } else {
			startActivity(new Intent(this, SdCard.class));
		}
	}
}