package com.example.shiro.projectkabanworld_v2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiro.projectkabanworld_v2.Dao.TaskDAO;

public class AddTask extends AppCompatActivity {


    public SQLiteDatabase data;
    public MySQLiteHelper sqLiteHelper;
    int IDproj;
    private TextView titreT;
    private EditText nameT;
    private EditText descriptionT;
    private RadioButton btnT;
    private RadioButton btnE;
    private RadioButton btnF;
    private Button btnOk;
    private Button btnCancel;
    private String etatAddTask;
    private String titreAddTask;
    private String resumeAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnT = (RadioButton) findViewById(R.id.rdbAddTodo);
        btnE = (RadioButton) findViewById(R.id.rdbAddDoing);
        btnF = (RadioButton) findViewById(R.id.rdbAddDone);
        btnOk = (Button) findViewById(R.id.btnValid);
        btnCancel = (Button) findViewById(R.id.btnAnnul);
        titreT = (TextView) findViewById(R.id.txtTitre);
        nameT = (EditText) findViewById(R.id.editTxtName);
        descriptionT = (EditText) findViewById(R.id.editTxtResume);

        titreT.setText("Ajouter une Tache");

        Intent id = getIntent();
        IDproj = id.getExtras().getInt("idProj");

        sqLiteHelper = new MySQLiteHelper(this);
        data = sqLiteHelper.getWritableDatabase();

        View.OnClickListener listenAddOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = nameT.getText().toString();
                String d = descriptionT.getText().toString();

                if (n.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Le champ Nom ne peut pas etre vide !", Toast.LENGTH_SHORT).show();
                } else if (d.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Le champ Description ne peut pas etre vide !", Toast.LENGTH_SHORT).show();
                } else {
                    titreAddTask = nameT.getText().toString();
                    resumeAddTask = descriptionT.getText().toString();

                    if (btnT.isChecked()) {
                        etatAddTask = btnT.getText().toString();
                    } else if (btnE.isChecked()) {
                        etatAddTask = btnE.getText().toString();
                    } else {
                        etatAddTask = btnF.getText().toString();
                    }

                    Project.listTache.add(new Task(titreAddTask, resumeAddTask, etatAddTask));
                    TaskDAO.insertTask(data, titreAddTask, resumeAddTask, etatAddTask, IDproj);

                    finish();
                }
            }
        };

        View.OnClickListener ListenNotOK = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        btnOk.setOnClickListener(listenAddOk);
        btnCancel.setOnClickListener(ListenNotOK);
    }
}
