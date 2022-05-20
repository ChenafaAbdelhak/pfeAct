package com.example.pfeact;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

//not used in application, keeped just in case

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        setContentView(R.layout.activity_drawer_base);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_produit:
                startActivity(new Intent(this, ProduitsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_clients:
                startActivity(new Intent(this, ClientsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_fournisseurs:
                startActivity(new Intent(this, FournisseurActivity.class));
                overridePendingTransition(0,0);
                break;


        }
        return false;
    }

    protected void allocateActivityTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }


}