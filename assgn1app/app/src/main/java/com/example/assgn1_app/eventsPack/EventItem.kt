package com.example.assgn1_app.eventsPack

data class EventItem(
    var id: Long = 0L,
    var title: String = "",
    var link: String = "",
    var guid: String = "",
    var pubDate: String = "",
    var description: String = "",
    var category: String = ""
)
