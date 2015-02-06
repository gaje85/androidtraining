package com.paypal.first.contactsadapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetPhoneNumber extends Activity{
	String phonum = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getphone);
		Button but = (Button)findViewById(R.id.grpbut);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText et = (EditText)findViewById(R.id.num);
				phonum = et.getText().toString();
                Intent intent = new Intent(GetPhoneNumber.this,ContactListActivity.class);
                intent.putExtra("num", phonum);
                startActivity(intent);
				//Send it to Rest service on postexecute show next scree
			}
		});
        Button newList = (Button)findViewById(R.id.newlist);
        newList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetPhoneNumber.this,NewListActivity.class);
                startActivity(intent);
                  }
        });
		
	}
	


}
