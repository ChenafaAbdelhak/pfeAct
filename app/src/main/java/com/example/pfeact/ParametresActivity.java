package com.example.pfeact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ParametresActivity extends AppCompatActivity {

    private static final int STORAGE_REQUEST_CODE_EXPORT = 1;
    private static final int STORAGE_REQUEST_CODE_IMPORT = 2;
    private String[] storagePermissions;
    CardView cardExport;
    CardView cardImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        cardExport = findViewById(R.id.idCardExport);
        cardImport = findViewById(R.id.idCardImport);
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.paramètres);
        }
/*
        cardExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermission()) {
                    exportCSV();
                }
                else {
                    requestStoragePermissionExport();
                }
            }
        });

        cardImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermission()) {
                    importCSV();
                }
                else {
                    requestStoragePermissionImport();
                }
            }
        });


 */

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

    /*
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(
                this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return  result;
    }

    private void requestStoragePermissionImport(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE_IMPORT);
    }

    private void requestStoragePermissionExport(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE_EXPORT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case STORAGE_REQUEST_CODE_EXPORT:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    exportCSV();
                }else{
                    Toast.makeText(this, R.string.permission_stockage_obligatiore, Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE_IMPORT:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    importCSV();
                }else{
                    Toast.makeText(this, R.string.permission_stockage_obligatiore, Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void importCSV() {
    }

    private void exportCSV() {
        File folder = new File(Environment.getExternalStorageDirectory()+ "/" + R.string.backup_folder);
        boolean isFolderCreated = false ;
        if(!folder.exists()){
            isFolderCreated = folder.mkdir();
        }

        Log.d("CSC_TAG", "exportCSV: "+isFolderCreated);

        String csvFileName = "Pos_System_Backup.csv";

        String filePathAndName = folder.toString() + "/" + csvFileName;

        ArrayList<ModelRe>
    }

     */
}