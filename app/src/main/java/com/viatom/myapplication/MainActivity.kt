package com.viatom.myapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URI
import kotlin.random.Random

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
    val n1="13207759669"
    val n2="15769415445"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myid = n2
        val toid = n1
        val gaga = JSONObject()
        gaga.put("id", myid)
        gaga.put("toid", toid)
        gaga.put("action", "call")
        Thread {
           val ff=Random(54455);
            val gaga=ArrayList<JWebSocketClient>();
            for(k in 0..500){
                val myid= ff.nextInt(10000,200000000).toString()
                val uri: URI = URI.create("ws://192.168.5.101:3003/ws?phone=" + myid)
                val client = object : JWebSocketClient(uri) {
                    override fun onMessage(message: String) {
                        //message就是接收到的消息
                        Log.e("JWebSClientService", message)
                    }
                }
                try {
                    client.connectionLostTimeout =20
                    client.connect()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                gaga.add(client)
            }



//            repeat(100000) {
//
//                client.send(gaga.toString())
//                Thread.sleep(1000)
//            }
            //client.closeBlocking()
        }.start()
    }



    override fun onPause() {
        client.closeBlocking()
        super.onPause()
    }
}