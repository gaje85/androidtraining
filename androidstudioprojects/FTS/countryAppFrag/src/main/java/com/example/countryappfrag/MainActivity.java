package com.example.countryappfrag;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loadCountryListFragment();
    }

    

    @Override
	public void onBackPressed() {		
		FragmentManager manager = getFragmentManager();
		int length = manager.getBackStackEntryCount();
		if(length >= 1) {
			manager.popBackStack();
		} else {
			super.onBackPressed();
		}
	}



	private void loadCountryListFragment() {
    		//get access to the fragment manager
    		FragmentManager manager = getFragmentManager();
    		
    		//create the fragment
    		CountryListFragment clf = new CountryListFragment();
    		
    		//begin a fragment transaction
    		FragmentTransaction trans = manager.beginTransaction();
    		
    		//add the fragment to the transaction
    		trans.add(R.id.mainLayout, clf, "CLF");
    		
    		trans.commit();
    	
		
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
