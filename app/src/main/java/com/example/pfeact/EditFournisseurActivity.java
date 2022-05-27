package com.example.pfeact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFournisseurActivity extends AppCompatActivity {
    private EditText nomFournisseurTV,adresseFournisseurTV,phoneFournisseurTV;
    private TextView fournisseurtidTV;
    private DatabaseHelper databaseHelper;
    private   String a,b,c;
    Toast toastMessage;
    int idFournisseur;
    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        bundle = intent.getExtras();
        idFournisseur = bundle.getInt("id");


        setContentView(R.layout.activity_edit_fournisseur);

        toastMessage =Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT);

        fournisseurtidTV = findViewById(R.id.idFournisseurEditId);
        nomFournisseurTV = findViewById(R.id.idNomFournisseurEditTV);
        adresseFournisseurTV = findViewById(R.id.idAdresseFournisseurEditTV);
        phoneFournisseurTV = findViewById(R.id.idPhoneFournisseurEditTV);

        fournisseurtidTV.setText(String.valueOf(idFournisseur));
        nomFournisseurTV.setText(bundle.getString("nom"));
        adresseFournisseurTV.setText(bundle.getString("adresse"));
        phoneFournisseurTV.setText(bundle.getString("phone"));

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // changing the title on action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Modifier un fournisseur");
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
                overridePendingTransition(0,0);
                break;
            case R.id.action_settings:
                //Toast.makeText(this,"item clicked",Toast.LENGTH_SHORT).show();
                String a = String.valueOf(nomFournisseurTV.getText());
                String b = String.valueOf(adresseFournisseurTV.getText());
                String c = String.valueOf(phoneFournisseurTV.getText());

                checkAndEdit(a,b,c);



                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void viderTV(){
        nomFournisseurTV.setText("");
        adresseFournisseurTV.setText("");
        phoneFournisseurTV.setText("");
        return;
    }

    private  void checkAndEdit(String a, String b, String c){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean n = databaseHelper.isFournisseurUnique(a);
        if (idFournisseur == 1){
            toastMessage.setText("Vous pouvez pas modifier ce fournisseur !");
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
            nomFournisseurTV.setError("ce nom existe déjà, essayez un autre !");

        }

        else if (a.isEmpty()){
            nomFournisseurTV.setError("ce champs doit étre rempli");
        }
        else {
            databaseHelper = new DatabaseHelper(EditFournisseurActivity.this);
            boolean res = databaseHelper.modifierFournisseur(idFournisseur,a,b,c);
            if (res == false){
                toastMessage.setText("L'opération de modification a échoué, Réeessayer.");
                toastMessage.show();
            }
            else {
                viderTV();
                toastMessage.setText("Fournisseur modifié avec succès");
                toastMessage.show();
                intent = new Intent(getApplicationContext(),FournisseurActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);



            }
        }
    }

}