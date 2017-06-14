package com.example.shiro.projectkabanworld_v2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiro.projectkabanworld_v2.Dao.ProjectDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<Project> listTodo = new ArrayList<>();
    public static ArrayList<Project> listDoing = new ArrayList<>();
    public static ArrayList<Project> listDone = new ArrayList<>();
    final ProjectAdaptateur todoAdpat = new ProjectAdaptateur(this, listTodo);
    final ProjectAdaptateur doingAdpat = new ProjectAdaptateur(this, listDoing);
    final ProjectAdaptateur doneAdpat = new ProjectAdaptateur(this, listDone);
    public SQLiteDatabase db;
    public MySQLiteHelper sqLiteHelper;
    private TextView noproject;
    private TextView todonoproject;
    private TextView doingnoproject;
    private TextView donenoproject;
    private TextView nT;
    private TextView nA;
    private TextView nF;
    private ListView lsvTodo;
    private ListView lsvDoing;
    private ListView lsvDone;
    private LinearLayout liTodo;
    private LinearLayout liDoing;
    private LinearLayout liDone;
    private Button add;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noproject = (TextView) findViewById(R.id.noProject);
        donenoproject = (TextView) findViewById(R.id.doneNoproject);
        doingnoproject = (TextView) findViewById(R.id.doingNoproject);
        todonoproject = (TextView) findViewById(R.id.todoNoproject);
        nT = (TextView) findViewById(R.id.nbpT);
        nA = (TextView) findViewById(R.id.nbpE);
        nF = (TextView) findViewById(R.id.nbpF);
        lsvTodo = (ListView) findViewById(R.id.lstViewTodo);
        lsvDoing = (ListView) findViewById(R.id.lstViewDoing);
        lsvDone = (ListView) findViewById(R.id.lstViewDone);

        liTodo = (LinearLayout) findViewById(R.id.LayoutTodo);
        liDoing = (LinearLayout) findViewById(R.id.layoutDoing);
        liDone = (LinearLayout) findViewById(R.id.layoutDone);

        add = (Button) findViewById(R.id.btnAddProjet);

        sqLiteHelper = new MySQLiteHelper(this);
        db = sqLiteHelper.getReadableDatabase();

        final List<Project> lstProject = ProjectDAO.getAll(db);

        if (lstProject.size() == 0) {
            noproject.setVisibility(View.VISIBLE);
            noproject.setText("Aucun projet defini");
            liTodo.setVisibility(View.GONE);
            liDoing.setVisibility(View.GONE);
            liDone.setVisibility(View.GONE);

        } else if (lstProject.size() != 0) {
            noproject.setVisibility(View.INVISIBLE);
            liTodo.setVisibility(View.VISIBLE);
            liDoing.setVisibility(View.VISIBLE);
            liDone.setVisibility(View.VISIBLE);

            if (listTodo.size() == 0) {
                todonoproject.setText("Aucun projet à faire.");
                todonoproject.setVisibility(View.VISIBLE);
                nT.setVisibility(View.INVISIBLE);
                lsvTodo.setVisibility(View.GONE);
            } else {
                todonoproject.setVisibility(View.GONE);
                nT.setVisibility(View.VISIBLE);
                nT.setText("(" + listTodo.size() + ")");
                lsvTodo.setVisibility(View.VISIBLE);
            }

            if (listDoing.size() == 0) {
                doingnoproject.setText("Aucun projet en cours.");
                doingnoproject.setVisibility(View.VISIBLE);
                nA.setVisibility(View.INVISIBLE);
                lsvDoing.setVisibility(View.GONE);
            } else {
                doingnoproject.setVisibility(View.GONE);
                nA.setVisibility(View.VISIBLE);
                nA.setText("(" + listDoing.size() + ")");
                lsvDoing.setVisibility(View.VISIBLE);
            }

            if (listDone.size() == 0) {
                donenoproject.setText("Aucun projet fini.");
                donenoproject.setVisibility(View.VISIBLE);
                nF.setVisibility(View.INVISIBLE);
                lsvDone.setVisibility(View.GONE);
            } else {
                donenoproject.setVisibility(View.GONE);
                nF.setVisibility(View.VISIBLE);
                nF.setText("(" + listDone.size() + ")");
                lsvDone.setVisibility(View.VISIBLE);
            }


            for (int i = 0; i < lstProject.size(); i++) {

                String e = lstProject.get(i).getProjectEtat();

                if (e != null || e.isEmpty()) {
                    if (e.equals("Todo")) {

                        listTodo.add(new Project(lstProject.get(i).getNameProject(), lstProject.get(i).getTxtProject()));

                    } else if (e.equals("Doing")) {

                        listDoing.add(new Project(lstProject.get(i).getNameProject(), lstProject.get(i).getTxtProject()));

                    } else if (e.equals("Done")) {

                        listDone.add(new Project(lstProject.get(i).getNameProject(), lstProject.get(i).getTxtProject()));

                    }
                } else {
                    Toast.makeText(this, "Erreur, projet vide ou null" + lstProject.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        }

        lsvTodo.setAdapter(todoAdpat);
        lsvDoing.setAdapter(doingAdpat);
        lsvDone.setAdapter(doneAdpat);

        AdapterView.OnItemClickListener itemT = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int p = (int) lsvTodo.getItemIdAtPosition(position);

                String n = listTodo.get(p).getNameProject();
                String r = listTodo.get(p).getTxtProject();
                int IDt = ProjectDAO.getID(db, listTodo.get(p).getNameProject());

                Intent projetT = new Intent(v.getContext(), Project.class);

                projetT.putExtra("nameP", n);
                projetT.putExtra("resumeP", r);
                projetT.putExtra("idProjet", IDt);
                projetT.putExtra("etatProj", "A Faire");
                projetT.putExtra("posiProj", +position);

                startActivityForResult(projetT, 4);
            }
        };

        AdapterView.OnItemClickListener itemA = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int p = (int) lsvDoing.getItemIdAtPosition(position);

                String n = listDoing.get(p).getNameProject();
                String r = listDoing.get(p).getTxtProject();
                int IDa = ProjectDAO.getID(db, listDoing.get(p).getNameProject());

                Intent projetA = new Intent(v.getContext(), Project.class);

                projetA.putExtra("nameP", n);
                projetA.putExtra("resumeP", r);
                projetA.putExtra("idProjet", IDa);
                projetA.putExtra("etatProj", "En cours");
                projetA.putExtra("posiProj", +position);

                startActivityForResult(projetA, 5);
            }
        };

        AdapterView.OnItemClickListener itemD = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int p = (int) lsvDone.getItemIdAtPosition(position);

                String n = listDone.get(p).getNameProject();
                String r = listDone.get(p).getTxtProject();
                int IDd = ProjectDAO.getID(db, listDone.get(p).getNameProject());

                Intent projetD = new Intent(v.getContext(), Project.class);

                projetD.putExtra("nameP", n);
                projetD.putExtra("resumeP", r);
                projetD.putExtra("idProjet", IDd);
                projetD.putExtra("etatProj", "Fait");
                projetD.putExtra("posiProj", +position);

                startActivityForResult(projetD, 6);
            }
        };

        lsvTodo.setOnItemClickListener(itemT);
        lsvDoing.setOnItemClickListener(itemA);
        lsvDone.setOnItemClickListener(itemD);

        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProjet = new Intent(v.getContext(), AddProjet.class);
                startActivity(addProjet);
            }
        };

        add.setOnClickListener(addListener);
    }


    public void verifProject() {
        db = sqLiteHelper.getReadableDatabase();
        final List<Project> lsProject = ProjectDAO.getAll(db);
        if (lsProject.size() == 0) {
            noproject.setVisibility(View.VISIBLE);
            noproject.setText("Aucun projet defini");
            liTodo.setVisibility(View.GONE);
            liDoing.setVisibility(View.GONE);
            liDone.setVisibility(View.GONE);

        } else if (lsProject.size() != 0) {
            noproject.setVisibility(View.INVISIBLE);
            liTodo.setVisibility(View.VISIBLE);
            liDoing.setVisibility(View.VISIBLE);
            liDone.setVisibility(View.VISIBLE);

            if (listTodo.size() == 0) {
                todonoproject.setText("Aucun projet à faire.");
                todonoproject.setVisibility(View.VISIBLE);
                nT.setVisibility(View.INVISIBLE);
                lsvTodo.setVisibility(View.GONE);
            } else {
                todonoproject.setVisibility(View.GONE);
                nT.setVisibility(View.VISIBLE);
                nT.setText("(" + listTodo.size() + ")");
                lsvTodo.setVisibility(View.VISIBLE);
            }

            if (listDoing.size() == 0) {
                doingnoproject.setText("Aucun projet en cours.");
                doingnoproject.setVisibility(View.VISIBLE);
                nA.setVisibility(View.INVISIBLE);
                lsvDoing.setVisibility(View.GONE);
            } else {
                doingnoproject.setVisibility(View.GONE);
                nA.setVisibility(View.VISIBLE);
                nA.setText("(" + listDoing.size() + ")");
                lsvDoing.setVisibility(View.VISIBLE);
            }

            if (listDone.size() == 0) {
                donenoproject.setText("Aucun projet fini.");
                donenoproject.setVisibility(View.VISIBLE);
                nF.setVisibility(View.INVISIBLE);
                lsvDone.setVisibility(View.GONE);
            } else {
                donenoproject.setVisibility(View.GONE);
                nF.setVisibility(View.VISIBLE);
                nF.setText("(" + listDone.size() + ")");
                lsvDone.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        todoAdpat.notifyDataSetChanged();
        doingAdpat.notifyDataSetChanged();
        doneAdpat.notifyDataSetChanged();
        verifProject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        todoAdpat.notifyDataSetChanged();
        doingAdpat.notifyDataSetChanged();
        doneAdpat.notifyDataSetChanged();
        verifProject();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        listTodo.clear();
        listDoing.clear();
        listDone.clear();

        todoAdpat.notifyDataSetChanged();
        doingAdpat.notifyDataSetChanged();
        doneAdpat.notifyDataSetChanged();
        verifProject();
    }
}
