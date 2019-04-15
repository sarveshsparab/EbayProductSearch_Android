package com.sarveshparab.ebayproductsearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sarveshparab.ebayproductsearch.LandingActivity;
import com.sarveshparab.ebayproductsearch.utility.ValUtil;

public class PSSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(ValUtil.SPLASH_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(PSSplashActivity.this, LandingActivity.class));
        finish();
    }
}
