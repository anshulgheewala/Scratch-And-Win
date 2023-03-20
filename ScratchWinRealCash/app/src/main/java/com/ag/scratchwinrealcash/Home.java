package com.ag.scratchwinrealcash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

public class Home extends AppCompatActivity implements MaxAdListener {

    ImageView imageView, imageView2,imageView3;
    CardView scratch, contact, withdraw, other,privacy_policy;
    TextView value;
    public static final String shared_pref = "sharedprefs";
    public static final String text = "TEXT";
    private String TEXT;
    private MaxInterstitialAd interstitialAd;
    private MaxInterstitialAd interstitialAd2;
    private MaxAdView adView;
    private int retryAttempt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        intent.getStringExtra("keyval");
        loadinterads();
        setContentView(R.layout.activity_home);
        imageView = findViewById(R.id.img1);
        imageView2 = findViewById(R.id.img2);
        imageView3 = findViewById(R.id.img3);
        scratch = findViewById(R.id.scratch);
        contact = findViewById(R.id.contact);
        withdraw = findViewById(R.id.withdraw);
        other = findViewById(R.id.other);
        value = findViewById(R.id.value);
        privacy_policy=findViewById(R.id.privacy_policy);
        adView = findViewById(R.id.adView);
        adView.loadAd();
        getSupportActionBar().hide();

        gettext();


        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                // AppLovin SDK is initialized, start loading ads
                loadinterads();

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }

                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Redirect("https://play.google.com/store/apps/details?id=com.ag.e_akhbaar");
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,faq.class);
                startActivity(intent);

            }
        });


        scratch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }

                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);

            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }
//                else {
//                    Toast.makeText(Home.this, "failed to load the ad", Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(Home.this, Withdraw.class);
                intent.putExtra("key2", value.getText().toString());
                startActivity(intent);
            }
        });


        //other here is share button
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //        SHARE BUTTON CODE
                    int requestcode1 = 1;

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("/text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "agdev");
                    i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(i, "Share With"));
                    i.setType("*/*");
                    startActivityForResult(i, requestcode1);

                } catch (Exception e) {
                    Toast.makeText(Home.this, "Unable to share the app please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUs();
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Uri uri=Uri.parse("https://www.freeprivacypolicy.com/live/2865b78c-f98a-4149-9f3a-a55643ff8f44");
               Intent intent1=new Intent(Intent.ACTION_VIEW,uri);
               startActivity(intent1);
            }
        });

        load();


        adView.loadAd();


    }

    public void gettext() {
        Intent intent = getIntent();
        String txt = intent.getStringExtra("key");
        value.setText(txt);

        if (value.getText().toString() == "") {
            value.setText("0");
        }

    }

    public void saved() {
        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(text, value.getText().toString());
        editor.apply();
    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref, MODE_PRIVATE);
        TEXT = sharedPreferences.getString(text, "");
        value.setText(TEXT);

    }

    @Override
    protected void onPause() {
        super.onPause();
        saved();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        load();
    }

    private void ContactUs() {
        int requestcode = 1;
        String email = "agdev2905@gmail.com";
        String subject = "Reporting For App " + getString(R.string.app_name);
        Intent intent0 = new Intent(Intent.ACTION_SEND);
        intent0.setData(Uri.parse("mail to:"));
        intent0.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent0.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent0.putExtra(Intent.EXTRA_TEXT, "Hey There,");
        intent0.setType("*/Gmail/");
        startActivityForResult(intent0, requestcode);
        if (intent0.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent0, "Choose an email Client:"));
        }
    }

    public void loadinterads() {

        interstitialAd = new MaxInterstitialAd("e9b053803f0a3bad", this);
        interstitialAd.setListener((MaxAdListener) this);

        // Load the first ad
        interstitialAd.loadAd();

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

        //Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
        // Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();


    }

    public void Redirect(String s) {
        Uri uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}