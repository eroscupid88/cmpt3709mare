package com.example.cmpt370_9mare

import android.view.KeyEvent
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import com.example.cmpt370_9mare.ui.event.CreateEventFragment
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ConflictTest : BaseTest() {
    @Test
    fun TC1_create_event_causes_conflict() {
        val testTitle = "First event"
        val conflictTitle = "Conflict event"

        createEvent(testTitle)
        createEvent(conflictTitle, timeFrom = "06:02")
        onView(withText("Conflict Found")).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.cancel_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        deleteEvent(testTitle)
    }

    @Test
    fun TC2_modify_event_causes_conflict() {
        val testTitle = "First event"
        val conflictTitle = "Conflict event"

        createEvent(testTitle)
        createEvent(conflictTitle, timeFrom = "12:16", timeTo = "14:16")
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_future_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(conflictTitle)).perform(click())
        onView(withId(R.id.inputTimeFrom)).perform(SetButtonText("06:00"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withText("Conflict Found")).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.cancel_create_event)).perform(click())
        deleteEvent(testTitle)
        deleteEvent(conflictTitle)
    }
}