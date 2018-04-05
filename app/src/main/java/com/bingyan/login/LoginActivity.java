package com.bingyan.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bingyan.R;
import com.bingyan.bytable.ClasstableMainActivity;
import com.bingyan.login.interf.GetDataCallback;
import com.bingyan.login.utils.DataUtils;

import java.io.IOException;

/**
 * Created by cyc20 on 2018/3/16.
 */

public class LoginActivity extends AppCompatActivity implements Bridge.OnSuccessCallback {

    /*  WebView mWebView;
      WebSettings mWebSettings;
      CookieManager mCookieManager;
      String js="events:function(start,end, callback){\n" +
              "//时间显示为yyyy-mm-dd\n" +
              "var startstr=$.fullCalendar.formatDate(start,'yyyy-MM-dd');\n" +
              "var endstr=$.fullCalendar.formatDate(end,'yyyy-MM-dd');\n" +
              "$.ajax({ \n" +
              "type: \"post\",\n" +
              "url : \"/aam/score/CourseInquiry_ido.action\",\n" +
              "dataType:'json',\n" +
              "data: {\"start\":startstr,\"end\":endstr},\n" +
              "success: function(json){\n" +
              "callback(json);\n" +
              "//菜单长度、背景长度\n" +
              "$('.contentsl').height($('.contentsr').height() + 28);\n" +
              "var bodyb = $('.contents').height() + 171\n" +
              "$('.bodyb').height(bodyb)\t\n" +
              "} " +
              "});"+
              "}";
      */
    private WebView wv;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wv = findViewById(R.id.wv);
        mContext=this;
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new Bridge(this), "bridge");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
               /* try {
                    String js=DataUtils.getJsFileFromHttp();
                    view.loadUrl(js);
                }catch (IOException e) {
                    e.printStackTrace();
                }
*/
               try {
                   DataUtils.getJsFileFromHttp(new GetDataCallback() {
                       @Override
                       public void onSuccess(String data) {
                           Log.d(TAG, "onSuccess: " + data);
                           runOnUiThread(()->{
                               Log.d(TAG, "onSuccess: "+"runOnUiThread();");
                               wv.loadUrl(data);

                               Intent intent=new Intent(LoginActivity.this,ClasstableMainActivity.class);
                               startActivity(intent);
                           });
                       }

                       @Override
                       public void onError() {
                           Log.d(TAG, "onError: "+"data is error");
                           Toast.makeText(getApplicationContext(), "获取数据错误", Toast.LENGTH_SHORT).show();
                       }
                   });
               }catch (IOException e){
                   e.printStackTrace();
               }
                return true;
            }
        });
        wv.loadUrl("https://pass.hust.edu.cn/cas/login?service=http%3A%2F%2Fhubs.hust.edu.cn%2Fhustpass.action");
    }


    // private static final String TAG = "MainActivity";
    private static final String TAG = "LoginActivity";

    @Override
    public void onSuccuess() {
        Intent intent=new Intent(LoginActivity.this,ClasstableMainActivity.class);
        startActivity(intent);
    }

  /*  private static class Bridge {
        private Context context;

        OnSuccessCallback mOnSuccessCallback;
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
    }*/


}
