package com.example.assgn1_app.eventsPack

interface OnEventListener {
    fun onEventClick(event: EventItem)
    fun onEventLongClick(event: EventItem): Boolean
}
