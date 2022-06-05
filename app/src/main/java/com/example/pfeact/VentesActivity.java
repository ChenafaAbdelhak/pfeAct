package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.ClientAdapter;
import com.example.pfeact.myClasses.FactureVente;
import com.example.pfeact.myClasses.FactureVenteAdapter;
import com.example.pfeact.myClasses.FamilleAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class VentesActivity extends AppCompatActivity implements FactureVenteAdapter.OnFactureVenteListener {

    private ArrayList<FactureVente> factureVenteArrayList;
    private ArrayList<Client> clientArrayList;
    private  DatabaseHelper databaseHelper;
    private FactureVenteAdapter adapter;
    private RecyclerView factureVenteRV;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Ventes");
        }

        factureVenteRV = (RecyclerView)findViewById(R.id.idRVFactureVente);


        factureVenteArrayList = new ArrayList<>();
        clientArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(VentesActivity.this);
        factureVenteArrayList = databaseHelper.afficherFactureVente();
        clientArrayList = databaseHelper.afficherClient();

        //factureVenteArrayList.sort();
        // initializing our adapter class.
        adapter = new FactureVenteAdapter(factureVenteArrayList, VentesActivity.this,this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        factureVenteRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        factureVenteRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        factureVenteRV.setAdapter(adapter);
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


    @Override
    public void onFactureVenteClick(int position) {
        FactureVente factureVente = factureVenteArrayList.get(position);
        //Toast.makeText(getApplicationContext(),"g  "+factureVente.getId(),Toast.LENGTH_LONG).show();
        bundle = new Bundle();
        String nomClient = "";
        bundle.putLong("idFacture",factureVente.getId());
        bundle.putFloat("remise",factureVente.getRemise());
        bundle.putFloat("montantTotal",factureVente.getMontantTotal());
        bundle.putString("dateVente",factureVente.getDateVente());
        bundle.putString("heureVente",factureVente.getHeureVente());
        for (int i=0;i<clientArrayList.size();i++){
            if (factureVente.getIdClient() == clientArrayList.get(i).getId_client())
                nomClient = clientArrayList.get(i).getNomClient();
        }
        bundle.putString("nomClient",nomClient);
        Intent intent = new Intent(VentesActivity.this,DetailVenteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}