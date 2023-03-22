package com.mw.fantasy.utility.socketUtil;

import android.util.Log;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketUtility {

    public static final String SOCKET_LOGS="SOCKET_LOGS";
    private Socket mSocket;
    private MyEmitterListener emitterListener_EVENT_CONNECTING= new MyEmitterListener("EVENT_CONNECTING");
    private MyEmitterListener emitterListener_EVENT_CONNECT= new MyEmitterListener("EVENT_CONNECT");
    private MyEmitterListener emitterListener_EVENT_CONNECT_ERROR= new MyEmitterListener("EVENT_CONNECT_ERROR");
    private MyEmitterListener emitterListener_EVENT_DISCONNECT= new MyEmitterListener("EVENT_DISCONNECT");
    private MyEmitterListener emitterListener_EVENT_ERROR= new MyEmitterListener("EVENT_ERROR");
    private MyEmitterListener emitterListener_EVENT_CONNECT_TIMEOUT= new MyEmitterListener("EVENT_CONNECT_TIMEOUT");
    private MyEmitterListener emitterListener_EVENT_RECONNECT_ATTEMPT= new MyEmitterListener("EVENT_RECONNECT_ATTEMPT");
    private MyEmitterListener emitterListener_EVENT_RECONNECTING= new MyEmitterListener("EVENT_RECONNECTING");
    private MyEmitterListener emitterListener_EVENT_RECONNECT= new MyEmitterListener("EVENT_RECONNECT");
    private MyEmitterListener emitterListener_EVENT_RECONNECT_ERROR= new MyEmitterListener("EVENT_RECONNECT_ERROR");
    private MyEmitterListener emitterListener_EVENT_RECONNECT_FAILED= new MyEmitterListener("EVENT_RECONNECT_FAILED");

    public SocketUtility(Socket mSocket) {
        this.mSocket = mSocket;
    }

    public void onDefaultEvent(){
        if (mSocket==null) return;
        mSocket.on(Socket.EVENT_CONNECTING, emitterListener_EVENT_CONNECTING);
        mSocket.on(Socket.EVENT_CONNECT, emitterListener_EVENT_CONNECT);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, emitterListener_EVENT_CONNECT_ERROR);
        mSocket.on(Socket.EVENT_DISCONNECT, emitterListener_EVENT_DISCONNECT);
        mSocket.on(Socket.EVENT_ERROR, emitterListener_EVENT_ERROR);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, emitterListener_EVENT_CONNECT_TIMEOUT);
        mSocket.on(Socket.EVENT_RECONNECT_ATTEMPT, emitterListener_EVENT_RECONNECT_ATTEMPT);
        mSocket.on(Socket.EVENT_RECONNECTING, emitterListener_EVENT_RECONNECTING);
        mSocket.on(Socket.EVENT_RECONNECT, emitterListener_EVENT_RECONNECT);
        mSocket.on(Socket.EVENT_RECONNECT_ERROR, emitterListener_EVENT_RECONNECT_ERROR);
        mSocket.on(Socket.EVENT_RECONNECT_FAILED, emitterListener_EVENT_RECONNECT_FAILED);
    }

    public void offDefaultEvent(){
        if (mSocket==null) return;
        mSocket.off(Socket.EVENT_CONNECTING, emitterListener_EVENT_CONNECTING);
        mSocket.off(Socket.EVENT_CONNECT, emitterListener_EVENT_CONNECT);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, emitterListener_EVENT_CONNECT_ERROR);
        mSocket.off(Socket.EVENT_DISCONNECT, emitterListener_EVENT_DISCONNECT);
        mSocket.off(Socket.EVENT_ERROR, emitterListener_EVENT_ERROR);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, emitterListener_EVENT_CONNECT_TIMEOUT);
        mSocket.off(Socket.EVENT_RECONNECT_ATTEMPT, emitterListener_EVENT_RECONNECT_ATTEMPT);
        mSocket.off(Socket.EVENT_RECONNECTING, emitterListener_EVENT_RECONNECTING);
        mSocket.off(Socket.EVENT_RECONNECT, emitterListener_EVENT_RECONNECT);
        mSocket.off(Socket.EVENT_RECONNECT_ERROR, emitterListener_EVENT_RECONNECT_ERROR);
        mSocket.off(Socket.EVENT_RECONNECT_FAILED, emitterListener_EVENT_RECONNECT_FAILED);
    }

    public class MyEmitterListener implements Emitter.Listener{
        private String name;
        public MyEmitterListener(String name) {
            this.name = name;
        }

        @Override
        public void call(Object... args) {
            Log.d(SOCKET_LOGS, "============================================================");
            Log.d(SOCKET_LOGS, "->"+name);
            for (int i = 0; i < args.length; i++) {
                Log.d(SOCKET_LOGS, "->"+name+"->args["+i+"] = " + args[i].toString());
            }

        }
    }
}
