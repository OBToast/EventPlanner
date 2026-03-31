package com.example.eventplanner;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Events")
public class PlannedEvent {
    @PrimaryKey (autoGenerate = true)
    int id;
    String title;
    String location;
    int year;
    int month;
    int day;
    Category category;
    enum Category {
        Work,
        Social,
        Travel,
        Other
    }
    public PlannedEvent(String title, String location, int year, int month, int day, Category category)
    {
        this.title = title;
        this.location = location;
        this.year = year;
        this.month = month;
        this.day = day;
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
    public int[] getEventTime()
    {
        return new int[]{year,month,day};
    }

    public Category getEventCategory()
    {
        return category;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return(id);
    }
}