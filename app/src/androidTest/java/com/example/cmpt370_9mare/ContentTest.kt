package com.example.cmpt370_9mare


import android.view.KeyEvent
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.calendar.CalendarFragment
import com.example.cmpt370_9mare.ui.event.CreateEventFragment
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ContentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private fun setDate(date: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(isDisplayed(), isAssignableFrom(Button::class.java))
            }

            override fun perform(uiController: UiController, view: View) {
                (view as Button).text = date
            }

            override fun getDescription(): String {
                return "Replace Date"
            }
        }
    }

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
//    @Test
//    fun dashboard_fragment_test() {
//
//        onView(withId(R.id.event_list_recycler_view)).perform(click())
//        onView(withId(R.id.event_list_recycler_view)).check(matches(notNullValue()))
//    }

    @Test
    fun TC1_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC2_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("TC2")).perform(
            pressKey(
                KeyEvent.KEYCODE_ENTER
            )
        )
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC3_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.inputDate)).perform(setDate("2021-03-13"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC4_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
//        onView(withId(R.id.input_title)).perform(ViewActions.typeText("TC2")).perform(
//            ViewActions.pressKey(
//                KeyEvent.KEYCODE_ENTER
//            )
//        )
//        onView(withId(R.id.inputDate)).perform(setDate("2021-03-13"))
        onView(withId(R.id.inputTimeFrom)).perform(setDate("3:00"))
        onView(withId(R.id.inputTimeTo)).perform(setDate("5:00"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC5_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("TC5")).perform(
            pressKey(
                KeyEvent.KEYCODE_ENTER
            )
        )
        onView(withId(R.id.inputDate)).perform(setDate("2021-03-13"))
//        onView(withId(R.id.inputTimeFrom)).perform(setDate("3:00"))
//        onView(withId(R.id.inputTimeTo)).perform(setDate("5:00"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC6_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("TC6")).perform(
            pressKey(
                KeyEvent.KEYCODE_ENTER
            )
        )
        //onView(withId(R.id.inputDate)).perform(setDate("2021-03-13"))
        onView(withId(R.id.inputTimeFrom)).perform(setDate("3:00"))
        onView(withId(R.id.inputTimeTo)).perform(setDate("5:00"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC7_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
//        onView(withId(R.id.input_title)).perform(ViewActions.typeText("TC6")).perform(
//            ViewActions.pressKey(
//                KeyEvent.KEYCODE_ENTER
//            )
//        )
        onView(withId(R.id.inputDate)).perform(setDate("2021-03-13"))
        onView(withId(R.id.inputTimeFrom)).perform(setDate("3:00"))
        onView(withId(R.id.inputTimeTo)).perform(setDate("5:00"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }

    @Test
    fun TC8_Test_title_pickDate_pickTime() {
        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(typeText("TC8")).perform(
            pressKey(
                KeyEvent.KEYCODE_ENTER
            )
        )
        onView(withId(R.id.inputDate)).perform(setDate("2021-03-13"))
        onView(withId(R.id.inputTimeFrom)).perform(setDate("3:00"))
        onView(withId(R.id.inputTimeTo)).perform(setDate("5:00"))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.show_past_events)).perform(click())
    }
}