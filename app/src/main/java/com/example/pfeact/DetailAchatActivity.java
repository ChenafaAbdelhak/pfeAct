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

import com.example.pfeact.myClasses.LigneAchat;
import com.example.pfeact.myClasses.LigneAchatAdapter;
import com.example.pfeact.myClasses.LigneVente;
import com.example.pfeact.myClasses.LigneVenteAdapter;
import com.example.pfeact.myClasses.Produit;

import java.util.ArrayList;

public class DetailAchatActivity extends AppCompatActivity {
    private Bundle args;
    private long idFacture;
    private float montantTotal;
    private String dateAchat, heureAchat, nomFournisseur;
    private RecyclerView achatsRV;
    private DatabaseHelper databaseHelper;
    private AlertDialog.Builder builder;
    private LigneAchatAdapter adapter;
    private ArrayList<LigneAchat> ligneAchatArrayList;
    private ArrayList<Produit> produitArrayList;
    private TextView idFactureNfournisseurTv, montantTotalTv, dateHeuretv;
    private Button deleteFactureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_achat);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail facture achat");
        }

        if (getIntent().getExtras() != null) {
            args = getIntent().getExtras();
            idFacture = args.getLong("idFacture");
            montantTotal = args.getFloat("montantTotal");
            nomFournisseur = args.getString("nomFournisseur");
            dateAchat = args.getString("dateAchat");
            heureAchat = args.getString("heureAchat");
        }

        achatsRV = findViewById(R.id.idRVDetailAchat);

        idFactureNfournisseurTv = findViewById(R.id.idTvidFactureAFournisseurDetailAchat);
        montantTotalTv = findViewById(R.id.idTvMontantTotaleDetailAchat);
        dateHeuretv = findViewById(R.id.idTvDateHeureFactureDetailAchat);
        deleteFactureBtn = findViewById(R.id.idBtnSuprimerFactureDetailAchat);
        builder = new AlertDialog.Builder(this);

        idFactureNfournisseurTv.setText("Facture : #" + idFacture + "   Fournisseur: " + nomFournisseur);
        montantTotalTv.setText("" + montantTotal + " DZD");
        dateHeuretv.setText("" + dateAchat + "  " + heureAchat);

        buildRecyclerView();

        deleteFactureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Vous voullez supprimer cette facture ?").setCancelable(true)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList<Produit> produitArrayList = databaseHelper.afficherProduits();
                                databaseHelper.supprimerFactureAchat(idFacture, ligneAchatArrayList, produitArrayList);
                                Toast.makeText(getApplicationContext(), "facture supprimée avec succès", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(DetailAchatActivity.this, AchatsActivity.class));
                                overridePendingTransition(0, 0);
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
                startActivity(new Intent(this, AchatsActivity.class));
                overridePendingTransition(0, 0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildRecyclerView() {


        // below line we are creating a new array list
        databaseHelper = new DatabaseHelper(this);

        ligneAchatArrayList = new ArrayList<>();
        produitArrayList = new ArrayList<>();

        ligneAchatArrayList = databaseHelper.afficherLigneAchat(idFacture);
        produitArrayList = databaseHelper.afficherProduits();
        // initializing our adapter class.
        adapter = new LigneAchatAdapter(ligneAchatArrayList, produitArrayList, DetailAchatActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        achatsRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        achatsRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        achatsRV.setAdapter(adapter);
    }
}