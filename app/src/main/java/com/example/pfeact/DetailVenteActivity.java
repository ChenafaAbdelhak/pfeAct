package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.myClasses.ClientAdapter;
import com.example.pfeact.myClasses.LigneVente;
import com.example.pfeact.myClasses.LigneVenteAdapter;
import com.example.pfeact.myClasses.Produit;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DetailVenteActivity extends AppCompatActivity {

    private Bundle args;
    private long idFacture;
    private float remise,montantTotal;
    private String dateVente,heureVente,nomClient;
    private RecyclerView ventesRV;
    private DatabaseHelper databaseHelper;
    private AlertDialog.Builder builder;
    private LigneVenteAdapter adapter;
    private ArrayList<LigneVente> ligneVenteArrayList;
    private ArrayList<Produit> produitArrayList;
    private TextView idFactureNclientTv,remiseTv,montantTotalTv,dateHeuretv;
    private Button deleteFactureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vente);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail facture");
        }

        if (getIntent().getExtras() != null){
        args = getIntent().getExtras();
        idFacture = args.getLong("idFacture");
        remise = args.getFloat("remise");
        montantTotal = args.getFloat("montantTotal");
        nomClient = args.getString("nomClient");
        dateVente = args.getString("dateVente");
        heureVente = args.getString("heureVente");}

        ventesRV = findViewById(R.id.idRVDetailVente);

        idFactureNclientTv = findViewById(R.id.idTvidFactureVClientDetailVente);
        remiseTv = findViewById(R.id.idTvRemiseDetailVente);
        montantTotalTv = findViewById(R.id.idTvMontantTotaleDetailVente);
        dateHeuretv = findViewById(R.id.idTvDateHeureFactureDetailVente);
        deleteFactureBtn = findViewById(R.id.idBtnSuprimerFactureDetailVente);
        builder = new AlertDialog.Builder(this);

        idFactureNclientTv.setText("#"+idFacture+"  par "+nomClient);
        remiseTv.setText("Remise : "+remise+" DZD");
        montantTotalTv.setText(""+montantTotal+" DZD");
        dateHeuretv.setText(""+dateVente+"  "+heureVente);

        buildRecyclerView();

        deleteFactureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Vous voullez supprimer cette facture ?").setCancelable(true)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList<Produit> produitArrayList = databaseHelper.afficherProduits();
                                databaseHelper.supprimerFactureVente(idFacture,ligneVenteArrayList,produitArrayList);
                                Toast.makeText(getApplicationContext(),"facture supprimée avec succès",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(DetailVenteActivity.this, VentesActivity.class));
                                overridePendingTransition(0,0);
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, VentesActivity.class));
                overridePendingTransition(0,0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildRecyclerView() {



        // below line we are creating a new array list
        databaseHelper = new DatabaseHelper(this);

        ligneVenteArrayList = new ArrayList<>();
        produitArrayList = new ArrayList<>();

        ligneVenteArrayList = databaseHelper.afficherLigneVente(idFacture);
        produitArrayList = databaseHelper.afficherProduits();
        // initializing our adapter class.
        adapter = new LigneVenteAdapter(ligneVenteArrayList, produitArrayList,DetailVenteActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ventesRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        ventesRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        ventesRV.setAdapter(adapter);
    }
}