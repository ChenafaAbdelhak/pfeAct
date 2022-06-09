package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pfeact.myClasses.FactureVente;

import java.util.ArrayList;
import java.util.Calendar;

public class VoirBeneficieActivity extends AppCompatActivity {
    private EditText debutSemaineEdt, finSemaineEdt;
    private TextView beneficieTV;
    private String debutSemaine, finSemaine;
    private DatePickerDialog.OnDateSetListener setListener, setListener2;
    private ArrayList<FactureVente> factureVenteArrayList;
    private DatabaseHelper db;
    private float beneficie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_beneficie);

        debutSemaineEdt = findViewById(R.id.idDebutSemaine);
        finSemaineEdt = findViewById(R.id.idFinSemaine);
        beneficieTV = findViewById(R.id.idBeneficieTV);

        debutSemaine = debutSemaineEdt.getText().toString();
        finSemaine = finSemaineEdt.getText().toString();

        beneficie=getData();
        beneficieTV.setText("la benefice=" + beneficie);
        beneficieTV.setVisibility(View.VISIBLE);



        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Voir Beneficie");
        }

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        debutSemaineEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(VoirBeneficieActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                debutSemaineEdt.setText(date);
            }
        }
        ;
        Calendar calendar2 = Calendar.getInstance();
        final int yearFin = calendar2.get(Calendar.YEAR);
        final int monthFin = calendar2.get(Calendar.MONTH);
        final int dayFin = calendar2.get(Calendar.DAY_OF_MONTH);

        finSemaineEdt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                DatePickerDialog datePickerDialog = new DatePickerDialog(VoirBeneficieActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        , setListener2, yearFin, monthFin, dayFin);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener2=new DatePickerDialog.OnDateSetListener()

        {
            @Override
            public void onDateSet (DatePicker view,int yearFin, int monthFin, int dayOfMonthFin)
            {
                monthFin = monthFin + 1;
                String dateFin = yearFin + "-" + monthFin + "-" + dayFin;
                finSemaineEdt.setText(dateFin);
            }
        }

        ;

    }
    public float getData(){
        float benefice = 0;
        factureVenteArrayList=new ArrayList<FactureVente>();
        for(int i=0;i<factureVenteArrayList.size();i++){
            factureVenteArrayList=db.getFactureVenteFromTo(debutSemaine,finSemaine);
            benefice=factureVenteArrayList.get(i).getBeneficeFacture();

        }
        return benefice;
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