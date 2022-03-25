package com.example.cmpt370_9mare

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DashboardTest : BaseTest() {
    @Test
    fun dashboard_event_list() {
        val testTitle = "Test Dashboard Default List"

        createEvent(testTitle)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).check(matches(isDisplayed()))
        deleteEvent(testTitle)
    }

    @Test
    fun dashboard_future_events() {
        val testTitle = "Test Dashboard Future Events"

        createEvent(testTitle)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_future_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).check(matches(isDisplayed()))
        deleteEvent(testTitle)
    }

    @Test
    fun dashboard_past_events() {
        val testTitle = "Test Dashboard Past Events"

        createEvent(testTitle, PAST)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).check(matches(isDisplayed()))
        deleteEvent(testTitle)
    }

    @Test
    fun dashboard_search_event() {
        val testTitle = "Test Dashboard Search Event"

        createEvent(testTitle)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.search_event)).perform(click())
        onView(withHint("Enter Event Name")).perform(typeText(testTitle))
        onView(withText("SEARCH")).perform(click())
        onView(withText(testTitle)).check(matches(isDisplayed()))
        deleteEvent(testTitle)
    }
}