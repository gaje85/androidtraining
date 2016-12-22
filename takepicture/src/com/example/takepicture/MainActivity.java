package com.example.takepicture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int currentCount = 0;
	 
	 private static final int CAMERA_PIC_REQUEST = 1111;
	    private ImageView mImageOne,mImageTwo,mImageThree,mImageFour;
	    private Button createButton,deleteButton = null;
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	 
	        mImageOne = (ImageView) findViewById(R.id.camera_image1);
	        mImageTwo = (ImageView) findViewById(R.id.camera_image2);
	        mImageThree = (ImageView) findViewById(R.id.camera_image3);
	        mImageFour = (ImageView) findViewById(R.id.camera_image4);
	        //1
	        Button createButton = (Button)findViewById(R.id.imgButton);
	        createButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				     startActivityForResult(intent, CAMERA_PIC_REQUEST);	
				}
			});
	        Button deleteButton = (Button)findViewById(R.id.delButton);
	        deleteButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mImageOne.setImageBitmap(null);
					mImageTwo.setImageBitmap(null);
					mImageThree.setImageBitmap(null);
					mImageFour.setImageBitmap(null);
					currentCount = 0;
				}
			}); 
	       
	    }
	 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == CAMERA_PIC_REQUEST) {
	            //2
	            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	            switch (currentCount) {
				case 0:
					mImageOne.setImageBitmap(thumbnail);
					break;
				case 1:
					mImageTwo.setImageBitmap(thumbnail);
					break;
				case 2:
					mImageThree.setImageBitmap(thumbnail);
					break;
				case 3:
					mImageFour.setImageBitmap(thumbnail);
					break;
				default:
					Toast.makeText(this, "Only 4 images of form 16 required", Toast.LENGTH_LONG).show(); 
					break;
				}
	            
	            //3
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	            //4
	            File file = new File(Environment.getExternalStorageDirectory()+File.separator + currentCount+"image.jpg");
	            try {
	                file.createNewFile();
	                FileOutputStream fo = new FileOutputStream(file);
	                //5
	                fo.write(bytes.toByteArray());
	                fo.close();
	            } catch (IOException e) {
	                
	                e.printStackTrace();
	            }
	            currentCount++;
	            
	        }
	    }
	    	    
	}
    

