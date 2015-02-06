package com.example.countryappfrag;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddCountryFragment extends Fragment {
	Button doneButton;
	Button cancelButton;
	EditText countryNameET;
	AddCountryListener listener;
	
	public AddCountryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = 
				inflater.inflate(R.layout.fragment_add_country, container, false);
		countryNameET = (EditText) view.findViewById(R.id.editText1);
		
		doneButton = (Button) view.findViewById(R.id.button1);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String countryName = countryNameET.getText().toString();
				if(listener != null) {
					listener.add(countryName);
				}
				FragmentManager manager = getFragmentManager();
				manager.popBackStack();
			}
		});
		
		cancelButton = (Button) view.findViewById(R.id.button2);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager manager = getFragmentManager();
				manager.popBackStack();
			}
		});
		return view;
	}
	
	
}
