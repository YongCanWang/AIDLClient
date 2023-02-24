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

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var iMyAidlInterface: IMyAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder)
            Log.e(TAG, "onServiceConnected: ")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e(TAG, "onServiceDisconnected: ")
        }
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
        val str = iMyAidlInterface?.getStr(2)
        Log.e(TAG, "onServiceConnected: $str")
    }


    fun onGoing(view: View) {
        val packageInfo = packageManager.getPackageInfo("com.mapscloud.aidlservice", 0)
        if (packageInfo == null) {
            Log.e(TAG, "服务不存在")
        }
        val componentName =
            ComponentName("com.mapscloud.aidlservice", "com.mapscloud.aidlservice.MainActivity")
        var intent = Intent()
        intent.component = componentName
        startActivity(intent)
    }
}