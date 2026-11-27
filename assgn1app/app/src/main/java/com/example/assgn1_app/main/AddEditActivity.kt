package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventData
import com.example.assgn1_app.eventsPack.EventItem
import com.example.assgn1_app.maps.MapsActivity
import timber.log.Timber

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var linkInput: EditText
    private lateinit var pubDateInput: EditText
    private lateinit var categoryInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_activity)

        Timber.d("AddEditActivity created")

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        linkInput = findViewById(R.id.linkInput)
        pubDateInput = findViewById(R.id.pubDateInput)
        categoryInput = findViewById(R.id.categoryInput)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveEvent()
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            Timber.d("Cancelled AddEditActivity")
            finish()
        }

        val mapPicker =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data ?: return@registerForActivityResult
                    val selectedLatitude = data.getDoubleExtra("latitude", 0.0)
                    val selectedLongitude = data.getDoubleExtra("longitude", 0.0)
                    Timber.d("Selected location from map: lat=$selectedLatitude, lng=$selectedLongitude")
                    Toast.makeText(this, "Location selected: $selectedLatitude, $selectedLongitude", Toast.LENGTH_SHORT).show()
                }
            }

        findViewById<Button>(R.id.buttonLoc).setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            mapPicker.launch(intent)
        }

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            Timber.d("Loading existing event with id=$editingId")
            loadExistingEvent(editingId!!)
        }
    }

    private fun loadExistingEvent(id: Long) {
        val event = EventData.eventsStore.findOne(id)

        if (event == null) {
            Timber.w("Event with id=$id not found")
            Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        titleInput.setText(event.title)
        descriptionInput.setText(event.description)
        linkInput.setText(event.link)
        pubDateInput.setText(event.pubDate)
        categoryInput.setText(event.category)
    }

    private fun saveEvent() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val link = linkInput.text.toString().trim()
        val pubDate = pubDateInput.text.toString().trim()
        val category = categoryInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            Timber.w("Save attempted with empty title")
            return
        }

        if (editingId == null || editingId == -1L) {
            val event = EventItem(
                title = title,
                description = description,
                link = link,
                pubDate = pubDate,
                category = category
            )
            EventData.eventsStore.create(event)
            Timber.d("Created new event id=${event.id}, title=$title")
            Toast.makeText(this, "Event created", Toast.LENGTH_SHORT).show()
        } else {
            val event = EventItem(
                id = editingId!!,
                title = title,
                description = description,
                link = link,
                pubDate = pubDate,
                category = category
            )
            EventData.eventsStore.update(event)
            Timber.d("Updated event id=${event.id}, title=$title")
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
