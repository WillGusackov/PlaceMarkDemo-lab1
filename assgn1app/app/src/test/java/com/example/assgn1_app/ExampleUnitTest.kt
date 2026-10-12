package com.example.assgn1_app

import com.example.assgn1_app.placemark.PlacedMark
import com.example.assgn1_app.placemark.PlacemarkMemStore
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private lateinit var store: PlacemarkMemStore

    @Before
    fun setup() {
        store = PlacemarkMemStore()
    }

    @Test
    fun testCreateAndFindMark() {
        val mark = PlacedMark(title = "Campus", desc = "Main library", x = 53.3498, y = -6.2603)
        store.create(mark)

        val marks = store.findAll()
        assertEquals(1, marks.size)
        assertEquals("Campus", marks[0].title)
    }

    @Test
    fun testUpdateMark() {
        val mark = PlacedMark(title = "Park", desc = "Old desc", x = 1.0, y = 2.0)
        store.create(mark)

        val updatedMark = PlacedMark(id = mark.id, title = "Park Updated", desc = "New desc", x = 1.5, y = 2.5)
        val success = store.update(updatedMark)

        assertTrue(success)
        val found = store.findOne(mark.id)
        assertNotNull(found)
        assertEquals("Park Updated", found?.title)
    }

    @Test
    fun testDeleteMark() {
        val mark = PlacedMark(title = "Lake", desc = "Lake view", x = 5.0, y = 10.0)
        store.create(mark)

        val success = store.delete(mark.id)
        assertTrue(success)
        assertEquals(0, store.findAll().size)
    }
}