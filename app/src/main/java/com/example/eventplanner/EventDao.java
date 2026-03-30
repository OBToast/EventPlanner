package com.example.eventplanner;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface EventDao {
    @Upsert
    public void insertPlannedEvent(PlannedEvent plannedEvent);
    @Delete
    public void deletePlannedEvent(PlannedEvent plannedEvent);

    @Query("SELECT * FROM Events ORDER BY id ASC")
    public List<PlannedEvent> getPlannedEvents();
}
