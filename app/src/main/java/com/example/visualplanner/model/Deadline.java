package com.example.visualplanner.model;

import java.util.Calendar;
import java.util.Date;

public class Deadline {
    private String description;
    private Date startDate;
    private Date endDate;

    public Deadline(String description, Date endDate) {
        this.description = description;
        this.startDate = Calendar.getInstance().getTime();
        this.endDate = endDate;
    }

    public Deadline(String description, Date startDate, Date endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
