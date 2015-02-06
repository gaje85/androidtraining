package com.paypal.first.singaporefirstapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    private final String DEBUG_TAG = getClass().getSimpleName().toString();
    Context context = null;
    String address = "";

    public void onReceive(Context context, Intent intent) {
    	Log.i("com.smsreceived", "broadcastreceiver called ..");
    	this.context = context;
        // ---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        int contactId = -1;
       
        Log.i("com.smsreceived", "broadcastreceiver called .."+bundle);
        if (bundle != null) {
            // ---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                address = msgs[i].getOriginatingAddress();
               // contactId = ContactsUtils.getContactId(mContext, address, "address");
                str += msgs[i].getMessageBody().toString();
               // str += "\n";
        }   
            Log.i("com.smsreceived", address+"::"+str);
            str = str.trim();
            Toast.makeText(context, "Incoming SMS from "+address, Toast.LENGTH_LONG).show();
            if(str.equals("silent")){
      		  AudioManager mode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      		  mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }else if(str.equals("normal")){
    		  AudioManager mode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    		  mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }else if(str.equals("vibrate")){
    		  AudioManager mode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    		  mode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }else if(str.equals("getprofile")){
            	//String profile = getCurrentProfile(context);
            	AudioManager mode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            	int ringerMode = mode.getRingerMode();
            	String sendSMS = "Current Profile :: ";
            	switch (ringerMode) {
				case AudioManager.RINGER_MODE_NORMAL:
					sendSMS+="Normal";
                    break;
                    case AudioManager.RINGER_MODE_SILENT:
                        sendSMS+="Silent";
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        sendSMS+="Vibrate";
                        break;

				default:
					break;
				}
            	
            	 Toast.makeText(context,  sendSMS, Toast.LENGTH_LONG).show();
            	 SmsManager sms = SmsManager.getDefault();
                 sms.sendTextMessage(address, null, sendSMS, null,null);  
                 Toast.makeText(context, "sms sent to "+address, Toast.LENGTH_LONG).show();
            }
            Log.i("com.smsreceived", "before calling bluethoot "+str);
            if(str.equals("bluetoothon")){
            	BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            	System.out.println("adapter on == "+adapter);
            	if (!adapter.enable()) {
        			adapter.enable();
        		}
              }
            if(str.equals("bluetoothoff")){
            	  BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            	  System.out.println("adapter off == "+adapter);
              	//if (adapter.enable()) {
          			adapter.disable();
          		//}
              }

            
    }
    }

	private String getCurrentProfile(Context context) {
		Object o = context.getSystemService("profile");
	    try {

	        Class<?> ProfileManager = Class.forName("android.app.ProfileManager");
	        Class<?> Profile = Class.forName("android.app.Profile");
	        try {

	            Method getActiveProfile = ProfileManager.getDeclaredMethod("getActiveProfile", null);
	            Method getName = Profile.getDeclaredMethod("getName", null);

	            try {

	                String strProfile = (String) getName.invoke(getActiveProfile.invoke(o));
	                Log.i("smsmon","Current profile is: " + strProfile);
	                return strProfile;

	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            } catch (InvocationTargetException e) {
	                e.printStackTrace();
	            }

	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        }           

	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}


   
}