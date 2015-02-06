package com.paypal.first.contactsadapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ContanctAdapter extends ArrayAdapter<ContactBean> {

	private Activity activity;
	private List<ContactBean> items;
	private int row;
	private ContactBean objBean;
//	boolean[] itemChecked;
   // boolean array[];
//	private int pos;


	public ContanctAdapter(Activity act, int row, List<ContactBean> items) {
		super(act, row, items);

		this.activity = act;
		this.row = row;
		this.items = items;
		//array =new boolean[items.size()];

	}
   // Access data from model(collections) and set it in out layout components
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		 final ViewHolder holder;
		final int pos=position;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
		return view;

		objBean = items.get(position);
		

		holder.tvname = (TextView) view.findViewById(R.id.tvname);
		holder.tvPhoneNo = (TextView) view.findViewById(R.id.tvphone);
		holder.groupcheckbox = (CheckBox) view.findViewById(R.id.groupcheckbox);
		

		if (holder.tvname != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tvname.setText(Html.fromHtml(objBean.getName()));
		}
		if (holder.tvPhoneNo != null && null != objBean.getPhoneNo()
				&& objBean.getPhoneNo().trim().length() > 0) {
			holder.tvPhoneNo.setText(Html.fromHtml(objBean.getPhoneNo()));
		}
		/*if(holder.groupcheckbox !=null && null !=objBean.getCheckbox() && objBean.getCheckbox().trim().length() >0){
			holder.groupcheckbox.setText(Html.fromHtml(objBean.getCheckbox()));
		}*/
		/*if (itemChecked[position])
			   holder.groupcheckbox.setChecked(true);
			  else
			   holder.groupcheckbox.setChecked(false);*/
//		txt1.setText(items.get(position));
        int selectedindexitem=0;
			 
			  holder.groupcheckbox.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View v) {
			    // TODO Auto-generated method stub
			    /*if (holder.groupcheckbox.isChecked())
			     itemChecked[position] = true;
			    else
			     itemChecked[position] = false;
			   }*/
				   if(holder.groupcheckbox.isChecked())
	                {
	                    items.get(pos).setChecked(true);


	                }else{
	                	items.get(pos).setChecked(false);
	                    //array[pos]=false;
	                }
				   	            }
	        }); 
	        holder.groupcheckbox.setChecked(items.get(pos).isChecked());


		return view; 
	}

	public class ViewHolder {
		public CheckBox groupcheckbox;
		public TextView tvname, tvPhoneNo;
	}

}
