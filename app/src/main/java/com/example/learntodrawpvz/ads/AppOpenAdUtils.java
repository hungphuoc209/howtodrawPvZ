package com.example.learntodrawpvz.ads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.learntodrawpvz.ProjectApplication;
import com.example.learntodrawpvz.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenAdUtils implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenAdUtils";
    @SuppressLint("StaticFieldLeak")
    private static AppOpenAdUtils INSTANCE;
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private Application application;
    private long loadTime = 0;
    private AppOpenAdCloseListener adCloseListener;
    private static final int MAX_RETRY_LOAD_AD = 5;
    private int countAppOpenAds = 0;
    private Activity currentActivity;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }

    public interface AppOpenAdCloseListener {
        void onAdClosed();
    }

    public static AppOpenAdUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppOpenAdUtils();
        }
        return INSTANCE;
    }

    public void init(ProjectApplication myApplication) {
        this.application = myApplication;
        this.application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        fetchAd();
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null;
                            isShowingAd = false;
                            if (adCloseListener != null) {
                                adCloseListener.onAdClosed();
                            }
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Log.e(LOG_TAG, adError.getMessage());
                            if (countAppOpenAds < MAX_RETRY_LOAD_AD) {
                                fetchAd();
                            } else if (adCloseListener != null) {
                                adCloseListener.onAdClosed();
                            }
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            if (currentActivity != null)
                appOpenAd.show(currentActivity, fullScreenContentCallback);
        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }

    public void showAppOpenAd(AppOpenAdCloseListener adCloseListener2) {
        if (canShowAppOpenAd()) {
            this.adCloseListener = adCloseListener2;
            this.showAdIfAvailable();
            return;
        }
        fetchAd();
        this.adCloseListener.onAdClosed();
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            Log.e(LOG_TAG, "AdNoLoaded");
            return;
        }

        // Handle the error.
        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            /**
             * Called when an app open ad has loaded.
             *
             * @param ad the loaded app open ad.
             */
            @Override
            public void onAppOpenAdLoaded(AppOpenAd ad) {
                appOpenAd = ad;
                loadTime = (new Date()).getTime();
                countAppOpenAds = 0;
                Log.e(LOG_TAG, "AdLoaded");
            }

            /**
             * Called when an app open ad has failed to load.
             *
             * @param loadAdError the error.
             */
            @Override
            public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                // Handle the error.
                Log.e(LOG_TAG, "onAppOpenAdFailedToLoad: " + loadAdError.getMessage());
            }

        };
        ++countAppOpenAds;
        AdRequest request = getAdRequest();

        AppOpenAd.load(
                application, application.getString(R.string.admob_app_open_id), request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo() {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * (long) 4));
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
    }

    public boolean canShowAppOpenAd() {
        return appOpenAd != null && isAdAvailable();
    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
//        if (!BillingPreferenceManager.isPurchased())
        if(ProjectApplication.hasAds) {
            showAdIfAvailable();
        }
    }
}
