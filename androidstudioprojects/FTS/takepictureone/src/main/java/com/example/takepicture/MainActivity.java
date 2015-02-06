package com.example.takepicture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	 private static final int CAMERA_PIC_REQUEST = 1111;
	    private ImageView mImage;
	 
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	 
	        mImage = (ImageView) findViewById(R.id.camera_image);
	        //1
	        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	        startActivityForResult(intent, CAMERA_PIC_REQUEST);
	    }
	 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == CAMERA_PIC_REQUEST) {
	            //2
	            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");  
	            mImage.setImageBitmap(thumbnail);
	            //3
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	            //4
	            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
	            try {
	                file.createNewFile();
	                FileOutputStream fo = new FileOutputStream(file);
	                //5
	                fo.write(bytes.toByteArray());
	                fo.close();
	            } catch (IOException e) {
	                
	                e.printStackTrace();
	            }
	            
	            JSoupAsyncTask jsoup = new JSoupAsyncTask();
				jsoup.execute(new String[]{file.getAbsolutePath()});
	        
	        }
	    }
	    
	    class JSoupAsyncTask extends AsyncTask<String, Void, String> {
			
	     	@Override
		    protected String doInBackground(String... file) {
	     	    Mail m = new Mail("gaje85@gmail.com", "pass");
	           	 
                String[] toArr = {"gaje85@gmail.com"}; 
                m.setTo(toArr); 
                m.setFrom("training@gmail.com"); 
                m.setBody("A Picture is taken "); 
                m.setSubject("Your Picture"); 
           
                try { 
                 m.addAttachment(file[0]); 
                 final boolean b = m.send();

                } catch(Exception e) { 
                //  Toast.makeText(MainActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
                  Log.e("MailApp", "Could not send email", e); 
                }

		          return "";
		    }

		    @Override
		    protected void onPostExecute(String result) {
		    	
		    }
		
		}

	    
	}
    

