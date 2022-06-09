package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class
RapportsActivity extends AppCompatActivity implements View.OnClickListener {
    CardView monthlyRapportCard,weeklyRapportCard,voirBeneficieCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports);

        monthlyRapportCard=findViewById(R.id.idMonthlyRapport);
        weeklyRapportCard=findViewById(R.id.idWeeklyRapport);
        voirBeneficieCard=findViewById(R.id.idVoirBenefecie);

        monthlyRapportCard.setOnClickListener(this);
        weeklyRapportCard.setOnClickListener(this);
        voirBeneficieCard.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Rapports");
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

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.idMonthlyRapport:
                intent = new Intent(this,RapportsDeMoisActivity.class);
                startActivity(intent);
                break;
            case R.id.idWeeklyRapport:
                intent = new Intent(this, RapportsDeSemaineActivity.class);
                startActivity(intent);
                break;
            case R.id.idVoirBenefecie:
                intent = new Intent(this, VoirBeneficieActivity.class);
                startActivity(intent);
                break;
        }
    }
}