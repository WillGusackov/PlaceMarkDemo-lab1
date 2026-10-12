package com.example.assgn1_app.main

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventData
import com.example.assgn1_app.eventsPack.EventItem

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

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        linkInput = findViewById(R.id.linkInput)
        pubDateInput = findViewById(R.id.pubDateInput)
        categoryInput = findViewById(R.id.categoryInput)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveEvent()
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()
        }

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            loadExistingEvent(editingId!!)
        }
    }

    private fun loadExistingEvent(id: Long) {
        val event = EventData.eventsStore.findOne(id)

        if (event == null) {
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
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
