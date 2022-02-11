package com.example.learntodrawpvz

import android.content.Context
import android.media.MediaPlayer

class Utils {

    companion object {
        fun tapEffect(context: Context, sound: Boolean) {
            if (sound) {
                val tapEffect = MediaPlayer.create(context, R.raw.tap)
                tapEffect.start()
            }
        }
    }
}