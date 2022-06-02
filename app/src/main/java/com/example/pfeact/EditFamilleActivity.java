package com.example.pfeact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFamilleActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText nomFamilleET;
    private TextView idFamilleTV;
    private Toast toast;
    private  Bundle bundle;
    private  int idFamille;
    private  String nomFamille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_famille);

        bundle = getIntent().getExtras();
        idFamille = bundle.getInt("idFamille");
        nomFamille = bundle.getString("nomFamille");

        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        nomFamilleET = findViewById(R.id.idETnomFamileEditFamille);
        idFamilleTV = findViewById(R.id.idTvidFamilleEditFamille);
        databaseHelper = new DatabaseHelper(this);

        idFamilleTV.setText("id Famille : "+idFamille);
        nomFamilleET.setText(""+nomFamille);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ClientsActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.action_settings:
                //Toast.makeText(this,"item clicked",Toast.LENGTH_SHORT).show();
                checkAndAdd();


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_client_menu, menu);
        return true;
    }


    private void checkAndAdd() {
        String nomFamille = nomFamilleET.getText().toString();

        if (nomFamille.matches("")) {
            nomFamilleET.setError("ce champs doit étre rempli ");
            return;
        }
        if (!databaseHelper.isFamilleUnique(nomFamille ) && !nomFamille.matches(bundle.getString("nomFamille"))) {
            nomFamilleET.setError("famille déja existe  ");
            return;
        }
        int res = databaseHelper.modifierFamille(idFamille,nomFamille);
        if (res == 0) {
            toast.setText("L'opération de modification  a échoué, Réeessayer...");
            toast.show();
        } else {

            toast.setText("famille modifiée avec succès");
            toast.show();
            startActivity(new Intent(this,FamilleActivity.class));
        }

    }
}