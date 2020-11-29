package com.example.tvapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramsHolder> {
    private final Context context;
    private final ProgramsJson[] programs;

    public ProgramsAdapter(Context context, ProgramsJson[] programs)
    {
        this.context = context;
        this.programs = programs;
    }

    @NonNull
    @Override
    public ProgramsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.program, parent, false);

        return new ProgramsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramsHolder holder, int position) {
        holder.programTextView.setText(programs[position].name);
    }

    @Override
    public int getItemCount() {
        return programs.length;
    }

    public static class ProgramsHolder extends RecyclerView.ViewHolder
    {
        TextView programTextView;
        public ProgramsHolder(@NonNull View itemView) {
            super(itemView);

            programTextView = itemView.findViewById(R.id.programNameTextView);
        }
    }
}
