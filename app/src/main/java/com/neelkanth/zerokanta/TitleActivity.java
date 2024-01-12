package com.neelkanth.zerokanta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.neelkanth.zerokanta.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    ActivityTitleBinding binding;
    private InterstitialAd pvpAd, pVsAiAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // Banner Ad
        MobileAds.initialize(this, initializationStatus -> {

        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.titleAD.loadAd(adRequest);


        // Player Vs Player Ad
        InterstitialAd.load(this, getString(R.string.player_vs_player_ad), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("Error", loadAdError.toString());
                binding.progressBar.setVisibility(View.GONE);
                startActivity(new Intent(TitleActivity.this, MainActivity.class));
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                binding.progressBar.setVisibility(View.GONE);
                pvpAd = interstitialAd;
                pvpAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        startActivity(new Intent(TitleActivity.this, MainActivity.class));
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        binding.progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(TitleActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        binding.progressBar.setVisibility(View.GONE);
                        pvpAd = null;
                    }
                });
            }
        });

        // Player Vs Ai Ad
        InterstitialAd.load(this, getString(R.string.player_vs_ai_ad), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                binding.progressBar.setVisibility(View.GONE);
                startActivity(new Intent(TitleActivity.this, WithAIActivity.class));
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                binding.progressBar.setVisibility(View.GONE);
                pVsAiAd = interstitialAd;
                pVsAiAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();

                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        binding.progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(TitleActivity.this, WithAIActivity.class));
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        binding.progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(TitleActivity.this, WithAIActivity.class));

                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        binding.progressBar.setVisibility(View.GONE);
                        pVsAiAd = null;
                    }
                });

            }
        });



        binding.PPImg.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            if (pvpAd != null){
                pvpAd.show(this);
                binding.progressBar.setVisibility(View.GONE);
            }
            else {
                binding.progressBar.setVisibility(View.GONE);
                startActivity(new Intent(TitleActivity.this, MainActivity.class));
            }
        });

        binding.PAiImg.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            if (pVsAiAd != null){
                pVsAiAd.show(this);
                binding.progressBar.setVisibility(View.GONE);
            }
            else {
                binding.progressBar.setVisibility(View.GONE);
                startActivity(new Intent(TitleActivity.this, WithAIActivity.class));
            }
        });


    }
}