// CallbackInterface.aidl
package com.mapscloud.call;

import android.os.Bundle;

// Declare any non-default types here with import statements

interface CallbackInterface {
    /**
     * bundle: in out or inout
     *
     */
    void callback(int number1, int number2, inout Bundle bundle);
}