package com.example.assgn1_app.eventsPack

import android.content.Context
import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

object EventParser {
    fun loadEventsFromXml(context: Context, store: EventMemStore) {
        val inputStream: InputStream = context.assets.open("tech-events.xml")
        val parser: XmlPullParser = Xml.newPullParser()
        parser.setInput(inputStream, null)

        var eventType = parser.eventType
        var title = ""
        var link = ""
        var guid = ""
        var pubDate = ""
        var description = ""
        var category = ""
        var currentTag = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    currentTag = parser.name
                }
                XmlPullParser.TEXT -> {
                    val text = parser.text?.trim() ?: ""
                    if (text.isNotEmpty()) {
                        when (currentTag) {
                            "title" -> title = text
                            "link" -> link = text
                            "guid" -> guid = text
                            "pubDate" -> pubDate = text
                            "description" -> description = text
                            "category" -> if (category.isEmpty()) category = text else category += ", $text"
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "item") {
                        val event = EventItem(
                            title = title,
                            link = link,
                            guid = guid,
                            pubDate = pubDate,
                            description = description,
                            category = category
                        )
                        store.create(event)

                        // Reset fields for the next item
                        title = ""
                        link = ""
                        guid = ""
                        pubDate = ""
                        description = ""
                        category = ""
                    }
                }
            }
            eventType = parser.next()
        }
        inputStream.close()
    }
}