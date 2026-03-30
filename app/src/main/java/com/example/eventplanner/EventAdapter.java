package com.example.eventplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<PlannedEvent> eventList;
    private ViewEventsFragment fragment;

    public List<PlannedEvent> getEventsList()
    {
        return eventList;
    }
    public EventAdapter(List<PlannedEvent> eventList, ViewEventsFragment fragment) {
        this.eventList = eventList;
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        PlannedEvent event = eventList.get(position);
        holder.textView.setText(event.getEventTitle());
        //holder.itemView.setOnClickListener(v -> {
        //    fragment.onItemClicked(name);
        //});
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new EventViewHolder(view);
    }
    public class EventViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
    public void updateList(List<PlannedEvent> newList){
        eventList = newList;
        notifyDataSetChanged();
    }
}
