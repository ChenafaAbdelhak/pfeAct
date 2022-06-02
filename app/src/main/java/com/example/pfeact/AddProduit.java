package com.example.pfeact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.Famille;
import com.example.pfeact.myClasses.Fournisseur;
import com.example.pfeact.myClasses.Produit;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class AddProduit extends AppCompatActivity {

    private EditText designationET,barcodeET,quantiteET,prixAchatET,prixVenteET;
    private  String designation,barcode;
    private  int quantite, idFamille;
    private  float prixachat,prixVente;
    private ImageView scanBarcodeIV;
    private Spinner familleSpinner,fournisseurSpinner;
    private DatabaseHelper databaseHelper;
    private Fournisseur selectedFournisseur;
    private Famille selectedFamille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // changing the title on action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ajouter un produit");
        }

        designationET = findViewById(R.id.idETDesignationAddProduit);
        barcodeET = findViewById(R.id.idETBarcodeProduitAddProduit);
        quantiteET = findViewById(R.id.idETQuantiteAddProduit);
        prixAchatET = findViewById(R.id.idETPrixAchatAddProduit);
        prixVenteET = findViewById(R.id.idETPrixVenteAddProduit);
        scanBarcodeIV = findViewById(R.id.idIVScanBarcodeAddClient);

        /*
        quantiteET.setText("0");
        prixVenteET.setText("0");
        prixVenteET.setText("0");

         */

        familleSpinner = findViewById(R.id.idSpinnerFamilleAddProduit);
        fournisseurSpinner = findViewById(R.id.idSpinnerFournisseurAddProduit);

        databaseHelper = new DatabaseHelper(this);

        //setting up spinners
        ArrayList<Fournisseur> fournisseurArrayList;
        fournisseurArrayList = databaseHelper.afficherFournisseurs();
        ArrayAdapter<Fournisseur> fournisseurArrayAdapter = new ArrayAdapter<Fournisseur>(this,
                android.R.layout.simple_spinner_item,
                fournisseurArrayList);
        fournisseurArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fournisseurSpinner.setAdapter(fournisseurArrayAdapter);


        ArrayList<Famille> familleArrayList;
        familleArrayList = databaseHelper.afficherFamille();
        ArrayAdapter<Famille> familleArrayAdapter = new ArrayAdapter<Famille>(this,
                android.R.layout.simple_spinner_item,
                familleArrayList);
        familleArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familleSpinner.setAdapter(familleArrayAdapter);
        //spinners

        //add barcode by scanning
        scanBarcodeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(AddProduit.this);
                intentIntegrator.setPrompt("Scan a barcode");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
            }
        });




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
                startActivity(new Intent(this, ProduitsActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.action_settings:

                    checkAndAdd();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //put the scanned barcode in the barcode edittext
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "annulée", Toast.LENGTH_SHORT).show();
            } else {
                String barcodeResult = intentResult.getContents();
                barcodeET.setText(barcodeResult);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkAndAdd(){
        designation = designationET.getText().toString();
        barcode = barcodeET.getText().toString();

        if (designation.matches("")){
            designationET.setError("ce champs doit étre rempli");
            return;
        }
        if (!databaseHelper.isProduitUnique(designation)){
            designationET.setError("ce produit existe déjà, essayez un autre designation!");
            return;
        }
        if (!barcode.matches("")) {
            if (!databaseHelper.isBarcodeUnique(barcode)) {
                barcodeET.setError("ce barcode existe déjà, essayez un autre !");
                return;
            }
        }
        if (quantiteET.getText().toString().matches("")){
            quantiteET.setError("ce champs doit étre rempli");
            return;
        }
        if (prixAchatET.getText().toString().matches("")){
            prixAchatET.setError("ce champs doit étre rempli");
            return;
        }
        if (prixVenteET.getText().toString().matches("")){
            prixVenteET.setError("ce champs doit étre rempli");
            return;
        }

        quantite = Integer.parseInt(quantiteET.getText().toString());
        prixachat = Float.parseFloat(prixAchatET.getText().toString());
        prixVente = Float.parseFloat(prixVenteET.getText().toString());
        selectedFamille = (Famille) familleSpinner.getSelectedItem();
        selectedFournisseur = (Fournisseur) fournisseurSpinner.getSelectedItem();

        if (quantite == 0){

            long res =databaseHelper.ajouterProduit(designation,barcode,quantite,prixachat,prixVente,selectedFamille.getId());
            if (res == -1) {
                Toast.makeText(this, "L'opération d'ajout a échoué, Réeessayer...",Toast.LENGTH_LONG ).show();
            }
            else {
                Toast.makeText(this, "produit ajouté avec succès",Toast.LENGTH_LONG ).show();
                viderChamps();
            }
        }else {
            long res =databaseHelper.ajouterProduit(designation,barcode,quantite,prixachat,prixVente,selectedFamille.getId());
            if (res == -1){
                Toast.makeText(this, "L'opération d'ajout a échoué, Réeessayer...",Toast.LENGTH_LONG ).show();
                return;
            }
            Produit produit = new Produit((int) res,designation ,barcode ,quantite , prixachat, prixVente, idFamille,0);

           int resOp =  databaseHelper.achatParAjoutProduit(selectedFournisseur.getId(), produit.getQte() * produit.getPrixAchat(), produit);

           if (resOp == 1)
               Toast.makeText(this, "produit ajouté avec succès, facture d'achat ajoutée avec succès .",Toast.LENGTH_LONG ).show();
                viderChamps();
        }

    }

    private void viderChamps(){
        designationET.setText("");
        barcodeET.setText("");
        prixAchatET.setText("");
        prixVenteET.setText("");
        quantiteET.setText("");
    }

}