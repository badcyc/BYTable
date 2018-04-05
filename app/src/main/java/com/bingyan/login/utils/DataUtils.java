package com.bingyan.login.utils;

import android.util.Log;

import com.bingyan.login.interf.GetDataCallback;
import com.bingyan.utils.BaseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by cyc20 on 2018/4/2.
 */

public class DataUtils {

    private static final String TAG = "DataUtils";
    public static void getJsFileFromHttp(GetDataCallback getDataCallback) throws IOException {
        new Thread(() -> {
            URL url;
            BufferedReader in = null;
            URLConnection urlConnection;
            try {
                url = new URL("https://raw.githubusercontent.com/badcyc/curriculum/master/js");
                urlConnection = url.openConnection();
                urlConnection.connect();
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String s;
                while ((s = in.readLine()) != null) {
                    stringBuilder.append(s);
                }
                getDataCallback.onSuccess(stringBuilder.toString());
                Log.d(TAG, "getJsFileFromHttp: "+stringBuilder.toString());
                //System.out.println(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
                getDataCallback.onError();
            } finally {
                BaseUtils.closeIO(in);
            }
        }).start();


        // String result=BaseUtils.inputStreamToString(urlConnection.getInputStream());

    }

}
