package com.example.activityapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.activityapp.R
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testSecondActivityShowsChallenge() {
        // Launch the main activity
        ActivityScenario.launch(MainActivity::class.java)

        // Click the button to open second activity
        onView(withId(R.id.buttonExplicit))
            .check(matches(isDisplayed()))
            .perform(click())

        // Verify the challenges text view exists and contains expected text
        onView(withId(R.id.textChallenges))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("1. Fragmentation"))))
            .check(matches(withText(containsString("2. Battery efficiency"))))
            .check(matches(withText(containsString("3. Security"))))
            .check(matches(withText(containsString("4. Performance"))))
            .check(matches(withText(containsString("5. UX consistency"))))
    }
}