package com.kids.pp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EnterNameActivity extends Activity {
	private Button mMicButton;
	private static final int REQUEST_CODE = 1;
	EditText et = null;
	private Button serButton;
	byte data[] = null;
	ImageView iv = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		data = intent.getExtras().getByteArray("data");
		Log.i("ss", "data u" + data);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.two);
		mMicButton = (Button) findViewById(R.id.mic_button);
		iv = (ImageView) findViewById(R.id.imageView1);

		String state = Environment.getExternalStorageState();

		File folder_gui = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "GUI");
		File outFile = new File(folder_gui, "temp.jpg"); 

		Bitmap myBitmap = BitmapFactory.decodeFile(outFile.getAbsolutePath());

		iv.setImageBitmap(myBitmap);

		et = (EditText) findViewById(R.id.name);
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities == null || activities.size() == 0) {
			mMicButton.setEnabled(false);
			Toast.makeText(getApplicationContext(), "Not Supported",
					Toast.LENGTH_LONG).show();
		}
		mMicButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runVoiceRecognition();
			}
		});

		serButton = (Button) findViewById(R.id.send);
		serButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SendImage si = new SendImage();
				si.execute(new String[] { et.getText().toString() });
			}
		});
	}

	class SendImage extends AsyncTask<String, Void, String> {

		@Override
		public String doInBackground(String... params) {
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient();

				// create post method
				// HttpPost postMethod = new
				// HttpPost("http://174.143.148.125:8080/Imgservletjxta/imgserv");
				HttpPost postMethod = new HttpPost(
						"http://www.chennaihypermarket.com/Imgservletjxta/imgserv");
				String imgEncoded = Base64.encode(data, 0, data.length);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("name", params[0]));
				nameValuePairs.add(new BasicNameValuePair("stringdata",
						imgEncoded));
				postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse resp = httpClient.execute(postMethod);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast toast = Toast.makeText(EnterNameActivity.this,
					"Img sent to server", Toast.LENGTH_LONG);
			toast.show();
		}
	}

	public void runVoiceRecognition() {
		Intent intent = VoiceRecognitionIntentFactory
				.getFreeFormRecognizeIntent("Speak Now...");
		startActivityForResult(intent, REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ArrayList<String> matches = null;
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			// mResultList.setAdapter(new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1, matches));
			Log.i("ss", "Voice detected :: " + matches);
			et.setText(matches.get(0));

		}
	}

}
