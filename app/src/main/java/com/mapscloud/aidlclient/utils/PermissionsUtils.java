package com.mapscloud.aidlclient.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author TomCan
 * @description:
 * @date:2023/2/23 10:42
 */
public class PermissionsUtils {
    private static final String TAG = "PermissionsUtils";
    private static String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA
    };

    /**
     * 检查权限
     */
    public static void checkPermissions(Activity context) {
        PackageManager packageManager = context.getPackageManager();
        for (String s : permissions) {
            try {
                PermissionInfo permissionInfo = packageManager.getPermissionInfo(s, 0);
                CharSequence charSequence = permissionInfo.loadLabel(packageManager);
                if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, permissions, 10001);
                } else {
                    Log.e(TAG, "checkPermissions: 权限通过" + charSequence);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
