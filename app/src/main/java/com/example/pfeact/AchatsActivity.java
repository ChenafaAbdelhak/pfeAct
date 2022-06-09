package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.FactureAchat;
import com.example.pfeact.myClasses.FactureAchatAdapter;
import com.example.pfeact.myClasses.FactureVente;
import com.example.pfeact.myClasses.FactureVenteAdapter;
import com.example.pfeact.myClasses.Fournisseur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AchatsActivity extends AppCompatActivity implements FactureAchatAdapter.OnFactureAchatListener {

    private ArrayList<FactureAchat> factureAchatArrayList;
    private ArrayList<FactureAchat> filteredFactureAchatArrayList;
    private ArrayList<Fournisseur> fournisseurArrayList;
    private  DatabaseHelper databaseHelper;
    private FactureAchatAdapter adapter;
    private RecyclerView factureAchatRV;
    private Bundle bundle;
    private EditText dateDebutEt,dateFinEt;
    private RadioGroup dateRadioGroup;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Achats");
        }

        factureAchatRV = (RecyclerView)findViewById(R.id.idRVFactureAchat);


        factureAchatArrayList = new ArrayList<>();
        fournisseurArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(AchatsActivity.this);
        factureAchatArrayList = databaseHelper.afficherFactureAchat();
        fournisseurArrayList = databaseHelper.afficherFournisseurs();

        //factureVenteArrayList.sort();
        // initializing our adapter class.
        adapter = new FactureAchatAdapter(factureAchatArrayList,fournisseurArrayList, AchatsActivity.this,this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        factureAchatRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        factureAchatRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        factureAchatRV.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.filterFacture:
                final AlertDialog.Builder d = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.filter_facture_layout, null);
                d.setView(dialogView);
                dateDebutEt = dialogView.findViewById(R.id.idEtDateDebutVentes);
                dateFinEt = dialogView.findViewById(R.id.idEtDateFinVentes);
                dateRadioGroup = (RadioGroup) dialogView.findViewById(R.id.radio_date);
                Spinner spinner = (Spinner) dialogView.findViewById(R.id.idSpFilterClientVentes);

                ArrayList<Fournisseur> fournisseurArrayLists;
                fournisseurArrayLists = databaseHelper.afficherFournisseurs();
                fournisseurArrayLists.add(0,new Fournisseur(-1,"Tous les fournisseurs","","",0));
                ArrayAdapter<Fournisseur> fournisseurArrayAdapter;
                fournisseurArrayAdapter = new ArrayAdapter<Fournisseur>(getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        fournisseurArrayLists);
                fournisseurArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(fournisseurArrayAdapter);

                dateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {


                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                        if(i != -1){
                            dateDebutEt.setText("DEBUT [yyyy-mm-dd]");
                            dateFinEt.setText("FIN [yyyy-mm-dd]");
                        }
                    }
                });

                DatePickerDialog.OnDateSetListener dateDebutL =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);
                        updateDateDebut();
                    }
                };

                DatePickerDialog.OnDateSetListener dateFinL =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);
                        updateDateFin();
                    }
                };

                dateDebutEt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateRadioGroup.clearCheck();
                        new DatePickerDialog(AchatsActivity.this,dateDebutL,myCalendar.get(Calendar.YEAR)
                                ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                dateFinEt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateRadioGroup.clearCheck();
                        new DatePickerDialog(AchatsActivity.this,dateFinL,myCalendar.get(Calendar.YEAR)
                                ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                d.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fournisseur fournisseur = (Fournisseur) spinner.getSelectedItem();

                        if ((dateDebutEt.getText().toString().contains("yyyy") || dateFinEt.getText().toString().contains("yyyy")) &&
                                (dateRadioGroup.getCheckedRadioButtonId() == -1) && (fournisseur.getId() == -1)) {
                            Toast.makeText(getApplicationContext(), "veuillez sélectionner une date valide", Toast.LENGTH_LONG).show();
                        } else {
                            String dateDebut = dateDebutEt.getText().toString();
                            String dateFin = dateFinEt.getText().toString();

                            int selectedId = dateRadioGroup.getCheckedRadioButtonId();
                            RadioButton radioButton  = (RadioButton) dateRadioGroup.findViewById(selectedId);
                            if (selectedId == -1) {
                                filteredFactureAchatArrayList = databaseHelper.getFilteredFactureAchat(dateDebut, dateFin, "no item selected", fournisseur.getId());
                                adapter.setArraylist(filteredFactureAchatArrayList);
                            }else {
                                filteredFactureAchatArrayList = databaseHelper.getFilteredFactureAchat(dateDebut, dateFin, String.valueOf(radioButton.getText()), fournisseur.getId());
                                adapter.setArraylist(filteredFactureAchatArrayList);
                            }
                        }
                    }
                });
                d.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"negative button",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = d.create();
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.ventes_menu, menu);




        return true;
    }


    @Override
    public void onFactureAchatClick(int position) {

        FactureAchat factureAchat = adapter.getArraylist().get(position);
        //Toast.makeText(getApplicationContext(),"g  "+factureVente.getId(),Toast.LENGTH_LONG).show();
        bundle = new Bundle();
        String nomFournisseur = "";
        bundle.putLong("idFacture",factureAchat.getId());
        bundle.putFloat("montantTotal",factureAchat.getMontantTotal());
        bundle.putString("dateAchat",factureAchat.getDateAchat());
        bundle.putString("heureAchat",factureAchat.getHeureAchat());

        for (int i=0;i<fournisseurArrayList.size();i++){
            if (factureAchat.getIdFournisseur() == fournisseurArrayList.get(i).getId())
                nomFournisseur = fournisseurArrayList.get(i).getNomF();
        }

        bundle.putString("nomFournisseur",nomFournisseur);
        Intent intent = new Intent(AchatsActivity.this,DetailAchatActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);




    }

    private void updateDateDebut(){
        String myFormat="YYYY-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        dateDebutEt.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateDateFin(){
        String myFormat="YYYY-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        dateFinEt.setText(dateFormat.format(myCalendar.getTime()));
    }
}