package com.paypal.first.contactsadapter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class NewListActivity extends Activity {

    ListView listView = null;
    List<Person> listPerson = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        listView = (ListView)findViewById(R.id.listView);
        listPerson = new ArrayList<Person>();
        for(int i=0;i<5;i++){
            Person person = new Person();
            person.setFirstName("first "+i);
            person.setLastName("last "+i);
            person.setAge(i+1);
            listPerson.add(person);
        }
        final NewListAdapter adapter = new NewListAdapter(this,R.layout.newlistlayout,listPerson);
        listView.setAdapter(adapter);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Person person = new Person();
                    person.setFirstName("firsttimer ");
                    person.setLastName("lasttimer ");
                    person.setAge(34);
                    listPerson.add(person);
                    Log.i("test", "Person added in timer");
                   // listView.refreshDrawableState();
                    NewListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        },0,5000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
