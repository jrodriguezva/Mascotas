package es.architectcoders.mascotas.ui.login


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import es.architectcoders.mascotas.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class UpdateUserActivityTest  : KoinTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(LoginActivity::class.java, false, false)

    @Test
    fun updateUserActivityTest() {

        // launch initial Activity
        activityTestRule.launchActivity(null)

        // fill username
        val textInputEditText = onView(
            allOf(
                withId(R.id.usernameEdit),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.usernameLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("a@a.com"), closeSoftKeyboard())

        // fill password
        val textInputEditText2 = onView(
            allOf(
                withId(R.id.passwordEdit),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("123456"), closeSoftKeyboard())

        // click login button
        val extendedFloatingActionButton = onView(
            allOf(
                withId(R.id.login), withText("Sign in"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        extendedFloatingActionButton.perform(click())

        // Click toolbar profile button
        val actionMenuItemViewProfile = onView(
            allOf(
                withId(R.id.btnProfile), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbarAdvert),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemViewProfile.perform(click())

        // Click toolbar edit profile button
        val actionMenuItemViewEditProfile = onView(
            allOf(
                withId(R.id.btnEdit), withContentDescription("Edit"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbarProfile),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemViewEditProfile.perform(click())

        // focus on name edit test
        val textInputEditText3 = onView(
            allOf(
                withId(R.id.nameEdit), withText("Antonio"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nameLayout),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("Antonio test"))

        // focus on name edit test to fill Antonio test
        val textInputEditText4 = onView(
            allOf(
                withId(R.id.nameEdit), withText("Antonio test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nameLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(closeSoftKeyboard())

        // click Save button to retrieve data
        val extendedFloatingActionButton2 = onView(
            allOf(
                withId(R.id.updateUserButton), withText("Save"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    11
                )
            )
        )
        extendedFloatingActionButton2.perform(scrollTo(), click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
