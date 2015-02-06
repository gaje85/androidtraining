package com.doepiccoding.facedetection;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

public class MainActivity extends Activity {

	private int midScreenWidth;
	private int midScreenHeight;
	private Camera mCamera;
	private SurfaceView cameraSurface;
	private SurfaceHolder cameraSurfaceHolder;
	private CustomView myCustomView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		myCustomView = (CustomView)findViewById(R.id.myCustomView);
		cameraSurface = (SurfaceView) findViewById(R.id.cameraSurface);
        cameraSurfaceHolder = cameraSurface.getHolder();
        cameraSurfaceHolder.addCallback(cameraSurfaceHolderCallbacks);
        
        //Screen sizes...
        Display display = getWindowManager().getDefaultDisplay();
        midScreenHeight = display.getHeight() / 2;
        midScreenWidth = display.getWidth() / 2;
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
                    /*if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
                    	cameraId = camIndex;
                    	break;
                    }*/
                }
                mCamera = Camera.open();
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
			mCamera.setFaceDetectionListener(faceDetectionListener);
			mCamera.startPreview();
			mCamera.startFaceDetection();
		} 
	};
	
	private FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {
		
		@Override
		public void onFaceDetection(Face[] faces, Camera camera) {
			for(int i = 0 ; i < faces.length ; i++){
				int posX = midScreenWidth - faces[0].rect.centerX();
				int posY = midScreenHeight + faces[0].rect.centerY();
				myCustomView.setPoints(posX, posY);
			}
			myCustomView.invalidate();
		}
	};
	
	protected void onDestroy() {
		super.onDestroy();
		if(mCamera != null){
			mCamera.release();
		}
	};
}
