package com.andy.recustomviews.input;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class MemuIME extends InputMethodService {
	private static MemuIME mIme;
	private static boolean mStarted = false;
	private static Socket socket;
	private static final String EDIT_STATUS_START = "##PREEDITSTART##";
	private static final String EDIT_STATUS_END = "##PREEDITEND##";
	private static final String COMMIT_STATUS_START = "##COMMITSTART##";
	private static final String COMMIT_STATUS_END = "##COMMITEND##";
	private static final String CURSOR_INDEX_START = "##PREEDITCURSORSTART##";
	private static final String CURSOR_INDEX_END = "##PREEDITCURSOREND##";

	public boolean onEvaluateFullscreenMode() {
		return false;
	}

	public boolean onEvaluateInputViewShown() {
		return false;
	}

	public void onFinishInput() {
		mIme = null;
		super.onFinishInput();
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
		return super.onKeyUp(paramInt, paramKeyEvent);
	}

	public void onStartInput(EditorInfo paramEditorInfo, boolean paramBoolean) {
		super.onStartInput(paramEditorInfo, paramBoolean);
		mIme = this;
		if (!mStarted)
			new Thread(new ConnectionThread()).start();
	}

	protected boolean connect() {
		boolean rc = true;
                int server_port = 21501;
		try {
			socket = new Socket("127.0.0.1", server_port);
		} catch (UnknownHostException e) {
			rc = false;
			e.printStackTrace();
		} catch (IOException e) {
			rc = false;
			e.printStackTrace();
		}
		return rc;
	}

	protected void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ConnectionThread implements Runnable {
		
		public void run() {
			byte[] arrayOfByte = new byte[32768];
			while (true) {
				mStarted = true;
				if (MemuIME.this.connect()) {
					try {
						while (true) {
							int i = MemuIME.socket.getInputStream().read(arrayOfByte);
							if (i <= 0)
								break;
							String str = new String(arrayOfByte, 0, i);
							System.out.println("");
							if (MemuIME.mIme != null) {
								InputConnection localInputConnection = MemuIME.mIme
										.getCurrentInputConnection();

								if (localInputConnection instanceof BaseInputConnection){
									Log.e("MEmuIME", "I am BaseInputConnection");
								}

								if (localInputConnection != null){
									do{
										Log.e("MEmuIME", "origin str = " + str );
										
										if(str.length() == 0){
											break;
										}
										if(str.startsWith(EDIT_STATUS_START)){
											int index = str.indexOf(EDIT_STATUS_END);
											String tmp = str.substring(0, index+EDIT_STATUS_END.length());
											str = str.substring(tmp.length());
											Log.e("MEmuIME", "edit str = " + tmp +", \n left = "+str);
											tmp = tmp.substring(EDIT_STATUS_START.length(), tmp.length()-EDIT_STATUS_END.length());
											localInputConnection.performContextMenuAction(android.R.id.selectAll);
											if (localInputConnection.performContextMenuAction(android.R.id.selectAll)){
												String all = localInputConnection.getSelectedText(0).toString();
												Log.e("Andy", "select all = "+all);

											}
											localInputConnection.setComposingText(tmp, 1);
											localInputConnection.setComposingRegion(2, 4);
											continue;
										}else if(str.startsWith(COMMIT_STATUS_START)){
											int index = str.indexOf(COMMIT_STATUS_END);
											String tmp = str.substring(0, index+COMMIT_STATUS_END.length());
											str = str.substring(tmp.length());
											Log.e("MEmuIME", "commit str = " + tmp +", \n left = "+str);
											tmp = tmp.substring(COMMIT_STATUS_START.length(), tmp.length()-COMMIT_STATUS_END.length());
											localInputConnection.commitText(tmp, 1);
											continue;
										}else if(str.startsWith(CURSOR_INDEX_START)){
											int index = str.indexOf(CURSOR_INDEX_END);
											String tmp = str.substring(0, index+CURSOR_INDEX_END.length());
											str = str.substring(tmp.length());
											Log.e("MEmuIME", "edit index  = " + tmp+", \n left = "+str);
											tmp = tmp.substring(CURSOR_INDEX_START.length(), tmp.length()-CURSOR_INDEX_END.length());
											int cursor = Integer.parseInt(tmp);
											localInputConnection.setSelection(cursor, cursor);
											continue;
										}else {

										}


										localInputConnection.performContextMenuAction(android.R.id.selectAll);
										CharSequence charSequence1 = localInputConnection.getTextBeforeCursor(1000, 0);
										CharSequence charSequence2 = localInputConnection.getTextAfterCursor(1000, 0);
										Log.e("MEmuIME_1", "char1 = "+charSequence1);
										Log.e("MEmuIME_1", "char2 = "+charSequence2);

//										Log.e("MEmuIME", "direct str = " + str );
										localInputConnection.commitText(str, 1);
										break;
										
									}while(true);
									
								}
							}
						}
					} catch (IOException localIOException) {
						Log.d("MemuIME", "read IOException " + localIOException.getMessage());
					}
				}
			}
		}
	}
}
