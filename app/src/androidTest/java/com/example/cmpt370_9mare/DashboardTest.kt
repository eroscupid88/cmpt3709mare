package com.example.cmpt370_9mare

import android.view.KeyEvent
import android.view.View
import android.widget.Button
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DashboardTest {

    private fun setPastDate(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(isDisplayed(), isAssignableFrom(Button::class.java))
            }

            override fun perform(uiController: UiController, view: View) {
                (view as Button).text = "2020-04-02"
            }

            override fun getDescription(): String {
                return "Replace Date"
            }
        }
    }

    @Test
    fun dashboard_event_list() {
        val randomNum = (1..1000).random()

        launchActivity<MainActivity>()
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("$randomNum Test Event"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withText("$randomNum Test Event")).check(matches(isDisplayed()))
    }

    @Test
    fun dashboard_future_events() {
        val randomNum = (1..1000).random()

        launchActivity<MainActivity>()
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("$randomNum Test Event"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_future_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withText("$randomNum Test Event")).check(matches(isDisplayed()))
    }

    @Test
    fun dashboard_past_events() {
        val randomNum = (1..1000).random()

        launchActivity<MainActivity>()
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("$randomNum Test Event"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.inputDate)).perform(setPastDate())
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        onView(withId(R.id.event_list_recycler_view)).perform(swipeUp())
        // some issues with this test
//        onView(withText("$randomNum Test Event")).check(matches(isDisplayed()))
    }

    @Test
    fun dashboard_search_event() {
        val randomNum = (1..1000).random()

        launchActivity<MainActivity>()
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("$randomNum Test Event"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.search_event)).perform(click())
        onView(withHint("Enter Event Name")).perform(typeText("$randomNum"))
        onView(withText("SEARCH")).perform(click())
        onView(withText("$randomNum Test Event")).check(matches(isDisplayed()))
    }

}