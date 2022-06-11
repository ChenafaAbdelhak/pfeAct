package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pfeact.myClasses.Produit;

public class InfoGeneralActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView nombreProduitsTV,montantTotAchatTv,montantTotVenteTv,produitPlusVenduTv
            ,chiffreaffaireAnnelleTv,getChiffreaffaireMoisTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_general);

        databaseHelper = new DatabaseHelper(this);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Informations sur les produits");
        }

        nombreProduitsTV = findViewById(R.id.idTvNombreTotProduits);
        montantTotAchatTv = findViewById(R.id.idTvMontantTotProduitsPAchat);
        montantTotVenteTv = findViewById(R.id.idTvMontantTotProduitsPVente);
        produitPlusVenduTv=findViewById(R.id.idTvProduitPlusVendu);
        chiffreaffaireAnnelleTv = findViewById(R.id.idTvChiffreAffairesAnnuelle);
        getChiffreaffaireMoisTv = findViewById(R.id.idTvChiffreAffairesMois);

        nombreProduitsTV.setText(String.valueOf(databaseHelper.countProduits()));
        montantTotAchatTv.setText(String.valueOf(databaseHelper.getCapitalTotalAchat()));
        montantTotVenteTv.setText(String.valueOf(databaseHelper.getCapitalTotalVente()));
        Produit produit = databaseHelper.getProduitPlusVendu();
        if (produit != null) {
            produitPlusVenduTv.setText(produit.getDesignation() + "    " + produit.getQte() + " fois");
        }else{
            produitPlusVenduTv.setText("");
        }
        chiffreaffaireAnnelleTv.setText(String.valueOf(""+databaseHelper.getChiffreAffaireAnnuelle())+"  DZD");
        getChiffreaffaireMoisTv.setText(String.valueOf(""+databaseHelper.getChiffreAffaireMois())+"   DZD");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, RapportsActivity.class));
                overridePendingTransition(0,0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}