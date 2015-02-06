package com.paypal.first.singaporefirstapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity
 {

    public static final String TAG = "firstapp";
     public static int request_code = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.next);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Button Clicked ..");
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("one","This is from main activity");
                startActivityForResult(intent, request_code);
            }
        });
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode == request_code){
             Bundle bundle = data.getExtras();
             String sec = (String)bundle.getString("sec");
             Log.i("test",sec);
         }
     }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

     @Override
     protected void onPause() {
         super.onPause();
         Log.i("test","on pause on main activity");
     }

     @Override
     protected void onRestart() {
         super.onRestart();
         Log.i("test","on restart on main activity");
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
