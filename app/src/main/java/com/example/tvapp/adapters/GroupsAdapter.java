package com.example.tvapp.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapp.MainActivity;
import com.example.tvapp.R;
import com.example.tvapp.interfaces.OnGroupClickListener;
import com.example.tvapp.server.GroupsJson;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsHolder> {
    private final Context context;
    private final GroupsJson[] groups;
    private final OnGroupClickListener onGroupClickListener;

    private int previousPosition = 0;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull GroupsHolder holder, int position) {
        if (MainActivity.currentPosition == position) {
            holder.groupNameTextView.setBackgroundColor(context.getColor(R.color.teal_700));
        } else
            holder.groupNameTextView.setBackgroundColor(context.getColor(R.color.black));


        holder.groupNameTextView.setText(groups[position].name);
        holder.groupNameTextView.setOnClickListener(view -> {
            onGroupClickListener.onGroupClick(position);

            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return groups.length;
    }

    public void onChangePosition() {
        notifyItemChanged(previousPosition);
        notifyItemChanged(MainActivity.currentPosition);
        previousPosition = MainActivity.currentPosition;
    }

    public static class GroupsHolder extends RecyclerView.ViewHolder {
        TextView groupNameTextView;

        public GroupsHolder(@NonNull View itemView) {
            super(itemView);

            groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
        }


    }
}
