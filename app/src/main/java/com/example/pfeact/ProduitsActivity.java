package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.MenuItem;

import com.example.pfeact.databinding.ActivityProduitsBinding;
import com.example.pfeact.myClasses.Produit;

import java.util.ArrayList;

public class ProduitsActivity extends AppCompatActivity {

    private ActivityProduitsBinding activityProduitsBinding;
    private DatabaseHelper databaseHelper;
    private ArrayList<Produit> produitArrayList = new ArrayList<>();
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
}