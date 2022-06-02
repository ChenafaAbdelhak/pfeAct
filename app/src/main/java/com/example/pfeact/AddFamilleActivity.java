package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddFamilleActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText nomFamilleET;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_famille);

        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        nomFamilleET = findViewById(R.id.idETnomFamileAddFamille);
        databaseHelper = new DatabaseHelper(this);


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
        if (!databaseHelper.isFamilleUnique(nomFamille)) {
            nomFamilleET.setError("famille déja existe  ");
            return;
        }
        long res = databaseHelper.ajouterFamille(nomFamille);
        if (res == -1) {
            toast.setText("L'opération d'ajout a échoué, Réeessayer...");
            toast.show();
        } else {

            toast.setText("famille ajouté avec succès");
            toast.show();
            startActivity(new Intent(AddFamilleActivity.this,FamilleActivity.class));
        }

    }
}