package com.example.shiro.projectkabanworld_v2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiro.projectkabanworld_v2.Dao.ProjectDAO;

import static com.example.shiro.projectkabanworld_v2.MainActivity.listDoing;
import static com.example.shiro.projectkabanworld_v2.MainActivity.listDone;
import static com.example.shiro.projectkabanworld_v2.MainActivity.listTodo;

public class AddProjet extends AppCompatActivity {

    public SQLiteDatabase data;
    public MySQLiteHelper sqLiteHelper;
    private TextView titreT;
    private EditText nameT;
    private EditText descriptionT;
    private RadioButton btnT;
    private RadioButton btnE;
    private RadioButton btnF;
    private Button btnOk;
    private Button btnCancel;
    private String etatAddProj;
    private String titreAddProj;
    private String resumeAddProj;
    private RelativeLayout layPrincipal;

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
        layPrincipal = (RelativeLayout) findViewById(R.id.activity_add);

        titreT.setText("Ajouter un Project");
        layPrincipal.setBackgroundColor(5);

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
                    titreAddProj = nameT.getText().toString();
                    resumeAddProj = descriptionT.getText().toString();

                    if (btnT.isChecked()) {
                        etatAddProj = btnT.getText().toString();
                        listTodo.add(new Project(titreAddProj, resumeAddProj));
                    } else if (btnE.isChecked()) {
                        etatAddProj = btnE.getText().toString();
                        listDoing.add(new Project(titreAddProj, resumeAddProj));
                    } else if (btnF.isChecked()) {
                        etatAddProj = btnF.getText().toString();
                        listDone.add(new Project(titreAddProj, resumeAddProj));
                    }

                    ProjectDAO.insertProject(data, titreAddProj, resumeAddProj, etatAddProj);

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
