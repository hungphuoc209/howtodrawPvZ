package com.example.learntodrawpvz

import com.chibatching.kotpref.KotprefModel

object Pref : KotprefModel() {
    var soundEnabled by booleanPref(true)
}