package com.mvp.lt.andfixtest;

import android.content.Context;
import android.widget.Toast;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2017/11/24/024
 */

public class TestUtils {
    private Context mContext;
    private void printlinadd(Context context) {
        int a = 2;
        int b = 3;
        Toast.makeText(context, ""+a/b, Toast.LENGTH_LONG).show();
    }
}
