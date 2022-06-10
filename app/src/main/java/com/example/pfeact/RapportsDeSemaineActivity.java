package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pfeact.myClasses.FactureVente;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class RapportsDeSemaineActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private BarChart barChart;
    private DatabaseHelper db;
    private ArrayList<FactureVente> factureVenteArrayList = new ArrayList<>();
    private String currentDay;
    private float sommeBeneficie;
    private ArrayList<BarEntry> barArraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports_de_semaine);

        db = new DatabaseHelper(this);
        barChart=findViewById(R.id.idBarChart);
        getData();
        BarDataSet barDataSet=new BarDataSet(barArraylist,"beneficie de semaine");
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setValueTextColors(Collections.singletonList(Color.BLACK));
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("beneficies de semaine");

        }
    }

    private void getData() {
        barArraylist=new ArrayList<>();
       /* for (int i = 7; i >= 0; i--) {
            currentDay=getPastDayDate(i);
            factureVenteArrayList=db.getFactureVenteInDay(currentDay);
            sommeBeneficie=0;
            if(factureVenteArrayList.size()==0){
                sommeBeneficie=0;
            }
            else {
                for (int k = 0; k < factureVenteArrayList.size(); k++) {
                    sommeBeneficie = sommeBeneficie + factureVenteArrayList.get(k).getBeneficeFacture();
                }
            }
            barArraylist.add(new BarEntry(Float.parseFloat(currentDay),sommeBeneficie));
        }*/
        barArraylist.add(new BarEntry(2f,10));
        barArraylist.add(new BarEntry(3f,35));
        barArraylist.add(new BarEntry(4f,100));
        barArraylist.add(new BarEntry(5f,5));
        barArraylist.add(new BarEntry(6f,50));
    }

    private String getPastDayDate(int i){
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-i);
        return dateFormat.format(cal.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, RapportsActivity.class));
                overridePendingTransition(0,0);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}