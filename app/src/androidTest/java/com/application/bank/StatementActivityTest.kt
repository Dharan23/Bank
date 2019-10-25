package com.application.bank

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.application.bank.ui.login.LoginActivity
import com.application.bank.ui.statement.StatementActivity
import com.application.bank.util.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StatementActivityTest {

    @get:Rule
    var statementActivityRule: ActivityTestRule<StatementActivity> =
        ActivityTestRule(StatementActivity::class.java, true, true)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun statementRecyclerTest() {
        val itemsCount = getCountFromRecyclerView(R.id.statement_rv)
        if (itemsCount > 0) {
            onView(withRecyclerView(R.id.statement_rv).atPosition(0)).perform(click())
        }
    }

    fun withRecyclerView(@IdRes recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int): Int {
        var COUNT = 0
        val matcher = object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                COUNT = (item as RecyclerView).adapter!!.itemCount
                return true
            }

            override fun describeTo(description: Description) {}
        }
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher))
        val result = COUNT
        COUNT = 0
        return result
    }

    @Test
    fun onLogoutClick() {
        Intents.init()
        onView(withId(R.id.iv_logout)).perform(click())
        intended(hasComponent(LoginActivity::class.java.name))
        Intents.release()
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}