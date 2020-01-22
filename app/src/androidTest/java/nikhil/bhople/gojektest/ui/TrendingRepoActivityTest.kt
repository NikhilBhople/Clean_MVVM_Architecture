package nikhil.bhople.gojektest.ui

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import nikhil.bhople.gojektest.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TrendingRepoActivityTest{
    @Rule
    @JvmField
    val rule = ActivityTestRule(TrendingRepoActivity::class.java, false, false)


    @Test
    fun retryButtonClickedUiTest(){


    }

}