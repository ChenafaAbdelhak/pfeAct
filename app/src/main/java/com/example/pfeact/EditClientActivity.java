package com.example.pfeact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.myClasses.Client;
import com.google.android.material.snackbar.Snackbar;

public class EditClientActivity extends AppCompatActivity {

    private EditText nomClientTV,adresseClientTV,phoneClientTV;
    private TextView clientidTV;
    private DatabaseHelper databaseHelper;
    private   String a,b,c;
    Toast toastMessage;
    int idClient;
    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        bundle = intent.getExtras();
        idClient = bundle.getInt("id");


        setContentView(R.layout.activity_edit_client);

        toastMessage =Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT);

        clientidTV = findViewById(R.id.idClientEditId);
        nomClientTV = findViewById(R.id.idNomClientEditTV);
        adresseClientTV = findViewById(R.id.idAdresseClientEditTV);
        phoneClientTV = findViewById(R.id.idPhoneClientEditTV);

        clientidTV.setText(String.valueOf(idClient));
        nomClientTV.setText(bundle.getString("nom"));
        adresseClientTV.setText(bundle.getString("adresse"));
        phoneClientTV.setText(bundle.getString("phone"));

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // changing the title on action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Modifier un client");
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
                String a = String.valueOf(nomClientTV.getText().toString());
                String b = String.valueOf(adresseClientTV.getText().toString());
                String c = String.valueOf(phoneClientTV.getText().toString());

                checkAndEdit(a,b,c);



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

    private  void checkAndEdit(String a, String b, String c){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean n = databaseHelper.isClientUnique(a);
        if (idClient == 1){
            toastMessage.setText("Vous pouvez pas modifier ce client !");
            toastMessage.show();
           // Snackbar s = Snackbar.make(nomClientTV,"",Snackbar.LENGTH_LONG);
           // s.show();
        }

        else if (a==bundle.getString("nom") && b==bundle.getString("adresse") && c==bundle.getString("phone")){
            toastMessage.setText("Vous avez entrer les mémes informations");
            toastMessage.show();
        }

        else if(n==false && !(a.equals(bundle.get("nom"))))
        {
            nomClientTV.setError("ce nom existe déjà, essayez un autre !");

        }

        else if (a.isEmpty()){
            nomClientTV.setError("ce champs doit étre rempli");
        }
        else {
            databaseHelper = new DatabaseHelper(EditClientActivity.this);
            boolean res = databaseHelper.modifierClient(idClient,a,b,c);
            if (res == false){
                toastMessage.setText("L'opération de modification a échoué, Réeessayer.");
                toastMessage.show();
                }
            else {
                viderTV();
                toastMessage.setText("Client modifié avec succès");
                toastMessage.show();
                intent = new Intent(getApplicationContext(),ClientsActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);



            }
        }
    }

}