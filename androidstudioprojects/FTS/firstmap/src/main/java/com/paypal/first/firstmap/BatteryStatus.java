package com.paypal.first.firstmap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class BatteryStatus extends Activity {
	private TextView batteryStatus;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.battmain);
		batteryStatus = (TextView) this.findViewById(R.id.text);

		getBatteryStatus();
	}

	private void getBatteryStatus() {
		BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				// context.unregisterReceiver(this);

				// integer containing the current health constant.
				int health = intent
						.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);

				// integer field containing the current battery level, from 0 to
				// EXTRA_SCALE.
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

				// integer containing the maximum battery level.
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

				// integer indicating whether the device is plugged in to a
				// power source
				int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,
						-1);

				// boolean indicating whether a battery is present.
				boolean present = intent.getBooleanExtra(
						BatteryManager.EXTRA_PRESENT, false);

				// integer containing the current status constant.
				int status = intent
						.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

				// String describing the technology of the current battery.
				String technology = intent
						.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);

				// integer containing the current battery temperature.
				int temperature = intent.getIntExtra(
						BatteryManager.EXTRA_TEMPERATURE, -1);
				temperature = temperature / 10;

				// integer containing the current battery voltage level.
				int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,
						-1);

				String healthSt = findHealthSt(health);
				String remPc = calcRemPc(level, scale);
				String pluggedSt = findPluggedSt(plugged);
				String statusSt = findStatusSt(status);

				batteryStatus.setText("\n\nBattery Present : " + present);
				batteryStatus.append("\n\nHealth : " + healthSt);
				batteryStatus.append("\n\nRemaining % : " + remPc);
				batteryStatus.append("\n\nPlugged into : " + pluggedSt);
				batteryStatus.append("\n\nState : " + statusSt);
				batteryStatus.append("\n\nTechnology : " + technology);
				batteryStatus.append("\n\nTemperature : " + temperature
						+ "\u00B0 C");
				batteryStatus.append("\n\nVoltage : " + voltage + "mV");

			}

			private String findStatusSt(int status) {
				String result = "N/A";
				switch (status) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					result = "Charging";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					result = "Discharging";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					result = "Full";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					result = "Not Charging";
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					result = "Unknown";
					break;
				}
				return result;
			}

			private String findPluggedSt(int plugged) {
				String result = "N/A";
				switch (plugged) {
				case 0:
					result = "Battery";
					break;
				case BatteryManager.BATTERY_PLUGGED_AC:
					result = "AC";
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					result = "USB";
					break;
				}
				return result;
			}

			private String calcRemPc(int level, int scale) {
				if (level >= 0 && scale > 0) {
					return "" + ((level * 100) / scale);
				}
				return "N/A";
			}

			private String findHealthSt(int health) {
				String result = "N/A";
				switch (health) {
				case BatteryManager.BATTERY_HEALTH_DEAD:
					result = "Dead";
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					result = "Good";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					result = "Over Voltage";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					result = "Over Heat";
					break;
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					result = "Unknown";
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					result = "Unspecified Failure";
					break;
				}
				return result;
			}

		};

		// This is a sticky broadcast containing the charging state, level, and
		// other information about the battery.
		// loop it for 
		IntentFilter batteryLevelFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelReceiver, batteryLevelFilter);

	}
}









