package com.example.cmpt370_9mare



import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cmpt370_9mare.ui.event.CreateEventFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Tests for all navigation flows
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTests: BaseTest() {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)



    @Test
    fun calendar_fragment_navigate_to_create_event_fragment(){
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<CreateEventFragment>(themeResId=R.style.Theme)
        scenario.onFragment{
            fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }


    }
}