package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pfeact.myClasses.FactureVente;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Collections;

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

    private DatabaseHelper db;
    private String année = "2022";
    private int i;
    private ArrayList<String> xAxisLabel = new ArrayList<>();
    private String[] xData={"janvier","fevrier","mars","avril","mai","juin"};
    private ArrayList<BarEntry> barEntriesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports_de_mois);
        barEntriesArrayList=new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Rapports de mois");
        }

        barChart = findViewById(R.id.idBarChartMois);
        db = new DatabaseHelper(this);

        xAxisLabel.add("janvier");
        xAxisLabel.add("fevrier");
        xAxisLabel.add("mars");
        xAxisLabel.add("avril");
        xAxisLabel.add("mai");
        xAxisLabel.add("juin");

        factureVenteArrayList = db.getFactureVenteInMonth("01-01-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficeJan = beneficeJan + factureVenteArrayList.get(i).getBeneficeFacture();
        }
        barEntriesArrayList.add(new BarEntry(0,beneficeJan));

        factureVenteArrayList = db.getFactureVenteInMonth("01-02-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficefev = beneficefev + factureVenteArrayList.get(i).getBeneficeFacture();
        }
        barEntriesArrayList.add(new BarEntry(1,beneficefev));

        factureVenteArrayList = db.getFactureVenteInMonth("01-03-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficemars = beneficemars + factureVenteArrayList.get(i).getBeneficeFacture();
        }
        barEntriesArrayList.add(new BarEntry(2,15000));

        factureVenteArrayList = db.getFactureVenteInMonth("01-04-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficeAvril = beneficeAvril + factureVenteArrayList.get(i).getBeneficeFacture();
        }
        barEntriesArrayList.add(new BarEntry(3,beneficeAvril));

        factureVenteArrayList = db.getFactureVenteInMonth("01-05-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficemai = beneficemai + factureVenteArrayList.get(i).getBeneficeFacture();
        }
        barEntriesArrayList.add(new BarEntry(4,beneficemai));

        factureVenteArrayList = db.getFactureVenteInMonth("01-06-" + année + "");
        for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficeJuin = beneficeJuin + factureVenteArrayList.get(i).getBeneficeFacture();
        }
        barEntriesArrayList.add(new BarEntry(5,beneficeJuin));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);

            }
        });
        BarDataSet barDataSet=new BarDataSet(barEntriesArrayList,"Bénéfice par mois");
        barChart.getXAxis().setLabelRotationAngle(90f);
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setValueTextColors(Collections.singletonList(Color.BLACK));
        barDataSet.setValueTextSize(16f);








      /*  factureVenteArrayList = db.getFactureVenteFromTo("01-01-" + année + "", "31-01-" + année + "");
            for (i = 0; i < factureVenteArrayList.size(); i++) {
            factureVenteArrayList.get(i);
            beneficeJan = beneficeJan + factureVenteArrayList.get(i).getBeneficeFacture();
            }
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
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("beneficies de mois");
        //pieChart.setDrawEntryLabels(true);
        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1=e.toString().indexOf("(sum):");
                String beneficies=e.toString().substring(pos1 +7);

                for(int i=0;i< yData.length;i++){
                    if(yData[i]==Float.parseFloat(beneficies)){
                        pos1=i;
                        break;
                    }
                }
                String mois=xData[pos1 + 1];
                //Toast.makeText(this,"Mois" + mois + "Beneficies:" + beneficies + "DA" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

       */
    }
    /*
    private void addDataSet() {
        ArrayList<PieEntry> yEntys=new ArrayList<>();
        ArrayList<String> xEntrys=new ArrayList<>();

        for(int i=0;i < yData.length;i++){
            yEntys.add(new PieEntry(yData[i],i));
        }
        for(int i=1;i < xData.length;i++){
            xEntrys.add(xData[i]);
        }
        //create data set
        PieDataSet pieDataSet=new PieDataSet(yEntys,"rapports de mois");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        //add colors to dataset
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        colors.add(Color.LTGRAY);
        colors.add(Color.DKGRAY);
        colors.add(Color.TRANSPARENT);
        colors.add(Color.CYAN);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

     */

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