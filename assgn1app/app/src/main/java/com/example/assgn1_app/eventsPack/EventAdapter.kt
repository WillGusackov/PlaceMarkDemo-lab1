package com.example.assgn1_app.eventsPack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assgn1_app.R

class EventAdapter(
    private var events: List<EventItem>,
    private val onEdit: (EventItem) -> Unit,
    private val onDelete: (EventItem) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
        val coordinatesText: TextView = itemView.findViewById(R.id.coordinatesText)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_placed_mark, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleText.text = event.title
        holder.descriptionText.text = event.description
        holder.coordinatesText.text = event.pubDate

        holder.editButton.setOnClickListener {
            onEdit(event)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(event)
        }
    }

    override fun getItemCount(): Int = events.size

    fun updateEvents(newEvents: List<EventItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
