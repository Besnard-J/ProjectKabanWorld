package com.example.shiro.projectkabanworld_v2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class TaskAdaptateur extends BaseAdapter {

    private Context context;
    private ArrayList<Task> taches;

    public TaskAdaptateur(Context ctx, ArrayList<Task> tasks) {
        this.taches = tasks;
        this.context = ctx;

    }

    @Override
    public int getCount() {
        return taches.size();
    }

    @Override
    public Object getItem(int position) {
        return taches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutLiTask = (LinearLayout) LayoutInflater.from(context).
                inflate(R.layout.taches_adaptateur, parent, false);

        Task currentTask = (Task) getItem(position);

        TextView tvTaskName = (TextView)
                layoutLiTask.findViewById(R.id.nomTask);
        TextView tvTaskResume = (TextView)
                layoutLiTask.findViewById(R.id.resumeTask);
        TextView tvEtatTask = (TextView)
                layoutLiTask.findViewById(R.id.etatTask);

        tvTaskName.setText(currentTask.getNameTask().toString());
        tvTaskResume.setText(currentTask.getDescription().toString());
        tvEtatTask.setText(currentTask.getTaskEtat().toString());

        return layoutLiTask;
    }
}
