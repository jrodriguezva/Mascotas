package es.architectcoders.mascotas.ui.utils

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

fun getActivityInstance(): Activity {
    var currentActivity: Activity? = null
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        val resumedActivities =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivities.iterator().hasNext()) {
            currentActivity = resumedActivities.iterator().next()
        }
    }
    return currentActivity!!
}

fun getIdlingResourceActivityInstance(): Activity {
    lateinit var currentActivity: Activity
    val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
    if (resumedActivities.iterator().hasNext()) {
        currentActivity = resumedActivities.iterator().next()
    }
    return currentActivity
}