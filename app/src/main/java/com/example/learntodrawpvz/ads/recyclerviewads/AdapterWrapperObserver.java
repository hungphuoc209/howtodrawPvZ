package com.example.learntodrawpvz.ads.recyclerviewads;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterWrapperObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapterWrapper;
    private AdmobAdapterCalculator adapterCalculator;
    private AdmobFetcherBase fetcher;

    public AdapterWrapperObserver(@NonNull RecyclerView.Adapter<RecyclerView.ViewHolder> adapterWrapper,
                                  @NonNull AdmobAdapterCalculator admobAdapterCalculator,
                                  @NonNull AdmobFetcherBase admobFetcher) {
        this.adapterWrapper = adapterWrapper;
        this.adapterCalculator = admobAdapterCalculator;
        this.fetcher = admobFetcher;
    }

    @Override
    public void onChanged() {
        adapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        this.onItemRangeChanged(positionStart, itemCount, null);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        int fetchedAdsCount = fetcher.getFetchedAdsCount();
        //getting the position in a final presentation
        int wrapperIndexFirst = adapterCalculator.translateSourceIndexToWrapperPosition(positionStart, fetchedAdsCount);
        int wrapperIndexLast = adapterCalculator.translateSourceIndexToWrapperPosition(positionStart + itemCount - 1, fetchedAdsCount);
        if (itemCount == 1)
            adapterWrapper.notifyItemRangeChanged(wrapperIndexFirst, 1, payload);
        else
            adapterWrapper.notifyItemRangeChanged(wrapperIndexFirst, wrapperIndexLast - wrapperIndexFirst + 1, payload);

    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        int fetchedAdsCount = fetcher.getFetchedAdsCount();
        //getting the position in a final presentation
        int wrapperIndexFirst = adapterCalculator.translateSourceIndexToWrapperPosition(positionStart, fetchedAdsCount);
        int wrapperIndexLast = adapterCalculator.translateSourceIndexToWrapperPosition(positionStart + itemCount - 1, fetchedAdsCount);
        if (itemCount == 1)
            adapterWrapper.notifyItemRangeInserted(wrapperIndexFirst, 1);
        else
            adapterWrapper.notifyItemRangeInserted(wrapperIndexFirst, wrapperIndexLast - wrapperIndexFirst + 1);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        int fetchedAdsCount = fetcher.getFetchedAdsCount();
        //getting the position in a final presentation
        int fromWrapperIndexFirst = adapterCalculator.translateSourceIndexToWrapperPosition(fromPosition, fetchedAdsCount);
        int fromWrapperIndexLast = adapterCalculator.translateSourceIndexToWrapperPosition(fromPosition + itemCount - 1, fetchedAdsCount);
        int toWrapperIndexFirst = adapterCalculator.translateSourceIndexToWrapperPosition(toPosition, fetchedAdsCount);
        int toWrapperIndexLast = adapterCalculator.translateSourceIndexToWrapperPosition(toPosition + itemCount - 1, fetchedAdsCount);
        int wrapperItemCount = fromWrapperIndexLast - fromWrapperIndexFirst + 1;
        if (itemCount == 1)
            adapterWrapper.notifyItemMoved(fromWrapperIndexFirst, 1);
        else for (int i = 0; i < wrapperItemCount; itemCount++)
            adapterWrapper.notifyItemMoved(fromWrapperIndexFirst + i, toWrapperIndexFirst + i);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        int fetchedAdsCount = fetcher.getFetchedAdsCount();
        //getting the position in a final presentation
        int wrapperIndexFirst = adapterCalculator.translateSourceIndexToWrapperPosition(positionStart, fetchedAdsCount);
        int wrapperIndexLast = adapterCalculator.translateSourceIndexToWrapperPosition(positionStart + itemCount - 1, fetchedAdsCount);
        if (itemCount == 1)
            adapterWrapper.notifyItemRangeRemoved(wrapperIndexFirst, 1);
        else
            adapterWrapper.notifyItemRangeRemoved(wrapperIndexFirst, wrapperIndexLast - wrapperIndexFirst + 1);
    }
}