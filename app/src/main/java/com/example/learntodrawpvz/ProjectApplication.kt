package com.example.learntodrawpvz

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.example.learntodrawpvz.ads.AdsManager
import com.example.learntodrawpvz.ads.AppOpenAdUtils
import com.example.learntodrawpvz.ads.InterstitialUtils

import com.google.android.gms.ads.MobileAds

class ProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Kotpref.init(this)
        MobileAds.initialize(this)
        InterstitialUtils.getInstance().init(this)
        AdsManager.initAdmob(this)
        AppOpenAdUtils.getInstance().init(this)
    }

    companion object {
        lateinit var appContext: Context
        const val hasAds = true
    }
}