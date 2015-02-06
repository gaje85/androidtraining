package com.paypal.first.filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SdCard extends Activity implements OnClickListener {

	Button button, button2;
	EditText editText;

	final String FILE_NAME = "Sample.txt";
	final String TEST_STRING = "This text is going to store as a text file in SD card.";

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		button = (Button) findViewById(R.id.write);
		button.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.read);
		button2.setOnClickListener(this);
		editText = (EditText)findViewById(R.id.EditText01);
	}

	
	public void onClick(View v) {
		if (v == button) {
			try {
				File root = Environment.getExternalStorageDirectory();
				if (root.canWrite()) {
					File file = new File(root, "/upload/"+FILE_NAME);
					File dir = new File(root,"/upload");
					dir.mkdir();
					FileWriter writer = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(writer);
					out.write(TEST_STRING);
					out.close();
					writer.close();
					Toast.makeText(this, "File writed successfully!", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Toast.makeText(this, "Error in writing file!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		} else {
			try {
				File root = Environment.getExternalStorageDirectory();
				if (root.canRead()) {
					File file = new File(root, "/upload/"+FILE_NAME);
					FileReader reader = new FileReader(file);
					BufferedReader in = new BufferedReader(reader);
					String temp, content = "";
					while ((temp = in.readLine()) != null) {
						content += temp;
					}
					in.close();
					reader.close();
					editText.setText(content);
					Toast.makeText(this, "File readed successfully!", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Toast.makeText(this, "Error in reading file!", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}

}
