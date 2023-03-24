package com.infostride.trackbuggy.domain.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.domain.receiver.RestartBackgroundService
import com.infostride.trackbuggy.domain.socket.SocketHandler
import com.infostride.trackbuggy.ui.HolderActivity
import com.infostride.trackbuggy.utils.Constants
import com.infostride.trackbuggy.utils.Constants.SOCKET_LISTENER
import com.infostride.trackbuggy.utils.Constants.STARTFOREGROUND_ACTION
import com.infostride.trackbuggy.utils.Constants.deviceLatitude
import com.infostride.trackbuggy.utils.Constants.deviceLongitude
import com.infostride.trackbuggy.utils.Constants.deviceToken
import com.infostride.trackbuggy.utils.Constants.userId
import com.infostride.trackbuggy.utils.SharedPreference
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val CHANNEL = "Events App"

@AndroidEntryPoint
class LocationService : Service() {
    private var counter = 0
    private  var latitude: Double = 0.0
    private  var longitude: Double = 0.0
    private  lateinit var mSocket : Socket
    private val receiver = RestartBackgroundService()

    @Inject
    lateinit var prefManager: SharedPreference
    lateinit var userLoginData:LoginResponse.Data

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        userLoginData = prefManager.getUserData()!!

        createChannel()
        startService()
        requestLocationUpdates()
        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        mSocket= SocketHandler.getSocket()
        mSocket.on(SOCKET_LISTENER){args ->
            Log.d("args:", "onCreate: $args")

            if (args[0] != null) {
                val counter = args[0]
                //  runOnUiThread { showToast(counter.toString()) }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*return if (intent?.extras?.get("STOP") != "STOP") {
         val events = intent?.extras?.getStringArrayList("enabledActions")
            registerReceiver(receiver, IntentFilter().apply {
                for (i in events?.indices!!) {
                    addAction(events[i])
                }
            })
            START_NOT_STICKY
        } else {
            stopSelf()
            START_NOT_STICKY
        }*/
        super.onStartCommand(intent, flags, startId)
        if (intent != null) {
            val action = intent.action
            when (action) {
                STARTFOREGROUND_ACTION -> ""
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, RestartBackgroundService::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onDestroy()
    }


    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL,
                    CHANNEL,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            channel.enableVibration(false)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startService() {
        val notifyIntent = Intent(this, HolderActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }

        val notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent,
            flags)

        val notification = NotificationCompat
            .Builder(this, CHANNEL)
            .setContentIntent(notifyPendingIntent)
            .setSilent(true)
            .setSmallIcon(com.infostride.trackbuggy.R.drawable.app_icon_blue)
            .setContentTitle(resources.getString(com.infostride.trackbuggy.R.string.app_name))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(createNotificationLayout())
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationLayout(): RemoteViews {
        val view = RemoteViews(packageName, com.infostride.trackbuggy.R.layout.remote_view)
        view.setOnClickPendingIntent(com.infostride.trackbuggy.R.id.remoteButtonClose, createPendingIntent())
        return view
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, LocationService::class.java)
        intent.putExtra("STOP", "STOP")
        return PendingIntent.getService(this, 1, intent, flags)
    }

    private fun requestLocationUpdates() {
        var request = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(10)
            fastestInterval = TimeUnit.SECONDS.toMillis(5)
            maxWaitTime = TimeUnit.MINUTES.toMillis(1)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                        Log.d("Location Service", "location update ${location.latitude }")
                            if (mSocket.isActive)
                            {
                                val json = JSONObject()
                                try {
                                    json.put(deviceToken, HolderActivity.device_token)
                                    json.put(deviceLatitude,latitude.toString())
                                    json.put(deviceLongitude,longitude.toString())
                                    json.put(userId,userLoginData.id)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                                mSocket.emit(Constants.SOCKET_EMITTER,json)
                                Log.d("onLocationResult", " $json")
}
                    }
                }
            }, null)
        }
    }
    private val flags = when{
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            PendingIntent.FLAG_MUTABLE
        else -> {PendingIntent.FLAG_MUTABLE}
    }
}