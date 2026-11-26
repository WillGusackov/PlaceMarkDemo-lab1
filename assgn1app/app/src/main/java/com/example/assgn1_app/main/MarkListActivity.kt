package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
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

class MarkListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_list)

        Timber.d("MarkListActivity created")

        // Load XML data if empty
        if (EventData.eventsStore.findAll().isEmpty()) {
            EventParser.loadEventsFromXml(this, EventData.eventsStore)
            Timber.d("Loaded events from XML")
        }

        recyclerView = findViewById(R.id.marksRecyclerView)

        adapter = EventAdapter(
            events = EventData.eventsStore.findAll(),
            listener = object : OnEventListener {
                override fun onEventClick(event: EventItem) {
                    Timber.d("Editing event id=${event.id}, title=${event.title}")
                    editMark(event)
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

        // Search by title
        val searchBar = findViewById<SearchView>(R.id.searchBar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().lowercase()
                val filtered = if (query.isEmpty()) {
                    EventData.eventsStore.findAll()
                } else {
                    EventData.eventsStore.findAll().filter { event ->
                        event.title.lowercase().contains(query)
                    }
                }
                Timber.d("Search query: '$query', results: ${filtered.size}")
                adapter.updateEvents(filtered)
                return true
            }
        })

        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener {
            Timber.d("Returning to MainActivity")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            Timber.d("MarkListActivity resumed - refreshing events list")
            adapter.updateEvents(EventData.eventsStore.findAll())
        }
    }

    private fun editMark(mark: EventItem) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", mark.id)
        startActivity(intent)
    }

    private fun showDeleteConfirmationDialog(mark: EventItem) {
        AlertDialog.Builder(this)
            .setTitle("Delete Mark")
            .setMessage("Are you sure you want to delete '${mark.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                Timber.d("Confirmed deletion of mark id=${mark.id}")
                deleteMark(mark)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                Timber.d("Cancelled deletion of mark id=${mark.id}")
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteMark(mark: EventItem) {
        EventData.eventsStore.delete(mark.id)
        adapter.updateEvents(EventData.eventsStore.findAll())
    }
}
