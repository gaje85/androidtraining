package mb.android.cps.receivers;

import mb.android.cps.common.SharedValues;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutCallReceiver extends BroadcastReceiver {
	private static final String ACTION = "android.intent.action.NEW_OUTGOING_CALL";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			SharedValues.isOgCall = true;
			
//			String outNum = "Unknown";
			if (getResultData() != null) {
				SharedValues.ogCallNumber = getResultData();
			}
//			new SmsSender(SharedValues.OG_CALL, outNum, null).start();
		}
	}

}
