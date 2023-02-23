package com.mapscloud.aidlclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapscloud.aidlclient.utils.PermissionsUtils

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionsUtils.checkPermissions(this)
    }

    var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e(TAG, "onServiceConnected: ")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e(TAG, "onServiceDisconnected: ")
        }
    }

    fun onBinding(view: View) {
        val intent = Intent()
        intent.action = "com.mapscloud.aidlservice.aidl.dataservice.action" // AndroidManifest intent-filter
        intent.`package` = "com.mapscloud.aidlservice"  // applicationId
        intent.putExtra("aidlclient", "我来自AIDLClient")
        startForegroundService(intent)
//        startService(intent)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }


    fun onComm(view: View) {

    }


    fun onGoing(view: View) {

    }
}