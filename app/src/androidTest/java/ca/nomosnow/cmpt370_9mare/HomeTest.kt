package ca.nomosnow.cmpt370_9mare

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ca.nomosnow.cmpt370_9mare.ui.calendar.CalendarFragment
import ca.nomosnow.cmpt370_9mare.ui.event.CreateEventFragment
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

class HomeTest : BaseTest() {
    @Test
    fun TC1_Test_Today_Event_with_No_Event() {
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.expandableListView)).perform(click())
    }

    @Test
    fun TC2_Test_Today_Event_with_1_Event() {
        val date = LocalDate.now().toString()
        val eventName1 = "TC2 Event 1"
        createEvent(eventName1, date, timeFrom = "01:00", timeTo = "02:00")
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.expandableListView)).perform(click())
        deleteEvent(eventName1)
    }

    @Test
    fun TC3_Test_Today_Event_with_2_Events() {
        val date = LocalDate.now().toString()
        val eventName2 = "TC3 Event 2"
        val eventName3 = "TC3 Event 3"
        createEvent(eventName2, date, timeFrom = "03:00", timeTo = "04:00")
        onView(withId(R.id.navigation_calendar)).perform(click())
        createEvent(eventName3, date, timeFrom = "05:00", timeTo = "06:00")
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.expandableListView)).perform(click())
        deleteEvent(eventName2)
        deleteEvent(eventName3)
    }

    @Test
    fun TC4_Test_Next_Event_with_No_Event() {
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.expandableListView)).perform(click())
    }

        @Test
    fun TC5_Test_Next_Event_with_1_Event() {
            val eventName4 = "TC5 Event 1"
            createEvent(eventName4, timeFrom = "01:00", timeTo = "02:00")
            onView(withId(R.id.navigation_home)).perform(click())
            onView(withId(R.id.expandableListView)).perform(click())
            deleteEvent(eventName4)
    }
    @Test
    fun TC6_Test_Next_Event_with_2_Events() {
        val eventName5 = "TC5 Event 2"
        val eventName6 = "TC5 Event 3"
        createEvent(eventName5, timeFrom = "01:00", timeTo = "02:00")
        createEvent(eventName6, timeFrom = "03:00", timeTo = "04:00")
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.expandableListView)).perform(click())
        deleteEvent(eventName5)
        deleteEvent(eventName6)
    }
    @Test
    fun TC7_Test_Today_display() {
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.expandableListView)).perform(click())
    }


}