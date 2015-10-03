package com.example.superflashlight;

import android.graphics.Color;
import android.view.View;


public class MainActivity extends Settings {
	
	public void onClick_Controller(View view) {
		
		// 隐藏所有布局内容
		hideAllUI();
		
		// 如果当前不在主界面， 则当点击圆形按钮时将显示主界面
		if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
			mUIMain.setVisibility(View.VISIBLE);
			mCurrentUIType = UIType.UI_TYPE_MAIN;
			
			mWarningLightFlicker = false;		//为假则退出警示灯线程循环，不再闪烁
			screenBrightness(mDefaultScreenBrightness / 255f);		// 设置转换后的屏幕亮度
			
			if (mBulbCrossFadeFlag) {
				mDrawable.reverseTransition(0);	
			}
			mBulbCrossFadeFlag = false;			// 默认灯泡为关闭状态
			mPoliceState = false;
			
			mSharedPreferences.edit()
					.putInt("warning_light_interval", mCurrentWarningLightInterval)
					.putInt("police_light_interval", mCurrentPoliceLightInterval)
					.commit();
			
		} else {	// 如果在主界面，则进行判断
			switch (mLastUIType) {
			case UI_TYPE_FLASHLIGHT:		// 闪光灯界面
				mUIFlashlight.setVisibility(View.VISIBLE);
				screenBrightness(1f);
				mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
				break;
			case UI_TYPE_WARNINGLIGHT:		// 警示灯界面
				mUIWarningLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
				new WarningLightThread().start();
				break;
			case UI_TYPE_MORSE:				// 莫尔斯界面
				mUIMorse.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_MORSE;
				break;
			case UI_TYPE_BLUB:				// 灯泡界面
				mUIBulb.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_BLUB;
				break;
			case UI_TYPE_COLOR:				// 颜色界面
				mUIColorLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_COLOR;
				break;
			case UI_TYPE_POLICE:			// 警灯界面
				mUIPoliceLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_POLICE;
				new PoliceThread().start();
				break;
				
			case UI_TYPE_SETTINGS:			// 设置界面
				mUISettings.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_SETTINGS;
				break;

			default:
				break;
			}
		}
	}
	
	
	public void onClick_ToFlashlight(View view) {
		hideAllUI();
		mUIFlashlight.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
		mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
	}
	
	public void onClick_ToWarninglight(View view) {
		hideAllUI();
		mUIWarningLight.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
		mCurrentUIType = mLastUIType;
		screenBrightness(1f);		// 设置屏幕亮度，1最亮，0最暗
		new WarningLightThread().start();
	}
	
	public void onClick_ToMorse(View view) {
		hideAllUI();
		mUIMorse.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_MORSE;
		mCurrentUIType = mLastUIType;
	}
	
	public void onClick_ToBulb(View view) {
		hideAllUI();
		mUIBulb.setVisibility(View.VISIBLE);
		mHideTextViewBulb.hide();
		mHideTextViewBulb.setTextColor(Color.BLACK);
		mLastUIType = UIType.UI_TYPE_BLUB;
		mCurrentUIType = mLastUIType;
	}
	
	public void onClick_ToColor(View view) {
		hideAllUI();
		mUIColorLight.setVisibility(View.VISIBLE);
		screenBrightness(1f);
		mLastUIType = UIType.UI_TYPE_COLOR;
		mCurrentUIType = mLastUIType;
		
		// 设置当前颜色的互补色
		mHideTextViewColorLight.setTextColor( Color.rgb(
				255 - Color.red(mCurrentColorLight), 
				255 - Color.green(mCurrentColorLight), 
				255 - Color.blue(mCurrentColorLight)));
	}

	public void onClick_ToPolice(View view) {
		hideAllUI();
		mUIPoliceLight.setVisibility(View.VISIBLE);
		screenBrightness(1f);
		mLastUIType = UIType.UI_TYPE_POLICE;
		mCurrentUIType = mLastUIType;
		mHideTextViewPoliceLight.hide();
		new PoliceThread().start();
	}
	
	public void onClick_ToSettings(View view) {
		hideAllUI();
		mUISettings.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_SETTINGS;
		mCurrentUIType = mLastUIType;
	}
         
	
}
