package com.example.eventplanner;

import android.media.metrics.Event;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class AddEventFragment extends Fragment {

    private AppDatabase db;
    public List<PlannedEvent> plannedEvents;
    private EditText titleEditText;
    private Button saveEventButton;
    public AddEventFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View thisFragmentView = inflater.inflate(R.layout.fragment_add_event, container, false);
        Button saveButton = thisFragmentView.findViewById(R.id.saveButton);

        titleEditText = thisFragmentView.findViewById(R.id.titleEditText);
        saveEventButton = thisFragmentView.findViewById(R.id.saveButton);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "event-database").allowMainThreadQueries().build();
        plannedEvents = db.eventDao().getPlannedEvents();

        saveButton.setOnClickListener(v -> {
            attemptEventInsertion();
        });

        return thisFragmentView;
    }


    public void attemptEventInsertion()
    {
        String title = titleEditText.getText().toString();
        db.eventDao().insertPlannedEvent(new PlannedEvent(title, "yes",3,PlannedEvent.Category.Work));
        Toast.makeText(getContext(), "Event saved", Toast.LENGTH_SHORT).show();
    }
}
