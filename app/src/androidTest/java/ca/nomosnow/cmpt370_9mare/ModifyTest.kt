package ca.nomosnow.cmpt370_9mare

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ModifyTest : BaseTest() {
    @Test
    fun TC1_text_copies_to_update_fragment() {
        val date = "${(2023..9999).random()}-04-02"
        val testTxt = "Testing"
        val testTitle = "TC1 First event"

        onView(withId(R.id.navigation_calendar)).perform(click())
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.input_title)).perform(
            typeText(testTitle),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.inputDate)).perform(SetButtonText(date))
        onView(withId(R.id.inputTimeFrom)).perform(SetButtonText("04:02"))
        onView(withId(R.id.inputTimeTo)).perform(SetButtonText("12:16"))
        onView(withId(R.id.input_location)).perform(
            typeText("$testTxt Location"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.event_url)).perform(
            scrollTo(),
            typeText("$testTxt URL"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.event_notes)).perform(
            scrollTo(),
            typeText("$testTxt Notes"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.layout_create_event_fragment)).perform(swipeDown())
        onView(withId(R.id.submit_create_event)).perform(click())

        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).perform(click())
        onView(withText("Edit")).perform(click())
        onView(withId(R.id.input_title)).check(matches(withText(testTitle)))
        onView(withId(R.id.input_location)).check(matches(withText("$testTxt Location")))
        onView(withId(R.id.inputDate)).check(matches(withText(date)))
        onView(withId(R.id.inputTimeFrom)).check(matches(withText("04:02")))
        onView(withId(R.id.inputTimeTo)).check(matches(withText("12:16")))
        onView(withId(R.id.event_url)).perform(scrollTo()).check(matches(withText("$testTxt URL")))
        onView(withId(R.id.event_notes)).perform(scrollTo())
            .check(matches(withText("$testTxt Notes")))
        onView(withId(R.id.delete_event)).perform(scrollTo(), click())
        onView(withText("Confirm")).perform(click())
    }

    @Test
    fun TC2_modifying_text_updates_event() {
        val oldDate = "${(2023..9999).random()}-04-02"
        val newDate = "${(2023..9999).random()}-04-02"
        val testTitle = "TC2 First event"
        val testNewTitle = "TC2 First event modified"

        createEvent(testTitle, oldDate)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).perform(click())
        onView(withText("Edit")).perform(click())
        onView(withId(R.id.input_title)).perform(
            clearText(),
            typeText(testNewTitle),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        onView(withId(R.id.inputDate)).perform(SetButtonText(newDate))
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testNewTitle)).perform(click())
        onView(withText(newDate)).check(matches(isDisplayed()))
        onView(withText("Edit")).perform(click())
        onView(withId(R.id.delete_event)).perform(scrollTo(), click())
        onView(withText("Confirm")).perform(click())
    }

    @Test
    fun TC3_no_conflicts_on_modifying_own_event() {
        val testTitle = "TC3 First event"

        createEvent(testTitle)
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.event_list_recycler_view)).perform(ScrollToBottom())
        onView(withText(testTitle)).perform(click())
        onView(withText("Edit")).perform(click())
        onView(withId(R.id.conflict_check)).perform(click())
        onView(withId(R.id.submit_create_event)).perform(click())
        onView(withId(R.id.search_event)).check(matches(isDisplayed()))
        deleteEvent(testTitle)
    }
}