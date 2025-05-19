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
class ActivityFlowTest {

    private lateinit var device: UiDevice
    private val packageName = "com.example.activityapp"
    private val timeout = 5000L

    @Before
    fun startMainActivityFromHomeScreen() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        device.wait(Until.hasObject(By.pkg(device.launcherPackageName)), timeout)

        // Launch app
        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for app to appear
        device.wait(Until.hasObject(By.pkg(packageName)), timeout)
    }

    @Test
    fun testSecondActivityShowsChallenge() {
        // Click the "Start Activity Explicitly" button (using the ID from your layout)
        val startButton = device.findObject(By.res(packageName, "button1"))
        startButton.click()

        // Wait for second activity to load (using the TextView ID from SecondActivity)
        device.wait(Until.hasObject(By.res(packageName, "textView2")), timeout)

        // Verify challenge text
        val challengeText = device.findObject(By.res(packageName, "textView2"))
        val actualText = challengeText.text

        // Update this list with your actual challenges from SecondActivity
        val expectedChallenges = listOf(
            "Device Fragmentation",
            "Security Concerns",
            "Battery Optimization",
            "Network Connectivity Issues",
            "User Interface Adaptation"
        )

        assertTrue(
            "Second activity should show one of the challenges. Found: $actualText",
            expectedChallenges.any { actualText.contains(it) }
        )
    }
}