package us.example.accelerometer;



import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorDemo extends Activity implements SensorListener {
  private static final String TAG = "SensorDemo";
  private SensorManager sensorManager;
  private TextView outView;
  private int sensor = SensorManager.SENSOR_ORIENTATION;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    outView = (TextView) findViewById(R.id.output);

    // Real sensor manager
    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
  }

  /** Register for the updates when Activity is in foreground */
  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume");
    sensorManager.registerListener(this, sensor);
  }

  /** Stop the updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause");
    sensorManager.unregisterListener(this, sensor);
  }

  public void onAccuracyChanged(int sensor, int accuracy) {
    Log.d(TAG, String.format("onAccuracyChanged  sensor: %d   accuraccy: %d",
        sensor, accuracy));
  }

  public void onSensorChanged(int sensorReporting, float[] values) {
    if (sensorReporting != sensor)
      return;

    float azimuth = Math.round(values[0]);
    float pitch = Math.round(values[1]);
    float roll = Math.round(values[2]);

    String out = String.format("Azimuth: %.2f\nPitch: %.2f\nRoll: %.2f",
        azimuth, pitch, roll);
    Log.d(TAG, out);
    outView.setText(out);
  }
}