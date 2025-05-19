package com.example.activityapp

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class UiAutomatorAssignmentTest {

    private lateinit var device: UiDevice
    private val packageName = "com.example.activityapp"
    private val timeout = TimeUnit.SECONDS.toMillis(10)

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Grant custom permission needed by your app
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("pm grant $packageName com.example.activityapp.MSE412")

        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        assertNotNull(launcherPackage)
        device.wait(Until.hasObject(By.pkg(launcherPackage)), timeout)

        // Launch app
        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for app to appear
        device.wait(Until.hasObject(By.pkg(packageName)), timeout)
    }

    @Test
    fun testSecondActivityChallenge() {
        // Find button using both resource ID and text as fallback
        val buttonSelector = By.res(packageName, "buttonExplicit")
            .clazz("android.widget.Button")
            .text("Start Activity Explicitly")

        val explicitButton: UiObject2? = device.wait(Until.findObject(buttonSelector), timeout)
        assertNotNull("Could not find 'Start Activity Explicitly' button", explicitButton)

        explicitButton?.click()

        // Wait for second activity to load and verify text
        val challengeText: UiObject2? = device.wait(Until.findObject(
            By.res(packageName, "textChallenges")
                .clazz("android.widget.TextView")
        ), timeout)

        assertNotNull("Could not find challenge text view", challengeText)

        val expectedChallenges = listOf(
            "Fragmentation",
            "Battery efficiency",
            "Security",
            "Performance",
            "UX consistency"
        )

        val actualText = challengeText?.text ?: ""
        assertTrue(
            "Challenge text doesn't match expected patterns. Found: $actualText",
            expectedChallenges.any { actualText.contains(it) }
        )
    }
}