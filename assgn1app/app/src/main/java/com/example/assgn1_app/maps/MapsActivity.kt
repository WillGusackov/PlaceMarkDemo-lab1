package com.example.assgn1_app.maps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventData
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private var selectedPoint: GeoPoint? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.show_map)

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        val lat = intent.getDoubleExtra("latitude", 53.3498)
        val lon = intent.getDoubleExtra("longitude", -6.2603)
        val start = GeoPoint(lat, lon)

        map.controller.setZoom(18.0)
        map.controller.setCenter(start)
        setSelectedPoint(start)
        showExistingMarkers()

        val receiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(point: GeoPoint): Boolean {
                setSelectedPoint(point)
                return true
            }

            override fun longPressHelper(point: GeoPoint): Boolean {
                setSelectedPoint(point)
                return true
            }
        }
        map.overlays.add(MapEventsOverlay(receiver))

        findViewById<Button>(R.id.confirmLocationButton).setOnClickListener {
            val point = selectedPoint ?: return@setOnClickListener
            val resultIntent = Intent().apply {
                putExtra("latitude", point.latitude)
                putExtra("longitude", point.longitude)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun setSelectedPoint(point: GeoPoint) {
        selectedPoint = point

        if (marker == null) {
            marker = Marker(map).apply {
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            map.overlays.add(marker)
        }

        marker?.position = point
        marker?.title = "Selected location"
        map.invalidate()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        map.onPause()
        super.onPause()
    }

    private fun showExistingMarkers() {
        val events = EventData.eventsStore.findAll()

        events.forEach { event ->
            val point = GeoPoint(53.3498 + (event.id * 0.001), -6.2603 + (event.id * 0.001))

            val m = Marker(map).apply {
                position = point
                title = event.title
                snippet = event.description
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }

            map.overlays.add(m)
        }

        map.invalidate()
    }
}
