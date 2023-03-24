package com.infostride.trackbuggy.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

/***
 * Created by @author Poonam Rani by 23 Jan 2023
 */


/**
 * [launchActivity] Extension for smarter launching of Activities
 */
inline fun <reified T : Activity> Context.launchActivity(
    noinline modify: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.modify()
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}
/**
 * [kotlinFileName] Extension to get the file name
 */
inline val <T : Any> T.kotlinFileName: String
get() = javaClass.simpleName + ".kt"

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}



