package com.example.assgn1_app.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventAdapter
import com.example.assgn1_app.eventsPack.EventData
import com.example.assgn1_app.eventsPack.EventParser

class EventListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_list_activity)

        // Load events from XML into memory store if empty
        if (EventData.eventsStore.findAll().isEmpty()) {
            EventParser.loadEventsFromXml(this, EventData.eventsStore)
        }

        recyclerView = findViewById(R.id.eventRecyclerView)

        adapter = EventAdapter(
            events = EventData.eventsStore.findAll(),
            onEdit = { event ->
                // Handle edit click
            },
            onDelete = { event ->
                EventData.eventsStore.delete(event.id)
                adapter.updateEvents(EventData.eventsStore.findAll())
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.returnButton).setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.updateEvents(EventData.eventsStore.findAll())
        }
    }
}
