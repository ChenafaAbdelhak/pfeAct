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
import android.renderscript.ScriptGroup;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.databinding.ActivityProduitsBinding;
import com.example.pfeact.myClasses.ClientAdapter;
import com.example.pfeact.myClasses.Famille;
import com.example.pfeact.myClasses.PProduitAdapter;
import com.example.pfeact.myClasses.Produit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProduitsActivity extends AppCompatActivity implements PProduitAdapter.OnProduitListener {

    private ActivityProduitsBinding activityProduitsBinding;
    private DatabaseHelper databaseHelper;
    private RecyclerView produitRV;
    private ArrayList<Produit> produitArrayList = new ArrayList<>();
    private ArrayList<Famille> familleArrayList = new ArrayList<>();
    private FloatingActionButton fabAddProduit ;
    private TextView aucuneResultat;
    private PProduitAdapter produitAdapter;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits);
       // allocateActivityTitle("Produits");
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Produits");
        }
        builder = new AlertDialog.Builder(this);

        aucuneResultat = findViewById(R.id.idTVaucuneResultatProduit);
        aucuneResultat.setVisibility(View.INVISIBLE);
        produitRV = findViewById(R.id.idRVProduit);
        buildRecyclerView();
        fabAddProduit = findViewById(R.id.idFabAddProduit);
        Intent intentFab = new Intent(this, AddProduit.class);
        fabAddProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentFab);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildRecyclerView() {



        // below line we are creating a new array list
        produitArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(ProduitsActivity.this);
        produitArrayList = databaseHelper.afficherProduits();
        familleArrayList = databaseHelper.afficherFamille();
        // initializing our adapter class.
        produitAdapter = new PProduitAdapter(produitArrayList,familleArrayList, ProduitsActivity.this,this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        produitRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        produitRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        produitRV.setAdapter(produitAdapter);
    }

    @Override
    public void onDeleteButtonClick(int position) {

;        if (databaseHelper.isProduitDeleteSafe(produitArrayList.get(position).getId()) == false) {
            Snackbar s = Snackbar.make(produitRV, "ce produit figure dans des facture, vous pouvez pas le supprimer ..", Snackbar.LENGTH_LONG);
            s.show();
            return;
        } else {
            builder.setTitle("Vous voullez supprimer ce produit ?").setCancelable(true)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHelper.supprimerProduit(produitArrayList.get(position).getId());
                            Snackbar s = Snackbar.make(produitRV, "produit supprimée avec succés", Snackbar.LENGTH_LONG);
                            s.show();
                            produitArrayList.remove(position);
                            produitAdapter.notifyItemRemoved(position);
                            //Toast.makeText(getApplicationContext(),"")
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();

        }
    }

    @Override
    public void onEditButtonClick(int position) {

        Bundle bundle = new Bundle();

        Intent intent = new Intent(getApplicationContext(),EditProduitActivity.class);
        bundle.putInt("idProduit",produitArrayList.get(position).getId());
        bundle.putString("designation",produitArrayList.get(position).getDesignation());
        bundle.putString("barcode",produitArrayList.get(position).getCodebarreProduit());
        bundle.putFloat("prixAchat",produitArrayList.get(position).getPrixAchat());
        bundle.putFloat("prixVente",produitArrayList.get(position).getPrixVente());
        bundle.putInt("qte",produitArrayList.get(position).getQte());
        bundle.putInt("idFamille",produitArrayList.get(position).getIdFamille());

        intent.putExtras(bundle);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }
}