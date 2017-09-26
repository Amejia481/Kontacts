package com.arturomejiamarmol.contacts.contacts;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.arturomejiamarmol.contacts.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ContactsActivityTest {

    @Rule
    public ActivityTestRule<ContactsActivity> mActivityTestRule = new ActivityTestRule<>(ContactsActivity.class);

    @Test
    public void contactsActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab)));


        floatingActionButton.perform(click());


        ViewInteraction firstNameEditText = onView(
                allOf(withId(R.id.first_name)));

        firstNameEditText.perform(typeText("Arturo"), closeSoftKeyboard());


        ViewInteraction lastNameEditText = onView(
                allOf(withId(R.id.last_name)));

        lastNameEditText.perform(click());
        lastNameEditText.perform(scrollTo(), typeText("Mejia"), closeSoftKeyboard());

        ViewInteraction dateOfBirthEditText = onView(
                allOf(withId(R.id.date_of_birth)));


        dateOfBirthEditText.perform(scrollTo(), typeText("01/09/1990"), closeSoftKeyboard());

        ViewInteraction addContactButton = onView(
                allOf(withId(R.id.add_new_contact)));


        addContactButton.perform(scrollTo(), click());


         onView(
                allOf(withId(R.id.topic), withText("Arturo Mejia"),
                        isDisplayed()));

    }


}
