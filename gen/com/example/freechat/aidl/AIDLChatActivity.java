/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\gitwork\\freechat\\FreeChat\\src\\com\\example\\freechat\\aidl\\AIDLChatActivity.aidl
 */
package com.example.freechat.aidl;
public interface AIDLChatActivity extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.freechat.aidl.AIDLChatActivity
{
private static final java.lang.String DESCRIPTOR = "com.example.freechat.aidl.AIDLChatActivity";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.freechat.aidl.AIDLChatActivity interface,
 * generating a proxy if needed.
 */
public static com.example.freechat.aidl.AIDLChatActivity asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.freechat.aidl.AIDLChatActivity))) {
return ((com.example.freechat.aidl.AIDLChatActivity)iin);
}
return new com.example.freechat.aidl.AIDLChatActivity.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onMessageSendFinished:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.onMessageSendFinished(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onNewMessageReceived:
{
data.enforceInterface(DESCRIPTOR);
char _arg0;
_arg0 = (char)data.readInt();
byte[] _arg1;
_arg1 = data.createByteArray();
this.onNewMessageReceived(_arg0, _arg1);
reply.writeNoException();
reply.writeByteArray(_arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.freechat.aidl.AIDLChatActivity
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void onMessageSendFinished(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onMessageSendFinished, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNewMessageReceived(char type, byte[] message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((int)type));
_data.writeByteArray(message);
mRemote.transact(Stub.TRANSACTION_onNewMessageReceived, _data, _reply, 0);
_reply.readException();
_reply.readByteArray(message);
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onMessageSendFinished = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onNewMessageReceived = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void onMessageSendFinished(boolean flag) throws android.os.RemoteException;
public void onNewMessageReceived(char type, byte[] message) throws android.os.RemoteException;
}
