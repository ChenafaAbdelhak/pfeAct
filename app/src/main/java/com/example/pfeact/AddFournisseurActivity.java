package com.example.pfeact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddFournisseurActivity extends AppCompatActivity {

    private EditText nomFournisseurTV, adresseFournisseurTV, phoneFournisseurTV;
    private DatabaseHelper databaseHelper;
    private String a, b, c;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fournisseur);

        nomFournisseurTV = findViewById(R.id.idNomFournisseurAddTV);
        adresseFournisseurTV = findViewById(R.id.idAdresseFournisseurAddTV);
        phoneFournisseurTV = findViewById(R.id.idPhoneFournisseurAddTV);


        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // changing the title on action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ajouter un fournisseur");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_client_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, FournisseurActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.action_settings:
                //Toast.makeText(this,"item clicked",Toast.LENGTH_SHORT).show();
                String a = String.valueOf(nomFournisseurTV.getText());
                String b = String.valueOf(adresseFournisseurTV.getText());
                String c = String.valueOf(phoneFournisseurTV.getText());

                checkAndAdd(a,b,c);


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void viderTV() {
        nomFournisseurTV.setText("");
        adresseFournisseurTV.setText("");
        phoneFournisseurTV.setText("");
        return;
    }

    private void checkAndAdd(String a, String b, String c) {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean n = databaseHelper.isFournisseurUnique(a);


        if (n == false) {
            nomFournisseurTV.setError("ce nom existe déjà, essayez un autre !");
        } else if (a.isEmpty()) {
            nomFournisseurTV.setError("ce champs doit étre rempli");
        } else {
            databaseHelper = new DatabaseHelper(AddFournisseurActivity.this);
            long res = databaseHelper.ajouterFournisseur(a, b, c);
            if (res == -1) {
                toast.setText("L'opération d'ajout a échoué, Réeessayer...");
                toast.show();
            } else {
                toast.setText("Fournisseur ajouté avec succès");
                toast.show();
                viderTV();


            }
        }
    }

}