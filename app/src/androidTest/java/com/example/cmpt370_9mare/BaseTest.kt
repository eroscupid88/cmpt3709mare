package com.example.cmpt370_9mare
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
open class BaseTest {
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
}