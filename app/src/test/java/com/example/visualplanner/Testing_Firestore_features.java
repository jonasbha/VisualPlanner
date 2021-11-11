package com.example.visualplanner;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.visualplanner.utility.Format;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class Testing_Firestore_features {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void adding_event_succeeds() {



        //assertEquals(initSize + 1, repo.getEvents().getValue().size());
    }

    @Test
    public void date_formatted_correctly() {
        String original = "Mon Nov 08 08:45:08 GMT+00:00 2021";
        String formatted = Format.date(original);

        assertEquals("Mon Nov 2021", formatted);
    }
}
