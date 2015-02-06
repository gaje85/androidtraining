package com.doepiccoding.facedetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
	private int posX;
	private int posY;
	private Bitmap leftEyeBmp;
	private int leftEyeBmpWidth;
	private int leftEyeBmpHeight;
	private Paint paint = new Paint();

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		leftEyeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.my_face_glasses);
		if(leftEyeBmp != null){
			leftEyeBmpWidth = leftEyeBmp.getWidth();
			leftEyeBmpHeight = leftEyeBmp.getHeight();
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if(leftEyeBmp != null && !leftEyeBmp.isRecycled())leftEyeBmp.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(leftEyeBmp, posX - leftEyeBmpWidth /2, posY - leftEyeBmpHeight /2, paint);
		
	}
	
	public void setPoints(int x, int y){
		this.posX = x;
		this.posY = y;
	}

}
