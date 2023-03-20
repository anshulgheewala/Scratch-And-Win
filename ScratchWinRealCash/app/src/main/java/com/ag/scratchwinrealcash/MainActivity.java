package com.ag.scratchwinrealcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.anupkumarpanwar.scratchview.ScratchView;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements  MaxAdListener{

    TextView textView,tex;
    ScratchView scratchView;
    Button button;
    public static final String shared_pref="sharedprefs";
    public static final String text="TEXT";
    private String TEXT;
    private MaxInterstitialAd interstitialAd2;
    private int retryAttempt;
    LottieAnimationView lottieAnimationView;
    private MaxAdView adView;
    ImageView iv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv);
        scratchView=findViewById(R.id.scratchview);
        button =findViewById(R.id.mask);
        tex=findViewById(R.id.tex);
        adView=findViewById(R.id.adView);
        iv=findViewById(R.id.iv);
        lottieAnimationView=findViewById(R.id.lottie);
        getSupportActionBar().hide();
        loadinterads();


        FirebaseMessaging.getInstance().subscribeToTopic("notification");

















        AppLovinSdk.getInstance(this).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
                loadinterads();
            }

        } );
        scratchView.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {

                iv.setImageResource(R.drawable.trophy);






                if (interstitialAd2.isReady()){
                   interstitialAd2.showAd();

                   if (textView.getText().toString()==""){
                       textView.setText("0");
                   }

                   reward_given();






//                    Random r1=new Random();
//                    float min=0.10f;
//                    float max=0.15f;
//                    float random1=min+ r1.nextFloat()*(max-min);
//                    String a=textView.getText().toString();
//                    float b=Float.parseFloat(a);
//                    float c=b+random1;
//                    textView.setText(""+c);
//                    Toast.makeText(MainActivity.this, "Congrats You got "+random1, Toast.LENGTH_SHORT).show();
//                    tex.setText(""+random1);



                 lottieAnimationView.playAnimation();
                }
                else if (!interstitialAd2.isReady()){
                    iv.setImageResource(R.drawable.btl);

                    if (textView.getText().toString() == "") {
                        textView.setText("0");
                    }

                    tex.setText("Sorry Better Luck Next Time");

                }




            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (interstitialAd2.isReady()){
                    interstitialAd2.showAd();
                }
//                else {
//                    Toast.makeText(MainActivity.this, "Failed to load ads", Toast.LENGTH_SHORT).show();
//                }
               if (scratchView.isRevealed()){
                   scratchView.mask();
               }
               else{
                   Toast.makeText(MainActivity.this, "The ScratchCard is already covered", Toast.LENGTH_LONG).show();
               }
            }
        });
        adView.loadAd();
        
        



        load();


    }

    public void saved(){
        SharedPreferences sharedPreferences=getSharedPreferences(shared_pref,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(text,textView.getText().toString());
        editor.apply();
    }

    public void load(){
        SharedPreferences sharedPreferences= getSharedPreferences(shared_pref,MODE_PRIVATE);
        TEXT=sharedPreferences.getString(text,"");
        textView.setText(TEXT);

    }
    public void updateViews(){
        textView.setText(TEXT);
    }

  //  @Override
//    protected void onPause() {
//        super.onPause();
//        saved();
//        sharetext();
//    }

    public void sharetext(){
        Intent intent=new Intent(MainActivity.this,Home.class);
        intent.putExtra("key",textView.getText().toString());
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saved();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saved();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saved();
    }

    public void loadinterads(){

        interstitialAd2 = new MaxInterstitialAd( "3b2d1eaa5732233e", this );
        interstitialAd2.setListener((MaxAdListener) this);

        // Load the first ad
        interstitialAd2.loadAd();

    }

    @Override
    public void onAdLoaded(MaxAd maxAd) {


    }

    @Override
    public void onAdDisplayed(MaxAd maxAd) {
        loadinterads();
    }

    @Override
    public void onAdHidden(MaxAd maxAd) {


    }

    @Override
    public void onAdClicked(MaxAd maxAd) {

    }

    @Override
    public void onAdLoadFailed(String s, MaxError maxError) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {

    }

    public void reward_given(){

        String a=textView.getText().toString();


        float b=Float.parseFloat(a);


        if(b<=40){
            Random r1=new Random();
            float min=0.10f;
            float max=0.15f;
            float random1=min+ r1.nextFloat()*(max-min);
            float c=b+random1;
            textView.setText(""+c);
            Toast.makeText(MainActivity.this, "Congrats You got "+random1, Toast.LENGTH_SHORT).show();
            tex.setText(""+random1);
        }
        else{
            Random r1=new Random();
            float min=0.05f;
            float max=0.10f;
            float random1=min+ r1.nextFloat()*(max-min);
            float c=b+random1;
            textView.setText(""+c);
            Toast.makeText(MainActivity.this, "Congrats You got "+random1, Toast.LENGTH_SHORT).show();
            tex.setText(""+random1);
        }
    }
}