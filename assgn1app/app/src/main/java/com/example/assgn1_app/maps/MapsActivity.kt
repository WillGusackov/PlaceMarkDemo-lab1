package com.example.assgn1_app.maps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var selectedMarker: Marker? = null
    private var selectedLatLng: LatLng = LatLng(53.3498, -6.2603)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_map)

        val lat = intent.getDoubleExtra("latitude", 53.3498)
        val lon = intent.getDoubleExtra("longitude", -6.2603)
        selectedLatLng = LatLng(lat, lon)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<Button>(R.id.confirmLocationButton).setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("latitude", selectedLatLng.latitude)
                putExtra("longitude", selectedLatLng.longitude)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Timber.d("Google Map is ready")

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f))
        updateSelectedMarker(selectedLatLng)

        mMap.setOnMapClickListener { latLng ->
            updateSelectedMarker(latLng)
        }

        showExistingMarkers()
    }

    private fun updateSelectedMarker(latLng: LatLng) {
        selectedLatLng = latLng
        selectedMarker?.remove()
        selectedMarker = mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Selected Location")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    private fun showExistingMarkers() {
        val events = EventData.eventsStore.findAll()
        events.forEach { event ->
            val point = LatLng(53.3498 + (event.id * 0.001), -6.2603 + (event.id * 0.001))
            mMap.addMarker(
                MarkerOptions()
                    .position(point)
                    .title(event.title)
                    .snippet(event.description)
            )
        }
    }
}
