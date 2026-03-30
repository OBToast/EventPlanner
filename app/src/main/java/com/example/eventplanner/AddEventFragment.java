package com.example.eventplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddEventFragment extends Fragment {

    private EditText titleText;
    public AddEventFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisFragmentView = inflater.inflate(R.layout.fragment_add_event, container, false);
        Button saveButton = thisFragmentView.findViewById(R.id.saveButton);
        titleText = thisFragmentView.findViewById(R.id.titleEditText);


        return thisFragmentView;
    }

    public void attemptEventInsertion()
    {
        String title = titleText.getText().toString();
        PlannedEvent plannedEvent = new PlannedEvent(title, "yes",3, PlannedEvent.Category.Work);
        EventDatabase db = EventDatabase.getInstance(requireContext());
        db.eventDao().insertPlannedEvent(plannedEvent);
    }
}
