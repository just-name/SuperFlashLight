package com.example.superflashlight;

import android.graphics.Color;
import android.view.View;


public class MainActivity extends Settings {
	
	public void onClick_Controller(View view) {
		
		// �������в�������
		hideAllUI();
		
		// �����ǰ���������棬 �򵱵��Բ�ΰ�ťʱ����ʾ������
		if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
			mUIMain.setVisibility(View.VISIBLE);
			mCurrentUIType = UIType.UI_TYPE_MAIN;
			
			mWarningLightFlicker = false;		//Ϊ�����˳���ʾ���߳�ѭ����������˸
			screenBrightness(mDefaultScreenBrightness / 255f);		// ����ת�������Ļ����
			
			if (mBulbCrossFadeFlag) {
				mDrawable.reverseTransition(0);	
			}
			mBulbCrossFadeFlag = false;			// Ĭ�ϵ���Ϊ�ر�״̬
			mPoliceState = false;
			
			mSharedPreferences.edit()
					.putInt("warning_light_interval", mCurrentWarningLightInterval)
					.putInt("police_light_interval", mCurrentPoliceLightInterval)
					.commit();
			
		} else {	// ����������棬������ж�
			switch (mLastUIType) {
			case UI_TYPE_FLASHLIGHT:		// ����ƽ���
				mUIFlashlight.setVisibility(View.VISIBLE);
				screenBrightness(1f);
				mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
				break;
			case UI_TYPE_WARNINGLIGHT:		// ��ʾ�ƽ���
				mUIWarningLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
				new WarningLightThread().start();
				break;
			case UI_TYPE_MORSE:				// Ī��˹����
				mUIMorse.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_MORSE;
				break;
			case UI_TYPE_BLUB:				// ���ݽ���
				mUIBulb.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_BLUB;
				break;
			case UI_TYPE_COLOR:				// ��ɫ����
				mUIColorLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_COLOR;
				break;
			case UI_TYPE_POLICE:			// ���ƽ���
				mUIPoliceLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_POLICE;
				new PoliceThread().start();
				break;
				
			case UI_TYPE_SETTINGS:			// ���ý���
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
		screenBrightness(1f);		// ������Ļ���ȣ�1������0�
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
		
		// ���õ�ǰ��ɫ�Ļ���ɫ
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
