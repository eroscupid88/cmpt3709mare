package com.example.cmpt370_9mare


import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import com.example.cmpt370_9mare.ui.calendar.CalendarFragmentDirections
import com.example.cmpt370_9mare.ui.event.CreateEventFragment
import com.example.cmpt370_9mare.ui.event.CreateEventFragmentDirections
import com.example.cmpt370_9mare.ui.event.NewEventFragment
import com.example.cmpt370_9mare.ui.event.NewEventFragmentDirections
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


/**
 * Tests for all navigation flows
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTests : BaseTest() {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    /**
     * Testing action fragment from calendar fragment to create event fragment
     */
    @Test
    fun calendar_fragment_navigate_to_create_event_fragment() {
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragmentInContainer<CalendarFragment>(
            bundleOf("eventID" to -1),
            themeResId = R.style.Theme_Cmpt3709mare
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.floatingActionButton)).perform(click())
        verify(mockNavController).navigate(CalendarFragmentDirections.actionNavigationCalendarToCreateEventFragment())
    }
    

    /**
     * Testing action fragment from create event fragment to calendar using cancel button
     */
    @Test
    fun create_event_fragment_navigate_to_calendar_fragment() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<CreateEventFragment>(
                bundleOf("eventID" to -1),
                themeResId = R.style.Theme_Cmpt3709mare
            )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.cancel_create_event)).perform(click())
        verify(mockNavController).navigateUp()
    }


    /**
     * Testing action fragment from create event fragment to calendar using add button
     */
    @Test
    fun create_event_fragment_navigate_to_calendar_fragment_add() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<CreateEventFragment>(
                bundleOf("eventID" to -1),
                themeResId = R.style.Theme_Cmpt3709mare
            )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // perform input
        onView(withId(R.id.input_title)).perform(ViewActions.typeText("Test Event "))
        // Click start order
        onView(withId(R.id.submit_create_event)).perform(click())
        verify(mockNavController).navigate(CreateEventFragmentDirections.actionCreateEventFragmentToNavigationCalendar())
    }


    /**
     * Testing action fragment from create event fragment to calendar using add button
     */
    @Test
    fun new_Button_fragment_navigate_to_create_event_fragment_never() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<NewEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.new_event_never)).perform(click())
        verify(mockNavController).navigate(NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment())
    }

    /**
     * Testing action fragment from create event fragment to calendar using day button
     */
    @Test
    fun new_Button_fragment_navigate_to_create_event_fragment_day() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<NewEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.new_event_everyday)).perform(click())
        verify(mockNavController).navigate(NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment())
    }


    /**
     * Testing action fragment from create event fragment to calendar using week button
     */
    @Test
    fun new_Button_fragment_navigate_to_create_event_fragment_week() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<NewEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.new_event_every_week)).perform(click())
        verify(mockNavController).navigate(NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment())
    }

    /**
     * Testing action fragment from create event fragment to calendar using 2week button
     */
    @Test
    fun new_Button_fragment_navigate_to_create_event_fragment_2week() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<NewEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.new_event_every_2week)).perform(click())
        verify(mockNavController).navigate(NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment())
    }

    /**
     * Testing action fragment from create event fragment to calendar using month button
     */
    @Test
    fun new_Button_fragment_navigate_to_create_event_fragment_month() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<NewEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.new_event_every_month)).perform(click())
        verify(mockNavController).navigate(NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment())
    }

    /**
     * Testing action fragment from create event fragment to calendar using year button
     */
    @Test
    fun new_Button_fragment_navigate_to_create_event_fragment_year() {
        val mockNavController = mock(NavController::class.java)
        val scenario =
            launchFragmentInContainer<NewEventFragment>(themeResId = R.style.Theme_Cmpt3709mare)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        // Click start order
        onView(withId(R.id.new_event_every_year)).perform(click())
        verify(mockNavController).navigate(NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment())
    }


}