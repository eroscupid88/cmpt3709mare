package com.example.cmpt370_9mare

import android.view.KeyEvent
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun fullAppFlow() {
        // Launch the main activity
        launchActivity<MainActivity>()
        // Start with Home Fragment button from bottom menu
//        onView(withId(R.id.navigation_home)).perform(click())
        // Start with calendar Fragment button from bottom menu
//        onView(withId(R.id.navigation_calendar)).perform(click())
        // Start with Dashboard Fragment button from bottom menu
//        onView(withId(R.id.navigation_dashboard)).perform(click())
//        // Start with Notification Fragment button from bottom menu
//        onView(withId(R.id.navigation_notifications)).perform(click())

    }

    fun createEvent(
        title: String,
        date: String = "2024-04-02",
        timeFrom: String = "04:02",
        timeTo: String = "12:16"
    ) {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(ViewActions.typeText(title))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.inputDate)).perform(SetButtonText(date))
        onView(withId(R.id.inputTimeFrom)).perform(SetButtonText(timeFrom))
        onView(withId(R.id.inputTimeTo)).perform(SetButtonText(timeTo))
        onView(withId(R.id.submit_create_event)).perform(click())
    }

    fun deleteEvent(title: String) {
        onView(ViewMatchers.withText(title)).perform(click())
        onView(withId(R.id.layout_create_event_fragment)).perform(ViewActions.swipeUp())
        onView(withId(R.id.delete_event)).perform(click())
    }

    class SetButtonText(private val date: String) : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return CoreMatchers.allOf(
                ViewMatchers.isDisplayed(),
                ViewMatchers.isAssignableFrom(Button::class.java)
            )
        }

        override fun perform(uiController: UiController, view: View) {
            (view as Button).text = date
        }

        override fun getDescription(): String {
            return "Replace button text"
        }
    }

    class ScrollToBottom : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return CoreMatchers.allOf(
                ViewMatchers.isDisplayed(),
                ViewMatchers.isAssignableFrom(RecyclerView::class.java)
            )
        }

        override fun perform(uiController: UiController?, view: View?) {
            val recyclerView = view as RecyclerView
            val itemCount = recyclerView.adapter?.itemCount
            val position = itemCount?.minus(1) ?: 0
            recyclerView.scrollToPosition(position)
            uiController?.loopMainThreadUntilIdle()
        }

        override fun getDescription(): String {
            return "Scroll RecyclerView to bottom"
        }
    }
}