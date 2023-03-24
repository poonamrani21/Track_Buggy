package com.infostride.trackbuggy.ui.track_device

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.databinding.ActivityMapBinding
import com.infostride.trackbuggy.domain.socket.SocketHandler
import com.infostride.trackbuggy.utils.showToast
import io.socket.client.Socket
import org.json.JSONObject


class MapActivity : AppCompatActivity(), OnMapReadyCallback {


    private  lateinit var mSocket : Socket
   private lateinit var binding:ActivityMapBinding
   private lateinit var mActivity: Activity
   private var userLatitude:Double = 30.3398
    private var userLongitude:Double = 76.3869
   private var deviceLatitude:Double = 30.7085928
   private var deviceLongitude:Double = 76.7021209
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mActivity = this@MapActivity
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        mSocket = SocketHandler.getSocket()
        mSocket.on("message") { args ->
            Log.d("deviceLatitude:", "onCreate: $args")
            if (args[0] != null) {
                val jsonObj = args[0] as JSONObject
                runOnUiThread {
                      deviceLatitude=jsonObj.getString("deviceLatitude").toDouble()
                      deviceLongitude=jsonObj.getString("deviceLongitude").toDouble()
                     showToast(jsonObj.toString())
                }
            }
        }
    }

    override fun onMapReady(gmap: GoogleMap) {
        map=gmap
        val latLng = LatLng(userLatitude, userLongitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Lat:" + latLng.latitude + "Long:" + latLng.longitude)

       /* markerOptions.apply {
            setIcon(BitmapDescriptorFactory.fromResource(R.drawable.test_cp_ac))
        }*/
       // map.addMarker(markerOptions)
        drawMarker(LatLng(userLatitude,userLongitude),LatLng(deviceLatitude,deviceLongitude))


        with(map) {
            // A geodesic polyline that goes around the world.
            addPolyline(PolylineOptions().apply {
                add(LatLng(userLatitude,userLongitude), LatLng(deviceLatitude,deviceLongitude))
                width(5.toFloat())
                color(Color.BLUE)
                geodesic(true)
            })

            // Move the googleMap so that it is centered on the mutable polyline.
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(userLatitude,userLongitude), 3f))

            // Add a listener for polyline clicks that changes the clicked polyline's color.
            setOnPolylineClickListener { polyline ->
                // Flip the values of the red, green and blue components of the polyline's color.
                polyline.color = polyline.color xor 0x00ffffff
            }
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
    }
    fun drawMarker(userLocation: LatLng, deviceLocation: LatLng) {

        // Creating an instance of MarkerOptions
        val markerOptions1 = MarkerOptions()
        markerOptions1.title("You")
       // markerOptions1.snippet("Marker Yo Yo")
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOptions1.position(userLocation!!)
        val markerOptions2 = MarkerOptions()
        markerOptions2.title("Device")
       // markerOptions2.snippet("Marker Xo Xo")
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOptions2.position(deviceLocation)

        // Adding marker on the Google Map
        map.addMarker(markerOptions1)
        map.addMarker(markerOptions2)
    }
}