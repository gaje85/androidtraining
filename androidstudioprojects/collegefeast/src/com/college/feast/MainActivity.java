package com.college.feast;

import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button but = (Button)findViewById(R.id.but);
        but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSoupAsyncTask jsoup = new JSoupAsyncTask();
				jsoup.execute(new String[]{});
				//addCalendarEvent();
			}
		});
    }

    
    public void addCalendarEvent(){
    	 
    	/* Intent intent = new Intent(Intent.ACTION_INSERT)
         .setData(Events.CONTENT_URI)
         .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
         .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() * 1000 * 60 * 60)
         .putExtra(Events.TITLE, "Yoga")
         .putExtra(Events.DESCRIPTION, "Group class")
         .putExtra(Events.EVENT_LOCATION, "The gym")
         .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
         .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
          startActivity(intent); */
    	
    /*	Calendar beginTime = Calendar.getInstance();
        beginTime.set(2014, 8 - 1, 30, 7, 30);



        ContentValues l_event = new ContentValues();
        l_event.put("calendar_id", CalIds[0]);
        l_event.put("title", "event");
        l_event.put("description",  "This is test event");
        l_event.put("eventLocation", "School");
        l_event.put("dtstart", beginTime.getTimeInMillis());
        l_event.put("dtend", beginTime.getTimeInMillis());
        l_event.put("allDay", 0);
        l_event.put("rrule", "FREQ=YEARLY");
        // status: 0~ tentative; 1~ confirmed; 2~ canceled
        // l_event.put("eventStatus", 1);

        l_event.put("eventTimezone", "India");
        Uri l_eventUri;
        if (Build.VERSION.SDK_INT >= 8) {
            l_eventUri = Uri.parse("content://com.android.calendar/events");
        } else {
            l_eventUri = Uri.parse("content://calendar/events");
        }
        Uri l_uri = MainActivity.this.getContentResolver()
                .insert(l_eventUri, l_event);
                
         */
    	
    	ContentResolver cr = MainActivity.this.getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis());
        values.put(CalendarContract.Events.TITLE, "test3333");
        values.put(CalendarContract.Events.DESCRIPTION, "This is test 3333");

        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        // default calendar
        values.put(CalendarContract.Events.CALENDAR_ID, 1);

        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY");
        //for one hour
        values.put(CalendarContract.Events.DURATION, "+P1H");

        values.put(CalendarContract.Events.HAS_ALARM, 1);

        // insert event to calendar
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }
    
    
    
    class JSoupAsyncTask extends AsyncTask<String, Void, String> {
		
     	@Override
	    protected String doInBackground(String... place) {
     		  try{
     			Document doc = Jsoup.connect("http://campus.knowafest.com/states/Tamil-Nadu/").get();
     			
     			Element table = doc.attr("class","dataTableClass");
     			//Log.i("jsoup", "reHref === "+relHref);
     			/*for(Element link : links){
     				String relHref = link.attr("href"); 
         			String absHref = link.attr("abs:href");
         			Log.i("jsoup", "reHref === "+relHref);
     			}*/
     			
     			
     		  }catch(Exception e){
     			  e.printStackTrace();
     		  }
	          return "";
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	
	    }
	
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
