package com.example.eventplanner;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Create a database of PlannedEvents
@Database(entities = {PlannedEvent.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
}
