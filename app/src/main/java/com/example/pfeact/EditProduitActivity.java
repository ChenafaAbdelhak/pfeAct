package com.example.pfeact;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.myClasses.Famille;
import com.example.pfeact.myClasses.Fournisseur;
import com.example.pfeact.myClasses.Produit;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class EditProduitActivity extends AppCompatActivity {

    private EditText designationET,barcodeET,quantiteET,prixAchatET,prixVenteET;
    private  String designation,barcode;
    private TextView idProduitTv,descForFournisseurSpinner;
    private  int quantite, idFamille, idProduit;
    private  float prixAchat,prixVente;
    private ImageView scanBarcodeIV;
    private Spinner familleSpinner,fournisseurSpinner;
    private DatabaseHelper databaseHelper;
    private Famille selectedFamille;
    private Fournisseur selectedFournisseur;
    private  Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produit);

        bundle = getIntent().getExtras();

        idProduit = bundle.getInt("idProduit");
        designation = bundle.getString("designation");
        barcode = bundle.getString("barcode");
        quantite = bundle.getInt("qte");
        prixAchat = bundle.getFloat("prixAchat");
        prixVente = bundle.getFloat("prixVente");
        idFamille = bundle.getInt("idFamille");




        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        // changing the title on action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Modifier un produit");
        }


        idProduitTv = findViewById(R.id.idTVidProduitEditProduit);
        designationET = findViewById(R.id.idETDesignationEditProduit);
        barcodeET = findViewById(R.id.idETBarcodeProduitEditProduit);
        quantiteET = findViewById(R.id.idETQuantiteEditProduit);
        prixAchatET = findViewById(R.id.idETPrixAchatEditProduit);
        prixVenteET = findViewById(R.id.idETPrixVenteEditProduit);
        scanBarcodeIV = findViewById(R.id.idIVScanBarcodeEditClient);
        descForFournisseurSpinner = findViewById(R.id.idtvTextForFournisseurSpinner);


        familleSpinner = findViewById(R.id.idSpinnerFamilleEditProduit);

        idProduitTv.setText(String.valueOf(idProduit));
        designationET.setText(designation);
        barcodeET.setText(barcode);
        quantiteET.setText(String.valueOf(quantite));
        prixAchatET.setText(String.valueOf(prixAchat));
        prixVenteET.setText(String.valueOf(prixVente));

        quantiteET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (quantiteET.getText().toString().matches("")){

                }
                else if (Integer.parseInt(quantiteET.getText().toString()) > bundle.getInt("qte")){
                    fournisseurSpinner.setVisibility(View.VISIBLE);
                    descForFournisseurSpinner.setVisibility(View.VISIBLE);
                }
                else {
                    fournisseurSpinner.setVisibility(View.INVISIBLE);
                    descForFournisseurSpinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        familleSpinner = findViewById(R.id.idSpinnerFamilleEditProduit);
        fournisseurSpinner = findViewById(R.id.idSpinnerFournisseurEditProduit);

        fournisseurSpinner.setVisibility(View.INVISIBLE);
        descForFournisseurSpinner.setVisibility(View.INVISIBLE);


        databaseHelper = new DatabaseHelper(this);

        ArrayList<Famille> familleArrayList;
        familleArrayList = databaseHelper.afficherFamille();

        int indexCurrentFamilly=-1;

        for (int i=0;i<familleArrayList.size();i++){
            if (familleArrayList.get(i).getId() == idFamille){
                indexCurrentFamilly = i ;
            }
        }

        ArrayAdapter<Famille> familleArrayAdapter = new ArrayAdapter<Famille>(this,
                android.R.layout.simple_spinner_item,
                familleArrayList);
        familleArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familleSpinner.setAdapter(familleArrayAdapter);
        familleSpinner.setSelection(indexCurrentFamilly);

        ArrayList<Fournisseur> fournisseurArrayList;
        fournisseurArrayList = databaseHelper.afficherFournisseurs();
        ArrayAdapter<Fournisseur> fournisseurArrayAdapter = new ArrayAdapter<Fournisseur>(this,
                android.R.layout.simple_spinner_item,
                fournisseurArrayList);
        fournisseurArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fournisseurSpinner.setAdapter(fournisseurArrayAdapter);
        ///////////

        scanBarcodeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(EditProduitActivity.this);
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

                checkAndEdit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    private  void  checkAndEdit (){
        designation = designationET.getText().toString();
        barcode = barcodeET.getText().toString();

        if (designation.matches("")){
            designationET.setError("ce champs doit étre rempli");
            return;
        }
        if (!databaseHelper.isProduitUnique(designation) && !(designation.equals(bundle.get("designation")))){
            designationET.setError("ce produit existe déjà, essayez un autre designation!");
            return;
        }
        if (!barcode.matches("")) {
            if (!databaseHelper.isBarcodeUnique(barcode) && !(barcode.equals(bundle.getString("barcode")))) {
                barcodeET.setError("ce barcode existe déjà, essayez un autre !");
                return;
            }
        }
        if (quantiteET.getText().toString().matches("")){
            quantiteET.setError("ce champs doit étre rempli");
            return;
        }
        if (Integer.parseInt(quantiteET.getText().toString()) < bundle.getInt("qte")){
            quantiteET.setError("vous pouvez pas diminuer la quantité");
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
        prixAchat = Float.parseFloat(prixAchatET.getText().toString());
        prixVente = Float.parseFloat(prixVenteET.getText().toString());
        selectedFamille = (Famille) familleSpinner.getSelectedItem();

        if (quantite == bundle.getInt("qte")){
            if (barcode.matches(""))
                barcode = null;
            int res = databaseHelper.modifierProduit(idProduit,designation,barcode,quantite,prixAchat,prixVente,selectedFamille.getId());
            if (res == 0) {
                Toast.makeText(this, "L'opération de modification a échoué, Réeessayer...",Toast.LENGTH_LONG ).show();
            }
            else {
                Toast.makeText(this, "produit modifié avec succès",Toast.LENGTH_LONG ).show();

            }
        }else {
            selectedFournisseur = (Fournisseur) fournisseurSpinner.getSelectedItem();
            int qteAjoutee = quantite - bundle.getInt("qte");
            float prixAchatPonderee = ((prixAchat * qteAjoutee)+ (bundle.getFloat("prixAchat") * bundle.getInt("qte")))/quantite;
            long res =databaseHelper.modifierProduit(idProduit,designation,barcode,quantite,prixAchatPonderee,prixVente,selectedFamille.getId());
            if (res == -1){
                Toast.makeText(this, "L'opération de modification a échoué, Réeessayer...",Toast.LENGTH_LONG ).show();
                return;
            }
            Produit produit = new Produit(idProduit,designation ,barcode ,quantite , prixAchat, prixVente, idFamille,0);

            int resOp =  databaseHelper.achatParModificationProduit(selectedFournisseur.getId(),  produit, qteAjoutee);

            if (resOp == 1)
                Toast.makeText(this, "achat effectuer avec succès ",Toast.LENGTH_LONG ).show();
            Intent intent = new Intent(getApplicationContext(), ProduitsActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }

    }

}