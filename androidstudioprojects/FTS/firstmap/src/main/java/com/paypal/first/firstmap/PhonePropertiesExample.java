package com.paypal.first.firstmap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.TextView;

public class PhonePropertiesExample extends Activity {
	private TextView telMgrOutput;

	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);

		this.setContentView(R.layout.phonemain);

		this.telMgrOutput = (TextView) findViewById(R.id.telmgroutput);
	}

	@Override
	public void onStart() {
		super.onStart();

		// TelephonyManager
		final TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		this.telMgrOutput.setText(telMgr.toString());

		// PhoneStateListener
		PhoneStateListener phoneStateListener = new PhoneStateListener() {

			@Override
			public void onCallStateChanged(final int state,
					final String incomingNumber) {
				telMgrOutput.setText(getTelephonyOverview(telMgr));
			}
		};

		// Registers a listener object to receive notification of changes in
		// specified telephony states.
		telMgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

		String telephonyOverview = getTelephonyOverview(telMgr);
		this.telMgrOutput.setText(telephonyOverview);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	// Parse TelephonyManager values into a human readable String.
	public String getTelephonyOverview(final TelephonyManager telMgr) {

		// Returns a constant indicating the call state (cellular) on the
		// device.
		int callState = telMgr.getCallState();

		String callStateString = "NA";

		switch (callState) {
		case TelephonyManager.CALL_STATE_IDLE:
			callStateString = "IDLE";
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			callStateString = "OFFHOOK";
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			callStateString = "RINGING";
			break;
		}

		// Returns the current location of the device.
		GsmCellLocation cellLocation = (GsmCellLocation) telMgr
				.getCellLocation();

		// GSM location area code
		//String cellLocAreaCode = "" + cellLocation.getLac();

		// GSM cell id
		//String cellLocId = "" + cellLocation.getCid();

		// Returns the unique device ID, for example, the IMEI for GSM and the
		// MEID for CDMA phones.
		String deviceId = telMgr.getDeviceId();

		// Returns the software version number for the device, for example, the
		// IMEI/SV for GSM phones.
		String deviceSoftwareVersion = telMgr.getDeviceSoftwareVersion();

		// Returns the phone number string for line 1, for example, the MSISDN
		// for a GSM phone.
		//String line1Number = telMgr.getLine1Number();

		// Returns the ISO country code equivalent of the current registered
		// operator's MCC (Mobile Country Code).
		//String networkCountryIso = telMgr.getNetworkCountryIso();

		// Returns the numeric name (MCC+MNC) of current registered operator.
		//String networkOperator = telMgr.getNetworkOperator();

		// Returns the alphabetic name of current registered operator.
		//String networkOperatorName = telMgr.getNetworkOperatorName();

		// Returns a constant indicating the device phone type.
		int phoneType = telMgr.getPhoneType();
		String phoneTypeString = "NA";
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_CDMA:
			phoneTypeString = "CDMA";
			break;
		case TelephonyManager.PHONE_TYPE_GSM:
			phoneTypeString = "GSM";
			break;
		case TelephonyManager.PHONE_TYPE_NONE:
			phoneTypeString = "NONE";
			break;
		}

		// Returns the ISO country code equivalent for the SIM provider's
		// country code.
	//	String simCountryIso = telMgr.getSimCountryIso();

		// Returns the MCC+MNC (mobile country code + mobile network code) of
		// the provider of the SIM. 5 or 6 decimal digits.
		//String simOperator = telMgr.getSimOperator();

		// Returns the Service Provider Name (SPN).
		//String simOperatorName = telMgr.getSimOperatorName();

		// Returns the serial number of the SIM, if applicable.
		//String simSerialNumber = telMgr.getSimSerialNumber();

		// Returns the unique subscriber ID, for example, the IMSI for a GSM
		// phone.
		//String simSubscriberId = telMgr.getSubscriberId();

		// Returns a constant indicating the state of the device SIM card.
		int simState = telMgr.getSimState();
		String simStateString = "NA";
		switch (simState) {
		case TelephonyManager.SIM_STATE_ABSENT:
			simStateString = "ABSENT";
			break;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			simStateString = "NETWORK_LOCKED";
			break;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			simStateString = "PIN_REQUIRED";
			break;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			simStateString = "PUK_REQUIRED";
			break;
		case TelephonyManager.SIM_STATE_READY:
			simStateString = "STATE_READY";
			break;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			simStateString = "STATE_UNKNOWN";
			break;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("  \n\ncallState = " + callStateString);
		//sb.append("  \n\ncellLocationAreaCode = " + cellLocAreaCode);
		//sb.append("  \n\ncellLocationId = " + cellLocId);
		sb.append("  \n\ndeviceId = " + deviceId);
		sb.append("  \n\ndeviceSoftwareVersion = " + deviceSoftwareVersion);
		//sb.append("  \n\nline1Number = " + line1Number);
		//sb.append("  \n\nnetworkCountryIso = " + networkCountryIso);
		//sb.append("  \n\nnetworkOperator = " + networkOperator);
		//sb.append("  \n\nnetworkOperatorName = " + networkOperatorName);
		sb.append("  \n\nphoneTypeString = " + phoneTypeString);
		//sb.append("  \n\nsimCountryIso = " + simCountryIso);
		//sb.append("  \n\nsimOperator = " + simOperator);
		//sb.append("  \n\nsimOperatorName = " + simOperatorName);
		//sb.append("  \n\nsimSerialNumber = " + simSerialNumber);
		//sb.append("  \n\nsimSubscriberId = " + simSubscriberId);
		sb.append("  \n\nsimStateString = " + simStateString);

		return sb.toString();
	}
}