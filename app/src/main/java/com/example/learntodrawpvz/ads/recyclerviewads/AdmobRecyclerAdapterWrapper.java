package com.example.learntodrawpvz.ads.recyclerviewads;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.Collection;
import java.util.Collections;

/**
 * Adapter that has common functionality for any adapters that need to show ads in-between
 * other data.
 */
public class AdmobRecyclerAdapterWrapper
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements AdmobFetcherBase.AdmobListener {

    private final String TAG = AdmobRecyclerAdapterWrapper.class.getCanonicalName();

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(new AdapterWrapperObserver(this, getAdapterCalculator(), adFetcher));
        notifyDataSetChanged();
    }

    private AdmobFetcher adFetcher;
    private Context mContext;
    private EAdType mEAdType = EAdType.NATIVE_LARGE;
    private AdmobAdapterCalculator AdapterCalculator = new AdmobAdapterCalculator();

    public void setEAdType(EAdType eAdType) {
        this.mEAdType = eAdType;
    }

    public EAdType getEAdType() {
        return mEAdType;
    }

    /*
     * Gets an object which incapsulates transformation of the source and ad blocks indices
     */
    public AdmobAdapterCalculator getAdapterCalculator() {
        return AdapterCalculator;
    }

    /*
     * Injects an object which incapsulates transformation of the source and ad blocks indices. You could override calculations
     * by inheritance of AdmobAdapterCalculator class
     */
    public void setAdapterCalculator(AdmobAdapterCalculator adapterCalculatordmob) {
        AdapterCalculator = adapterCalculatordmob;
    }

    private static final int VIEW_TYPE_AD_LARGE = 1001;
    private static final int VIEW_TYPE_AD_SMALL = 1002;

    private final static int DEFAULT_NO_OF_DATA_BETWEEN_ADS = 10;
    private final static int DEFAULT_LIMIT_OF_ADS = 3;
    private final static int DEFAULT_VIEWTYPE_SOURCE_MAX = 0;

    /**
     * Gets the number of ads that have been fetched so far.
     *
     * @return the number of ads that have been fetched
     */
    public int getFetchedAdsCount() {
        return adFetcher.getFetchedAdsCount();
    }

    /**
     * Gets the number of ads have been fetched so far + currently fetching ads
     *
     * @return the number of already fetched ads + currently fetching ads
     */
    public int getFetchingAdsCount() {
        return adFetcher.getFetchingAdsCount();
    }

//    private int getViewTypeNativeLarge() {
//        return getViewTypeBiggestSource() + VIEW_TYPE_AD_LARGE + 1;
//    }
//
//    private int getViewTypeNativeSmall() {
//        return getViewTypeBiggestSource() + VIEW_TYPE_AD_SMALL + 1;
//    }

    /*
     * Gets the number of your data items between ad blocks, by default it equals to 10.
     * You should set it according to the Admob's policies and rules which says not to
     * display more than one ad block at the visible part of the screen
     * so you should choose this parameter carefully and according to your item's height and screen resolution of a target devices
     */
    public int getNoOfDataBetweenAds() {
        return AdapterCalculator.getNoOfDataBetweenAds();
    }

    /*
     * Sets the number of your data items between ad blocks, by default it equals to 10.
     * You should set it according to the Admob's policies and rules which says not to
     * display more than one ad block at the visible part of the screen
     * so you should choose this parameter carefully and according to your item's height and screen resolution of a target devices
     */
    public void setNoOfDataBetweenAds(int mNoOfDataBetweenAds) {
        AdapterCalculator.setNoOfDataBetweenAds(mNoOfDataBetweenAds);
    }

    public int getFirstAdIndex() {
        return AdapterCalculator.getFirstAdIndex();
    }

    /*
     * Sets the first ad block index (zero-based) in the adapter, by default it equals to 0
     */
    public void setFirstAdIndex(int firstAdIndex) {
        AdapterCalculator.setFirstAdIndex(firstAdIndex);
    }

    /*
     * Gets the max count of ad blocks per dataset, by default it equals to 3 (according to the Admob's policies and rules)
     */
    public int getLimitOfAds() {
        return AdapterCalculator.getLimitOfAds();
    }

    /*
     * Sets the max count of ad blocks per dataset, by default it equals to 3 (according to the Admob's policies and rules)
     */
    public void setLimitOfAds(int mLimitOfAds) {
        AdapterCalculator.setLimitOfAds(mLimitOfAds);
    }

    private int viewTypeBiggestSource;

    /*
     * Gets the biggest value among all the view types in the underlying source adapter, by default it equals to 0.
     */
    public int getViewTypeBiggestSource() {
        return viewTypeBiggestSource;
    }

    /*
     * Sets the biggest value among all the view types in the underlying source adapter, by default it equals to 0.
     */
    public void setViewTypeBiggestSource(int viewTypeBiggestSource) {
        this.viewTypeBiggestSource = viewTypeBiggestSource;
    }

    private NativeAdLayoutContext mSmallAdsLayoutContext;

    /*
     * Gets the context (the res layout id and a strategy of inflating and binding) for published content ads {@link https://support.google.com/admob/answer/6240809}
     */
    public NativeAdLayoutContext getSmallAdsLayoutContext() {
        return mSmallAdsLayoutContext;
    }

    /*
     * Sets the context (the res layout id and a strategy of inflating and binding) for published content ads {@link https://support.google.com/admob/answer/6240809}
     */
    public void setSmallAdsLayoutContext(NativeAdLayoutContext smallAdsLayoutContext) {
        this.mSmallAdsLayoutContext = smallAdsLayoutContext;
    }

    private NativeAdLayoutContext mLargeAdsLayoutContext;

    /*
     * Gets the context (the res layout id and a strategy of inflating and binding) for published install app ads {@link https://support.google.com/admob/answer/6240809}
     */
    public NativeAdLayoutContext getLargeAdsLayoutContext() {
        return mLargeAdsLayoutContext;
    }

    /*
     * Sets the context (the res layout id and a strategy of inflating and binding) for published install app ads {@link https://support.google.com/admob/answer/6240809}
     */
    public void setLargeAdsLayoutContext(NativeAdLayoutContext largeAdsLayoutContext) {
        this.mLargeAdsLayoutContext = largeAdsLayoutContext;
    }

//    /**
//     * Use this constructor for test purposes. if you are going to release the live version
//     * please use the appropriate constructor
//     * @see #AdmobRecyclerAdapterWrapper(Context, String)
//     * @param testDevicesId sets a devices ID to test ads interaction.
//     * You could pass null but it's better to set ids for all your test devices
//     * including emulators. for emulator just use the
//     * @see {AdRequest.DEVICE_ID_EMULATOR}
//     */
//    public AdmobRecyclerAdapterWrapper(Context context, String[] testDevicesId) {
//        this(context, testDevicesId, EnumSet.allOf(EAdType.class));
//    }
//    /**
//     * @param admobReleaseUnitId sets a release unit ID for admob banners.
//     * If you are testing the ads please pass null
//     * ID should be active, please check it in your Admob's account.
//     * Be careful: don't set it or set to null if you still haven't deployed a Release.
//     * Otherwise your Admob account could be banned
//     */
//    public AdmobRecyclerAdapterWrapper(Context context, String admobReleaseUnitId) {
//        this(context, admobReleaseUnitId, EnumSet.allOf(EAdType.class));
//    }
//
//    /**
//     * @param admobReleaseUnitIds sets a release unit IDs for admob banners.
//     * It works like FIFO (first in = first out). Each ad block will get one from the queue.
//     * If the desired count of ad blocks is greater than this collection size
//     * then the last entry will be duplicated to remaining ad blocks.
//     * If you are testing the ads please use constructor for tests
//     * @see #AdmobRecyclerAdapterWrapper(Context, String[])
//     * ID should be active, please check it in your Admob's account.
//     * Be careful: don't set it or set to null if you still haven't deployed a Release.
//     * Otherwise your Admob account could be banned
//     */
//    public AdmobRecyclerAdapterWrapper(Context context, Collection<String> admobReleaseUnitIds) {
//        this(context, admobReleaseUnitIds, EnumSet.allOf(EAdType.class));
//    }
//
//    /**
//     * Use this constructor for test purposes. if you are going to release the live version
//     * please use the appropriate constructor
//     * @see #AdmobRecyclerAdapterWrapper(Context, String)
//     * @param testDevicesId sets a devices ID to test ads interaction.
//     * You could pass null but it's better to set ids for all your test devices
//     * including emulators. for emulator just use the
//     * @see {AdRequest.DEVICE_ID_EMULATOR}
//     * @param adTypesToShow sets the types of ads to show in the list.
//     * By default all types are loaded by wrapper.
//     * i.e. pass EnumSet.of(EAdType.ADVANCED_INSTALLAPP) to show only install app ads
//     */
//    public AdmobRecyclerAdapterWrapper(Context context, String[] testDevicesId) {
//        init(context, null, testDevicesId);
//    }
//
//    /**
//     * @param admobReleaseUnitId sets a release unit ID for admob banners.
//     * If you are testing the ads please use constructor for tests
//     * @see #AdmobRecyclerAdapterWrapper(Context, String[])
//     * ID should be active, please check it in your Admob's account.
//     * Be careful: don't set it or set to null if you still haven't deployed a Release.
//     * Otherwise your Admob account could be banned
//     * @param adTypesToShow sets the types of ads to show in the list.
//     * By default all types are loaded by wrapper.
//     * i.e. pass EnumSet.of(EAdType.ADVANCED_INSTALLAPP) to show only install app ads
//     */
//    public AdmobRecyclerAdapterWrapper(Context context, String admobReleaseUnitId) {
//        Collection<String> releaseUnitIds = admobReleaseUnitId==null
//                ? null
//                : Collections.singletonList(admobReleaseUnitId);
//        init(context, releaseUnitIds, null);
//    }
//
//    /**
//     * @param admobReleaseUnitIds sets a release unit ID for admob banners.
//     * It works like FIFO (first in = first out). Each ad block will get one from the queue.
//     * If the desired count of ad blocks is greater than this collection size
//     * then the last entry will be duplicated to remaining ad blocks.
//     * If you are testing the ads please use constructor for tests
//     * @see #AdmobRecyclerAdapterWrapper(Context, String[])
//     * ID should be active, please check it in your Admob's account.
//     * Be careful: don't set it or set to null if you still haven't deployed a Release.
//     * Otherwise your Admob account could be banned
//     * @param adTypesToShow sets the types of ads to show in the list.
//     * By default all types are loaded by wrapper.
//     * i.e. pass EnumSet.of(EAdType.ADVANCED_INSTALLAPP) to show only install app ads
//     */
//    public AdmobRecyclerAdapterWrapper(Context context, Collection<String> admobReleaseUnitIds) {
//        init(context, admobReleaseUnitIds, null);
//    }

    public AdmobRecyclerAdapterWrapper(Context context, String admobReleaseUnitId) {
        init(context, Collections.singletonList(admobReleaseUnitId));
    }

    private void init(Context context, Collection<String> admobReleaseUnitIds) {
        setViewTypeBiggestSource(DEFAULT_VIEWTYPE_SOURCE_MAX);
        setNoOfDataBetweenAds(DEFAULT_NO_OF_DATA_BETWEEN_ADS);
        setLimitOfAds(DEFAULT_LIMIT_OF_ADS);
        setLargeAdsLayoutContext(NativeLargeLayoutContext.getDefault());
        setSmallAdsLayoutContext(NativeSmallLayoutContext.getDefault());
        mContext = context;

        adFetcher = new AdmobFetcher();

        if (admobReleaseUnitIds != null)
            adFetcher.setReleaseUnitIds(admobReleaseUnitIds);

        adFetcher.addListener(this);
        // Start prefetching ads
        adFetcher.prefetchAds(context.getApplicationContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder == null)
            return;
        int itemViewType = viewHolder.getItemViewType();
        if (itemViewType == VIEW_TYPE_AD_SMALL) {
            UnifiedNativeAdView lvi2 = (UnifiedNativeAdView) viewHolder.itemView;
            UnifiedNativeAd ad2 = (UnifiedNativeAd) getItem(position);
            getSmallAdsLayoutContext().bind(ad2, lvi2);
        }
        if (itemViewType == VIEW_TYPE_AD_LARGE) {
            UnifiedNativeAdView lvi2 = (UnifiedNativeAdView) viewHolder.itemView;
            UnifiedNativeAd ad2 = (UnifiedNativeAd) getItem(position);
            getLargeAdsLayoutContext().bind(ad2, lvi2);
        } else {
            int origPos = AdapterCalculator.getOriginalContentPosition(position,
                    adFetcher.getFetchedAdsCount(), mAdapter.getItemCount());
            mAdapter.onBindViewHolder(viewHolder, origPos);
        }
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_AD_LARGE || viewType == VIEW_TYPE_AD_SMALL) {
            return new NativeHolder(onCreateItemView(parent, viewType));
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    private UnifiedNativeAdView onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_AD_LARGE)
            return getLargeAdsLayoutContext().inflateView(parent);
        else if (viewType == VIEW_TYPE_AD_SMALL)
            return getSmallAdsLayoutContext().inflateView(parent);
        else return null;
    }

    /**
     * <p>Gets the count of all data, including interspersed ads.</p>
     * <p/>
     * <p>If data size is 10 and an ad is to be showed after every 5 items starting at the index 0, this method
     * will return 12.</p>
     *
     * @return the total number of items this adapter can show, including ads.
     * @see AdmobRecyclerAdapterWrapper#setNoOfDataBetweenAds(int)
     * @see AdmobRecyclerAdapterWrapper#getNoOfDataBetweenAds()
     */
    @Override
    public int getItemCount() {

        if (mAdapter != null) {
            /*
            No of currently fetched ads, as long as it isn't more than no of max ads that can
            fit dataset.
             */
            int noOfAds = AdapterCalculator.getAdsCountToPublish(adFetcher.getFetchedAdsCount(), mAdapter.getItemCount());
            return mAdapter.getItemCount() > 0 ? mAdapter.getItemCount() + noOfAds : 0;
        } else {
            return 0;
        }
    }

    /**
     * Gets the item in a given position in the dataset. If an ad is to be returned,
     * a {@link NativeAd} object is returned.
     *
     * @return the object or ad contained in this adapter position
     */
    public Object getItem(int position) {
        if (AdapterCalculator.canShowAdAtPosition(position, adFetcher.getFetchedAdsCount())) {
            int adPos = AdapterCalculator.getAdIndex(position);
            return adFetcher.getAdForIndex(adPos);
        } else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (AdapterCalculator.canShowAdAtPosition(position, adFetcher.getFetchedAdsCount())) {
            return getEAdType() == EAdType.NATIVE_LARGE ? VIEW_TYPE_AD_LARGE : VIEW_TYPE_AD_SMALL;
        } else {
            int origPos = AdapterCalculator.getOriginalContentPosition(position,
                    adFetcher.getFetchedAdsCount(), mAdapter.getItemCount());
            return mAdapter.getItemViewType(origPos);
        }
    }

    /**
     * Destroys all currently fetched ads
     */
    public void destroyAds() {
        adFetcher.destroyAllAds();
    }

    /**
     * Clears all currently displaying ads to update them
     */
    public void requestUpdateAd() {
        adFetcher.clearMapAds();
    }

    @Override
    public void onAdLoaded(int adIdx) {
        notifyDataSetChanged();
    }

    @Override
    public void onAdsCountChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onAdFailed(int adIdx, int errorCode, Object adPayload) {
        notifyDataSetChanged();
    }

}
