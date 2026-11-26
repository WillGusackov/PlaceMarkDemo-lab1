package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventData
import com.example.assgn1_app.maps.MapsActivity
import timber.log.Timber

class EventDetailActivity : AppCompatActivity() {

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_item)

        val eventId = intent.getLongExtra("id", -1L)
        Timber.d("EventDetailActivity started for event id=$eventId")

        val event = EventData.eventsStore.findOne(eventId)
        if (event == null) {
            Timber.e("Event with id $eventId not found")
            Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val titleText = findViewById<TextView>(R.id.titleText)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)
        val pubDateText = findViewById<TextView>(R.id.pubDateText)
        val coordinatesText = findViewById<TextView>(R.id.coordinatesText)
        val linkText = findViewById<TextView>(R.id.linkText)

        titleText.text = event.title
        categoryText.text = if (event.category.isNotEmpty()) "Category: ${event.category}" else "Category: General"
        descriptionText.text = if (event.description.isNotEmpty()) event.description else "No description available."
        pubDateText.text = if (event.pubDate.isNotEmpty()) event.pubDate else "N/A"
        coordinatesText.text = "Event ID: ${event.id}"
        linkText.text = if (event.link.isNotEmpty()) event.link else "N/A"

        if (event.link.isNotEmpty()) {
            linkText.setOnClickListener {
                try {
                    val openUrlIntent = Intent(Intent.ACTION_VIEW, event.link.toUri())
                    startActivity(openUrlIntent)
                } catch (e: Exception) {
                    Timber.e(e, "Error opening link: ${event.link}")
                }
            }
        }

        val showOnMapButton = findViewById<Button>(R.id.showOnMapButton)
        val favoriteButton = findViewById<Button>(R.id.favoriteButton)
        val addToCalendarButton = findViewById<Button>(R.id.addToCalendarButton)
        val backButton = findViewById<Button>(R.id.backButton)

        showOnMapButton.setOnClickListener {
            Timber.d("Show on Map clicked for event id=${event.id}")
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("title", event.title)
            startActivity(intent)
        }

        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            val message = if (isFavorite) "Added '${event.title}' to Favorites" else "Removed '${event.title}' from Favorites"
            Timber.d("Favorite toggled: $message")
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        addToCalendarButton.setOnClickListener {
            Timber.d("Add to Calendar clicked for event title='${event.title}'")
            val calendarIntent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, event.title)
                putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            }
            try {
                startActivity(calendarIntent)
            } catch (e: Exception) {
                Timber.e(e, "Failed to launch calendar intent")
                Toast.makeText(this, "No calendar app found", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            Timber.d("Back button clicked in EventDetailActivity")
            finish()
        }
    }
}
