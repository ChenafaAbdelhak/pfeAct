package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.databinding.ActivityClientsBinding;
import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.ClientAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ClientsActivity extends AppCompatActivity implements ClientAdapter.OnClientListener {

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase myDatabase;
    FloatingActionButton fabAddClient ;
    //ActivityClientsBinding activityClientsBinding;
    private RecyclerView clientsRV;
    private ArrayList<Client> clientArrayList ;
    private ClientAdapter clientAdapter;
    private AlertDialog.Builder builder;
    private TextView tvAucuneResultat;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        //allocateActivityTitle("Clients");
        ActionBar actionBar = getSupportActionBar();
        builder = new AlertDialog.Builder(this);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Clients");
        }
        Intent intentFab = new Intent(this, AddClientActivity.class);
        fabAddClient = findViewById(R.id.idFabAddClient);
        clientsRV = findViewById(R.id.idRVClients);
        tvAucuneResultat = findViewById(R.id.idTVaucuneResultat);
        tvAucuneResultat.setVisibility(View.INVISIBLE);
        buildRecyclerView();
        fabAddClient.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

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
                tvAucuneResultat.setVisibility(View.INVISIBLE);
                clientsRV.setVisibility(View.VISIBLE);
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void buildRecyclerView() {



        // below line we are creating a new array list
        clientArrayList = new ArrayList<>();
        myDBHelper = new DatabaseHelper(ClientsActivity.this);
        clientArrayList = myDBHelper.afficherClient();
        // initializing our adapter class.
        clientAdapter = new ClientAdapter(clientArrayList, ClientsActivity.this,this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        clientsRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        clientsRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        clientsRV.setAdapter(clientAdapter);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Client> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Client item : clientArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getNomClient().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            clientsRV.setVisibility(View.INVISIBLE);
            tvAucuneResultat.setVisibility(View.VISIBLE);
            //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
              //      .setAction("Action", null).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            clientAdapter.filterList(filteredlist);
        }
    }


    @Override
    public void onDeleteButtonClick(int position) {
       if (clientArrayList.get(position).getId_client() == 1){
           Snackbar s = Snackbar.make(clientsRV,"Vous pouvez pas supprimer ce client !",Snackbar.LENGTH_LONG);
           s.show();
       }
       else if(myDBHelper.isClientDeleteSafe(clientArrayList.get(position).getId_client()) == false){
            Snackbar s = Snackbar.make(clientsRV,"Vous pouvez pas supprimer ce client, il a déja effectuer des achats..",Snackbar.LENGTH_LONG);
            s.show();
        }else {
           builder.setTitle("Vous voullez supprimer ce client ?").setCancelable(true)
                   .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           myDBHelper.supprimerClient(clientArrayList.get(position).getId_client());
                           Snackbar s = Snackbar.make(clientsRV,"client supprimée avec succés",Snackbar.LENGTH_LONG);
                           s.show();
                           removeItem(position);
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

        Intent intent = new Intent(getApplicationContext(),EditClientActivity.class);
        bundle.putInt("id",clientArrayList.get(position).getId_client());
        bundle.putString("nom",clientArrayList.get(position).getNomClient());
        bundle.putString("adresse",clientArrayList.get(position).getAdresseClient());
        bundle.putString("phone",clientArrayList.get(position).getPhoneClient());
        intent.putExtras(bundle);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }
    public void removeItem(int position) {

        clientArrayList.remove(position);
        clientAdapter.notifyItemRemoved(position);
    }
}

