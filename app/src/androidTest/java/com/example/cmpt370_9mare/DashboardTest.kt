package com.example.cmpt370_9mare

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import com.example.cmpt370_9mare.ui.dashboard.DashboardFragment
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DashboardTest {
    private val randomNum = (1..1000).random()

    @Test
    fun dashboard_event_list() {
        launchActivity<MainActivity>()
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("Test Event $randomNum"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withText("Test Event $randomNum")).check(matches(isDisplayed()))
    }
}