package com.mvp.lt.andfixtest;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import static android.content.ContentValues.TAG;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2017/11/24/024
 */

public class BaseApp extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //Ali热修复
        try {
            mPatchManager = new PatchManager(this);
            //初始化patch版本
            String pkName = this.getPackageName();
            String versionName = getPackageManager().getPackageInfo(pkName,0).versionName;
            Log.e(TAG,"pkName:"+pkName+",versionName："+versionName);
            //初始化版本名称
            mPatchManager.init(versionName);
            //加载之前的path
            mPatchManager.loadPatch();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
