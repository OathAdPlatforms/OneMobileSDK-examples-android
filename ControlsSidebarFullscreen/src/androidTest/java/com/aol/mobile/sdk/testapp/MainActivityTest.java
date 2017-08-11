package com.aol.mobile.sdk.testapp;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.aol.mobile.sdk.player.model.properties.Properties;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void beforeAll() {
        Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());
    }

    @Test
    public void makeContentScreenshotPortrait() {
        boolean done = false;
        boolean needsTouch = true;

        do {
            if (MainActivity.getPlayerFragment().getBinder().getPlayer() != null) {
                Properties properties = MainActivity.getPlayerFragment().getBinder().getPlayer().getProperties();
                if (properties.viewState == Properties.ViewState.Ad && properties.ad.isVideoStreamPlaying && needsTouch) {
                    Espresso.onView(ViewMatchers.withId(R.id.ad_pause_button)).perform(ViewActions.click());
                    Espresso.onView(ViewMatchers.withId(R.id.ad_play_button)).perform(ViewActions.click());
                    needsTouch = false;
                }
                if (properties.viewState == Properties.ViewState.Content && properties.playlistItem.video != null && properties.playlistItem.video.time != null) {
                    if (properties.playlistItem.video.time.current > 1500) {
                        Screengrab.screenshot("controlsSidebarFullscreenOffContentPortrait");
                        Espresso.onView(ViewMatchers.withId(42)).perform(ViewActions.click());
                        SystemClock.sleep(1000);
                        Screengrab.screenshot("controlsSidebarFullscreenOnContentPortrait");
                        done = true;
                    }
                }
            }
        } while (!done);
    }
}
