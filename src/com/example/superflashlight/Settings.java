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
	
	private boolean isInstallShortcut = false;	// 标志快捷方式是否被创建,默认为否
	
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
	
	// 判断快捷方式是否存在桌面
	public boolean shortcutInScreen() {
		
		final ContentResolver cr = getContentResolver();
		final Uri CONTENT_URI = Uri.parse("content://com.android.launcher.settings/favorites ? notify = true");// 保持默认
		Cursor c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?", new String[] { "超级手电筒" }, null);
		
		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}
		
		return isInstallShortcut;
	}
	
	// 添加快捷方式,	广播方式
	public void onClick_AddShortcut(View view) {
		
		// 判断快捷方式是否存在,不存在则创建，程序卸载的时候快捷方式会自动从桌面删除
		if (!isInstallShortcut) {
		
			// 创建添加快捷方式的Intent
			Intent installShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			// 快捷方式的名称
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
			// 不允许重复创建
			installShortcut.putExtra("duplicate", false);
			// 快捷方式的图片，一个Parcelable对象
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		
//			Intent flashlightIntent = new Intent();
//			flashlightIntent.setClassName("com.example.superflashlight", "com.example.superflashlight.MainActivity");
//			
//			flashlightIntent.setAction(Intent.ACTION_MAIN);
//			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			
//			// 点击快捷图片，运行的程序主入口
//			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
			
			ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
			
			// 发送广播
			sendBroadcast(installShortcut);
			
			Toast.makeText(this, "Create a shortcut to success!", Toast.LENGTH_SHORT).show();
			
		} else {
			Toast.makeText(this, "Shortcut already exists!", Toast.LENGTH_SHORT).show();
		}
	}
	
	// 移除快捷方式，	广播方式
	public void onClick_RemoveShortcut(View view) {
		
		// 判断快捷方式是否存在，存在则移除
		if (isInstallShortcut) {
		
			// 创建移除快捷方式的Intent
			Intent uninstallShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
			// 快捷方式的名称
			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
		
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
