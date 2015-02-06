package com.paypal.first.contactsadapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListActivity extends Activity implements
		OnItemClickListener {

	private ListView listView;
	private List<ContactBean> list = new ArrayList<ContactBean>();
	private Button button1;
	private CheckBox groupcheckbox;
	// SharedPreferences pref;
	// private String inputPhoneNumber;
	// private String inputname;
	private String tvname;
	private String tvphone;
	String phoNum = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent intent = getIntent();
		phoNum = intent.getStringExtra("num");
		//CheckBox groupcCheckBox = (CheckBox) findViewById(R.id.groupcheckbox);
		button1 = (Button) findViewById(R.id.grp);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				String query = "";
				query +=phoNum+"-";
				for (ContactBean cb : list) {
					Log.i("grp",cb.isChecked()+"");
					if(cb.isChecked()){
						query+=cb.getPhoneNo().trim()+","+cb.getName().trim()+":";
						//Intent i= new Intent(ContactListActivity.this, GroupActivity.class);
						//startActivity(i);
					}
				}
				Log.i("contact",query);
//				SendContactInfo contactInfo = new SendContactInfo();
//				contactInfo.execute(query);
			}
		});
		TextView tvname = (TextView) findViewById(R.id.tvname);
		TextView tvphone = (TextView) findViewById(R.id.tvphone);

		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		/*
		 * pref = getPreferences(MODE_PRIVATE); String tvname1 =
		 * pref.getString("inputname", null); String tvphone1 =
		 * pref.getString("inputphonenumber", null); if (tvname != null) {
		 * groupcheckbox.setChecked(true); if(tvphone !=null){
		 * groupcheckbox.setChecked(true); } } tvname.setText(tvname1);
		 * tvphone.setText(tvphone1); else { groupcheckbox.setChecked(false); }
		 */

		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {

			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

			ContactBean objContact = new ContactBean();
			objContact.setName(name);
			objContact.setPhoneNo(phoneNumber);
			list.add(objContact);

		}
		phones.close();

		ContanctAdapter objAdapter = new ContanctAdapter(
				ContactListActivity.this, R.layout.alluser_row, list);
		listView.setAdapter(objAdapter);
		// SparseBooleanArray mChecked = new SparseBooleanArray();

		if (null != list && list.size() != 0) {
			Collections.sort(list, new Comparator<ContactBean>() {

				@Override
				public int compare(ContactBean lhs, ContactBean rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			AlertDialog alert = new AlertDialog.Builder(
					ContactListActivity.this).create();
			alert.setTitle("");

			alert.setMessage(list.size() + " Contact Found!!!");

			alert.setButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alert.show();

		} else {
			showToast("No Contact Found!!!");
		}
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		

	}
	


}
