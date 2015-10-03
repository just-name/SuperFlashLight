package com.example.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.example.superflashlight.widget.HideTextView;

public class PoliceLight extends ColorLight {
	
	protected boolean mPoliceState;
	protected HideTextView mHideTextViewPoliceLight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mHideTextViewPoliceLight = (HideTextView) findViewById(R.id.textview_hide_police_light);
		
	}
	
	class PoliceThread extends Thread {
		@Override
		public void run() {
			super.run();
			mPoliceState = true;
			while (mPoliceState) {
				mHandler.sendEmptyMessage(Color.BLUE);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.BLACK);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.RED);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.BLACK);
				sleepExt(100);
				
			}
		}
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message message) {
			int color = message.what;
			mUIPoliceLight.setBackgroundColor(color);
		}
	};
	

}
