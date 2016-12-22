package com.android.training.d1.notifsEx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NotifsExample extends Activity implements OnClickListener,
		DialogInterface.OnClickListener, OnCancelListener {

	Button toastBt, dlgBt, stBar;

	AlertDialog alertDialog;
	ProgressDialog progressDialog;
	NotificationManager mManager;
	Intent intent;
	final int APP_ID = 0;

	boolean flag = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		toastBt = (Button) findViewById(R.id.Button01);
		dlgBt = (Button) findViewById(R.id.Button02);
		stBar = (Button) findViewById(R.id.Button03);

		toastBt.setOnClickListener(this);
		dlgBt.setOnClickListener(this);
		stBar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// Called when a view has been clicked.
		if (v == toastBt) {
			// Shows Toast notification
			Toast.makeText(this, "This is Toast Notification!",
					Toast.LENGTH_LONG).show();

		} else if (v == dlgBt) {
			// Shows Dialog notification

			progressDialog = new ProgressDialog(this);

			alertDialog = new AlertDialog.Builder(this).create();

			alertDialog.setTitle("Alert 1");
			alertDialog.setMessage("This is an alert");
			alertDialog.setButton("Positive", this);
			alertDialog.setButton2("Negative", this);
			alertDialog.setButton3("Neutral", this);
			alertDialog.show();

		} else {
			// Shows StatusBar notification

			if (!flag) {
				mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				intent = new Intent(this, SubActivity.class);

				// Constructs a Notification object with the information needed
				// to have a status bar icon without the standard expanded view.
				PendingIntent pendingIntent = PendingIntent.getActivity(NotifsExample.this, 1, intent, 0);

				Notification.Builder builder = new Notification.Builder(NotifsExample.this);

				builder.setAutoCancel(false);
				builder.setTicker("this is ticker text");
				builder.setContentTitle("WhatsApp Notification");
				builder.setContentText("You have a new message");
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setContentIntent(pendingIntent);
				builder.setOngoing(true);
				builder.setSubText("This is subtext...");   //API level 16
				builder.setNumber(100);
				builder.build();

				Notification  myNotication = builder.getNotification();
				mManager.notify(11, myNotication);
				/*Notification notification = new Notification(R.drawable.icon,
						"Notify", System.currentTimeMillis());

				// Sets the contentView field to be a view with the standard
				// "Latest Event" layout.
				// Uses the icon and when fields to set the icon and time fields
				// in the view.
				notification.setLatestEventInfo(this, "NotificationExample",
						"This is from my application", PendingIntent
								.getActivity(this.getBaseContext(), 0, intent,
										PendingIntent.FLAG_CANCEL_CURRENT));

				// Persistent notification on the status bar.
				mManager.notify(APP_ID, notification);
				*/
				flag = true;
				stBar.setText("Clear Notification");
			} else {

				// Cancel a previously shown notification.
				mManager.cancel(APP_ID);

				flag = false;
				stBar.setText("Show Notification");
			}

		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		// This method will be invoked when a button in the dialog is clicked.
		if (dialog.equals(alertDialog)) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				Toast.makeText(this, "Positive button clicked!",
						Toast.LENGTH_LONG).show();
				break;

			case AlertDialog.BUTTON_NEGATIVE:
				Toast.makeText(this, "Negative button clicked!",
						Toast.LENGTH_LONG).show();
				break;

			case AlertDialog.BUTTON_NEUTRAL:

				// Set a listener to be invoked when the positive button of the
				// dialog is pressed.
				progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
						"Cancel", this);

				// Shows the ProgressDialog.
				ProgressDialog.show(this, "ProcessDialog", "Loading...", true,
						true, this);
				break;
			}
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {

		// This method will be invoked when the dialog is canceled.
		if (dialog.equals(progressDialog)) {
			progressDialog.cancel();
		}
	}
}