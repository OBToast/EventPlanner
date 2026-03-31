package com.example.eventplanner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;

public class AddEventFragment extends Fragment {

    //Create fields for the database and a list of all of the planned events
    private AppDatabase db;
    public List<PlannedEvent> plannedEvents;
    //Create references for the fields
    private EditText titleEditText;
    private EditText locationEditText;
    private DatePicker datePicker;
    private Spinner categoiresSpinner;
    private Button saveEventButton;
    //Get the current time, so we can check if the event is going to be in the future or past
    private Calendar now = Calendar.getInstance();
    //Get the nav bar so we can disable it if we are updating an entry
    private BottomNavigationView bottomNavBar;

    private int id = -1;
    public AddEventFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisFragmentView = inflater.inflate(R.layout.fragment_add_event, container, false);

        //Assume this is a new entry, override later on if it is not
        saveEventButton = thisFragmentView.findViewById(R.id.saveButton);
        saveEventButton.setText("Save new event");
        //Get the bottom nav bar so we can disable navigation while updating entries
        bottomNavBar = getActivity().findViewById(R.id.bottomNavigationView);

        //Link the references for the fields
        titleEditText = thisFragmentView.findViewById(R.id.titleEditText);
        locationEditText = thisFragmentView.findViewById(R.id.locationEditText);
        datePicker = thisFragmentView.findViewById(R.id.datePicker);
        categoiresSpinner = thisFragmentView.findViewById(R.id.categoiresSpinner);

        //Calculate the current time
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        //Create an array adapter to adapt the categories for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.categories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Assign the array adapter to the spinner
        categoiresSpinner.setAdapter(adapter);

        //Get the arguments if we have come here from editing an entry in the view area
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            //If the arguments are not null, that means we are updating an entry in the database
            //Get the values of the planned event from the bundle
            //Get the ID of the entry we are updating
            id = getArguments().getInt("id", -1);
            //Get the title
            String title = arguments.getString("title");
            //Location
            String location = arguments.getString("location");
            //Day, month and year
            int day = arguments.getInt("day");
            int month = arguments.getInt("month");
            int year = arguments.getInt("year");
            //And the category
            PlannedEvent.Category category = (PlannedEvent.Category) getArguments().getSerializable("category");

            //Update entry fields to show existing values for the entry
            titleEditText.setText(title);
            locationEditText.setText(location);
            datePicker.updateDate(year,month,day);
            categoiresSpinner.setSelection(adapter.getPosition(category.toString()));

            saveEventButton.setText("Update event");
            bottomNavBar.setEnabled(false);
        }
        else
        {
            //If the arguments are null, we are creating a new entry in the database, set the id to -1 so we know its a new entry
            id = -1;
        }

        //Build the database if it has not already been built
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "event-database").allowMainThreadQueries().build();
        //Set the list of planned events to be the planned events stored in the database
        plannedEvents = db.eventDao().getPlannedEvents();

        //Set the function of the save button to save updates
        saveEventButton.setOnClickListener(v -> {
            attemptEventInsertionOrUpdate(id);
        });

        return thisFragmentView;
    }


    public void attemptEventInsertionOrUpdate(int id)
    {
        //Save the values of the input fields
        String title = titleEditText.getText().toString();
        String location = locationEditText.getText().toString();
        Calendar selected = Calendar.getInstance();
        selected.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),0,0,0);

        //Check that the selected date is in the future
        if (selected.after(now)) {
            //Convert the value of the spinner into a category
            PlannedEvent.Category category = PlannedEvent.Category.valueOf(categoiresSpinner.getSelectedItem().toString());

            //Check if the title is empty
            if(title.isEmpty())
            {
                //Alert the user the title is empty
                Toast.makeText(getContext(), "Please add a title", Toast.LENGTH_SHORT).show();
                return;
            }
            //Check if location is empty
            if(location.isEmpty())
            {
                //Alert user location is empty
                Toast.makeText(getContext(), "Please add a location", Toast.LENGTH_SHORT).show();
                return;
            }
            //If checks have been passed, form a new Planned Event ready for entry into the database
            PlannedEvent event = new PlannedEvent(title, location, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), category);

            //If the id is -1, then that means this is a new entry, and as such we insert it into the database
            if(id == -1)
            {

                db.eventDao().insertPlannedEvent(event);
                Toast.makeText(getContext(), "Event saved", Toast.LENGTH_SHORT).show();
            } else {
                //If the id is not -1, then this is an existing entry, and we need to update the entry in the database
                //Set the id of the event to the existing id, so we update the right entry
                event.setId(id);
                //Update the event
                db.eventDao().updatePlannedEvent(event);
                //Display confirmation
                Toast.makeText(getContext(), "Event updated", Toast.LENGTH_SHORT).show();
                //Reenable nav bar navigation
                NavController navController = Navigation.findNavController(requireView());
                bottomNavBar.setEnabled(true);
                //Pop this fragment, and go back to the viewing one
                navController.popBackStack();
            }

        } else {
            //If the date is in the past send a toast message saying so
            Toast.makeText(getContext(), "Please pick a date in the future", Toast.LENGTH_SHORT).show();
        }







    }
}
