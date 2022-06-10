package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pfeact.myClasses.FactureVente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class VoirBeneficieActivity extends AppCompatActivity {
    private EditText dateDebutEt, dateFinEt;
    private TextView beneficieTV;
    private DatePickerDialog.OnDateSetListener setListener, setListener2;
    private ArrayList<FactureVente> factureVenteArrayList;
    private DatabaseHelper databaseHelper;
    private RadioGroup dateRadioGroup;
    private Button calculerBeneficeBtn;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_beneficie);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Voir Beneficie");
        }
        factureVenteArrayList = new ArrayList<>();

        dateDebutEt = findViewById(R.id.idETDebutVoirBenefice);
        dateFinEt = findViewById(R.id.idETFinVoirBenefice);
        beneficieTV = findViewById(R.id.idBeneficieTV);
        databaseHelper = new DatabaseHelper(this);
        dateRadioGroup = (RadioGroup) findViewById(R.id.radio_dateBenefice);
        calculerBeneficeBtn = findViewById(R.id.idBtnCalculerBenefice);

        dateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                if(i != -1){
                    dateDebutEt.setText("[yyyy-mm-dd]");
                    dateFinEt.setText("[yyyy-mm-dd]");
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
                new DatePickerDialog(VoirBeneficieActivity.this,dateDebutL,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateFinEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRadioGroup.clearCheck();
                new DatePickerDialog(VoirBeneficieActivity.this,dateFinL,myCalendar.get(Calendar.YEAR)
                        ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        calculerBeneficeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((dateDebutEt.getText().toString().contains("yyyy") || dateFinEt.getText().toString().contains("yyyy")) &&
                        dateRadioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(VoirBeneficieActivity.this,"veuillez choisir une date", Toast.LENGTH_SHORT).show();
                }
                else {
                    String dateDebut = dateDebutEt.getText().toString();
                    String dateFin = dateFinEt.getText().toString();

                    int selectedId = dateRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton  = (RadioButton) dateRadioGroup.findViewById(selectedId);
                    if (selectedId == -1) {
                        factureVenteArrayList = databaseHelper.getFactureVenteForBenefice(dateDebut, dateFin, "no item selected");

                    }else {
                        factureVenteArrayList = databaseHelper.getFactureVenteForBenefice(dateDebut, dateFin, String.valueOf(radioButton.getText()));
                    }
                    float benefice = 0;

                    for (int i=0;i<factureVenteArrayList.size();i++){
                        benefice = benefice + factureVenteArrayList.get(i).getBeneficeFacture();
                    }

                    beneficieTV.setText(String.valueOf(benefice)+" DZD");

                }
            }
        });



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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, RapportsActivity.class));
                overridePendingTransition(0, 0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}