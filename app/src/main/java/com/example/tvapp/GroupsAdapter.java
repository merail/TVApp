package com.example.tvapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsHolder> {
    private final Context context;
    private final GroupsJson[] groups;
    private final OnGroupClickListener onGroupClickListener;

    public GroupsAdapter(Context context, GroupsJson[] groups, OnGroupClickListener onGroupClickListener) {
        this.context = context;
        this.groups = groups;
        this.onGroupClickListener = onGroupClickListener;
    }

    @NonNull
    @Override
    public GroupsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.group, parent, false);

        return new GroupsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsHolder holder, int position) {
        holder.groupNameTextView.setText(groups[position].name);
        holder.groupNameTextView.setOnClickListener(view -> onGroupClickListener.onGroupClick(position));
    }


    @Override
    public int getItemCount() {
        return groups.length;
    }

    public static class GroupsHolder extends RecyclerView.ViewHolder {
        TextView groupNameTextView;

        public GroupsHolder(@NonNull View itemView) {
            super(itemView);

            groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
        }
    }
}
