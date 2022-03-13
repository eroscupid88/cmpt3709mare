package com.example.cmpt370_9mare


import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import com.example.cmpt370_9mare.ui.dashboard.DashboardFragment
import com.example.cmpt370_9mare.ui.event.CreateEventFragment
import org.hamcrest.CoreMatchers.notNullValue
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
        onView(withId(R.id.next_month_calendar)).perform(click())
        onView(withId(R.id.button_last_month)).perform(click())

        // check for the content of the calender
        onView(withId(R.id.month_calendar_grid)).check(matches(notNullValue()))
        onView(withId(R.id.month_calendar_grid)).check(matches(isNotClickable()))

        // check for create new event button
        onView(withId(R.id.floatingActionButton)).check(matches(isClickable()))
        onView(withId(R.id.floatingActionButton)).check(matches(withText("New Event")))
    }

    /**
     * This function will test for event, description and location inputs
     * */
    @Test
    fun title_description_event_test() {
        // Check for the title event
        launchFragmentInContainer<CreateEventFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        onView(withId(R.id.input_title))
            .check(matches(withHint("title")))
        onView(withId(R.id.event_title))
            .check(matches(isNotClickable()))

        // Check for the event description
        onView(withId(R.id.input_description))
            .check(matches(withHint("Description")))
        onView(withId(R.id.event_description))
            .check(matches(isNotClickable()))

        // Check for the event location
        onView(withId(R.id.input_location))
            .check(matches(withHint("Location")))
        onView(withId(R.id.event_location))
            .check(matches(isNotClickable()))
    }

    @Test
    fun all_day_option_test() {
        launchFragmentInContainer<CreateEventFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        onView(withId(R.id.all_day))
            .check(matches(withText("All-day")))
        onView(withId(R.id.all_day))
            .check(matches(isChecked()))
        onView(withId(R.id.all_day)).perform(click())
    }

    @Test
    fun start_end_test() {
        launchFragmentInContainer<CreateEventFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        // check for start date and time
//        onView(withId(R.id.inputTimeTo))
//            .check(matches(withText("Mar 10,2022")))
        onView(withId(R.id.inputTimeTo))
            .check(matches(isClickable()))

//        onView(withId(R.id.inputDayTo))
//            .check(matches(withText("10:25 AM")))
        onView(withId(R.id.inputDate))
            .check(matches(isClickable()))

        // check for end date and time
//        onView(withId(R.id.inputTimeFrom))
//            .check(matches(withText("Mar 10,2022")))
        onView(withId(R.id.inputTimeFrom))
            .check(matches(isClickable()))

//        onView(withId(R.id.inputDayFrom))
//            .check(matches(withText("10:25 AM")))
        onView(withId(R.id.inputDate))
            .check(matches(isClickable()))
    }

    @Test
    fun repeat_event_button_test() {
        launchFragmentInContainer<CreateEventFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        onView(withId(R.id.repeat_button))
            .check(matches(withText("Repeat")))
        onView(withId(R.id.repeat_button))
            .check(matches(isClickable()))
    }

    @Test
    fun url_notes_test() {
        launchFragmentInContainer<CreateEventFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        // Check for the url
        onView(withId(R.id.event_url))
            .check(matches(withHint("URL")))
        onView(withId(R.id.url_layout))
            .check(matches(isNotClickable()))

        // Check for the notes
        onView(withId(R.id.event_notes))
            .check(matches(withHint("Notes")))
        onView(withId(R.id.event_notes_layout))
            .check(matches(isNotClickable()))
    }

    @Test
    fun cancel_create_button_test() {
        launchFragmentInContainer<CreateEventFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        onView(withId(R.id.cancel_create_event))
            .check(matches(withText("Cancel")))
        onView(withId(R.id.cancel_create_event))
            .check(matches(isClickable()))

        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.submit_create_event))
            .check(matches(withText("Add")))
        onView(withId(R.id.submit_create_event))
            .check(matches(isClickable()))
    }

    /**
     * test for dashboard fragment
     * */
    @Test
    fun dashboard_fragment_test() {

        onView(withId(R.id.event_list_recycler_view)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).check(matches(notNullValue()))
    }
}