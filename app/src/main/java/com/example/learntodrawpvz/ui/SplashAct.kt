package com.example.learntodrawpvz.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.sms.ezviewbinding.viewBinding
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.example.learntodrawpvz.R
import com.example.learntodrawpvz.ads.AppOpenAdUtils
import com.example.learntodrawpvz.databinding.SplashActivityBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SplashAct : AppCompatActivity() {
    private val binding by viewBinding(SplashActivityBinding::inflate)


    private val disposable = CompositeDisposable()

    companion object {
        var isAppStarted: Boolean = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        supportActionBar?.hide()

        val imageViewTarget = DrawableImageViewTarget(binding.titleSplash)
        Glide.with(this).load(R.drawable.splash).into<Target<Drawable>>(imageViewTarget)

        isAppStarted = true

        disposable.add(
            Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext {
                    if (AppOpenAdUtils.getInstance().canShowAppOpenAd()) {
                        disposable.dispose()
                        AppOpenAdUtils.getInstance().showAppOpenAd {
                            if (isAppStarted) {
                                isAppStarted = false
                                showButton()
                            }
                        }
                    } else {
                        if (it < 2) {
                            AppOpenAdUtils.getInstance().fetchAd()
                        } else {
                            disposable.dispose()
                            showButton()
                        }
                    }
                }.subscribe()
        )
    }

    private fun showButton() {
        Intent(this, MainAct::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}