package com.example.masterapp;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class IncomingCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                
                if(null == bundle)
                        return;
                
                Log.i("IncomingCallReceiver",bundle.toString());
                
                String state = bundle.getString(TelephonyManager.EXTRA_STATE);
                                
                Log.i("IncomingCallReceiver","State: "+ state);
                
                if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
                {
                        String phonenumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                                
                        Log.i("IncomingCallReceiver","Incomng Number: " + phonenumber);
                        
                        String info = "Detect Calls sample application\nIncoming number: " + phonenumber;
                        
                        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                        
                        ContentValues cv = new ContentValues();

                		// Adds a value to the set.
                		cv.put("number",phonenumber );
                		cv.put("date", new Date().toString());
                		// Convenience method for inserting a row into the database.
                		SQLiteDatabase db = null;
                		DatabaseHelperIncomingCall dbcall = new DatabaseHelperIncomingCall(context);
                		db = dbcall.getWritableDatabase();
                		
                		Log.i("ss", db+" Before insert..");
                		db.insert("incomingcallone", "number", cv);
                		Toast.makeText(context, "incoming call inserted successfully", Toast.LENGTH_LONG).show();
                		// remove from call log 
                		context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
                        
                }
        }

}