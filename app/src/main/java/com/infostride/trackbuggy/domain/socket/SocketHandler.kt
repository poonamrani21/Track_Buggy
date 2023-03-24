package com.infostride.trackbuggy.domain.socket

import android.util.Log
import com.infostride.trackbuggy.utils.Constants
import com.infostride.trackbuggy.utils.kotlinFileName
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket(){
        try {
            mSocket = IO.socket("http://10.20.20.224:1234/")
        } catch (e: URISyntaxException) {
            Log.d(kotlinFileName, "setSocket connection fail: $e: ")
        }
    }
    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
        mSocket.on(Socket.EVENT_CONNECT){
            Log.d(kotlinFileName, "EVENT_CONNECT->${it}")

        }
        Log.d(kotlinFileName, "establishConnection:  connected ")
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
        mSocket.on(Socket.EVENT_DISCONNECT){
            Log.d(kotlinFileName, "EVENT_DISCONNECT->${it[0]}")

        }
        Log.d(kotlinFileName, "closeConnection:  connected ")

    }

    fun checkSocketErrorIfNotConnected(){
        mSocket.on(Socket.EVENT_CONNECT_ERROR){ it->
            Log.d(kotlinFileName, "EVENT_CONNECT_ERROR->${it[0]}")

        }
    }
}