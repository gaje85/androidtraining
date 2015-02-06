package com.example.countryappfrag;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CountryListFragment extends Fragment implements AddCountryListener{
	
	Button addCountryButton;
	TextView countryListTV;
	StringBuffer countries = new StringBuffer();
	
	public CountryListFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		
		View view = inflater.inflate(R.layout.fragment_country_list, 
									container, 
									false);
		countryListTV = (TextView) view.findViewById(R.id.textView1);
		//get a reference to the button object
		addCountryButton = (Button)view.findViewById(R.id.addCountryButton);
		//attach an onclicklistener to the button
		addCountryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadAddCountryFragment();
			}
		});
		
		
		return view;
	}
	
	private void loadAddCountryFragment() {
		FragmentManager manager = getFragmentManager();
		//create the fragment
		AddCountryFragment acf = new AddCountryFragment();
		//attach the countrylistfragment as a listener to the
		//addcountryfragment
		acf.listener = this;
		
		//create a transaction
		FragmentTransaction trans = manager.beginTransaction();
		trans.remove(this);
		trans.add(R.id.mainLayout, acf, "AFC");
		//add the transaction to the back stack so that we 
		//handle the back button correctly
		trans.addToBackStack(null);
		
		trans.commit();
	}

	@Override
	public void add(String countryName) {
		countries.append(countryName + "\n");

	}

	@Override
	public void onResume() {
		super.onResume();
		countryListTV.setText(countries.toString());
	}
	
	

	
}
