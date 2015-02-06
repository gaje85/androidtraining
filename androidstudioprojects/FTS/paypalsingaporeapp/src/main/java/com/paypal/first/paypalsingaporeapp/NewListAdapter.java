package com.paypal.first.paypalsingaporeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gajg on 2/4/2015.
 */
public class NewListAdapter extends ArrayAdapter<Event>{

    List<Event> eventsList = null;
    int layoutResource = 0;
    Context context;
    public NewListAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.eventsList = objects;
        this.layoutResource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResource, null);

            //holder = new ViewHolder();
            //view.setTag(holder);
        }
        Event event = eventsList.get(position);
        TextView nameView = (TextView)view.findViewById(R.id.eventname);
        TextView dateView = (TextView)view.findViewById(R.id.eventdate);
        TextView placeView = (TextView)view.findViewById(R.id.eventplace);

        nameView.setText(event.getName());
        dateView.setText(event.getDate());
        placeView.setText(event.getPlace());
        return view;
    }
}
