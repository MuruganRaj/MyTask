package mytaskcom.user.nearrestaurants;

import android.app.Application;
import android.app.ListActivity;
import android.support.test.runner.AndroidJUnit4;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mytaskcom.user.nearrestaurants.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ItemListActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            Intents.init();
            super.beforeActivityLaunched();
        }

        @Override
        protected void afterActivityFinished() {
            super.afterActivityFinished();
            Intents.release();
        }
    };

    @Test
    public void shouldLaunchTheMainActivityAndFindItemsInTheList() throws Exception {
        onView(withId(R.id.list)).check(matches(withChild(withText("Hotel Salem Sudha"))));
        onView(withId(R.id.list)).check(matches(withChild(withText("Ashirvadam Hotel"))));
        onView(withId(R.id.list)).check(matches(withChild(withText("Sasikala Hotel"))));
        onView(withId(R.id.list)).check(matches(withChild(withText("Saravana Bhavan"))));
    }

    @Test
    public void shouldShowTheItemDetailWhenAnItemIsClicked() throws Exception {
        intending(hasComponent(MainActivity.class.getName()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, new Intent()));

        onView(withText("Hotel Salem Sudha")).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}