package com.application.bank

import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.application.bank.ui.login.LoginActivity
import com.application.bank.util.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@FixMethodOrder
class LoginScreenTest {
    private lateinit var loginActivity: LoginActivity

    @get:Rule
    var rule: ActivityTestRule<LoginActivity> =
        ActivityTestRule(LoginActivity::class.java)

    @Before
    fun register() {
        loginActivity = rule.activity
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun clickLoginButtonAfterFillingForm_LaunchStatementActivity() {
        val username = "baranidharan.1@tcs.com"
        val password = "Bank@12345"

        // Type username in Username Edit Text
        onView(withId(R.id.username)).perform(typeText(username), closeSoftKeyboard())

        // Type password in Password Edit Text
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())

        //Click on Login Button to check Login function
        onView(withId(R.id.login_btn)).perform(click())
    }

    @Test
    fun isUserNameEmpty() {
        val password = "Bank@12345"
        onView(withId(R.id.username)).perform(clearText())
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.login_btn)).perform(click())
        onView(withId(R.id.username)).check(matches(withError("Usuário empty")))
    }

    @Test
    fun isPasswordEmpty() {
        val username = "baranidharan.1@tcs.com"
        onView(withId(R.id.username)).perform(typeText(username), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(clearText())
        onView(withId(R.id.login_btn)).perform(click())
        onView(withId(R.id.password)).check(matches(withError("senha empty")))
    }

    @Test
    fun emailInvalid() {
        val username = "testemail"
        val password = "Bank@12345"
        onView(withId(R.id.username)).perform(typeText(username), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.login_btn)).perform(click())
        onView(withId(R.id.username)).check(matches(withError("Usuário inválido")))
    }

    @Test
    fun passwordInValid() {
        val username = "baranidharan.1@tcs.com"
        val password = "testpassword"
        onView(withId(R.id.username)).perform(typeText(username), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.login_btn)).perform(click())
        onView(withId(R.id.password)).check(matches(withError("senha inválido")))
    }

    private fun withError(expected: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                return if (item is EditText) {
                    item.error.toString() == expected
                } else false
            }

            override fun describeTo(description: Description) {
                description.appendText("Not found error message $expected, find it!")
            }
        }
    }

    @After
    fun unregister() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}