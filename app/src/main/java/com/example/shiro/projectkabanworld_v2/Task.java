package com.example.shiro.projectkabanworld_v2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.shiro.projectkabanworld_v2.Dao.TaskDAO;


public class Task extends AppCompatActivity {

    public SQLiteDatabase data;
    public MySQLiteHelper sqLiteHelper;
    private String nameTask;
    private String description;
    private String taskEtat;
    private int taskID = 0;
    private TextView taskN;
    private TextView taskR;
    private TextView taskE;
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

    public Task(String nameT, String resume, String taskE) {
        this.description = resume;
        this.nameTask = nameT;
        this.taskEtat = taskE;
    }

    public Task() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taches);

        taskN = (TextView) findViewById(R.id.nomTask);
        taskR = (TextView) findViewById(R.id.resumeTask);
        taskE = (TextView) findViewById(R.id.etatTask);
        btnT = (RadioButton) findViewById(R.id.rdbTodo);
        btnE = (RadioButton) findViewById(R.id.rdbDoing);
        btnF = (RadioButton) findViewById(R.id.rdbDone);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnModif = (Button) findViewById(R.id.btnModif);
        btnSuppr = (Button) findViewById(R.id.btnSuppr);

        editNameT = (EditText) findViewById(R.id.editTextTi);
        editDescriptT = (EditText) findViewById(R.id.editTextRe);

        layEtat = (LinearLayout) findViewById(R.id.layoutEtat);
        layBtn = (LinearLayout) findViewById(R.id.layoutBtn);

        Intent tn = getIntent();
        final String nameT = tn.getExtras().getString("nameT");
        taskN.setText(nameT);

        Intent tr = getIntent();
        final String resumeT = tr.getExtras().getString("resumeT");
        taskR.setText(resumeT);

        Intent te = getIntent();
        String etatT = te.getExtras().getString("etatT");
        taskE.setText(etatT);

        Intent id = getIntent();
        final int ID = id.getExtras().getInt("idProjet");

        Intent idt = getIntent();
        taskID = idt.getExtras().getInt("idTask");

        final Intent p = getIntent();
        final int pos = (p.getExtras().getInt("position"));

        sqLiteHelper = new MySQLiteHelper(this);
        data = sqLiteHelper.getWritableDatabase();

        View.OnClickListener listenModif = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layEtat.setVisibility(View.VISIBLE);
                layBtn.setVisibility(View.INVISIBLE);
                editNameT.setText(taskN.getText().toString());
                editDescriptT.setText(taskR.getText().toString());
                if (taskE.getText().equals(btnT.getText())) {
                    btnT.setChecked(true);
                } else if (taskE.getText().equals(btnE.getText())) {
                    btnE.setChecked(true);
                } else if (taskE.getText().equals(btnF.getText())) {
                    btnF.setChecked(true);
                }
            }
        };

        View.OnClickListener listenSuppr = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuild = new AlertDialog.Builder(Task.this);
                alertBuild.setTitle("Confirmation de Suppresion");
                alertBuild.setMessage("Souaiter vous vous vraiment supprimer cet element ?");
                alertBuild.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertBuild.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Task del = Project.listTache.get(pos);
                        Project.listTache.remove(del);
                        TaskDAO.deleteTask(data, ID, nameT);
                        finish();

                    }
                });
                alertBuild.show();


            }
        };

        View.OnClickListener listenOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuild2 = new AlertDialog.Builder(Task.this);
                alertBuild2.setTitle("Modification des donnnées");
                alertBuild2.setMessage("Souhaitez vous vraiment appliquer modification ?");
                alertBuild2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });
                alertBuild2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        taskN.setText(editNameT.getText().toString());
                        taskR.setText(editDescriptT.getText().toString());

                        if (btnT.isChecked()) {
                            taskE.setText(btnT.getText());
                        } else if (btnE.isChecked()) {
                            taskE.setText(btnE.getText());
                        } else {
                            taskE.setText(btnF.getText());
                        }
                        layEtat.setVisibility(View.INVISIBLE);
                        layBtn.setVisibility(View.VISIBLE);

                        Intent result = new Intent();
                        result.putExtra("etat", taskE.getText().toString());
                        result.putExtra("name", taskN.getText().toString());
                        result.putExtra("descript", taskR.getText().toString());
                        result.putExtra("posi", pos);
                        setResult(Activity.RESULT_OK, result);

                        TaskDAO.modifTable(data, taskN.getText().toString(), taskR.getText().toString(), taskE.getText().toString(), taskID);
                        ;
                    }
                });
                alertBuild2.show();
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getTaskEtat() {
        return taskEtat;
    }

    public void setTaskEtat(String taskEtat) {
        this.taskEtat = taskEtat;
    }


}
