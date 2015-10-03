package com.example.superflashlight;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class Settings extends PoliceLight implements OnSeekBarChangeListener {
	
	private boolean isInstallShortcut = false;	// ��־��ݷ�ʽ�Ƿ񱻴���,Ĭ��Ϊ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSeekBarPoliceLight.setOnSeekBarChangeListener(this);
		mSeekBarWarningLight.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.seekbar_warning_light:
			mCurrentWarningLightInterval = progress + 100;
			break;
		case R.id.seekbar_police_light:
			mCurrentPoliceLightInterval = progress + 50;
			break;
		default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	// �жϿ�ݷ�ʽ�Ƿ��������
	public boolean shortcutInScreen() {
		
		final ContentResolver cr = getContentResolver();
		final Uri CONTENT_URI = Uri.parse("content://com.android.launcher.settings/favorites ? notify = true");// ����Ĭ��
		Cursor c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?", new String[] { "�����ֵ�Ͳ" }, null);
		
		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}
		
		return isInstallShortcut;
	}
	
	// ��ӿ�ݷ�ʽ,	�㲥��ʽ
	public void onClick_AddShortcut(View view) {
		
		// �жϿ�ݷ�ʽ�Ƿ����,�������򴴽�������ж�ص�ʱ���ݷ�ʽ���Զ�������ɾ��
		if (!isInstallShortcut) {
		
			// ������ӿ�ݷ�ʽ��Intent
			Intent installShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			// ��ݷ�ʽ������
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "�����ֵ�Ͳ");
			// �������ظ�����
			installShortcut.putExtra("duplicate", false);
			// ��ݷ�ʽ��ͼƬ��һ��Parcelable����
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		
//			Intent flashlightIntent = new Intent();
//			flashlightIntent.setClassName("com.example.superflashlight", "com.example.superflashlight.MainActivity");
//			
//			flashlightIntent.setAction(Intent.ACTION_MAIN);
//			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			
//			// ������ͼƬ�����еĳ��������
//			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
			
			ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
			
			// ���͹㲥
			sendBroadcast(installShortcut);
			
			Toast.makeText(this, "Create a shortcut to success!", Toast.LENGTH_SHORT).show();
			
		} else {
			Toast.makeText(this, "Shortcut already exists!", Toast.LENGTH_SHORT).show();
		}
	}
	
	// �Ƴ���ݷ�ʽ��	�㲥��ʽ
	public void onClick_RemoveShortcut(View view) {
		
		// �жϿ�ݷ�ʽ�Ƿ���ڣ��������Ƴ�
		if (isInstallShortcut) {
		
			// �����Ƴ���ݷ�ʽ��Intent
			Intent uninstallShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
			// ��ݷ�ʽ������
			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "�����ֵ�Ͳ");
		
//			Intent flashlightIntent = new Intent();
//			flashlightIntent.setClassName("com.example.superflashlight", "com.example.superflashlight.MainActivity");
//			flashlightIntent.setAction(Intent.ACTION_MAIN);
//			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
			
			String appClass = this.getPackageName() + "." + this.getLocalClassName();
			ComponentName comp = new ComponentName(this.getPackageName(), appClass);
			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
		
			sendBroadcast(uninstallShortcut);
		} else {
			Toast.makeText(this, "Shortcuts do not exist!", Toast.LENGTH_SHORT).show();
		}
	}

}
