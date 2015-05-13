package com.example.freechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.RemoteException;
import android.util.Log;

import com.example.freechat.aidl.AIDLChatActivity;

public class FCLocalClientSocket {
	private static final String LOG_TAG = FCLocalClientSocket.class.getName()
			.toString();
	private AIDLChatActivity mCallback;
	private Socket mClient;
	private InputStream mInputStream;
	private OutputStream mOutputStream;

	public FCLocalClientSocket() {
		initSocket();
	}

	public FCLocalClientSocket(AIDLChatActivity callback) {
		mCallback = callback;
		initSocket();
	}

	public void setCallBack(AIDLChatActivity callback) {
		Log.v(LOG_TAG, "set callback");
		mCallback = callback;
	}

	private void initSocket() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.v(LOG_TAG, "socket init");

					mClient = new Socket(FCConfigure.SERVER_TCP_ADDR,
							FCConfigure.SERVER_TCP_PORT);
					mInputStream = mClient.getInputStream();
					mOutputStream = mClient.getOutputStream();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (mClient != null) {
					startReceiveData();
				}
			}
		}).start();
	}

	private void startReceiveData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.v(LOG_TAG, "start receive data");

				doStartReceive();
			}
		}).start();
	}

	private void doStartReceive() {
		byte[] buffer = new byte[5];
		int length = 0;
		try {
			while (!mClient.isClosed() && !mClient.isInputShutdown()) {

				// read 5 bytes first
				length = mInputStream.read(buffer, 0, 5);
				if (length != -1) {
					Log.v(LOG_TAG, "doStartReceive head: " + length + "---"
							+ buffer[0]);
				}

				if (length == 5) {
					char type = (char) buffer[0];
					Log.v(LOG_TAG, "type is: " + type + " buffer[0] is " + buffer[0]);
					
					int dataLength = FCMessageUtil.byteToInt(buffer, 1);
					
					if(type == 'b' || type == 'c') {
						byte [] from = new byte[100];
						mInputStream.read(from, 0, 100);
						Log.v(LOG_TAG, "doStartReceive from");
					}					

					Log.v(LOG_TAG, "doStartReceive data length: " + dataLength);
					
					buffer = new byte[dataLength];
					int len = 0;
					int currentLen = 0;
					while (currentLen < dataLength) {
						len = mInputStream.read(buffer, currentLen, dataLength - currentLen);
						currentLen += len;
						Log.v(LOG_TAG, "len is " + len);
						Log.v(LOG_TAG, "current len is " + currentLen);
					}
					Log.v(LOG_TAG, "out of while and current len is " + currentLen);
					
					if (currentLen > 0 && mCallback != null) {
						mCallback.onNewMessageReceived(type, buffer);
					}
					buffer = new byte[5];
				}

				Thread.sleep(200);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public boolean sendDataToServer(char type, byte[] msg) {

		if (mClient == null) {
			Log.e(LOG_TAG, "client socket is null");
			return false;
		}

		Log.v(LOG_TAG, "start sending data " + "type is " + type);
		Log.v(LOG_TAG, "start sending data " + "length is " + msg.length);

		byte[] totalBuffer = new byte[msg.length + 5];

		totalBuffer[0] = (byte) type;
		byte[] lenBuffer = FCMessageUtil.intToByte(msg.length);

		// copy lenBuffer to totalBuffer
		System.arraycopy(lenBuffer, 0, totalBuffer, 1, lenBuffer.length);

		// copy lenBuffer to totalBuffer
		System.arraycopy(msg, 0, totalBuffer, 5, msg.length);

		try {
			mOutputStream.write(totalBuffer);
			mOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public boolean sendFileToServer(char type, String fromName, String toName, byte[] msg) {
		if (mClient == null) {
			Log.e(LOG_TAG, "client socket is null");
			return false;
		}

		Log.v(LOG_TAG, "send file " + "type is " + type);
		Log.v(LOG_TAG, "send file " + "length is " + msg.length);

		byte[] totalBuffer = new byte[5 + 100 + 100 + msg.length];

		totalBuffer[0] = (byte) type;
		byte[] lenBuffer = FCMessageUtil.intToByte(msg.length);

		// copy lenBuffer to totalBuffer
		System.arraycopy(lenBuffer, 0, totalBuffer, 1, lenBuffer.length);

		// copy fromName and toName to totalBuffer		
		System.arraycopy(fromName.getBytes(), 0, totalBuffer, 5, fromName.getBytes().length);
		System.arraycopy(toName.getBytes(), 0, totalBuffer, 105, toName.getBytes().length);
		
		// copy lenBuffer to totalBuffer
		System.arraycopy(msg, 0, totalBuffer, 205, msg.length);

		try {
			mOutputStream.write(totalBuffer);
			mOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public void stop() {
		Log.e(LOG_TAG, "socket stop!");
		if (mClient != null) {
			try {
				mClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mClient = null;
		}
	}
}
