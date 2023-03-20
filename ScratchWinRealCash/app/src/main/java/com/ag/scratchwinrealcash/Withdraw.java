package com.ag.scratchwinrealcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Withdraw extends AppCompatActivity implements MaxAdListener{

    EditText name, upi, amount;
    Button cash_out;
    TextView textView100;
    DatabaseReference users;
    public static final String shared_pref = "sharedprefs";
    public static final String text = "TEXT";
    private String TEXT;
    private MaxAdView adView;
    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        Intent intent = getIntent();
        textView100 = findViewById(R.id.tvw);
        String s = intent.getStringExtra("key2");
        getSupportActionBar().hide();
        textView100.setText(s);
        loadinterads();

        AppLovinSdk.getInstance( this).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
                loadinterads();
            }
        } );

        name = findViewById(R.id.edittext_name);
        upi = findViewById(R.id.edittext_upi);
        amount = findViewById(R.id.edittext_amount);
        cash_out = findViewById(R.id.cashout);
        adView=findViewById(R.id.adView);
        adView.loadAd();

        cash_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }

                if (name.getText().toString().isEmpty() || upi.getText().toString().isEmpty() || amount.getText().toString().isEmpty()) {
                    Toast.makeText(Withdraw.this, "Please fill the Entries Correctly!", Toast.LENGTH_SHORT).show();
                } else {
                    String con = textView100.getText().toString();
                    float c = Float.parseFloat(con);
                    String a = amount.getText().toString();
                    float b = Float.parseFloat(a);


                    //here c is earned amount and b is user entered to withdraw

                    users = FirebaseDatabase.getInstance().getReference().child("users");

                    if (b >= 50 && c >= 50 && c >= b) {
                        Toast.makeText(Withdraw.this, "congrats request sent", Toast.LENGTH_SHORT).show();
                        insertStudentData();
                        Updatetv();
                        saved();
                    } else if (c < 50 || b < 50) {
                        Toast.makeText(Withdraw.this, "Low Amount", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Withdraw.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        adView.loadAd();
    }






    public void insertStudentData() {
       try {
           String Name = name.getText().toString();
           String Upi = upi.getText().toString();
           String Amount = amount.getText().toString();
           String val = textView100.getText().toString();

           UserData userData=new UserData(Name,Upi,Amount,val);

           users.push().setValue(userData);
       }
       catch (Exception e){
           Toast.makeText(this, "Check Your Entries", Toast.LENGTH_SHORT).show();
       }
    }

    public void Updatetv() {
        String x = textView100.getText().toString();
        float y = Float.parseFloat(x);
        y = y - Float.parseFloat(amount.getText().toString());
        textView100.setText("" + y);

        Intent intent = new Intent(Withdraw.this, Home.class);
        intent.putExtra("keyval", y);
        startActivity(intent);
    }

    public void saved() {
        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(text, textView100.getText().toString());
        editor.apply();
    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences(shared_pref, MODE_PRIVATE);
        TEXT = sharedPreferences.getString(text, "");
        textView100.setText(TEXT);

    }
    public void loadinterads(){

        interstitialAd = new MaxInterstitialAd( "e9b053803f0a3bad", this );
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

    }

    @Override
    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {

    }
}