package com.example.assgn1_app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assgn1_app.main.AddEditActivity
import com.example.assgn1_app.placemark.PlacedMark

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.assgn1_app", appContext.packageName)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMark() {
        val mark = PlacedMark(
            title = "Test Title",
            desc = "Test Description",
            x = 1.0,
            y = 2.0
        )
    }

    @Test
    fun testAddEditLayout() {
        ActivityScenario.launch(AddEditActivity::class.java)
        onView(withHint("Title")).check(matches(isDisplayed()))
        onView(withHint("Description")).check(matches(isDisplayed()))
        onView(withText("Save")).check(matches(isDisplayed()))
        onView(withText("Cancel")).check(matches(isDisplayed()))
    }
}