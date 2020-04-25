package es.architectcoders.mascotas.ui.login


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import es.architectcoders.mascotas.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UpdateUserActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java, false, false)

    @Test
    fun updateUserActivityTest() {
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

        val actionMenuItemView = onView(
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
        actionMenuItemView.perform(click())

        val actionMenuItemView2 = onView(
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
        actionMenuItemView2.perform(click())

        val actionMenuItemView3 = onView(
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
        actionMenuItemView3.perform(click())

        val actionMenuItemView4 = onView(
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
        actionMenuItemView4.perform(click())

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
