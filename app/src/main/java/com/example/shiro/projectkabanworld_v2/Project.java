package com.example.shiro.projectkabanworld_v2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.shiro.projectkabanworld_v2.Dao.ProjectDAO;
import com.example.shiro.projectkabanworld_v2.Dao.TaskDAO;

import java.util.ArrayList;
import java.util.List;

import static com.example.shiro.projectkabanworld_v2.MainActivity.listDoing;
import static com.example.shiro.projectkabanworld_v2.MainActivity.listDone;
import static com.example.shiro.projectkabanworld_v2.MainActivity.listTodo;


public class Project extends AppCompatActivity {

    public static ArrayList<Task> listTache = new ArrayList<>();
    final TaskAdaptateur lstTask = new TaskAdaptateur(this, listTache);
    public SQLiteDatabase db;
    public SQLiteDatabase data;
    public MySQLiteHelper sqLiteHelper;
    private String nameProject;
    private String txtProject;
    private String projectEtat;
    private int ID;
    private ListView lsvTache;
    private TextView projetN;
    private TextView projetR;
    private TextView tache;
    private TextView etat;
    private TextView notask;
    private RadioButton btnT;
    private RadioButton btnE;
    private RadioButton btnF;
    private Button btnOk;
    private Button btnCancel;
    private Button btnModif;
    private Button btnSuppr;
    private EditText editNameT;
    private EditText editDescriptT;
    private LinearLayout layEtat;
    private LinearLayout layBtn;
    private Button addT;
    private CardView card;
    private EditText editNameP;
    private EditText editDescriptP;

    public Project(String nameProj, String resumeProj, String projEtat) {
        this.nameProject = nameProj;
        this.txtProject = resumeProj;
        this.projectEtat = projEtat;
    }

    public Project(String nameProj, String resumeProj) {
        this.nameProject = nameProj;
        this.txtProject = resumeProj;
    }

    public Project() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projet);

        projetN = (TextView) findViewById(R.id.nomProjet);
        projetR = (TextView) findViewById(R.id.resumeProjet);
        tache = (TextView) findViewById(R.id.txtTache);
        etat = (TextView) findViewById(R.id.etatProj);
        notask = (TextView) findViewById(R.id.txtNotask);

        lsvTache = (ListView) findViewById(R.id.listTache);
        card = (CardView) findViewById(R.id.cardproj);
        addT = (Button) findViewById(R.id.addTask);

        btnT = (RadioButton) findViewById(R.id.rdbTodo);
        btnE = (RadioButton) findViewById(R.id.rdbDoing);
        btnF = (RadioButton) findViewById(R.id.rdbDone);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnModif = (Button) findViewById(R.id.btnModif);
        btnSuppr = (Button) findViewById(R.id.btnSuppr);

        editNameP = (EditText) findViewById(R.id.editTextT);
        editDescriptP = (EditText) findViewById(R.id.editTextR);

        layEtat = (LinearLayout) findViewById(R.id.layoutEtat);
        layBtn = (LinearLayout) findViewById(R.id.layoutBtn);

        sqLiteHelper = new MySQLiteHelper(this);
        db = sqLiteHelper.getReadableDatabase();
        data = sqLiteHelper.getWritableDatabase();

        Intent pn = getIntent();
        String nameP = pn.getExtras().getString("nameP");
        projetN.setText(nameP);

        Intent pr = getIntent();
        String resumeP = pr.getExtras().getString("resumeP");
        projetR.setText(resumeP);

        Intent id = getIntent();
        ID = id.getExtras().getInt("idProjet");

        Intent me = getIntent();
        final String monEtat = me.getExtras().getString("etatProj");
        etat.setText(monEtat);

        Intent p = getIntent();
        final int posi = p.getExtras().getInt("posiProj");


        List<Task> lstTaskDB = TaskDAO.getAll(db, ID);
        if (lstTaskDB.size() == 0) {
            notask.setText("Acune tache n'existe dans ce projet");
            notask.setVisibility(View.VISIBLE);
            listTache.clear();
        } else {
            notask.setVisibility(View.GONE);
            listTache.clear();
            for (int i = 0; i < lstTaskDB.size(); i++) {
                listTache.add(new Task(lstTaskDB.get(i).getNameTask(), lstTaskDB.get(i).getDescription(), lstTaskDB.get(i).getTaskEtat()));
            }
        }

        lsvTache.setAdapter(lstTask);


        final AdapterView.OnItemClickListener itemL = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int p = (int) lstTask.getItemId(position);

                String n = listTache.get(p).getNameTask();
                String r = listTache.get(p).getDescription();
                String e = listTache.get(p).getTaskEtat();
                int IDT = TaskDAO.getIDT(db, listTache.get(p).getNameTask());

                Intent task = new Intent(v.getContext(), Task.class);
                task.putExtra("nameT", n);
                task.putExtra("resumeT", r);
                task.putExtra("etatT", e);
                task.putExtra("position", p);
                task.putExtra("idProjet", ID);
                task.putExtra("idTask", IDT);
                startActivityForResult(task, 3);
            }
        };

        lsvTache.setOnItemClickListener(itemL);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent task = new Intent(v.getContext(), AddTask.class);
                task.putExtra("idProj", ID);
                startActivity(task);

            }
        };

        addT.setOnClickListener(listener);


        View.OnClickListener listenModif = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layEtat.setVisibility(View.VISIBLE);
                layBtn.setVisibility(View.INVISIBLE);
                editNameP.setText(projetN.getText().toString());
                editDescriptP.setText(projetR.getText().toString());
                if (etat.getText().equals(btnT.getText().toString())) {
                    btnT.setChecked(true);
                } else if (etat.getText().equals(btnE.getText().toString())) {
                    btnE.setChecked(true);
                } else if (etat.getText().equals(btnF.getText().toString())) {
                    btnF.setChecked(true);
                }

            }
        };


        View.OnClickListener listenSuppr = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuild = new AlertDialog.Builder(Project.this);
                alertBuild.setTitle("Confirmation de Suppresion");
                alertBuild.setMessage("Souaiter vous vous vraiment supprimer cet element ?");
                alertBuild.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertBuild.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        if (monEtat.equals("A Faire")) {
                            Project del = listTodo.get(posi);
                            listTodo.remove(del);
                            ProjectDAO.deleteProject(db, ID);

                        } else if (monEtat.equals("En cours")) {
                            Project del = listDoing.get(posi);
                            listDoing.remove(del);
                            ProjectDAO.deleteProject(db, ID);

                        } else if (monEtat.equals("Fait")) {
                            Project del = listDone.get(posi);
                            listDone.remove(del);
                            ProjectDAO.deleteProject(db, ID);

                        }

                        finish();
                    }
                });
                alertBuild.show();
            }
        };


        View.OnClickListener listenOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuild2 = new AlertDialog.Builder(Project.this);
                alertBuild2.setTitle("Modification des donnnées");
                alertBuild2.setMessage("Souhaitez vous vraiment appliquer modification ?");
                alertBuild2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertBuild2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        projetN.setText(editNameT.getText().toString());
                        projetR.setText(editDescriptT.getText().toString());

                        if (btnT.isChecked()) {
                            if (btnT.getText().toString() != etat.getText()) {
                                if (etat.getText() == "En cours") {

                                    Project addD = listDoing.get(posi);
                                    listTodo.add(addD);

                                    Project delD = listDoing.get(posi);
                                    listDoing.remove(delD);


                                } else if (etat.getText() == "Fait") {
                                    Project addF = listDone.get(posi);
                                    listTodo.add(addF);

                                    Project delF = listDone.get(posi);
                                    listDone.remove(delF);
                                }

                                etat.setText(btnT.getText());
                            }
                        } else if (btnE.isChecked()) {
                            if (btnE.getText().toString() != etat.getText()) {
                                if (etat.getText() == "A Faire") {

                                    Project addD = listTodo.get(posi);
                                    listDoing.add(addD);

                                    Project delT = listTodo.get(posi);
                                    listTodo.remove(delT);

                                } else if (etat.getText() == "Fait") {
                                    Project addF = listDone.get(posi);
                                    listDoing.add(addF);

                                    Project delF = listDone.get(posi);
                                    listTodo.remove(delF);
                                }
                                etat.setText(btnE.getText());
                            }
                        } else if (btnF.isChecked()) {
                            if (btnT.getText().toString() != etat.getText()) {
                                if (etat.getText() == "En cours") {
                                    Project addD = listDoing.get(posi);
                                    listDone.add(addD);

                                    Project delD = listDoing.get(posi);
                                    listDoing.remove(delD);
                                } else if (etat.getText() == "A Faire") {
                                    Project addD = listTodo.get(posi);
                                    listDone.add(addD);

                                    Project delT = listTodo.get(posi);
                                    listTodo.remove(delT);
                                }
                                etat.setText(btnF.getText());
                            }
                        }
                        layEtat.setVisibility(View.INVISIBLE);
                        layBtn.setVisibility(View.VISIBLE);

                        Intent result = new Intent();
                        result.putExtra("name", projetN.getText().toString());
                        result.putExtra("descript", projetR.getText().toString());
                        result.putExtra("etat", etat.getText().toString());
                        result.putExtra("posi", posi);
                        setResult(Activity.RESULT_OK, result);
                        finish();
                    }
                });
            }
        };


        View.OnClickListener listenNotOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layEtat.setVisibility(View.INVISIBLE);
                layBtn.setVisibility(View.VISIBLE);
            }
        };

        btnModif.setOnClickListener(listenModif);
        btnSuppr.setOnClickListener(listenSuppr);
        btnOk.setOnClickListener(listenOk);
        btnCancel.setOnClickListener(listenNotOk);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dt) {
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {

                String etat = dt.getExtras().getString("etat");
                String nameT = dt.getExtras().getString("name");
                String resT = dt.getExtras().getString("descript");
                int pos = dt.getExtras().getInt("posi");
                listTache.get(pos).setTaskEtat(etat);
                listTache.get(pos).setNameTask(nameT);
                listTache.get(pos).setDescription(resT);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        lstTask.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lstTask.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listTache.clear();
        lstTask.notifyDataSetChanged();
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getTxtProject() {
        return txtProject;
    }

    public void setTxtProject(String txtProject) {
        this.txtProject = txtProject;
    }

    public String getProjectEtat() {
        return projectEtat;
    }

    public void setProjectEtat(String projectEtat) {
        this.projectEtat = projectEtat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
