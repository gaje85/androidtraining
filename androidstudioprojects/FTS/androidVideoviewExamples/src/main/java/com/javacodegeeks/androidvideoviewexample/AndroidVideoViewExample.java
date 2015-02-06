package com.javacodegeeks.androidvideoviewexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class AndroidVideoViewExample extends Activity {

	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button but = (Button)findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat),"application/3gp");
                startActivity(intent);
            }
        });
		// Get the layout from video_main.xml

		if (mediaControls == null) {
			mediaControls = new MediaController(AndroidVideoViewExample.this);
		}

		// Find your VideoView in your video_main.xml layout
		myVideoView = (VideoView) findViewById(R.id.video_view);

		// Create a progressbar
		progressDialog = new ProgressDialog(AndroidVideoViewExample.this);
		// Set progressbar title
		progressDialog.setTitle("JavaCodeGeeks Android Video View Example");
		// Set progressbar message
		progressDialog.setMessage("Loading...");

		progressDialog.setCancelable(false);
		// Show progressbar
		progressDialog.show();

		try {
			myVideoView.setMediaController(mediaControls);
			myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat));

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		myVideoView.requestFocus();
		myVideoView.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				progressDialog.dismiss();
				myVideoView.seekTo(position);
				if (position == 0) {
					myVideoView.start();
				} else {
					myVideoView.pause();
				}
			}
		});

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}
}
