package com.example.pfeact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddClientActivity extends AppCompatActivity {

    private EditText nomClientTV,adresseClientTV,phoneClientTV;
    private DatabaseHelper databaseHelper;
    private   String a,b,c;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        nomClientTV = findViewById(R.id.idNomClientAddTV);
        adresseClientTV = findViewById(R.id.idAdresseClientAddTV);
        phoneClientTV = findViewById(R.id.idPhoneClientAddTV);


        toast = Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // changing the title on action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ajouter un client");
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
                startActivity(new Intent(this, ClientsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.action_settings:
                //Toast.makeText(this,"item clicked",Toast.LENGTH_SHORT).show();
                String a= String.valueOf(nomClientTV.getText());
                String b = String.valueOf(adresseClientTV.getText());
                String c = String.valueOf(phoneClientTV.getText());

                checkAndAdd(a,b,c);



                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void viderTV(){
        nomClientTV.setText("");
        adresseClientTV.setText("");
        phoneClientTV.setText("");
        return;
    }

    private  void checkAndAdd(String a, String b, String c){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean n = databaseHelper.isClientUnique(a);


        if(n==false)
        {
            nomClientTV.setError("ce nom existe déjà, essayez un autre !");
        }
        else if (a.isEmpty()){
            nomClientTV.setError("ce champs doit étre rempli");
        }
        else {
            databaseHelper = new DatabaseHelper(AddClientActivity.this);
            long res = databaseHelper.ajouterClient(a,b,c);
            if (res == -1){
                toast.setText("L'opération d'ajout a échoué, Réeessayer...");
                toast.show();
                }
            else {

                toast.setText(a+b+c);
                toast.show();
                viderTV();


            }
        }
    }

}