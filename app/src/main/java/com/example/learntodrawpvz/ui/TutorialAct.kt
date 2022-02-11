package com.example.learntodrawpvz.ui

import android.os.Bundle
import android.sms.ezviewbinding.viewBinding
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.learntodrawpvz.Constants
import com.example.learntodrawpvz.Pref
import com.example.learntodrawpvz.R
import com.example.learntodrawpvz.Utils
import com.example.learntodrawpvz.ads.AdsManager
import com.example.learntodrawpvz.databinding.TutorialActivityBinding
import com.example.learntodrawpvz.entities.PvZModel
import com.example.learntodrawpvz.extension.getResIdFromName

class TutorialAct : AppCompatActivity() {
    private val binding by viewBinding(TutorialActivityBinding::inflate)
    private lateinit var pvZModel: PvZModel
    private val stepAtThisTime: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pvZModel = Constants.getPvZ(intent.getIntExtra(SelectPlantAct.PVZ_INDEX, 1))

        initView()
        initListener()
        addAds()
    }

    private fun addAds() {
        AdsManager.addBanner(this, binding.adsContainer)
    }

    private fun initView() {
        stepAtThisTime.value = 0
        setUI(pvZModel)
    }

    private fun initListener() {
        binding.btnClose.setOnClickListener {
            tapEffect()
            finish()
        }

        binding.btnPrevious.setOnClickListener {
            tapEffect()
            stepAtThisTime.value = stepAtThisTime.value?.minus(1)
        }

        binding.btnNext.setOnClickListener {
            tapEffect()
            stepAtThisTime.value = stepAtThisTime.value?.plus(1)
        }

        stepAtThisTime.observe(this) {
            if (it == 0) {
                binding.btnPrevious.visibility = View.INVISIBLE
            } else {
                binding.btnPrevious.visibility = View.VISIBLE
            }

            if (it == pvZModel.steps - 1) {
                binding.btnNext.visibility = View.INVISIBLE
            } else {
                binding.btnNext.visibility = View.VISIBLE
            }
            setChange(it)
        }
    }

    private fun setUI(pvZModel: PvZModel) {
        binding.imgPlant.setImageResource(applicationContext.getResIdFromName(pvZModel.avatar))
        binding.tvNamePlant.text = pvZModel.name
        binding.imgDifficulty.setImageResource(applicationContext.getResIdFromName(pvZModel.level))
        binding.imgPlant.setImageResource(applicationContext.getResIdFromName(pvZModel.image[0]))
        binding.tvStep.text =
            applicationContext.getString(R.string.step_format_per, 1, pvZModel.steps)
    }

    private fun setChange(stepAtThisTime: Int) {
        binding.imgPlant.setImageResource(applicationContext.getResIdFromName(pvZModel.image[stepAtThisTime]))
        binding.tvStep.text =
            applicationContext.getString(
                R.string.step_format_per,
                stepAtThisTime + 1,
                pvZModel.steps
            )
    }

    private fun tapEffect() {
        Utils.tapEffect(this, Pref.soundEnabled)
    }
}