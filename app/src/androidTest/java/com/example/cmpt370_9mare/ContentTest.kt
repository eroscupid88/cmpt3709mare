package com.example.cmpt370_9mare


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ContentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun schedule_fragment_content_test() {
        launchFragmentInContainer<CalendarFragment>(themeResId = R.style.Theme)
        onView(withId(R.id.button_last_month)).perform(click())
        onView(withId(R.id.next_month_calendar)).perform(click())
    }
}