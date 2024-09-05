// IMyAidlInterface.aidl
package com.mapscloud.aidlservice;

import com.mapscloud.call.CallbackInterface;
import android.os.IBinder;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String add(int number1, int number2);

    void setListener(int number1, int number2, CallbackInterface callbackInterface);

    void setCallbackListener(int number1, int number2, IBinder callbackInterface);
}