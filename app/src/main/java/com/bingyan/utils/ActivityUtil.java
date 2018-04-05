package com.bingyan.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.bingyan.utils.BaseUtils.checkNotNull;

/**
 * Created by cyc20 on 2017/12/30.
 */

public class ActivityUtil {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static String getSharePreferencesByKey(Context context, String s, String key){
        SharedPreferences preferences=context.getSharedPreferences(s,Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }
    public static void putIntoSharePreferences(Context context,String s,String key,String value){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(s, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}
