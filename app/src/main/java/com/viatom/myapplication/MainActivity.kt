package com.viatom.myapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URI

class MainActivity : AppCompatActivity() {


    private val mHandler_beat = Handler()
    private val heartBeatRunnable_beat: Runnable = object : Runnable {
        override fun run() {


            //定时对长连接进行心跳检测
            mHandler_beat.postDelayed(
                this,
                1000
            )
        }
    }
    lateinit var client: JWebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myid = "889865"
        val toid = "8898er65"
        Thread {
            val uri: URI = URI.create("ws://192.168.6.110:8080?myid=" + myid)
            client = object : JWebSocketClient(uri) {
                override fun onMessage(message: String) {
                    //message就是接收到的消息
                    Log.e("JWebSClientService", message)
                }
            }
            try {
                client.connectionLostTimeout = 10
                client.connectBlocking()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            if (client != null && client.isOpen()) {
                val gaga = JSONObject()
                gaga.put("fuck", "1123")
                //  client.send(gaga.toString());
            }
            repeat(100) {
                val gaga = JSONObject()
                gaga.put("id", myid)
                gaga.put("toid", toid)
                gaga.put("params", "ddfg")
                client.send(gaga.toString())
                Thread.sleep(1000)
            }
            //client.closeBlocking()
        }.start()
    }

    override fun onDestroy() {
        client.closeBlocking()
        super.onDestroy()
    }
}