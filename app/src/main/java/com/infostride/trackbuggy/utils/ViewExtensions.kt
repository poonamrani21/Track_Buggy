package com.infostride.trackbuggy.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.infostride.trackbuggy.R

/****
 * Created by poonam on 7 March 2023
 */


/**
 * Shorthand [makeGone] extension function to make view gone
 */
fun View.makeGone() { this.visibility = View.GONE }

/**
 * Shorthand [makeVisible] extension function to make view visible
 */
fun View.makeVisible() { this.visibility = View.VISIBLE }


/**
 * Shorthand [setFadeAnimation] to show animations on view
 */
fun setFadeAnimation(view:View,context:Activity){
    view.startAnimation( AnimationUtils.loadAnimation(context, R.anim.fade_in))
}
/***
 * Shorthand [showSnackbar] extension to show message with "Snackbar"
 */
fun View.showSnackbar(view: View, msg: String, length: Int, actionMessage: CharSequence?, action: (View) -> Unit) {
    val snackbarObj = Snackbar.make(view, msg, length)
    val view = snackbarObj.view
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    view.layoutParams = params
    view.background = ContextCompat.getDrawable(context, R.drawable.snackbar_border) // for custom background
    snackbarObj.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
    snackbarObj.setActionTextColor(Color.WHITE)

    if (actionMessage != null) {
        snackbarObj.setAction(actionMessage) {action(this) }.show() } else { snackbarObj.show() }
}


/***
 * [showToast] with display the toast
 */
fun Context.showToast(message: String) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }


/***
 * [openPopUpView] will show a pop up window view
 */
fun openPopUpView(context: Activity,buttonText:String,title:String,message:String,icon:Int,dialogInstance:(DialogInterface) ->Unit){
  MaterialAlertDialogBuilder(context, R.style.CutShapeTheme)
        .setTitle(title)
        .setMessage(message)
       .setIcon(icon)
       .setPositiveButton(buttonText) { positiveButton, i -> dialogInstance(positiveButton)
       positiveButton.dismiss()}
        .setCancelable(false)
        .show()}

/***
 * [openPopUpView] will show a pop up window view
 */
fun logoutPopUpView(context: Activity,dialogInstance:(DialogInterface) ->Unit){
  MaterialAlertDialogBuilder(context, R.style.CutShapeTheme)
        .setTitle(R.string.logout)
        .setMessage(R.string.are_you_sure_you_want_logout)
       .setIcon(R.drawable.ic_logout_icon)
       .setPositiveButton(R.string.logout) { positiveButton, i -> dialogInstance(positiveButton)
       positiveButton.dismiss()}
      .setNegativeButton(R.string.cancel){it, i->
          it.dismiss()
      }
        .setCancelable(false)
        .show()}



