package com.example.visualplanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.visualplanner.model.Event;
import com.example.visualplanner.repository.FakeEventRepository;
import com.example.visualplanner.repository.IEventRepository;

import java.util.Objects;

public class Testing_event_repository {

    public IEventRepository repo;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void initialize_repository() {
        repo = new FakeEventRepository();
    }

    @Test
    public void adding_event_succeeds() {
        Event e = new Event("Test");
        int initSize = Objects.requireNonNull(repo.getEvents().getValue()).size();
        repo.addEvent(e);
        Event last = Objects.requireNonNull(repo.getEvents().getValue())
                .get(repo.getEvents().getValue().size()-1);

        assertEquals("Test", last.getTitle());
        assertEquals(initSize + 1, repo.getEvents().getValue().size());
    }

    @Test
    public void deleting_event_succeeds() {
        Event e = new Event("Test");
        int initSize = Objects.requireNonNull(repo.getEvents().getValue()).size();
        repo.addEvent(e);
        repo.deleteEvent(e);

        assertEquals(initSize, Objects.requireNonNull(repo.getEvents().getValue()).size());
    }
}