package mb.android.cps.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class CurDevInfo {

	private TelephonyManager teleMgr;
	//private String thisMobNum;
	private String thisMobIMEI;
	private String thisMobInfo;

	public CurDevInfo(Context context) {
		teleMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	
		//thisMobNum = teleMgr.getLine1Number();
		thisMobIMEI = teleMgr.getDeviceId();

		//if (thisMobNum.equals("") || thisMobNum == null) {
//			if (thisMobIMEI.equals("") || thisMobIMEI == null) {
//				thisMobInfo = "XXX";
//			} else {
				thisMobInfo = thisMobIMEI;
//			}
	//	} 
	//else {
//			thisMobInfo = thisMobNum;
	//	}
	}
	
	public TelephonyManager getTeleMgr() {
		return teleMgr;
	}	

//	public String getThisMobNum() {
//		return thisMobNum;
//	}

	public String getThisMobIMEI() {
		return thisMobIMEI;
	}

	public String getThisMobInfo() {
		return thisMobInfo;
	}
	
}
