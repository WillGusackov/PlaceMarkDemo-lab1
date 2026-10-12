package com.example.assgn1_app.eventsPack

import java.util.concurrent.atomic.AtomicLong

class EventMemStore {
    private val events = ArrayList<EventItem>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<EventItem> {
        return events
    }

    fun create(event: EventItem) {
        event.id = lastId.incrementAndGet()
        events.add(event)
    }

    fun update(event: EventItem): Boolean {
        val foundEvent = findOne(event.id)
        return if (foundEvent != null) {
            foundEvent.title = event.title
            foundEvent.link = event.link
            foundEvent.guid = event.guid
            foundEvent.pubDate = event.pubDate
            foundEvent.description = event.description
            foundEvent.category = event.category
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundEvent = findOne(id)
        return if (foundEvent != null) {
            events.remove(foundEvent)
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): EventItem? {
        return events.find { e -> e.id == id }
    }
}