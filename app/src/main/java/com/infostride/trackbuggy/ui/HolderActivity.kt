package com.infostride.trackbuggy.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.messaging.FirebaseMessaging
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.domain.socket.SocketHandler
import com.infostride.trackbuggy.utils.Helper
import com.infostride.trackbuggy.utils.kotlinFileName
import com.infostride.trackbuggy.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolderActivity : AppCompatActivity() {
    companion object{
        var device_token=""
        var userLatitude=""
        var userLongitude=""
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
       val data= intent?.getStringExtra("type")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)
        val data= intent?.getStringExtra("type")

        if (!Helper.isLocationEnabledOrNot(this)) { Helper.showAlertLocation(this, getString(R.string.gps_enable), getString(R.string.please_turn_on_gps),getString(R.string.ok)) }
            requestPermissionsSafely(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), 200)
            // The following lines connects the Android app to the server.
            SocketHandler.setSocket()
            SocketHandler.establishConnection()
            //Method call to getFirebaseToken
        getFirebaseToken()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
           showToast("Permission not granted")
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    userLatitude = location.latitude.toString()
                    userLongitude = location.longitude.toString()
                }

            }/*val mp: MediaPlayer = MediaPlayer.create(this, com.infostride.trackbuggy.R.raw.soho)
        mp.start()*/
    }
    /**
     *Method to get Firebase Token
     */
    private fun getFirebaseToken(): String {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(kotlinFileName, getString(R.string.fcm_failed), task.exception)
                return@OnCompleteListener
            }
            device_token = task.result
        })
        return device_token
    }
    //Method to request Location Permission
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String>,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }


}