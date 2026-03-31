package com.example.eventplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        //Get the database and DAO
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "event-database").allowMainThreadQueries().build();
        plannedEvents = db.eventDao().getPlannedEvents();

        //Get the recycler, pair it with the adapter
        eventAdapter = new EventAdapter(plannedEvents, this);
        recyclerView = thisFragmentView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(eventAdapter);

        //Set the recycler to a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return thisFragmentView;
    }

    private void loadEvents() {
        //Update events
        plannedEvents = db.eventDao().getPlannedEvents();
        eventAdapter.updateList(plannedEvents);
    }

    public void onDeleteClicked(PlannedEvent event){
        //Delete an event through the DAO and send a confirmation message, reload the list of events
        db.eventDao().deletePlannedEvent(event);
        Toast.makeText(getContext(), event.title + " deleted", Toast.LENGTH_SHORT).show();
        loadEvents();
    }
    public void onEditClicked(PlannedEvent event) {
        //Create a new bundle, with all of the data from the event
        Bundle bundle = new Bundle();
        bundle.putString("title",event.title);
        bundle.putString("location",event.location);
        bundle.putInt("year",event.year);
        bundle.putInt("month",event.month);
        bundle.putInt("day",event.day);
        bundle.putSerializable("category",event.category);
        bundle.putInt("id",event.id);

        //Pass that bundle to the other fragment so it knows what event we're editing
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.addEventFragment, bundle);
        //It only occurs to me now that I am writing these code comments, that there is no need to pass the whole event through a bundle,
        //Why not just pass the ID and then reconstruct the event from the database on the other side? Oh well. This is working reliably now.

        //Disable the nav bar so they cant leave midway through editing an event
        BottomNavigationView bottomNavBar = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavBar.setEnabled(false);

    }



}