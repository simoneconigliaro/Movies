package com.project.simoneconigliaro.ui;


import android.view.View;
import android.view.ViewGroup;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import com.project.simoneconigliaro.movies.R;
import com.project.simoneconigliaro.movies.ui.MovieListActivity;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class MovieListTest {

    private final static String GENRE_MYSTERY = "Mystery";
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = ActivityScenario.launch(MovieListActivity.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<MovieListActivity>() {
            @Override
            public void perform(MovieListActivity activity) {
                mIdlingResource = activity.getIdlingResource();
                // To prove that the test fails, omit this call:
                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });
    }

    @Test
    public void onSearchClick(){
        onView(withId(R.id.search_view)).perform(click());
    }

    @Test
    public void checkListIsVisible() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void checkGenreFirstItem(){
        onView(nthChildOf(withId(R.id.recycler_view), 0)).check(matches(hasDescendant(withText(GENRE_MYSTERY))));
    }

    @Test
    public void scrollToBottomList() {
       onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(20));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && view.equals(group.getChildAt(childPosition));
            }
        };
    }

}
