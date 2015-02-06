package com.paypal.first.singaporefirstapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BroadcastReceiverExample2 extends Activity {
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		BroadcastReceiver receiver = new MyReceiver();

		// New IntentFilter that matches a single action with no data.
		IntentFilter filter = new IntentFilter("1");

		// Add a new Intent action to match against.
		filter.addAction("1");

		// Register a BroadcastReceiver to be run in the main activity thread.
		// The receiver will be called with any broadcast Intent that matches
		// filter, in the main application thread.
		registerReceiver(receiver, filter);
       // unregisterReceiver(receiver);

		Intent intent = new Intent();

		// Set the general action to be performed.
		intent.setAction("1");

		// Broadcast the given intent to all interested BroadcastReceivers.
		sendBroadcast(intent);

		// intent.setAction("2");
		// sendBroadcast(intent);
	}
}