package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventAdapter
import com.example.assgn1_app.eventsPack.EventData
import com.example.assgn1_app.eventsPack.EventItem
import com.example.assgn1_app.eventsPack.EventParser
import com.example.assgn1_app.eventsPack.OnEventListener
import timber.log.Timber

class EventListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_list_activity)

        Timber.d("EventListActivity created")

        // Load events from XML into memory store if empty
        if (EventData.eventsStore.findAll().isEmpty()) {
            EventParser.loadEventsFromXml(this, EventData.eventsStore)
            Timber.d("Loaded events from XML in EventListActivity")
        }

        recyclerView = findViewById(R.id.eventRecyclerView)

        adapter = EventAdapter(
            events = EventData.eventsStore.findAll(),
            listener = object : OnEventListener {
                override fun onEventClick(event: EventItem) {
                    Timber.d("Editing event id=${event.id}, title=${event.title}")
                    val intent = Intent(this@EventListActivity, AddEditActivity::class.java)
                    intent.putExtra("id", event.id)
                    startActivity(intent)
                }

                override fun onEventLongClick(event: EventItem): Boolean {
                    Timber.d("Long pressed event id=${event.id}, title=${event.title}")
                    showDeleteConfirmationDialog(event)
                    return true
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.returnButton).setOnClickListener {
            Timber.d("EventListActivity finished by return button")
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            Timber.d("EventListActivity resumed - refreshing events list")
            adapter.updateEvents(EventData.eventsStore.findAll())
        }
    }

    private fun showDeleteConfirmationDialog(event: EventItem) {
        AlertDialog.Builder(this)
            .setTitle("Delete Event")
            .setMessage("Are you sure you want to delete '${event.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                Timber.d("Confirmed deletion of event id=${event.id}")
                EventData.eventsStore.delete(event.id)
                adapter.updateEvents(EventData.eventsStore.findAll())
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                Timber.d("Cancelled deletion of event id=${event.id}")
                dialog.dismiss()
            }
            .show()
    }
}
