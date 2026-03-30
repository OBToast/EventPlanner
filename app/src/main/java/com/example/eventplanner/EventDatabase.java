package com.example.eventplanner;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {PlannedEvent.class},
        version = 1
)
public abstract class EventDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    private static EventDatabase instance;
    public static synchronized EventDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    EventDatabase.class,
                    "EventDatabase"
            ).build();
        }
        return instance;
    }
}
