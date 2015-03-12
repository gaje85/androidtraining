package com.example.masterapp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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
            ContentValues cv = new ContentValues();

    		// Adds a value to the set.
    		cv.put("number", address);
    		cv.put("message", str);
    		// Convenience method for inserting a row into the database.
    		SQLiteDatabase db = null;
    		db = (new DatabaseHelper(context)).getWritableDatabase();
    		db.insert("incomingsms", "number", cv);
    		Toast.makeText(context, "SMS inserted successfully", Toast.LENGTH_LONG).show();
    		// remove from SMS log will not work for SMS phones
    		context.getContentResolver().delete(Uri.parse("content://sms" ), null, null);
    		Toast.makeText(context, "sms log deleted", Toast.LENGTH_LONG).show();
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