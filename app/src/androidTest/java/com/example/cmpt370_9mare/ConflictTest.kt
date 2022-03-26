package com.example.cmpt370_9mare

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ConflictTest : BaseTest() {
    @Test
    fun TC1_create_event_causes_conflict() {
        val conflictDate = "${(2023..9999).random()}-04-02"
        val testTitle = "TC1 First event"
        val conflictTitle = "TC1 Conflict event"

        createEvent(testTitle, conflictDate)
        createEvent(conflictTitle, conflictDate, true, "06:02")
        onView(withText("Conflict Found")).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.cancel_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        deleteEvent(testTitle)
    }

    @Test
    fun TC2_modify_event_causes_conflict() {
        val conflictDate = "${(2023..9999).random()}-04-02"
        val testTitle = "TC2 First event"
        val conflictTitle = "TC2 Conflict event"

        createEvent(testTitle, conflictDate)
        createEvent(conflictTitle, conflictDate, false, "12:16", "14:16")
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_future_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(conflictTitle)).perform(click())
        onView(withText(R.string.edit)).perform(click())
        onView(withId(R.id.inputTimeFrom)).perform(SetButtonText("06:00"))
        onView(withId(R.id.conflict_check)).perform(click())
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withText("Conflict Found")).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.cancel_create_event)).perform(click())
        deleteEvent(testTitle)
        deleteEvent(conflictTitle)
    }

    @Test
    fun TC3_delete_event_then_create_event_causes_no_conflict() {
        val testTitle = "TC3 Test event"

        createEvent(testTitle)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        deleteEvent(testTitle)
        createEvent(testTitle, conflictCheck = true)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_future_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).check(matches(isDisplayed()))
        deleteEvent(testTitle)
    }

    @Test
    fun TC4_conflicting_events_show_in_dialog() {
        val conflictDate = "${(2023..9999).random()}-04-02"
        val testTitle = "TC4 First event"
        val conflictTitle = "TC4 Conflict event"

        createEvent(testTitle, conflictDate)
        createEvent(conflictTitle, conflictDate, true, "06:02")
        onView(withText("Conflict Found")).check(matches(isDisplayed()))
        onView(withText("TC4 First event: 04:02 - 12:16\n")).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.cancel_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        deleteEvent(testTitle)
    }

    @Test
    fun TC5_multiple_conflicting_events() {
        val conflictDate = "${(2023..9999).random()}-04-02"
        val testTitle1 = "TC5 First event"
        val testTitle2 = "TC5 Second event"
        val conflictTitle = "TC5 Conflict event"

        createEvent(testTitle1, conflictDate)
        createEvent(testTitle2, conflictDate, false, "12:45", "13:45")
        createEvent(conflictTitle, conflictDate, true, "06:02", "13:00")
        onView(withText("Conflict Found")).check(matches(isDisplayed()))
        onView(withText("TC5 First event: 04:02 - 12:16\nTC5 Second event: 12:45 - 13:45\n"))
            .check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.cancel_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        deleteEvent(testTitle1)
        deleteEvent(testTitle2)
    }

    @Test
    fun TC6_conflict_timing_not_exclusive() {
        val conflictDate = "${(2023..9999).random()}-04-02"
        val testTitle1 = "TC6 First event"
        val testTitle2 = "TC6 Second event"

        createEvent(testTitle1, conflictDate)
        createEvent(testTitle2, conflictDate, true, "12:16", "13:16")
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        deleteEvent(testTitle1)
        deleteEvent(testTitle2)
    }
}