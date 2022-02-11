package com.example.learntodrawpvz.ui

import android.content.Intent
import android.os.Bundle
import android.sms.ezviewbinding.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learntodrawpvz.Pref
import com.example.learntodrawpvz.ProjectApplication
import com.example.learntodrawpvz.Utils
import com.example.learntodrawpvz.ads.InterstitialUtils
import com.example.learntodrawpvz.ads.NativeAdsUtils
import com.example.learntodrawpvz.databinding.PlantSelectActivityBinding

class SelectPlantAct : AppCompatActivity() {
    private val binding by viewBinding(PlantSelectActivityBinding::inflate)
    private lateinit var pvzAdapter: PvZAdapter

    companion object {
        const val PVZ_INDEX = "PVZ_INDEX"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        addAds()
    }

    private fun addAds() {
        if(ProjectApplication.hasAds) {
            NativeAdsUtils.addLargeNativeAd(this,binding.nativeAdsContainer)
        }
    }

    private fun initView() {
        pvzAdapter = PvZAdapter(this) {
            tapEffect()
            InterstitialUtils.getInstance().showInterstitialAd {
                val intent = Intent(this@SelectPlantAct, TutorialAct::class.java)
                intent.putExtra(PVZ_INDEX, it + 1)
                startActivity(intent)
            }
        }
    }

    private fun initListener() {
        binding.btnSound.setOnClickListener {
            Pref.soundEnabled = !Pref.soundEnabled
        }

        binding.btnSound.isChecked = Pref.soundEnabled

        binding.rvPlant.apply {
            adapter = pvzAdapter
            layoutManager =
                LinearLayoutManager(this@SelectPlantAct, LinearLayoutManager.VERTICAL, false)
        }
    }
    private fun tapEffect() {
        Utils.tapEffect(this,Pref.soundEnabled)
    }
}