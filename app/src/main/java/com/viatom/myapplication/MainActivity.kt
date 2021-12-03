package com.viatom.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.net.URI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread{
            val uri: URI = URI.create("ws://192.168.5.101:8080/user")
            val client: JWebSocketClient = object : JWebSocketClient(uri) {
                override fun onMessage(message: String) {
                    //message就是接收到的消息
                    Log.e("JWebSClientService", message)
                }
            }
            try {
                client.connectBlocking()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            if (client != null && client.isOpen()) {
                client.send("你好");
            }
        }.start()
    }
}