package ru.netology.testing.uiautomator

import android.content.Context
import android.net.sip.SipErrorCode.TIME_OUT
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"
const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    private lateinit var device: UiDevice
    private val emptyText = "    "
    private val textToSet = "Hello"

    @Before
    fun beforeEachPressHomeWaitLauncher() {

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)),TIMEOUT )
    }

    @Test
    fun testInputEmptyText () {
        waitForPackage(MODEL_PACKAGE)

        val expected = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text
        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = emptyText
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()

        val actual = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged")).text
        assertEquals(actual, expected)
    }

    @Test
    fun testInputTextNewActivity() {
        waitForPackage(MODEL_PACKAGE)

        device.findObject(By.res(MODEL_PACKAGE,"userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()
        device.findObject(By.res(MODEL_PACKAGE, "buttonActivity")).click()
        device.wait(Until.hasObject(By.res(MODEL_PACKAGE, "text")), TIMEOUT)
        val actual = device.findObject(By.res(MODEL_PACKAGE, "text")).text

        assertEquals(actual, textToSet)

    }

    private fun waitForPackage (MODEL_PACKAGE: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(MODEL_PACKAGE)), TIMEOUT)
    }

}