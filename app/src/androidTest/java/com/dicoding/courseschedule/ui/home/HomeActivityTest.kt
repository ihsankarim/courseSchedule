package com.dicoding.courseschedule.ui.home

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule<HomeActivity>(HomeActivity::class.java)

    @Test
    fun testAddCourseActivityIsDisplayed() {
        performClick(R.id.action_add)

        val addCourse = getAddCourseActivity()
        Assert.assertTrue(addCourse?.javaClass == AddCourseActivity::class.java)
        checkViewsWithIds(
            R.id.ed_course_name,
            R.id.spinner_day,
            R.id.tv_start_time,
            R.id.tv_end_time,
            R.id.ed_lecturer,
            R.id.ed_note
        )
    }

    private fun checkViewsWithIds(vararg viewIds: Int) {
        viewIds.forEach {
            Espresso.onView(ViewMatchers.withId(it))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    private fun performClick(viewId: Int) {
        Espresso.onView(ViewMatchers.withId(viewId)).apply {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            perform(ViewActions.click())
        }
    }

    private fun getAddCourseActivity(): Activity? {
        var activity: Activity? = null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            activity = ActivityLifecycleMonitorRegistry.getInstance()
                .getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0)
        }
        return activity
    }
}