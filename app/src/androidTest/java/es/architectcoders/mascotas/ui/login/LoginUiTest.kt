package es.architectcoders.mascotas.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.utils.FakeLocalDataSource
import es.architectcoders.mascotas.ui.utils.ViewTextIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

class LoginUiTest : KoinTest {

    companion object {
        const val EMAIL = "a@a.com"
        const val PASSWORD = "asdf123"
        const val PASSWORD_FAIL = "sjkfhlasd"
    }

    @get:Rule
    val activityTestRule = ActivityTestRule(LoginActivity::class.java, false, false)

    @Before
    fun setUp() {
        val testModule = module(override = true) {
            factory<LoginDataSource> { FakeLocalDataSource() }
        }
        loadKoinModules(testModule)
        activityTestRule.activity
    }

    @Test
    fun whenPasswordIsShortShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.usernameEdit)).perform(typeText("a@a.com"))
        onView(withId(R.id.passwordEdit)).perform(typeText("qwe"))
        onView(allOf(withId(R.id.login), withText(R.string.sign_in))).perform(click())

        onView(withText(activityTestRule.activity.getString(R.string.error_password_length))).check(matches(isDisplayed()))
    }

    @Test
    fun whenEmailIsNotValidShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.usernameEdit)).perform(typeText("a"))
        onView(withId(R.id.passwordEdit)).perform(typeText("qwe23456"))
        onView(allOf(withId(R.id.login), withText(R.string.sign_in))).perform(click())

        onView(withText(activityTestRule.activity.getString(R.string.error_email_format))).check(matches(isDisplayed()))
    }

    @Test
    fun whenEmailAndPasswordIsNotValidShouldShowToast() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.usernameEdit)).perform(typeText(EMAIL))
        onView(withId(R.id.passwordEdit)).perform(typeText(PASSWORD_FAIL))
        onView(allOf(withId(R.id.login), withText(R.string.sign_in))).perform(click())

        isSignInErrorDisplayed()
    }

    @Test
    fun whenEmailAndPasswordIsValidShouldNavigate() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.usernameEdit)).perform(typeText(EMAIL))
        onView(withId(R.id.passwordEdit)).perform(typeText(PASSWORD))
        onView(allOf(withId(R.id.login), withText(R.string.sign_in))).perform(click())

        onView(withId(R.id.toolbarAdvert))
            .check(matches(hasDescendant(withText("+cotas"))))
    }

    private fun isSignInErrorDisplayed() {
        val snackbarErrorTextIdlingResource = ViewTextIdlingResource(
            com.google.android.material.R.id.snackbar_text,
            R.string.error_user_not_found
        )
        IdlingRegistry.getInstance().register(snackbarErrorTextIdlingResource)

        isTextDisplayed(R.string.error_user_not_found)

        IdlingRegistry.getInstance().unregister(snackbarErrorTextIdlingResource)
    }

    private fun isTextDisplayed(textId: Int) {
        onView(withText(textId)).check(matches(isDisplayed()))
    }
}