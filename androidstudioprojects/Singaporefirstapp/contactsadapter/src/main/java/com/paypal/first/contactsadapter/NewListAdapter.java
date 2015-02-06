package com.paypal.first.contactsadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gajg on 2/4/2015.
 */
public class NewListAdapter extends ArrayAdapter<Person>{

    List<Person> persons = null;
    int layoutResource = 0;
    Context context;
    public NewListAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
        this.persons = objects;
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
        Person person = persons.get(position);
        TextView firstNameView = (TextView)view.findViewById(R.id.firstname);
        TextView lastNameView = (TextView)view.findViewById(R.id.lastname);
        TextView ageView = (TextView)view.findViewById(R.id.age);

        firstNameView.setText(person.getFirstName());
        lastNameView.setText(person.getLastName());
        ageView.setText(person.getAge()+"");
        return view;
    }
}
