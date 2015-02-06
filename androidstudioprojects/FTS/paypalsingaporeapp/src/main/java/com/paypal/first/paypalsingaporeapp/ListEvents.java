package com.paypal.first.paypalsingaporeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class ListEvents extends ActionBarActivity {
    ListView listView = null;
    List<Event> listEvent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        listView = (ListView)findViewById(R.id.listView);
        Button button = (Button)findViewById(R.id.mapbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEvents.this,MapsActivity.class);
                intent.putExtra("data",(ArrayList)listEvent);
                startActivity(intent);
            }
        });
        // Call async task

        JSoupTask task = new JSoupTask();
        task.execute(new String[]{""});


    }



    class JSoupTask extends AsyncTask<String, Void, List<Event>> {
        @Override
        protected List<Event> doInBackground(String... params) {
            try {
                listEvent = new ArrayList<Event>();
                Document doc = Jsoup.connect("https://www.eventbrite.sg/d/singapore--singapore/events/").get();
                //System.out.println(doc.toString());
                Elements elements = doc.getElementsByAttributeValue("itemprop","startDate");
                Elements elementsLocation = doc.getElementsByAttributeValue("itemprop","location");
                Elements elementsSummary = doc.getElementsByAttributeValue("itemprop","summary");
                for (int i=0;i<elements.size();i++){
                    //System.out.println(elements.get(i));
                    Event event = new Event();
                    Element element = elements.get(i);
                    String dateValue = element.text();
                    Element elementLocation = elementsLocation.get(i);
                    String locationValue = elementLocation.text();
                    Element elementSummary = elementsSummary.get(i);
                    String eventName = elementSummary.text();
                    Log.i("test", dateValue + " == " + locationValue + " == " + eventName);
                    event.setDate(dateValue);
                    event.setName(eventName);
                    event.setPlace(locationValue);
                    listEvent.add(event);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return listEvent;
        }

        @Override
        protected void onPostExecute(List<Event> s) {
            final NewListAdapter adapter = new NewListAdapter(ListEvents.this,R.layout.newlistlayout,s);
            listView.setAdapter(adapter);
        }
    }

}
