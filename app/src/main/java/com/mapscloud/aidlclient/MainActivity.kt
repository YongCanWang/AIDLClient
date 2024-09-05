package com.mapscloud.aidlclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapscloud.aidlservice.IMyAidlInterface
import com.mapscloud.call.CallbackInterface

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var iMyAidlInterface: IMyAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e(TAG, "onServiceConnected: 服务连接成功")
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e(TAG, "onServiceDisconnected: 服务连接失败")
        }
    }

    private fun setListener() {
        val stub = object : CallbackInterface.Stub() {
            override fun callback(number1: Int, number2: Int, bundle: Bundle) {
                Log.e(TAG, "callback: number1:$number1---number2:$number2")
                val sum = bundle.getString("result", "null")
                Log.e(TAG, "callback: result:$sum")
            }
        }
        Log.e(TAG, "setListener ---> CallbackInterface: ${stub.hashCode()}")
        iMyAidlInterface?.setListener(1, 2, stub)
        iMyAidlInterface?.setCallbackListener(2, 3, stub)
    }

    fun onBinding(view: View) {
        val intent = Intent()
        // 启动方式1
//        intent.action =
//            "com.mapscloud.aidlservice.aidl.aidlservice.action" // AndroidManifest intent-filter
//        intent.`package` = "com.mapscloud.aidlservice"  // applicationId
//        intent.putExtra("aidlclient", "我来自AIDLClient")
//        startForegroundService(intent)  // 不调用startForegroundService() 无法建立跨线程service链接，service不执行onStartCommand()
////        startService(intent)
//        bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        // 启动方式2
        val componentName =
            ComponentName("com.mapscloud.aidlservice", "com.mapscloud.aidlservice.aidl.AIDLService")
        intent.component = componentName
        intent.action = "com.mapscloud.aidlservice.aidl.aidlservice.action"
        intent.putExtra("aidlclient", "我来自AIDLClient")
        startForegroundService(intent)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

    fun onComm(view: View) {
        val str = iMyAidlInterface?.add(2, 3)
        Log.e(TAG, "result: $str")
        setListener()
    }

    fun onGoing(view: View) {
        val packageName = "com.mapscloud.aidlservice"
        val activityService = "com.mapscloud.ui.MainActivity"
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        if (packageInfo == null) {
            Log.e(TAG, "服务不存在")
        }
        val componentName =
            ComponentName(packageName, activityService)
        var intent = Intent()
        intent.component = componentName
        startActivity(intent)
    }

    fun onSend(view: View) {
        val intent = Intent()
        intent.action =
            "com.mapscloud.br.servicebroadcastreceiver.action" // AndroidManifest intent-filter
        intent.`package` = "com.mapscloud.aidlservice"  // applicationId
        intent.putExtra("arclient", "我来自ARClient")
        sendBroadcast(intent)
    }
}