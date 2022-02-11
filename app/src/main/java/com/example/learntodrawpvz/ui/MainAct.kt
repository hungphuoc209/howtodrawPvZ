package com.example.learntodrawpvz.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.sms.ezviewbinding.viewBinding
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.learntodrawpvz.Pref
import com.example.learntodrawpvz.R
import com.example.learntodrawpvz.Utils
import com.example.learntodrawpvz.ads.AdsManager
import com.example.learntodrawpvz.databinding.MainActivityBinding
import com.suddenh4x.ratingdialog.AppRating
import com.suddenh4x.ratingdialog.preferences.RatingThreshold
import es.dmoral.toasty.Toasty
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainAct : AppCompatActivity() {
    private val binding by viewBinding(MainActivityBinding::inflate)


    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppRating.Builder(this)
            .setMinimumLaunchTimes(3)
            .setMinimumDays(3)
            .setMinimumLaunchTimesToShowAgain(3)
            .setMinimumDaysToShowAgain(3)
            .useGoogleInAppReview()
            .setRatingThreshold(RatingThreshold.THREE)
            .setGoogleInAppReviewCompleteListener {
                Toasty.success(this, getString(R.string.review_completed), Toasty.LENGTH_SHORT)
                    .show()
            }
            .showIfMeetsConditions()

        initView()
        initListener()
        addAds()
    }

    private fun addAds() {
        AdsManager.addBanner(this, binding.adsContainer)
    }

    private fun initView() {
        supportActionBar?.hide()
    }

    private fun initListener() {
        binding.btnStart.setOnClickListener {
            tapEffect()
            if (isNetworkConnected(this)) {
                moveToMainActivity()
            } else {
                AlertDialog.Builder(this@MainAct)
                    .setMessage("Please connect the internet to process further")
                    .setCancelable(false)
                    .setPositiveButton(
                        getString(R.string.ok)
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.btnRate.setOnClickListener {
            tapEffect()
            AppRating.Builder(this).showNow()
        }

        binding.btnPrivacy.setOnClickListener {
            tapEffect()
            val url = getString(R.string.privacy_policy_url)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)
                ?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true || cm.getNetworkCapabilities(
                cm.activeNetwork
            )?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this@MainAct, SelectPlantAct::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun tapEffect() {
        Utils.tapEffect(this, Pref.soundEnabled)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}