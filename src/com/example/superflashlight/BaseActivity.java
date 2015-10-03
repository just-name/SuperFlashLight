package com.example.superflashlight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class BaseActivity extends Activity {
	
	protected enum UIType {
		UI_TYPE_MAIN, 
		UI_TYPE_FLASHLIGHT, 
		UI_TYPE_WARNINGLIGHT, 
		UI_TYPE_MORSE, 
		UI_TYPE_BLUB, 
		UI_TYPE_COLOR, 
		UI_TYPE_POLICE, 
		UI_TYPE_SETTINGS
	}
	
	// 子类可使用
	
	protected int mCurrentWarningLightInterval = 500;
	protected int mCurrentPoliceLightInterval = 100;
	
	protected ImageView mImageViewFlashlight;
	protected ImageView mImageViewFlashlightController;
	protected ImageView mImageViewWarningLight1;
	protected ImageView mImageViewWarningLight2;
	protected ImageView mImageViewBulb;
	
	protected EditText mEditTextMorseCode;
	
	protected Camera mCamera;
	protected Parameters mParameters;
	
	protected FrameLayout mUIFlashlight;	// 对应ui_flashlight.xml
	protected LinearLayout mUIMain;			// 对应ui_main.xml
	protected LinearLayout mUIWarningLight; // 对应ui_warning_light.xml
	protected LinearLayout mUIMorse;		// 对应ui_morse.xml
	protected FrameLayout mUIBulb;			// 对应ui_Bulb.xml
	protected FrameLayout mUIColorLight;	// 对应ui_colorlight.xml
	protected FrameLayout mUIPoliceLight;	// 对应ui_policelight.xml
	protected LinearLayout mUISettings;		// 对应ui_settings.xml
	
	protected UIType mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;	// 标志当前所属界面,默认为手电筒界面
	protected UIType mLastUIType = UIType.UI_TYPE_FLASHLIGHT;		// 标志最后所在界面，默认为手电筒界面
	
	protected int mDefaultScreenBrightness;
	
	protected int mFinishCount = 0; 	// 返回次数
	
	protected SharedPreferences mSharedPreferences;
	
	
	protected SeekBar mSeekBarWarningLight;
	protected SeekBar mSeekBarPoliceLight;
	protected Button mButtonAddShortcut;
	protected Button mButtonRemoveShortcut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mImageViewFlashlight = (ImageView) findViewById(R.id.imageview_flashlight);
		mImageViewFlashlightController = (ImageView) findViewById(R.id.imageview_flashlight_controller);
		mImageViewWarningLight1 = (ImageView) findViewById(R.id.imageview_warning_light1);
		mImageViewWarningLight2 = (ImageView) findViewById(R.id.imageview_warning_light2);
		mImageViewBulb = (ImageView) findViewById(R.id.imageview_bulb);
		
		mEditTextMorseCode = (EditText) findViewById(R.id.edittext_morse_code);
		
		mUIFlashlight = (FrameLayout) findViewById(R.id.framelayout_flashlight);
		mUIMain = (LinearLayout) findViewById(R.id.linearlayout_main);
		mUIWarningLight = (LinearLayout) findViewById(R.id.linearlayout_warning_light);
		mUIMorse = (LinearLayout) findViewById(R.id.linearlayout_morse);
		mUIBulb = (FrameLayout) findViewById(R.id.framelayout_bulb);
		mUIColorLight = (FrameLayout) findViewById(R.id.framelayout_color_light);
		mUIPoliceLight = (FrameLayout) findViewById(R.id.framelayout_police_light);
		mUISettings = (LinearLayout) findViewById(R.id.linearlayout_settings);
		
		mButtonAddShortcut = (Button) findViewById(R.id.button_add_shortcut);
		mButtonRemoveShortcut = (Button) findViewById(R.id.button_remove_shortcut);
		
		mSeekBarPoliceLight = (SeekBar) findViewById(R.id.seekbar_police_light);
		mSeekBarWarningLight = (SeekBar) findViewById(R.id.seekbar_warning_light);
		
		mDefaultScreenBrightness = getScreenBrightness();
		
		mSharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
		
		mSeekBarPoliceLight.setProgress(mCurrentPoliceLightInterval - 50);		// 频率最少为50ms闪烁一次
		mSeekBarWarningLight.setProgress(mCurrentWarningLightInterval - 100);	// 频率最少为100ms闪烁一次
		
		mCurrentWarningLightInterval = mSharedPreferences.getInt("warning_light_interval", 200);
		mCurrentPoliceLightInterval = mSharedPreferences.getInt("police_light_interval", 100);
	}
	
	// 隐藏视图
	protected void hideAllUI() {
		
		mUIMain.setVisibility(View.GONE); 			// 隐藏ui_main.xml中的布局内容
		mUIFlashlight.setVisibility(View.GONE);		// 隐藏ui_flashlight.xml中的布局内容
		mUIWarningLight.setVisibility(View.GONE);	// 隐藏ui_warninglight.xml中的布局内容
		mUIMorse.setVisibility(View.GONE);			// 隐藏ui_morse.xml中的布局内容
		mUIBulb.setVisibility(View.GONE);			// 隐藏ui_bulb.xml中的布局内容
		mUIColorLight.setVisibility(View.GONE);		// 隐藏ui_colorlight.xml中的布局内容
		mUIPoliceLight.setVisibility(View.GONE);	// 隐藏ui_policelight.xml中的布局内容
		mUISettings.setVisibility(View.GONE);		// 隐藏ui_settings.xml中的布局内容
	}
	
	// 延时
	protected void sleepExt(int t) {
		try {
			Thread.sleep(t);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
		
	
	// 屏幕亮度设置
	protected void screenBrightness(float value) {
		try {
			WindowManager.LayoutParams layout = getWindow().getAttributes();
			layout.screenBrightness = value;
			getWindow().setAttributes(layout);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 获取屏幕亮度	值为0~255
	protected int getScreenBrightness() {
		int value = 0;
		try {
			value = android.provider.Settings.System.getInt(
					getContentResolver(), 
					android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}
	
	@Override
	public void finish() {
		mFinishCount++;
		if (mFinishCount == 1) {
			Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
		} else if (mFinishCount == 2) {
			super.finish();
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mFinishCount = 0;	// 清零
		return super.dispatchTouchEvent(ev);
	}
	
	
	
	

}
