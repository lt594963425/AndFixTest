package com.mvp.lt.andfixtest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void testBug(View view) {
        Toast.makeText(this, "修复之前", Toast.LENGTH_LONG).show();
    }

    /**
     * 后台提供接口，有bug时，下载修复包 进行修复
     * @param view
     */
    public void andFix(View view) {
        try {
            // 测试 目前暂且放在本地
            String patchFileString = Environment.getExternalStorageDirectory() + "/fix.apatch";
            Log.e("TAG", patchFileString);
            // 修复apatch，不需要重启可立即生效
            BaseApp.mPatchManager.addPatch(patchFileString);
            Toast.makeText(this, "Bug修复成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Bug修复失败", Toast.LENGTH_LONG).show();
        }
    }



}
