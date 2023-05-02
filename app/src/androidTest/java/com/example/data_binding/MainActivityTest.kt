package com.example.data_binding

import android.graphics.ColorSpace.match
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun appLaunchSuccessfully(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun findRecyclerViewItem(){
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.recView))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText("MMK"))
            ))
    }

    @Test
    fun clickRecyclerViewItem(){
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.recView))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("MMK")),click()
            ))
    }
}