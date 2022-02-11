package com.example.learntodrawpvz.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.learntodrawpvz.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class NativeAdsUtils {
    private static UnifiedNativeAd nativeAd;

    public static void destroy() {
        UnifiedNativeAd unifiedNativeAd = nativeAd;
        if (unifiedNativeAd != null) {
            unifiedNativeAd.destroy();
        }
    }

    public static void addLargeNativeAd(Activity activity, ViewGroup viewGroup) {
        addNativeAd(activity, viewGroup, R.layout.layout_large_native_ads);
    }

    public static void addSmallNativeAd(Activity activity, ViewGroup viewGroup) {
        addNativeAd(activity, viewGroup, R.layout.layout_small_native_ads);
    }

    private static void addNativeAd(Activity activity, ViewGroup viewGroup, int i) {
        Builder builder = new Builder((Context) activity, activity.getResources().getString(R.string.admob_native_id));
        builder.forUnifiedNativeAd(unifiedNativeAd -> NativeAdsUtils.addNativeAd(activity, i, viewGroup, unifiedNativeAd));
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                Log.d("Native ads", "onAdFailedToLoad: " + i);
            }
        }).build().loadAd(getAdRequest());
    }

    static /* synthetic */ void addNativeAd(Activity activity, int i, ViewGroup viewGroup, UnifiedNativeAd unifiedNativeAd) {
        UnifiedNativeAd unifiedNativeAd2 = nativeAd;
        if (unifiedNativeAd2 != null) {
            unifiedNativeAd2.destroy();
        }
        nativeAd = unifiedNativeAd;
        UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(i, null);
        populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
        viewGroup.setVisibility(View.VISIBLE);
        viewGroup.removeAllViews();
        viewGroup.addView(unifiedNativeAdView);
    }

    private static AdRequest getAdRequest() {
        return new AdRequest.Builder().addTestDevice("0572C96D641ADC079565148454410DB1").build();
    }

    private static void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
        unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {
            unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        }
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getStarRating() == null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getAdvertiser() == null) {
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.GONE);
        } else {
            ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        VideoController videoController = unifiedNativeAd.getVideoController();
        if (videoController.hasVideoContent()) {
            videoController.setVideoLifecycleCallbacks(new VideoLifecycleCallbacks() {
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }
}
