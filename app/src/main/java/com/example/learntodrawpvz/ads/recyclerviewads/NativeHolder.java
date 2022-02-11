package com.example.learntodrawpvz.ads.recyclerviewads;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class NativeHolder extends RecyclerView.ViewHolder {

    public NativeHolder(ViewGroup adViewWrapper) {
        super(adViewWrapper);
    }

    public ViewGroup getAdViewWrapper() {
        return (ViewGroup) itemView;
    }

}
