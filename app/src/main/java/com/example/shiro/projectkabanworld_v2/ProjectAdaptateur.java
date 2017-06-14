package com.example.shiro.projectkabanworld_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ProjectAdaptateur extends BaseAdapter {

    private Context context;
    private ArrayList<Project> todoProject;


    public ProjectAdaptateur(Context ctx, ArrayList<Project> afProject) {
        this.todoProject = afProject;
        this.context = ctx;
    }

    @Override
    public int getCount() {
        return todoProject.size();
    }

    @Override
    public Object getItem(int position) {
        return todoProject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutLiProject = (LinearLayout) LayoutInflater.from(context).
                inflate(R.layout.project_adaptateur, parent, false);

        Project currentProject = (Project) getItem(position);

        TextView tvProjectName = (TextView)
                layoutLiProject.findViewById(R.id.nomProjet);
        TextView tvProjectResume = (TextView)
                layoutLiProject.findViewById(R.id.resumeProjet);

        layoutLiProject.setBackgroundColor(2);

        tvProjectName.setText(currentProject.getNameProject().toString());
        tvProjectResume.setText(currentProject.getTxtProject().toString());

        return layoutLiProject;

    }
}
