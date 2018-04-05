package com.bingyan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bingyan.bytable.ClasstableMainActivity;
import com.bingyan.bytable.Store;
import com.bingyan.login.LoginActivity;

/**
 * Created by cyc20 on 2018/4/4.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Store.isLocalHaveData(MainActivity.this)) {

                    Log.d(TAG, "onClick: "+"local have data");
                    Intent intent = new Intent(MainActivity.this, ClasstableMainActivity.class);
                    startActivity(intent);

                } else {

                    Log.d(TAG, "onClick: "+"local no data");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
