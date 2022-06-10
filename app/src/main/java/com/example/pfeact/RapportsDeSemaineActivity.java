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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

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
    private ArrayList<BarEntry> barEntriesArrayList;
    private ArrayList<String> xAxisLabel = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports_de_semaine);

        db = new DatabaseHelper(this);

        barChart=findViewById(R.id.idBarChart);
        getData();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);

            }
        });
        BarDataSet barDataSet=new BarDataSet(barEntriesArrayList,"Bénéfice par jour");
        barChart.getXAxis().setLabelRotationAngle(90f);
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setValueTextColors(Collections.singletonList(Color.BLACK));
        barDataSet.setValueTextSize(16f);


        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("beneficies de semaine");

        }
    }

    private void getData() {
        barEntriesArrayList=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            currentDay=getPastDayDate(i);
            factureVenteArrayList=db.getFactureVenteInDay(currentDay);
            sommeBeneficie=0;
            if(factureVenteArrayList.size()==0){
                sommeBeneficie = 0;
            }
            else {
                for (int k = 0; k < factureVenteArrayList.size(); k++) {
                    sommeBeneficie = sommeBeneficie + factureVenteArrayList.get(k).getBeneficeFacture();
                }
            }
            barEntriesArrayList.add(new BarEntry(i,sommeBeneficie));
            xAxisLabel.add(currentDay);
        }

        /*
        barArraylist.add(new BarEntry(1f,0));
        barArraylist.add(new BarEntry(3f,35));
        barArraylist.add(new BarEntry(4f,100));
        barArraylist.add(new BarEntry(5f,5));
        barArraylist.add(new BarEntry(6f,50));

         */
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