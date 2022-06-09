package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pfeact.myClasses.FactureVente;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class RapportsDeMoisActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private Spinner spinner;
    private BarChart barChart;
    private ArrayList<BarEntry> barArraylist;
    private ArrayList<FactureVente> factureVenteArrayList;
    private float beneficeJan = 0;
    private float beneficefev = 0;
    private float beneficemars = 0;
    private float beneficeAvril = 0;
    private float beneficemai = 0;
    private float beneficeJuin = 0;
    private float beneficeJuillet = 0;
    private float beneficeOut = 0;
    private float beneficeSeptembre = 0;
    private float beneficeOctobre = 0;
    private float beneficeNovembre = 0;
    private float beneficeDecembre = 0;
    private DatabaseHelper db;
    private String année = "2022";
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports_de_mois);

        barChart = findViewById(R.id.idBarChartMois);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Rapports de mois");
        }
        factureVenteArrayList = db.getFactureVenteFromTo("01-01-" + année + "", "31-01-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficeJan = beneficeJan + factureVenteArrayList.get(i).getBeneficeFacture();

            factureVenteArrayList = db.getFactureVenteFromTo("01-02-" + année + "", "31-02-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficefev = beneficefev + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-03-" + année + "", "31-03-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficemars = beneficemars + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-04-" + année + "", "31-04-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeAvril = beneficeAvril + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-05-" + année + "", "31-05-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficemai = beneficemai + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-06-" + année + "", "31-06-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeJuin = beneficeJuin + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-07-" + année + "", "31-07-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeJuillet = beneficeJuillet + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-08-" + année + "", "31-08-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeOut = beneficeOut + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-09-" + année + "", "31-09-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeSeptembre = beneficeSeptembre + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-10-" + année + "", "31-10-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeOctobre = beneficeOctobre + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-11-" + année + "", "31-11-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeNovembre = beneficeNovembre + factureVenteArrayList.get(i).getBeneficeFacture();
            }
            factureVenteArrayList = db.getFactureVenteFromTo("01-12-" + année + "", "31-12-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
                factureVenteArrayList.get(i);
                beneficeDecembre = beneficeDecembre + factureVenteArrayList.get(i).getBeneficeFacture();
            }

            barArraylist = new ArrayList<>();
            barArraylist.add(new BarEntry(0f, beneficeJan));
            barArraylist.add(new BarEntry(1f, beneficefev));
            barArraylist.add(new BarEntry(2f, beneficemars));
            barArraylist.add(new BarEntry(3f, beneficeAvril));
            barArraylist.add(new BarEntry(4f, beneficemai));
            barArraylist.add(new BarEntry(5f, beneficeJuin));
            barArraylist.add(new BarEntry(6f, beneficeJuillet));
            barArraylist.add(new BarEntry(7f, beneficeOut));
            barArraylist.add(new BarEntry(8f, beneficeSeptembre));
            barArraylist.add(new BarEntry(9f, beneficeOctobre));
            barArraylist.add(new BarEntry(10f, beneficeNovembre));
            barArraylist.add(new BarEntry(11f, beneficeDecembre));

            BarDataSet barDataSet = new BarDataSet(barArraylist, "rapports de mois");

            ArrayList<String> mois = new ArrayList<>();
            mois.add("janvier");
            mois.add("fevrier");
            mois.add("mars");
            mois.add("avril");
            mois.add("mai");
            mois.add("juin");
            mois.add("juillet");
            mois.add("out");
            mois.add("septembre");
            mois.add("octobre");
            mois.add("novembre");
            mois.add("decembre");

            BarData barData = new BarData((IBarDataSet) mois, barDataSet);
            barChart.setData(barData);
            barChart.setTouchEnabled(true);
            barChart.setDragEnabled(true);
            barChart.setScaleEnabled(true);

        }
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