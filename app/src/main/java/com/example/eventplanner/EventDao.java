package com.example.eventplanner;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insertPlannedEvent(PlannedEvent event);

    @Update
    void updatePlannedEvent(PlannedEvent event);
    @Delete
    public void deletePlannedEvent(PlannedEvent plannedEvent);

    //Sort events by year, month and day so the newest appear at top
    @Query("SELECT * FROM Events ORDER BY year ASC, month ASC, day ASC")
    public List<PlannedEvent> getPlannedEvents();

}
