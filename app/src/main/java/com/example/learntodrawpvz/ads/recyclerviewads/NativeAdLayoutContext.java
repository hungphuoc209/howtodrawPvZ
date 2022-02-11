package com.example.learntodrawpvz.ads.recyclerviewads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public abstract class NativeAdLayoutContext {

    private int mAdLayoutId;

    /*
     * Gets the res layout id for ads
     */
    public int getAdLayoutId() {
        return mAdLayoutId;
    }

    /*
     * Sets the res layout id for ads
     */
    public void setAdLayoutId(int mAdLayoutId) {
        this.mAdLayoutId = mAdLayoutId;
    }

    public abstract void bind(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView);

    public UnifiedNativeAdView inflateView(ViewGroup root) throws IllegalArgumentException{
        if(root == null) throw new IllegalArgumentException("root should be not null");
        // Inflate a layout and add it to the parent ViewGroup.
        LayoutInflater inflater = (LayoutInflater) root.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (UnifiedNativeAdView) inflater
                .inflate(getAdLayoutId(), root, false);
    }
}
