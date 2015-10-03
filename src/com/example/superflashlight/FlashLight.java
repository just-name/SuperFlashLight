package com.example.superflashlight;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class FlashLight extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mImageViewFlashlight.setTag(false);
		
		Point point = new Point();
		
		getWindowManager().getDefaultDisplay().getSize(point);	// 获取到设备屏幕分辨率, 以像素为单位
		
		LayoutParams laParams = mImageViewFlashlightController.getLayoutParams();
		laParams.width = point.x / 3;		// 热点宽带为当前设备分辨率宽带的三分之一
		laParams.height = point.y * 3/4;	// 热点高度为当前设备分辨率高度的四分之三
		
		mImageViewFlashlightController.setLayoutParams(laParams);
	}

	public void onClick_Flashlight(View view) {
		
//		// 检测当前设备是否支持闪光灯
//		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
//			Toast.makeText(this, "当前设备不支持闪关灯操作！", Toast.LENGTH_SHORT).show();
//		}
		
		if ( ((Boolean)mImageViewFlashlight.getTag()) == false ) {
			openFlashlight();
		} else {
			closeFlashlight();
		}
	}
	
	// 打开闪光灯
	protected void openFlashlight() {
		
		TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight.getDrawable();
		drawable.startTransition(100);
		mImageViewFlashlight.setTag(true);
		
		try {
			mCamera = Camera.open();
			int textureId = 0;
			mCamera.setPreviewTexture(new SurfaceTexture(textureId));	// 设置纹理
			mCamera.startPreview();
			
			mParameters = mCamera.getParameters();
			
			mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(mParameters);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 关闭闪光灯
	protected void closeFlashlight() {
		
		TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight.getDrawable();
		
		if ( ((Boolean)mImageViewFlashlight.getTag()) ) {
			drawable.reverseTransition(100);
			mImageViewFlashlight.setTag(false);
			if (mCamera != null) {		// 清除camera对象，不然会影响其他设备的正常使用
				mParameters = mCamera.getParameters();
				mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(mParameters);
				mCamera.stopPreview();
				
				mCamera.release();	// 释放相机对象
				mCamera = null;		// 全局变量使用完手动释放
			}
		}
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		closeFlashlight();	// 退出程序关闭闪光灯
	}
	
}
