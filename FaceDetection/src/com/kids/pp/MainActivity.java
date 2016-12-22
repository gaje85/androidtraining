package com.kids.pp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

public class MainActivity extends Activity {

	private int midScreenWidth;
	private int midScreenHeight;
	private Camera mCamera;
	private SurfaceView cameraSurface;
	private SurfaceHolder cameraSurfaceHolder;
	private byte[] dat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
	
		cameraSurface = (SurfaceView) findViewById(R.id.cameraSurface);
        cameraSurfaceHolder = cameraSurface.getHolder();
        cameraSurfaceHolder.addCallback(cameraSurfaceHolderCallbacks);
        
        //Screen sizes...
        Display display = getWindowManager().getDefaultDisplay();
        midScreenHeight = display.getHeight() / 2;
        midScreenWidth = display.getWidth() / 2;
        
        
	}
	PictureCallback mPicture = new PictureCallback() {

        
		@Override
        public void onPictureTaken(byte[] data, Camera camera) {
			 File pictureFile = getOutputMediaFile();
	            Log.e("path file 11111111", ""+pictureFile);
	            if (pictureFile == null) {
	                return;
	            }
			BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 1;
	        options.inDither = false; // Disable Dithering mode
	        options.inPurgeable = true; // Tell to gc that whether it needs free
	                                    // memory, the Bitmap can be cleared
	        options.inInputShareable = true; // Which kind of reference will be
	                                            // used to recover the Bitmap
	                                            // data after being clear, when
	                                            // it will be used in the future
	        options.inTempStorage = new byte[32 * 1024];
	        options.inPreferredConfig = Bitmap.Config.RGB_565;
	        Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

	        float orientation;
	        // others devices
	        if(bMap.getHeight() < bMap.getWidth()){
	            orientation = 90;
	            
	        } else {
	            orientation = 0;
	        }

	        Bitmap bMapRotate;
	        if (orientation != 0) {
	        	//Matrix matrix = new Matrix();
	            //matrix.postRotate(orientation);
	            float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1};
	            Matrix rotateRight = new Matrix();
	            Matrix matrixMirrorY = new Matrix();
	            matrixMirrorY.setValues(mirrorY);

	            rotateRight.postConcat(matrixMirrorY);

	            rotateRight.preRotate(270);
	            bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
	                    bMap.getHeight(), rotateRight, true);
	            Log.i("ss", "if");
	            
	        } else{
	        	bMapRotate = Bitmap.createScaledBitmap(bMap, bMap.getWidth(),
	                    bMap.getHeight(), true);
	            Log.i("ss", "else ");
	        }

	        FileOutputStream out;
	    boolean mExternalStorageAvailable = false;
	    boolean mExternalStorageWriteable = false;
	        try {
	      //  String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
	        //String fileName = "/" + System.currentTimeMillis() + ".jpg";
	        	 out = new FileOutputStream(pictureFile);
	            bMapRotate.compress(Bitmap.CompressFormat.JPEG, 50, out);
	           
	           
	            out.close();
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bMapRotate.compress(Bitmap.CompressFormat.JPEG, 50 , baos);    
	            byte[] b = baos.toByteArray(); 
		          dat = b;
		          if (bMapRotate != null) {
		                bMapRotate.recycle();
		                bMapRotate = null;
		            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

           
           /* try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
               
            }  catch (Exception e) {
            	e.printStackTrace();
            }*/
            Intent intent = new Intent(MainActivity.this,EnterNameActivity.class);
	          intent.putExtra("data", dat);
	          startActivity(intent);
        }
		
    };
    private static File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        else {
            File folder_gui = new File(Environment.getExternalStorageDirectory() + File.separator + "GUI");
            if (!folder_gui.exists()) {
                Log.v("ss", "Creating folder: " + folder_gui.getAbsolutePath());
                folder_gui.mkdirs();
            }
            File outFile = new File(folder_gui, "temp.jpg");
            Log.v("ss", "Returnng file: " + outFile.getAbsolutePath());
            return outFile;
        }
    }
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		Log.i("ss", "Touch is called "+event.getAction());
		if(event.getAction() == 0){
			// get the photo
			  mCamera.takePicture(null, null, mPicture);
			 // mCamera.release();
	          
		}
		
		return super.onTouchEvent(event);
	}
	
	private SurfaceHolder.Callback cameraSurfaceHolderCallbacks = new SurfaceHolder.Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if(mCamera == null)return;
			mCamera.stopPreview();
			mCamera.stopFaceDetection();
            mCamera.release();
            mCamera = null;
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {

            try {
            	//Try to open front camera if exist...
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                int cameraId = 0;
                int camerasCount = Camera.getNumberOfCameras();
                for ( int camIndex = 0; camIndex < camerasCount; camIndex++ ) {
                    Camera.getCameraInfo(camIndex, cameraInfo );
                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
                    	cameraId = camIndex;
                    	break;
                    }
                }
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
				mCamera.setPreviewDisplay(holder);
				
			} catch (Exception exception) {
				android.util.Log.e("TrackingFlow", "Surface Created Exception", exception);
				if(mCamera == null)return;
				mCamera.release();
				mCamera = null;  
            }
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			mCamera.startPreview();
	} 
	};
	
	
	protected void onDestroy() {
		super.onDestroy();
		/*if(mCamera != null){
			mCamera.release();
		}*/
	};
}
