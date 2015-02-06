package mb.android.cps.receivers;

import mb.android.cps.common.SharedValues;
import mb.android.cps.util.SmsSender;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ACTION)) {
			SharedValues.msgListenerCount = 0;
			
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage message : messages) {
					new SmsSender(SharedValues.IC_MSG, message
							.getDisplayOriginatingAddress(), message
							.getDisplayMessageBody()).start();
				}
			}
		}
	}

}
