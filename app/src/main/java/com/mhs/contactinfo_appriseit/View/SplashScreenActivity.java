package com.mhs.contactinfo_appriseit.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mhs.contactinfo_appriseit.Common.Constants;
import com.mhs.contactinfo_appriseit.Common.PreferenceHelper;
import com.mhs.contactinfo_appriseit.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            if (!PreferenceHelper.retriveData(this, Constants.USER_ID).isEmpty()){
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
            else {
                startActivity(new Intent(SplashScreenActivity.this, LoginRegActivity.class));
            }
            finish();
        }, SPLASH_SCREEN_TIME);
    }
}