package com.example.eventplanner;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Events")
public class PlannedEvent {
    @PrimaryKey (autoGenerate = true)
    int id;
    String title;
    String location;
    int time;
    Category category;
    enum Category {
        Work,
        Social,
        Travel,
        Other
    }
    public PlannedEvent(String title, String location, int time, Category category)
    {
        this.title = title;
        this.location = location;
        this.time = time;
        this.category = category;
    }

    public String getEventTitle()
    {
        return title;
    }
    public String getEventLocation()
    {
        return location;
    }
    public int getEventTime()
    {
        return time;
    }
    public Category getEventCategory()
    {
        return category;
    }
}