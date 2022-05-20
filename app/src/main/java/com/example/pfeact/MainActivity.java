package com.example.pfeact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends DrawerBaseActivity {

    CardView cardClient,cardFournissseur,cardProduit,cardCompoir,cardVentes,cardAchates,cardRapports
            ,cardParametres;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardClient = findViewById(R.id.idCardClient);
        cardFournissseur = findViewById(R.id.idCardFournisseur);
        cardProduit = findViewById(R.id.idCardProduit);
        cardCompoir = findViewById(R.id.idCardComptoir);
        cardVentes = findViewById(R.id.idCardVentes);
        cardAchates = findViewById(R.id.idCardAchates);
        cardRapports = findViewById(R.id.idCardRapports);
        cardParametres = findViewById(R.id.idCardParametres);

        cardClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ClientsActivity.class));
            }
        });
        cardFournissseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FournisseurActivity.class));
            }
        });
        cardProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProduitsActivity.class));
            }
        });
        cardCompoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ComptoirActivity.class));
            }
        });
        /*cardVentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),));
            }
        });
        cardAchates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),));
            }
        });*/
        cardRapports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RapportsActivity.class));
            }
        });
        cardParametres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ParametresActivity.class));
            }
        });
    }
}