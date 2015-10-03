package com.example.superflashlight;

import com.example.superflashlight.widget.HideTextView;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;

public class Bulb extends Morse {
	
	protected boolean mBulbCrossFadeFlag;
	protected TransitionDrawable mDrawable;
	protected HideTextView mHideTextViewBulb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDrawable = (TransitionDrawable) mImageViewBulb.getDrawable();
		mHideTextViewBulb = (HideTextView) findViewById(R.id.textview_hide_bulb);
	}
	
	public void onClick_BulbCrossFade(View view) {
		if (!mBulbCrossFadeFlag) {		// 如果没亮
			mDrawable.startTransition(500);
			mBulbCrossFadeFlag = true;
			screenBrightness(1f);
		} else {					   // 如果亮
			mDrawable.reverseTransition(500);
			mBulbCrossFadeFlag = false;
			screenBrightness(0f);
		}
	}

}
