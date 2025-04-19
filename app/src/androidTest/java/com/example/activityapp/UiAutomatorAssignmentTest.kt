package com.example.activityapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class UiAutomatorAssignmentTest {

    private lateinit var device: UiDevice
    private val TIMEOUT = 5000
    private val PACKAGE_NAME = "com.example.activityapp" // Update if your package is different

    @Before
    fun setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIMEOUT)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(PACKAGE_NAME)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT)
    }

    @Test
    fun testLaunchSecondActivityAndVerifyChallenge() {
        // Click on the "Start Activity Explicitly" button
        val startButton = device.findObject(By.text("Start Activity Explicitly"))
        startButton.click()

        // Wait and check for one known challenge in the second activity
        device.wait(Until.hasObject(By.textContains("Fragmentation")), TIMEOUT)

        val challenge = device.findObject(By.textContains("Fragmentation"))
        assertNotNull(challenge, "Expected challenge text 'Fragmentation' not found.")
    }
}
