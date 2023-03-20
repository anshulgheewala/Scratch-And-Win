package com.ag.scratchwinrealcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class splash extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        lottieAnimationView=findViewById(R.id.lottie_splash);


        Thread t1=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                finally {
                    Intent intent=new Intent(splash.this,Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        t1.start();
    }
}