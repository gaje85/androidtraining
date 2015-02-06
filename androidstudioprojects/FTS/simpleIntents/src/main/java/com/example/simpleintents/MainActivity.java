package com.example.simpleintents;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void phoneCall(View view) {
    		//send a request to android system to perform an action
    		Intent intent  = new Intent(Intent.ACTION_CALL);
    		
    		intent.setData(Uri.parse("tel:555-555-5555"));
    		
    		//send the intent to activity manager
    		startActivity(intent);
    }

    public void browse(View view) {
    		Intent intent = new Intent(Intent.ACTION_VIEW);
    		//intent.setData(Uri.parse("http://www.google.com"));
    		intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
    		startActivity(intent);
    }
    
    public void second(View view) {
    		Intent intent = new Intent(this, SecondActivity.class);
    		startActivity(intent);
    }
    
    public void countryApp(View view) {
    		Intent intent = new Intent();
    		ComponentName name = new ComponentName("com.example.countryapp", 
    				"com.example.countryapp.CountryListActivity");
    		intent.setComponent(name);
    		startActivity(intent);    		
    }
    
    public void addCountry(View view) {
    		Intent intent = 
    				new Intent("com.example.countryapp.action.ADD");
    		startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
