package com.example.visualplanner.model;

import java.util.Calendar;
import java.util.Date;

public class Deadline {
    private String description;
    private int column;
    private int span;

    public Deadline(String description, int column) {
        this.description = description;
        this.column = column;
    }

    public Deadline(String description, int column, int span) {
        this.description = description;
        this.column = column;
        this.span = span;
    }

    public int getSpan() {
        return span;
    }

    public String getDescription() {
        return description;
    }

    public int getColumn() {
        return column;
    }
}
