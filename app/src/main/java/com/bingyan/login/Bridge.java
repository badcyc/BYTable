package com.bingyan.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.bingyan.bytable.ClasstableMainActivity;
import com.bingyan.utils.ActivityUtil;

/**
 * Created by cyc20 on 2018/4/5.
 */

public class Bridge {

        private Context context;
    private static final String TAG = "Bridge";
      OnSuccessCallback mOnSuccessCallback=new OnSuccessCallback() {
          @Override
          public void onSuccuess() {
              Intent intent=new Intent(context,ClasstableMainActivity.class);
              context.startActivity(intent);
          }
      };
        public Bridge(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void show(String json) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
            });
            Log.d(TAG, "show: "+json);
            ActivityUtil.putIntoSharePreferences(context, "js", "js", json);
            mOnSuccessCallback.onSuccuess();
        }
       interface OnSuccessCallback{
        void onSuccuess();
     }
}
