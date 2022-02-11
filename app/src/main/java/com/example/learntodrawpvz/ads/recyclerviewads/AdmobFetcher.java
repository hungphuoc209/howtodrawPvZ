package com.example.learntodrawpvz.ads.recyclerviewads;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.learntodrawpvz.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdmobFetcher extends AdmobFetcherBase {

    private final String TAG = AdmobFetcher.class.getCanonicalName();

    /**
     * Maximum number of ads to prefetch.
     */
    public static final int PREFETCHED_ADS_SIZE = 2;
    /**
     * Maximum number of times to try fetch an ad after failed attempts.
     */
    private static final int MAX_FETCH_ATTEMPT = 4;

    private int mFetchingAdsCnt = 0;
    private AdLoader adLoader;
    private List<UnifiedNativeAd> mPrefetchedAdList = new ArrayList<>();
    private SparseArray adMapAtIndex = new SparseArray();

    private final List<String> mAdmobReleaseUnitIds = new ArrayList<>();

    /**
     * Gets native ad at a particular index in the fetched ads list.
     *
     * @param index the index of ad in the fetched ads list
     * @return the native ad in the list
     * @see #getFetchedAdsCount()
     */
    public synchronized UnifiedNativeAd getAdForIndex(final int index) {
        UnifiedNativeAd adNative = null;
        if (index >= 0)
            adNative = (UnifiedNativeAd) adMapAtIndex.get(index);

        if (adNative == null && mPrefetchedAdList.size() > 0) {
            adNative = mPrefetchedAdList.remove(0);

            if (adNative != null) {
                adMapAtIndex.put(index, adNative);
            }
        }

        ensurePrefetchAmount(); // Make sure we have enough pre-fetched ads
        return adNative;
    }

    @Override
    public synchronized int getFetchingAdsCount() {
        return mFetchingAdsCnt;
    }

    /**
     * Fetches a new native ad.
     *
     * @param context the current context.
     * @see #destroyAllAds()
     */
    public synchronized void prefetchAds(Context context) {
        super.prefetchAds(context);
        setupAds();
        fetchAd();
    }

    /**
     * Destroys ads that have been fetched, that are still being fetched and removes all resource
     * references that this instance still has. This should only be called when the Activity that
     * is showing ads is closing, preferably from the {@link android.app.Activity}.
     * </p>
     * The converse of this call is {@link #prefetchAds(Context)}.
     */
    public synchronized void destroyAllAds() {
        mFetchingAdsCnt = 0;
        adMapAtIndex.clear();
        mPrefetchedAdList.clear();

        Log.i(TAG, "destroyAllAds adList " + adMapAtIndex.size() + " prefetched " +
                mPrefetchedAdList.size());

        super.destroyAllAds();
    }

    /**
     * Destroys all the ads in Map to refresh it with new one
     * */
    public synchronized void clearMapAds() {
        adMapAtIndex.clear();
        mFetchingAdsCnt = mPrefetchedAdList.size();
    }

    /**
     * Fetches a new native ad.
     */
    protected synchronized void fetchAd() {
        Context context = mContext.get();

        if (context != null) {
            Log.i(TAG, "Fetching Ad now");
            if (lockFetch.getAndSet(true))
                return;
            mFetchingAdsCnt++;
            adLoader.loadAd(getAdRequest()); //Fetching the ads item
        } else {
            mFetchFailCount++;
            Log.i(TAG, "Context is null, not fetching Ad");
        }
    }

    /**
     * Ensures that the necessary amount of prefetched native ads are available.
     */
    protected synchronized void ensurePrefetchAmount() {
        if (mPrefetchedAdList.size() < PREFETCHED_ADS_SIZE &&
                (mFetchFailCount < MAX_FETCH_ATTEMPT)) {
            fetchAd();
        }
    }

    /**
     * Determines if the native ad can be used.
     *
     * @param adNative the native ad object
     * @return <code>true</code> if the ad object can be used, false otherwise
     */
    private boolean canUseThisAd(UnifiedNativeAd adNative) {
//        if (adNative != null) {
//            NativeAd.Image logoImage = null;
//            CharSequence header = null, body = null;
//            if (adNative instanceof NativeContentAd) {
//                NativeContentAd ad = (NativeContentAd) adNative;
//                logoImage = ad.getLogo();
//                header = ad.getHeadline();
//                body = ad.getBody();
//            } else if (adNative instanceof NativeAppInstallAd) {
//                NativeAppInstallAd ad = (NativeAppInstallAd) adNative;
//                logoImage = ad.getIcon();
//                header = ad.getHeadline();
//                body = ad.getBody();
//            }
//
//            if (!TextUtils.isEmpty(header)
//                    && !TextUtils.isEmpty(body)) {
//                return true;
//            }
//        }
        return adNative != null;
    }

    public String getDefaultUnitId() {
        return mContext.get().getString(R.string.admob_native_id);
    }

    private String getReleaseUnitId() {
        return mAdmobReleaseUnitIds.size() > 0 ? mAdmobReleaseUnitIds.get(0) : null;
    }

    /**
     * Subscribing to the native ads events
     */
    protected synchronized void setupAds() {
        String unitId = getReleaseUnitId() != null ? getReleaseUnitId() : getDefaultUnitId();
        AdLoader.Builder adloaderBuilder = new AdLoader.Builder(mContext.get(), unitId)
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, etc.
                        Log.i(TAG, "onAdFailedToLoad " + errorCode);
                        lockFetch.set(false);
                        mFetchFailCount++;
                        mFetchingAdsCnt--;
                        ensurePrefetchAmount();
                        onAdFailed( mPrefetchedAdList.size(), errorCode, null);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build());

        adloaderBuilder.forUnifiedNativeAd(this::onAdFetched);

        adLoader = adloaderBuilder.build();
    }

    /**
     * A handler for received native ads
     */
    private synchronized void onAdFetched(UnifiedNativeAd adNative) {
        Log.i(TAG, "onAdFetched");
        int index = -1;
        if (canUseThisAd(adNative)) {
            mPrefetchedAdList.add(adNative);
            index = mPrefetchedAdList.size() - 1;
            mNoOfFetchedAds++;
        }
        lockFetch.set(false);
        mFetchFailCount = 0;
        ensurePrefetchAmount();
        onAdLoaded(index);
    }

    public void setReleaseUnitIds(Collection<String> admobReleaseUnitIds) {
        if (admobReleaseUnitIds.size() > 1)
            throw new RuntimeException("Currently only supports one unit id.");

        mAdmobReleaseUnitIds.addAll(admobReleaseUnitIds);
    }
}
