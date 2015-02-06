package com.example.androidservice;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends IntentService {

	public MyService(String name) {
		super(name);
	}


    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

	}
	
	
	
}