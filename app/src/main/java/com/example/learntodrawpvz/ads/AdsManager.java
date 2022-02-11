package com.example.learntodrawpvz.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.learntodrawpvz.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AdsManager {
    public static void initAdmob(Context context) {
        MobileAds.initialize(context);
    }

    public static void addBanner(Activity activity, final ViewGroup viewGroup) {
        final AdView adView = new AdView(activity);
        adView.setAdSize(getAdSize(activity));
        adView.setAdUnitId(activity.getString(R.string.admob_banner_id));
        adView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                super.onAdLoaded();
                ViewParent parent = adView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(adView);
                }
                viewGroup.addView(adView);
            }

            public void onAdOpened() {
                super.onAdOpened();
                viewGroup.setVisibility(View.GONE);
            }
        });
        adView.loadAd(getAdRequest());
    }

    private static AdSize getAdSize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }

    private static AdRequest getAdRequest() {
        return new Builder().addTestDevice("0572C96D641ADC079565148454410DB1").build();
    }
}
