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
		
		getWindowManager().getDefaultDisplay().getSize(point);	// ��ȡ���豸��Ļ�ֱ���, ������Ϊ��λ
		
		LayoutParams laParams = mImageViewFlashlightController.getLayoutParams();
		laParams.width = point.x / 3;		// �ȵ���Ϊ��ǰ�豸�ֱ��ʿ��������֮һ
		laParams.height = point.y * 3/4;	// �ȵ�߶�Ϊ��ǰ�豸�ֱ��ʸ߶ȵ��ķ�֮��
		
		mImageViewFlashlightController.setLayoutParams(laParams);
	}

	public void onClick_Flashlight(View view) {
		
//		// ��⵱ǰ�豸�Ƿ�֧�������
//		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
//			Toast.makeText(this, "��ǰ�豸��֧�����صƲ�����", Toast.LENGTH_SHORT).show();
//		}
		
		if ( ((Boolean)mImageViewFlashlight.getTag()) == false ) {
			openFlashlight();
		} else {
			closeFlashlight();
		}
	}
	
	// �������
	protected void openFlashlight() {
		
		TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight.getDrawable();
		drawable.startTransition(100);
		mImageViewFlashlight.setTag(true);
		
		try {
			mCamera = Camera.open();
			int textureId = 0;
			mCamera.setPreviewTexture(new SurfaceTexture(textureId));	// ��������
			mCamera.startPreview();
			
			mParameters = mCamera.getParameters();
			
			mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(mParameters);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// �ر������
	protected void closeFlashlight() {
		
		TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight.getDrawable();
		
		if ( ((Boolean)mImageViewFlashlight.getTag()) ) {
			drawable.reverseTransition(100);
			mImageViewFlashlight.setTag(false);
			if (mCamera != null) {		// ���camera���󣬲�Ȼ��Ӱ�������豸������ʹ��
				mParameters = mCamera.getParameters();
				mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(mParameters);
				mCamera.stopPreview();
				
				mCamera.release();	// �ͷ��������
				mCamera = null;		// ȫ�ֱ���ʹ�����ֶ��ͷ�
			}
		}
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		closeFlashlight();	// �˳�����ر������
	}
	
}
