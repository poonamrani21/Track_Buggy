package com.infostride.trackbuggy.domain.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.ui.HolderActivity
import com.infostride.trackbuggy.utils.kotlinFileName
import org.json.JSONObject


class FirebaseNotification :FirebaseMessagingService(){

     val CHANNEL_ID = "1"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(kotlinFileName, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(kotlinFileName, token)
          })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(kotlinFileName, "onMessageReceived: $remoteMessage")
        val params: Map<String?, String?> = remoteMessage.data
        val jsonObj = JSONObject(params)
        val custom_data=  jsonObj.getString("custom_data")
       val type= JSONObject(custom_data).getString("type")
       val message= JSONObject(custom_data).getString("message")
       val title= JSONObject(custom_data).getString("title")
        sendMessage(message,title,type)
    }

    private fun sendMessage(message: String, title: String, type: String) {
        val notifyIntent = Intent(this, HolderActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }

        /**
         * Create and show a simple notification containing the received FCM message.
         *
         * @param message FCM message body received.
         */
        //High priority push IMPORTANCE_HIGH
            val intent = Intent(this, HolderActivity::class.java)
        intent.putExtra("type","1")

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_IMMUTABLE)
        var alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mp: MediaPlayer = MediaPlayer.create(applicationContext, alarmSound)
        mp.isLooping=true
       //  mp.start()
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(getColor(R.color.purple_700))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
             .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }


    }