package mb.android.cps.util;

import java.util.ArrayList;
import java.util.List;

import mb.android.cps.common.SharedValues;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsSender extends Thread {
	private int flag;
	private String otherNum;
	private String messageBody;

	private String url;
	private HttpPost httpPost;
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

	public SmsSender(int flag, String otherNum, String messageBody) {
		this.flag = flag;
		this.otherNum = otherNum;
		this.messageBody = messageBody;
	}

	@Override
	public void run() {
		try {
			super.run();

			url = SharedValues.MY_URL + "ParameterServlet";
			httpPost = new HttpPost(url);

			String message = "";
			SmsManager sms = SmsManager.getDefault();

			switch (flag) {
			case SharedValues.IC_MSG:
				message = "SMS from " + otherNum + " to "
						+ SharedValues.curDevInfo.getThisMobInfo() + " : "
						+ messageBody;

				nameValuePairs.add(new BasicNameValuePair("flag", Integer
						.toString(SharedValues.IC_MSG)));
				nameValuePairs.add(new BasicNameValuePair("sender", otherNum));
				nameValuePairs.add(new BasicNameValuePair("content",
						messageBody));
				nameValuePairs.add(new BasicNameValuePair("timestamp", Long
						.toString(System.currentTimeMillis())));
				break;

			case SharedValues.OG_MSG:
				message = "SMS to " + otherNum + " from "
						+ SharedValues.curDevInfo.getThisMobInfo() + " : "
						+ messageBody;
				Log.i("cps", message);

				nameValuePairs.add(new BasicNameValuePair("flag", Integer
						.toString(SharedValues.OG_MSG)));
				nameValuePairs
						.add(new BasicNameValuePair("receiver", otherNum));
				nameValuePairs.add(new BasicNameValuePair("content",
						messageBody));
				nameValuePairs.add(new BasicNameValuePair("timestamp", Long
						.toString(System.currentTimeMillis())));
				break;

			case SharedValues.MISSED_CALL:
				message = "Missed call from " + otherNum + " to "
						+ SharedValues.curDevInfo.getThisMobInfo();

				nameValuePairs.add(new BasicNameValuePair("flag", Integer
						.toString(SharedValues.MISSED_CALL)));
				nameValuePairs.add(new BasicNameValuePair("caller", otherNum));
				nameValuePairs.add(new BasicNameValuePair("timestamp", Long
						.toString(System.currentTimeMillis())));
				break;

			case SharedValues.IC_CALL:
				message = "Call from " + otherNum + " to "
						+ SharedValues.curDevInfo.getThisMobInfo()
						+ ". Call Duration: " + messageBody + " Seconds";

				nameValuePairs.add(new BasicNameValuePair("flag", Integer
						.toString(SharedValues.IC_CALL)));
				nameValuePairs.add(new BasicNameValuePair("caller", otherNum));
				nameValuePairs.add(new BasicNameValuePair("timestamp", Long
						.toString(System.currentTimeMillis())));
				nameValuePairs.add(new BasicNameValuePair("duration",
						messageBody));
				break;

			case SharedValues.OG_CALL:
				message = "Call to " + otherNum + " from "
						+ SharedValues.curDevInfo.getThisMobInfo()
						+ ". Call Duration: " + messageBody + " Seconds";

				nameValuePairs.add(new BasicNameValuePair("flag", Integer
						.toString(SharedValues.OG_CALL)));
				nameValuePairs.add(new BasicNameValuePair("caller", otherNum));
				nameValuePairs.add(new BasicNameValuePair("timestamp", Long
						.toString(System.currentTimeMillis())));
				nameValuePairs.add(new BasicNameValuePair("duration",
						messageBody));
				break;
			}

			Log.i("CPS", message);

			// Send info to Parent
			sms.sendTextMessage(SharedValues.PARENT_MOB_NUM, null, message,
					null, null);

			SharedValues.msgListenerCount = 0;

			// Send info to Server
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			new DefaultHttpClient().execute(httpPost);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
