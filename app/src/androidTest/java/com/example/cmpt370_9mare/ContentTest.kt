package com.example.cmpt370_9mare


import androidx.fragment.app.activityViewModels
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarViewModel
import com.example.cmpt370_9mare.ui.event.CreateEventFragment

import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class ContentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

//    @Test
//    fun schedule_fragment_content_test() {
//        launchFragmentInContainer<CalendarFragment>(themeResId = R.style.Theme)
//        onView(withId(R.id.previous_month_button)).perform(click())
//        onView(withId(R.id.forward_month_button)).perform(click())
//    }


    /**
     * Testing for create fragment content. In this function I will go to create fragment content
     * and testing for all the input text and button
     * */
    @Test
    fun create_event_fragment_content(){      // launch the entree menu fragment
        launchFragmentInContainer<CreateEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        // call all the instrument tests for create fragment content
        title_description_event_test()
        all_day_option_test()
        start_end_test()
        repeat_event_button_test()
        url_notes_test()
        cancel_create_button_test()
    }

    /**
     * This function will test for event, description and location inputs
     * */
    private fun title_description_event_test(){
        // Check for the title event
        onView(withId(R.id.input_title))
            .check(matches(withHint("title")))
        onView(withId(R.id.event_title))
            .check(matches(isNotClickable()))

        // Check for the event description
        onView(withId(R.id.input_description))
            .check(matches(withHint("description")))
        onView(withId(R.id.event_description))
            .check(matches(isNotClickable()))

        // Check for the event location
        onView(withId(R.id.input_location))
            .check(matches(withHint("Location")))
        onView(withId(R.id.event_location))
            .check(matches(isNotClickable()))
    }

    private fun all_day_option_test(){
        onView(withId(R.id.all_day))
            .check(matches(withText("All-day")))
        onView(withId(R.id.all_day))
            .check(matches(isChecked()))
    }

    private fun start_end_test(){
        // check for start date and time
        onView(withId(R.id.input_day_from))
            .check(matches(withText("Mar 10,2022")))
        onView(withId(R.id.chosen_day_start_layout))
            .check(matches(isNotClickable()))

        onView(withId(R.id.input_time_from))
            .check(matches(withText("10:25 AM")))
        onView(withId(R.id.chosen_time_start_layout))
            .check(matches(isNotClickable()))

        // check for end date and time
        onView(withId(R.id.input_day_to))
            .check(matches(withText("Mar 10,2022")))
        onView(withId(R.id.chosen_day_end_layout))
            .check(matches(isNotClickable()))

        onView(withId(R.id.input_time_to))
            .check(matches(withText("10:25 AM")))
        onView(withId(R.id.chosen_time_end_layout))
            .check(matches(isNotClickable()))
    }

    private fun repeat_event_button_test(){
        onView(withId(R.id.repeat_button))
            .check(matches(withText("Repeat")))
        onView(withId(R.id.repeat_button))
            .check(matches(isClickable()))
    }

    private fun url_notes_test(){

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

    private fun cancel_create_button_test(){
        onView(withId(R.id.cancel_create_event))
            .check(matches(withText("Cancel")))
        onView(withId(R.id.cancel_create_event))
            .check(matches(isClickable()))

        onView(withId(R.id.submit_create_event))
            .check(matches(withText("Add")))
        onView(withId(R.id.submit_create_event))
            .check(matches(isClickable()))
    }

}