package com.example.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.superflashlight.ColorPickerDialog.OnColorChangedListener;
import com.example.superflashlight.widget.HideTextView;

public class ColorLight extends Bulb implements OnColorChangedListener {
	
	protected int mCurrentColorLight = Color.RED;
	protected HideTextView mHideTextViewColorLight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mHideTextViewColorLight = (HideTextView) findViewById(R.id.textview_hide_color_light);
	}
	
	public void onClick_ShowColorPicker(View view) {
		new ColorPickerDialog(this, this, Color.RED).show();
	}

	@Override
	public void colorChanged(int color) {
		mUIColorLight.setBackgroundColor(color);
		mCurrentColorLight = color;
	}

}
