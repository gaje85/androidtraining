package com.paypal.first.resolveaddress;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et = (EditText)findViewById(R.id.editText);
        Button button = (Button)findViewById(R.id.address);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("test",et.getText().toString());
                HttpAsyncTask asyncTask = new HttpAsyncTask();
                asyncTask.execute(et.getText().toString());
            }
        });

    }

    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String response = "";
            for (String oneplace : params) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address="+oneplace+"&sensor=false");
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject topObject = new JSONObject(s);
                JSONArray resArr = (JSONArray)topObject.get("results");
                JSONArray arr = (JSONArray)( ((JSONObject)resArr.get(0)).get("address_components"));
                String address = "";
                for (int i=0;i<arr.length();i++){
                    JSONObject obj = (JSONObject)arr.get(i);
                    address += obj.get("long_name") + "\n";
                }
                TextView tv = (TextView)findViewById(R.id.result);
                tv.setText(address);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
