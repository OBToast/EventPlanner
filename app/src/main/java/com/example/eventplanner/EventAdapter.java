package com.example.eventplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    //Create a list of planned events
    private List<PlannedEvent> eventList;
    //Get a reference for the viewing fragment where the recycler is
    private ViewEventsFragment fragment;
    //Create a function to return the list of events
    public List<PlannedEvent> getEventsList()
    {
        return eventList;
    }
    //Construct the event adapter
    public EventAdapter(List<PlannedEvent> eventList, ViewEventsFragment fragment) {
        this.eventList = eventList;
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        //Get references to all of the fields required to display a planned event and set them with an events values, depending on our position in the list
        PlannedEvent event = eventList.get(position);
        holder.title.setText(event.getEventTitle());
        holder.location.setText(event.getEventLocation());
        //Format the time in day/month/year format for display
        int[] time = event.getEventTime();
        holder.time.setText(time[2]+"/"+(time[1]+1)+"/"+time[0]);
        holder.category.setText(event.getEventCategory().toString());
        //Assign the functions of the buttons
        holder.deleteButton.setOnClickListener(v -> {fragment.onDeleteClicked(event);});
        holder.editButton.setOnClickListener(v -> fragment.onEditClicked(event));

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }
    public class EventViewHolder extends RecyclerView.ViewHolder{
        //Extend the view holder with the fields we have for planned events
        TextView title, location, time, category;
        ImageButton deleteButton, editButton;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign the values
            title = itemView.findViewById(R.id.titleText);
            location = itemView.findViewById(R.id.locationText);
            time = itemView.findViewById(R.id.timeText);
            category = itemView.findViewById(R.id.categoryText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
    public void updateList(List<PlannedEvent> newList){
        //Update the stored list with the new provided list
        eventList = newList;
        notifyDataSetChanged();
    }
}
