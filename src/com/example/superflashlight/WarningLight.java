package com.example.superflashlight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WarningLight extends FlashLight {
	
	protected boolean mWarningLightFlicker;		// true:��˸	false��ֹͣ��˸
	protected boolean mWaringLightState;		// true����on��off false:��off��on
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mWarningLightFlicker = true;		// ��ʼ��˸
		
	}
	
	class WarningLightThread extends Thread {
		@Override
		public void run() {
			mWarningLightFlicker = true;
			while (mWarningLightFlicker) {
				try {
					Thread.sleep(300);
					mWarningHandler.sendEmptyMessage(0);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	private Handler mWarningHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (mWaringLightState) {
				mImageViewWarningLight1.setImageResource(R.drawable.warning_light_on);
				mImageViewWarningLight2.setImageResource(R.drawable.warning_light_off);
				mWaringLightState = false;
			} else {
				mImageViewWarningLight1.setImageResource(R.drawable.warning_light_off);
				mImageViewWarningLight2.setImageResource(R.drawable.warning_light_on);
				mWaringLightState = true;
			}
		}
	};

}
