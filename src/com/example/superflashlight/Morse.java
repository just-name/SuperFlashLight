package com.example.superflashlight;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Morse extends WarningLight {

	private final int DOT_TIME = 200;				// ��ͣ����ʱ��	��λ������
	private final int LINE_TIME = DOT_TIME * 3;		// ��ͣ����ʱ��
	private final int DOT_LINE_TIME = DOT_TIME;		// �㵽�ߵ�ʱ����
	
	private final int CHAR_CHAR_TIME = DOT_TIME * 3;// �ַ����ַ���ʱ����
	private final int WORD_WORD_TIME = DOT_TIME * 7;// ���ʵ����ʵ�ʱ����
	
	private String mMorseCode;						// �洢�����Ī��˹����
	
	private Map<Character, String> mMorseCodeMap = new HashMap<Character, String>();// �洢����ӳ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ���
		mMorseCodeMap.put('a', ".-");
		mMorseCodeMap.put('b', "-...");
		mMorseCodeMap.put('c', "-.-.");
		mMorseCodeMap.put('d', "-..");
		mMorseCodeMap.put('e', ".");
		mMorseCodeMap.put('f', "..-.");
		mMorseCodeMap.put('g', "--.");
		mMorseCodeMap.put('h', "....");
		mMorseCodeMap.put('i', "..");
		mMorseCodeMap.put('j', ".---");
		mMorseCodeMap.put('k', "-.-");
		mMorseCodeMap.put('l', ".-..");
		mMorseCodeMap.put('m', "--");
		mMorseCodeMap.put('n', "-.");
		mMorseCodeMap.put('o', "---");
		mMorseCodeMap.put('p', ".--.");
		mMorseCodeMap.put('q', "--.-");
		mMorseCodeMap.put('r', ".-.");
		mMorseCodeMap.put('s', "...");
		mMorseCodeMap.put('t', "-");
		mMorseCodeMap.put('u', "..-");
		mMorseCodeMap.put('v', "...-");
		mMorseCodeMap.put('w', ".--");
		mMorseCodeMap.put('x', "-..-");
		mMorseCodeMap.put('y', "-.--");
		mMorseCodeMap.put('z', "--..");

		mMorseCodeMap.put('0', "-----");
		mMorseCodeMap.put('1', ".----");
		mMorseCodeMap.put('2', "..---");
		mMorseCodeMap.put('3', "...--");
		mMorseCodeMap.put('4', "....-");
		mMorseCodeMap.put('5', ".....");
		mMorseCodeMap.put('6', "-....");
		mMorseCodeMap.put('7', "--...");
		mMorseCodeMap.put('8', "---..");
		mMorseCodeMap.put('9', "----.");

	}
	
	public void onClick_SendMorseCode(View view) {
		
//		// ��⵱ǰ�豸�Ƿ�֧�������
//		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
//			Toast.makeText(this, "��ǰ�豸��֧�����صƲ�����", Toast.LENGTH_SHORT).show();
//		}
		
		if (verifyMorseCode()) {
			sendSentense(mMorseCode);
		}
	}
	
	
	// ���͵�
	private void sendDot() {
		openFlashlight();
		sleepExt(DOT_TIME);
		closeFlashlight();
	}
	
	// ������
	private void sendLine() {
		openFlashlight();
		sleepExt(LINE_TIME);
		closeFlashlight();
	}
	
	// �����ַ�
	private void sendChar(char c) {
		
		String morseCode = mMorseCodeMap.get(c);
		
		if (morseCode != null) {
			char lastChar = ' ';
			
			for (int i = 0; i < morseCode.length(); i++) {
				char dotLine = morseCode.charAt(i);
				
				if (dotLine == '.'){
					sendDot();
				} else if (dotLine == '-') {
					sendLine();
				}
				
				if (i > 0 && i < morseCode.length() - 1) {
					if (lastChar == '.' && dotLine == '-') {	// �㵽�ߵļ��
						sleepExt(DOT_LINE_TIME);
					}
				}
				
				lastChar = dotLine;
			}
		}
	}
	
	// ���͵���
	private void sendWord(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			sendChar(c);
			if (i < s.length() - 1) {	// �ַ����ַ��ļ��
				sleepExt(CHAR_CHAR_TIME);
			}
		}
	}
	
	// ���;���
	private void sendSentense(String s) {
		String[] words = s.split(" +");		// �ո񴦷���
		for (int i = 0; i < words.length; i++) {
			sendWord(words[i]);
			if (i < words.length - 1) {
				sleepExt(WORD_WORD_TIME);		// ���ʵ����ʵļ��
			}
		}
		Toast.makeText(this, "The Morse code has been sent!", Toast.LENGTH_SHORT).show();
	}
	
	// ��֤������ַ�������������ĸ�����ֺͿո�
	private boolean verifyMorseCode() {
		// ��������ַ���ת����Сд
		mMorseCode = mEditTextMorseCode.getText().toString().toLowerCase();
		
		if ("".equals(mMorseCode)) {
			Toast.makeText(this, "Please enter the Morse code string", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		for (int i = 0; i < mMorseCode.length(); i++) {
			char c = mMorseCode.charAt(i);
			if (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c!= ' ') {
				Toast.makeText(this, "The Morse code is only letters and numbers!", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		
		return true;
	}
	
}
