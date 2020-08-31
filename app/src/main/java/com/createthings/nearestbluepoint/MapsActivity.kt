package com.createthings.nearestbluepoint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var listLocation = mutableListOf<LocationModel>()
    private lateinit var circleRadius: CircleOptions
    private val zoomLevel = 16.0f
    private val mainPoint = LatLng(-6.9025, 107.6188)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        prepareData()
        prepareUI()
    }

    private fun prepareUI() {
        circleRadius = CircleOptions()
            .center(mainPoint).radius(400.0)
            .strokeColor(Color.parseColor("#A3F60000"))
            .fillColor(Color.parseColor("#4BF60000"))
            .strokeWidth(6f)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun prepareData(): List<LocationModel> {
        listLocation.add(
            LocationModel(
                title = "Blue Point A",
                latitude = -6.9008,
                longitude = 107.6160
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point B",
                latitude = -6.90010,
                longitude = 107.6150
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point C",
                latitude = -6.9060,
                longitude = 107.6150
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point D",
                latitude = -6.90020,
                longitude = 107.6170
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point E",
                latitude = -6.90025,
                longitude = 107.6210
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point F",
                latitude = -6.90000,
                longitude = 107.6228
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point G",
                latitude = -6.9040,
                longitude = 107.6220
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point H",
                latitude = -6.9050,
                longitude = 107.6180
            )
        )
        listLocation.add(
            LocationModel(
                title = "Blue Point I",
                latitude = -6.9040,
                longitude = 107.6158
            )
        )
        return listLocation
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this, R.raw.maps_style
            )
        )
        mMap.addMarker(
            MarkerOptions()
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_main_point))
                .position(mainPoint)
                .title("Gedung Sate")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mainPoint, zoomLevel))
        mMap.addCircle(circleRadius)
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isScrollGesturesEnabled = false
        mMap.uiSettings.isTiltGesturesEnabled = false

        initiateAndCheckingBluePoint()
    }

    private fun initiateAndCheckingBluePoint() {
        listLocation.forEach {
            mMap.addMarker(
                MarkerOptions()
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_blue_point))
                    .position(LatLng(it.latitude, it.longitude))
                    .title(it.title)
            )
            val distance = FloatArray(2)
            Location.distanceBetween(
                it.latitude, it.longitude,
                circleRadius.center.latitude, circleRadius.center.longitude, distance
            )
            if (distance[0] <= circleRadius.radius) {
                Log.d("Location", "${it.title} you are INSIDE the radius")
            } else {
                Log.d("Location", "${it.title} you are OUTSIDE the radius")
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val width = 100
        val height = 100
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, width, height)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}