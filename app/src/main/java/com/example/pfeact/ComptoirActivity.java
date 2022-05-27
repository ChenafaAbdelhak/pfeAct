package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.databinding.ActivityComptoirBinding;
import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.Produit;
import com.example.pfeact.myClasses.ProduitAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ComptoirActivity extends AppCompatActivity implements ProduitAdapter.OnProduitCClickListener {

    private RecyclerView comptoirRV;
    public static ArrayList<Produit> produitArrayList;
    private TextView tvAucuneResultatComptoir;
    protected   ProduitAdapter produitAdapter;
    private String barcodeResult;
    private boolean isAnyItemSelected;
    private DatabaseHelper databaseHelper;

    //ActivityComptoirBinding activityComptoirBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptoir);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Comptoir");
        }


        comptoirRV = findViewById(R.id.idRVcomptoir);
        tvAucuneResultatComptoir = findViewById(R.id.idTVaucuneResultatc);
        tvAucuneResultatComptoir.setVisibility(View.INVISIBLE);
        isAnyItemSelected = false;

        databaseHelper = new DatabaseHelper(this);
        produitArrayList = new ArrayList<>();
        produitArrayList = databaseHelper.afficherProduits();


        produitAdapter = new ProduitAdapter(produitArrayList,this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        comptoirRV.setLayoutManager(layoutManager);
        comptoirRV.setAdapter(produitAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.comptoir_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        MenuItem shoopingCart = menu.findItem(R.id.shoopingCart);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                tvAucuneResultatComptoir.setVisibility(View.INVISIBLE);
                comptoirRV.setVisibility(View.VISIBLE);
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Produit> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Produit item : produitArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getDesignation().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            comptoirRV.setVisibility(View.INVISIBLE);
            tvAucuneResultatComptoir.setVisibility(View.VISIBLE);
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            produitAdapter.filterList(filteredlist);
        }
    }

    private void bCfilter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Produit> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (int i=0;i<produitArrayList.size();i++) {
            Produit item = produitArrayList.get(i);
            // checking if the entered string matched with any item of our recycler view.
            if(item.getCodebarreProduit() == null){}
            else if (item.getCodebarreProduit().contains(text)) {
                // if the item is matched we are
                // adding it to our filtered list.
                if (item.getQte() == 0 || item.getQte() == item.getClickCounter()){
                    Snackbar.make(comptoirRV,"Quantité insuffisante",Snackbar.LENGTH_LONG).show();
                    return;
                }else {
                    produitArrayList.get(i).setClickCounter(produitArrayList.get(i).getClickCounter() + 1);
                    Snackbar.make(comptoirRV, "un produit ajoutée au panier", Snackbar.LENGTH_LONG).show();
                    produitAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
        Snackbar.make(comptoirRV,"pas de correspondance avec ce code-barres",Snackbar.LENGTH_LONG).show();
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.shoopingCart:
                isAnyItemSelected = false;
                for(int i=0;i<produitArrayList.size();i++){
                    if (produitArrayList.get(i).getClickCounter() != 0)
                        isAnyItemSelected = true;
                }
                if (isAnyItemSelected == true) {
                    CartDialog bottomSheet = new CartDialog();
                    bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");

                }else {
                    Toast.makeText(this,"Panier vide !",Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.barcode:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
                break;


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
                // if the intentResult is not null we'll set
                // the content and format of scan message
                barcodeResult = intentResult.getContents();
                bCfilter(barcodeResult);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onProduitCClickListener(int position) {

    }

    public void dataChanged(){
        produitAdapter.notifyDataSetChanged();
    }

    public  void afterCheckout(){
        produitArrayList = databaseHelper.afficherProduits();
        produitAdapter.changeArraylist(produitArrayList);
    }
}