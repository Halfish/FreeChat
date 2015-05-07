package com.example.freechat;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.freechat.ui.FCMessage;

public class FCMessageUtil {

	// send message to server
	public static JSONArray messageToJson(FCMessage message, String toUser) {

		String content = message.getContent();
		int type = message.getMessageType();

		JSONArray jsonArray = new JSONArray();
		switch (type) {
		case FCMessage.TYPE_TXT:
			jsonArray.put("send_message");
			jsonArray.put(FCConfigure.myName);
			jsonArray.put(toUser);
			jsonArray.put(content);
			break;

		case FCMessage.TYPE_PIC:
			break;

		case FCMessage.TYPE_AUD:
			break;

		default:
			break;
		}

		return jsonArray;
	}

	// get data from server
	public static FCMessage jsonArrayToMessage(JSONArray jsonArray) {
		FCMessage message = null;

		try {
			String content = jsonArray.getString(2);
			message = new FCMessage(content, FCMessage.RECEIVE_MESSAGE);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}
}
