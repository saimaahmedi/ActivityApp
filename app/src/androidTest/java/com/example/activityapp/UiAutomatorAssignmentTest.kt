package com.example.activityapp

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiAutomatorAssignmentTest {

    private lateinit var device: UiDevice
    private val APP_PACKAGE = "com.example.activityapp"
    private val LAUNCH_TIMEOUT = 5000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch app
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(APP_PACKAGE)
            ?: throw IllegalStateException("No launch intent for $APP_PACKAGE")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for app
        device.wait(
            Until.hasObject(By.pkg(APP_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun testSecondActivityShowsChallenge() {
        // Click explicit activity button
        val explicitButton = device.wait(
            Until.findObject(By.res(APP_PACKAGE, "buttonExplicit")),
            5000 // Increased timeout
        ) ?: throw AssertionError("Explicit activity button not found")
        explicitButton.click()

        // Verify challenge text - using textChallenges from your layout
        val challengeText = device.wait(
            Until.findObject(By.res(APP_PACKAGE, "textChallenges")),
            5000 // Increased timeout
        ) ?: throw AssertionError("Challenge text view not found (ID: textChallenges)")

        val challengeTextString = challengeText.text ?: ""
        assertTrue(
            "Text should contain challenges: $challengeTextString",
            challengeTextString.contains("1. Fragmentation") &&
                    challengeTextString.contains("2. Battery efficiency") &&
                    challengeTextString.contains("3. Security") &&
                    challengeTextString.contains("4. Performance") &&
                    challengeTextString.contains("5. UX consistency")
        )
    }
}