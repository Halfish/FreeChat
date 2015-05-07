package com.example.freechat;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.freechat.aidl.AIDLChatActivity;
import com.example.freechat.aidl.AIDLPushService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class FCPushService extends Service {

	private static String Log_Tag = FCPushService.class.getName().toString();
	private AIDLChatActivity mCallback;
	private FCLocalClientSocket mClientSocket;

	public AIDLPushService.Stub mStub = new AIDLPushService.Stub() {
		@Override
		public boolean sendMessage(String message) throws RemoteException {
			boolean flag = false;
			if (mClientSocket == null) {
				return false;
			}
			try {
				flag = mClientSocket.sendMessageToServer(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mCallback.onMessageSendFinished(message);

			return flag;
		}

		@Override
		public void registerToPushService(AIDLChatActivity callback)
				throws RemoteException {
			mCallback = callback;
			mClientSocket.setCallBack(mCallback);
		}
	};

	public void onCreate() {
		mClientSocket = new FCLocalClientSocket();
		sendOnlineMessage();
	};

	private void sendOnlineMessage() {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray.put(0, "upload_name");
			jsonArray.put(1, "halfish");
			mClientSocket.sendMessageToServer(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		mClientSocket.stop();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(Log_Tag, "onBind called");
		return mStub;
	}

}
