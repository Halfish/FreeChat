package com.example.freechat.ui;


/**
 * Created by zhangwei on 15-4-17.
 */
public class FCMessage {
    public static final int SEND_MESSAGE = 0;
    public static final int RECEIVE_MESSAGE = 1;
    
    public static final int TYPE_TXT = 2;
    public static final int TYPE_PIC = 4;
    public static final int TYPE_AUD = 6;

    private String m_content;
    private long m_timeStamp;
    private int  m_messageAttr;  //属性: 是接收或是发出的信息
    private int  m_messageType;  //类型: 是文本\图片\语音
    

    public FCMessage(String content, int attr, int type, long timeStamp) {
        m_content = content;
        m_messageAttr = attr;
        m_messageType = type;
        m_timeStamp = timeStamp;
    }
    
    public FCMessage(String content, int attr, int type) {
        m_content = content;
        m_messageAttr = attr;
        m_messageType = type;
        m_timeStamp = System.currentTimeMillis();
    }

    public FCMessage(String content, int attr) {
        m_content = content;
        m_messageAttr = attr;
        m_messageType = TYPE_TXT;
        m_timeStamp = System.currentTimeMillis();
    }

    public String setToListView() {
        String content = null;
        switch (m_messageType) {
        	case TYPE_TXT:
        		content = m_content;
        		break;
        	case TYPE_PIC:
        		content = "send a picture, click to check";
        		break;
        	case TYPE_AUD:
        		content = "send a audio, click to check";
        		break;
        }
        return content;
    }
    
    public String getContent() {
    	return m_content;
    }

    public long getTimeStamp() {
        return m_timeStamp;
    }

    public int getMessageType() {
    	return m_messageType;
    }
    
    public int getMessageAttr() {
        return m_messageAttr;
    }
}
