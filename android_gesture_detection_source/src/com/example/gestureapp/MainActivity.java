package com.example.gestureapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

/*
 * This is demo code to accompany the Mobiletuts+ tutorial:
 * Android SDK: Gesture Detection
 * 
 * Sue Smith 22.12.13
 */

public class MainActivity extends Activity {

	//gesture detector
	private GestureDetectorCompat gDetect;
	
	//message text view
	private TextView messageView;
	//message array
	private String[] messages;
	//total messages
	private int numMessages = 10;
	//current message -start at zero
	private int currMessage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//retrieve view from layout
		messageView = (TextView)findViewById(R.id.the_message);
		//instantiate array
		messages = new String[numMessages];
		//set messages to show dummy text and counter int
		for(int i=0; i<numMessages; i++){
			messages[i]="Message "+i+":\n\n"+
					"Here is message "+i+":\n\n"+
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit, "
					+ "sed do eiusmod tempor incididunt ut labore et dolore magna "
					+ "aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
					+ "ullamco laboris nisi ut aliquip ex ea commodo consequat. "
					+ "Duis aute irure dolor in reprehenderit in voluptate velit "
					+ "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint "
					+ "occaecat cupidatat non proident, sunt in culpa qui officia "
					+ "deserunt mollit anim id est laborum.";
		}
		//start by showing first message
		messageView.setText(messages[currMessage]);
		//instantiate gesture listener, pass to detector 
		gDetect = new GestureDetectorCompat(this, new GestureListener());
	}

	//use gesture detector
	@Override
	public boolean onTouchEvent(MotionEvent event){
		this.gDetect.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	//gesture listener class
	public class GestureListener extends GestureDetector.SimpleOnGestureListener {

		//offset results
		private float flingMin = 100;
		private float velocityMin = 100;

		//implement onDown in case other gesture events are ignored
		@Override
		public boolean onDown(MotionEvent event) { 
			return true;
		}

		//handle fling gesture
		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, 
				float velocityX, float velocityY) {
			//user will move forward through messages when fling up or left
			boolean forward = false;
			//user will move backward through messages when fling down or right
			boolean backward = false;
			
			//calculate the change in X position within the fling gesture
			float horizontalDiff = event2.getX() - event1.getX();
			//calculate the change in Y position within the fling gesture
			float verticalDiff = event2.getY() - event1.getY();
			
			//work out absolute values
			float absHDiff = Math.abs(horizontalDiff);
			float absVDiff = Math.abs(verticalDiff);
			float absVelocityX = Math.abs(velocityX);
			float absVelocityY = Math.abs(velocityY);
			
			//is horizontal difference greater and are values valid
			if(absHDiff>absVDiff && absHDiff>flingMin && absVelocityX>velocityMin){
				if(horizontalDiff>0) backward=true;
				else forward=true;
			}
			else if(absVDiff>flingMin && absVelocityY>velocityMin){
				if(verticalDiff>0) backward=true;
				else forward=true;
			}

			//user is cycling forward through messages
			if(forward){
				//check current message is not at end of array
				//increment or set back to start
				if(currMessage<numMessages-1) currMessage++;
				else currMessage = 0;
				//set the message text display
				messageView.setText(messages[currMessage]);

			}
			//user is cycling backwards through messages
			else if(backward){
				//check that current message is not at start of array
				//decrement or set to last message
				if(currMessage>0) currMessage--;
				else currMessage = numMessages-1;
				//set the message text display
				messageView.setText(messages[currMessage]);
			}
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
