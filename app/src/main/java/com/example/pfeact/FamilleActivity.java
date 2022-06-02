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
import android.widget.Toast;

import com.example.pfeact.myClasses.Famille;
import com.example.pfeact.myClasses.FamilleAdapter;
import com.example.pfeact.myClasses.PProduitAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FamilleActivity extends AppCompatActivity implements FamilleAdapter.OnFamilleListener {

    private ArrayList<Famille> familleArrayList;
    private RecyclerView familleRV;
    private FloatingActionButton fabAddFamille ;
    private DatabaseHelper databaseHelper;
    private FamilleAdapter familleAdapter;
    private  Toast toast;
    private AlertDialog.Builder builder;
    private  Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famille);


        builder = new AlertDialog.Builder(this);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Familles");
        }

        familleRV = findViewById(R.id.idRVFamille);
        fabAddFamille = findViewById(R.id.idFabAddFamille);
        databaseHelper = new DatabaseHelper(this);

        familleArrayList = new ArrayList<>();
        familleArrayList = databaseHelper.afficherFamille();
        fabAddFamille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FamilleActivity.this,AddFamilleActivity.class));
            }
        });

        buildRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ParametresActivity.class));
                overridePendingTransition(0,0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void buildRecyclerView() {



        // below line we are creating a new array list


        // initializing our adapter class.
        familleAdapter = new FamilleAdapter(familleArrayList, this,this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        familleRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        familleRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        familleRV.setAdapter(familleAdapter);
    }

    @Override
    public void onDeleteButtonClick(int position) {

        if (familleArrayList.get(position).getId() == 1){
            toast.setText("Vous pouvez pas supprimer cette famille (Famille par défaut)!");
            toast.show();
        }else if(!databaseHelper.isFamilleDeleteSafe(familleArrayList.get(position).getId())){
            toast.setText("Vous pouvez pas supprimer cette famille (il y a des produit appartient a cette famille)");
            toast.show();
        }else {
            builder.setTitle("Vous voullez supprimer cette famille ?").setCancelable(true)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHelper.supprimerFamille(familleArrayList.get(position).getId());
                            toast.setText("famille supprimée avec succès .");
                            toast.show();
                            familleArrayList.remove(position);
                            familleAdapter.notifyItemRemoved(position);
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
        bundle = new Bundle();

        Intent intent = new Intent(getApplicationContext(),EditFamilleActivity.class);
        bundle.putInt("idFamille",familleArrayList.get(position).getId());
        bundle.putString("nomFamille",familleArrayList.get(position).getNomFamille());
        intent.putExtras(bundle);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }
}