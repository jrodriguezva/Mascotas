package es.architectcoders.mascotas.ui.profile

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.profile.activities.EditProfileActivity
import es.architectcoders.mascotas.ui.utils.FakeLocalDataSource
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

class EditProfileUITest : KoinTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(EditProfileActivity::class.java, false, false)

    @Before
    fun setUp() {
        val testModule = module(override = true) {
            factory<UserDataSource> { FakeLocalDataSource() }
        }
        loadKoinModules(testModule)
    }

    @Test
    fun whenNameIsShortShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("a"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.error_min_name_length))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenNameIsLongShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("12345678911131517"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_max_name_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenSurnameIsShortShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("S"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_min_surname_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenSurameIsLongShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("12345678911131517"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_max_surname_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenCityIsShortShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("a"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_min_city_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenCityIsLongShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("12345678911131517"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_max_city_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }





    @Test
    fun whenCountryIsShortShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("S"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_min_country_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenCountryIsLongShouldShowErrorMessage() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("12345678911131517"), ViewActions.closeSoftKeyboard())


        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withText(activityTestRule.activity.getString(R.string.error_max_country_length)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenUpdateUserShouldNavigateToProfile() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.nameEdit)).perform(
            ViewActions.typeText("Alan"))
        Espresso.onView(ViewMatchers.withId(R.id.surnameEdit)).perform(
            ViewActions.typeText("Smith"))
        Espresso.onView(ViewMatchers.withId(R.id.cityEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Madrid"))
        Espresso.onView(ViewMatchers.withId(R.id.countryEdit)).perform(
            ViewActions.scrollTo(), ViewActions.typeText("Spain"), ViewActions.closeSoftKeyboard())

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.updateUserButton), ViewMatchers.withText(R.string.save)))
            .perform(ViewActions.click())

        Thread.sleep(2000)
        Assert.assertTrue(activityTestRule.activity.isDestroyed)
    }
}