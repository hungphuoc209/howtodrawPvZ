package com.example.learntodrawpvz.ads;

import android.content.Context;

import com.example.learntodrawpvz.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

public class InterstitialUtils {
    private static InterstitialUtils INSTANCE;
    /* access modifiers changed from: private */
    private AdCloseListener adCloseListener;
    private InterstitialAd interstitialAd;
    /* access modifiers changed from: private */
    private boolean isReloaded = false;
    private final long A_MINUTE_IN_MILLIS = 0;
    private long lastShowInterstitialAds = 0;

    public interface AdCloseListener {
        void onAdClosed();
    }

    public static InterstitialUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InterstitialUtils();
        }
        return INSTANCE;
    }

    public void release() {
        this.interstitialAd = null;
        this.adCloseListener = null;
        this.isReloaded = false;
        INSTANCE = null;
    }

    public void init(Context context, String str, String str2) {
//         TODO: test
//        MobileAds.initialize(context, "ca-app-pub-4710143988871013~2022369089");
//        this.interstitialAd = new InterstitialAd(context);
//        this.interstitialAd.setAdUnitId("ca-app-pub-4710143988871013/3882245666");

        // Reality
        MobileAds.initialize(context, str);
        this.interstitialAd = new InterstitialAd(context);
        this.interstitialAd.setAdUnitId(str2);

        this.interstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                if (InterstitialUtils.this.adCloseListener != null) {
                    InterstitialUtils.this.adCloseListener.onAdClosed();
                }
                InterstitialUtils.this.loadInterstitialAd();
            }

            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (!InterstitialUtils.this.isReloaded) {
                    InterstitialUtils.this.isReloaded = true;
                    InterstitialUtils.this.loadInterstitialAd();
                }
            }

            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        loadInterstitialAd();
    }

    public void init(Context context) {
        init(context, context.getString(R.string.admob_app_id), context.getString(R.string.admob_instertitial_id));
    }

    /* access modifiers changed from: private */
    private void loadInterstitialAd() {
        InterstitialAd interstitialAd2 = this.interstitialAd;
        if (interstitialAd2 != null && !interstitialAd2.isLoading() && !this.interstitialAd.isLoaded()) {
            try {
                this.interstitialAd.loadAd(getAdRequest());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private AdRequest getAdRequest() {
        return new Builder().addTestDevice("0572C96D641ADC079565148454410DB1").build();
    }

    public void showInterstitialAd(AdCloseListener adCloseListener2) {
        if (canShowInterstitialAd()) {
            this.isReloaded = false;
            this.adCloseListener = adCloseListener2;
            this.interstitialAd.show();
            return;
        }
        loadInterstitialAd();
        adCloseListener2.onAdClosed();
    }

    public void showInterstitialAdAfterClick() {
        if (Calendar.getInstance().getTimeInMillis() - lastShowInterstitialAds < A_MINUTE_IN_MILLIS) {
            return;
        }

        if (canShowInterstitialAd()) {
            lastShowInterstitialAds = Calendar.getInstance().getTimeInMillis();
            this.isReloaded = false;
            this.interstitialAd.show();
        }
    }

    public boolean canShowInterstitialAd() {
        InterstitialAd interstitialAd2 = this.interstitialAd;
        return interstitialAd2 != null && interstitialAd2.isLoaded();
    }
}
