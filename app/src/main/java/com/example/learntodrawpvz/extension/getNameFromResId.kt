package com.example.learntodrawpvz.extension

import android.content.Context

fun Context.getNameFromResId(id: Int): String = resources.getResourceName(id)

fun Context.getResIdFromName(name: String) = resources.getIdentifier(name, "drawable", packageName)