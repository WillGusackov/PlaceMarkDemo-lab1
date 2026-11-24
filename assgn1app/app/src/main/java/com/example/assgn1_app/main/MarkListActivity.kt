package com.example.assgn1_app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assgn1_app.R
import com.example.assgn1_app.eventsPack.EventAdapter
import com.example.assgn1_app.eventsPack.EventData
import com.example.assgn1_app.eventsPack.EventItem
import com.example.assgn1_app.eventsPack.EventParser


class MarkListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mark_list)

        // Load XML data if empty
        if (EventData.eventsStore.findAll().isEmpty()) {
            EventParser.loadEventsFromXml(this, EventData.eventsStore)
        }

        recyclerView =
            findViewById(R.id.marksRecyclerView)

        adapter = EventAdapter(
            events = EventData.eventsStore.findAll(),

            onEdit = { event ->
                editMark(event)
            },

            onDelete = { event ->
                deleteMark(event)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter
//search by title here for extra marks :>
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
                adapter.updateEvents(filtered)
                return true
            }
        })

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {

            adapter.updateEvents(
                EventData.eventsStore.findAll()
            )
        }
    }

    private fun editMark(mark: EventItem) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", mark.id)
        startActivity(intent)
    }

    private fun deleteMark(mark: EventItem) {
        EventData.eventsStore.delete(mark.id)
        adapter.updateEvents(
            EventData.eventsStore.findAll()
        )
    }
}
