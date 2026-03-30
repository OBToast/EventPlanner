package com.example.eventplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;


public class ViewEventsFragment extends Fragment {

    public ViewEventsFragment(){

    }

    private EditText titleText;
    private List<PlannedEvent> plannedEvents;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private AppDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View thisFragmentView = inflater.inflate(R.layout.fragment_view_event, container, false);
        recyclerView = thisFragmentView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(eventAdapter);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "event-database").allowMainThreadQueries().build();
        plannedEvents = db.eventDao().getPlannedEvents();
        eventAdapter = new EventAdapter(plannedEvents, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventAdapter);

        return thisFragmentView;
    }

    private void loadEvents() {
        plannedEvents = db.eventDao().getPlannedEvents();
        eventAdapter.updateList(plannedEvents);
    }



}