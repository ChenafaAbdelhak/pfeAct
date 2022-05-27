package com.example.pfeact;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.Famille;
import com.example.pfeact.myClasses.Fournisseur;
import com.example.pfeact.myClasses.Produit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBname = "data.db";

    private static final String CREATE_CLIENT_TABLE =
            "CREATE TABLE Client (idClient INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nomClient TEXT NOT NULL UNIQUE, adresseClient TEXT, detteMaxClient Float, detteClient FLOAT" +
                    ", phoneClient TEXT)";

    private static final String CREATE_FOURNISSEUR_TABLE =
            "CREATE TABLE Fournisseur (idFournisseur INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nomFournisseur TEXT, adresseFournisseur TEXT, phoneFournisseur TEXT, detteFournisseur FLOAT )";


    private static final String CREATE_FAMILLE_TABLE =
            "CREATE TABLE Famille (idFamille INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nomFamille TEXT)";

    private static final String CREATE_FACTUREACHAT_TABLE =
            "CREATE TABLE FactureAchat (idFactureAchat INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " idFournisseur INTEGER REFERENCES Fournisseur (idFourniseur), dateAchat TEXT" +
                    "heureAchat TEXT, montantTotal FLOAT)";

    private static final String CREATE_LIGNEACHAT_TABLE =
            "CREATE TABLE LigneAchat (idProduit INTEGER REFERENCES Produit (idProduit)," +
                    " idFactureAchat INTEGER REFERENCES FactureAchat (idFactureAchat), " +
                    "qteAchat INTEGER, prixAchat FLOAT, prixVente FLOAT," +
                    " CONSTRAINT pm_LigneAchat" +
                    " PRIMARY KEY (idProduit,idFactureAchat))";

    private static  final String CREATE_PRODUIT_TABLE =
            "CREATE TABLE Produit (idProduit INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "designation TEXT UNIQUE NOT NULL, cbProduit TEXT, qte INTEGER, prixAchat FLOAT, prixVente FLOAT)";

    private static final String CREATE_FACTUREVENTE_TABLE =
            "CREATE TABLE FactureVente (idFactureVente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idClient INTEGER REFERENCES Client (idClient), dateVente TEXT," +
                    "heureVente TEXT, montantTotal FLOAT, remise FLOAT, beneficeFacture FLOAT )";

    private static final String CREATE_LIGNEVENTE_TABLE =
            "CREATE TABLE LigneVente (idProduit INTEGER REFERENCES Produit (idProduit)," +
                    " idFactureVente INTEGER REFERENCES FactureVente (idFactureVente), " +
                    " qteVendu INTEGER, prixAchat FLOAT, prixVente FLOAT, beneficeLigne FLOAT," +
                    " CONSTRAINT pm_LigneVente" +
                    " PRIMARY KEY (idProduit,idFactureVente))";



    public DatabaseHelper(Context context){

        super(context, DBname ,null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CLIENT_TABLE);
        db.execSQL(CREATE_FOURNISSEUR_TABLE);
        db.execSQL(CREATE_PRODUIT_TABLE);
        db.execSQL(CREATE_FAMILLE_TABLE);
        db.execSQL(CREATE_FACTUREACHAT_TABLE);
        db.execSQL(CREATE_FACTUREVENTE_TABLE);
        db.execSQL(CREATE_LIGNEACHAT_TABLE);
        db.execSQL(CREATE_LIGNEVENTE_TABLE);

        db.execSQL("insert into Produit( designation,cbProduit,qte,prixAchat,prixVente) values('www','1234',5,10,100);");
        db.execSQL("insert into Produit( designation,cbProduit,qte,prixAchat,prixVente) values('aaa','1235',5,10,100);");
        db.execSQL("insert into Fournisseur( nomFournisseur,adresseFournisseur,detteFournisseur,phoneFournisseur) values('DEFAULT','_',0,'_');");
        db.execSQL("insert into Client( nomClient,adresseClient,detteMaxClient,detteClient,phoneClient) values('ANONYME','_',0,0,'_');");
        db.execSQL("insert into Client( nomClient,adresseClient,detteMaxClient,detteClient,phoneClient) values('hjhj','_',11,11,'_');");
        db.execSQL("insert into Client( nomClient,adresseClient,detteMaxClient,detteClient,phoneClient) values('dddd','_',11,11,'_');");
    }



    public void ajouterProduit(String designation,String codebarreProduit,long qte,float prixAchat,float prixVente){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("designation",designation);
        cv.put("cbProduit",codebarreProduit);
        cv.put("qte",qte);
        cv.put("prixAchat",prixAchat);
        cv.put("prixVente",prixVente);

        db.insert("Produit",null,cv);

        db.close();

    }



    public ArrayList<Produit> afficherProduits(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Produit",null);

        ArrayList<Produit> produitArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                produitArrayList.add(new Produit(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                        cursor.getInt(3),cursor.getFloat(4),cursor.getFloat(5),0));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return produitArrayList;
    }

    public void modifierProduit(int id, String designation, String cbarre, int QuantiteProduit, float PrixAchat, float PrixVente) {
        ContentValues values = new ContentValues();
        values.put("idProduit", id);
        values.put("designation", designation);
        values.put("cbProduit",cbarre);
        values.put("qte", QuantiteProduit);
        values.put("prixAchat", PrixAchat);
        values.put("prixVente", PrixVente);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("Produit", values, "idProduit = ? ", new String[]{String.valueOf(id)});

        db.close();
    }



    public ArrayList<Client> afficherClient(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Client",null);

        ArrayList<Client> clientArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                clientArrayList.add(new Client(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getDouble(3),cursor.getDouble(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return clientArrayList;
    }


    public long ajouterClient(String nomClient,String adressClient,String PhoneClient){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();


        cv.put("nomClient",nomClient);
        cv.put("adresseClient",adressClient);
        cv.put("PhoneClient",PhoneClient);

        long result = db.insert("Client",null,cv);

        db.close();
        return result;
    }

    public boolean modifierClient(int id,String nomClientS,String adresseClientS,String phoneClientS) {
        ContentValues values = new ContentValues();
        values.put("nomClient", nomClientS);
        values.put("adresseClient", adresseClientS);
        values.put("phoneClient", phoneClientS);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update("Client", values, "idClient = ? ", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public void supprimerClient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Client", "idClient = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public  boolean isClientUnique (String nom){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Client WHERE nomClient ='"+nom+"'",null);

        cursor.moveToFirst();
        int n = cursor.getInt(0);

        if(n!=0){return  false;}
        else{return  true;}
    }

    public boolean isClientDeleteSafe (int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM FactureVente WHERE idClient ='"+id+"'",null);

        cursor.moveToFirst();
        int n = cursor.getInt(0);

        db.close();
        cursor.close();

        if(n!=0){return  false;}
        else{return  true;}
    }


    public ArrayList<Fournisseur> afficherFournisseurs(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Fournisseur",null);

        ArrayList<Fournisseur> fournisseurArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                fournisseurArrayList.add(new Fournisseur(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getDouble(4)));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return fournisseurArrayList;
    }

    public long ajouterFournisseur(String nomFournisseur,String adressFournisseur,String phoneFournisseur){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("nomFournisseur",nomFournisseur);
        cv.put("adresseFournisseur",adressFournisseur);
        cv.put("PhoneFournisseur",phoneFournisseur);

        long result = db.insert("Fournisseur",null,cv);

        db.close();
        return result;
    }

    public  boolean isFournisseurUnique (String nom){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Fournisseur WHERE nomFournisseur ='"+nom+"'",null);

        cursor.moveToFirst();
        int n = cursor.getInt(0);

        if(n!=0){return  false;}
        else{return  true;}
    }

    public boolean isFournisseurDeleteSafe (int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM FactureAchat WHERE idFournisseur ='"+id+"'",null);

        cursor.moveToFirst();
        int n = cursor.getInt(0);

        db.close();
        cursor.close();

        if(n!=0){return  false;}
        else{return  true;}
    }

    public boolean modifierFournisseur(int id,String nomFournisseurS,String adresseFournisseurS,String phoneFournisseurS) {
        ContentValues values = new ContentValues();
        values.put("nomFournisseur", nomFournisseurS);
        values.put("adresseFournisseur", adresseFournisseurS);
        values.put("phoneFournisseur", phoneFournisseurS);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update("Fournisseur", values, "idFournisseur = ? ", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public void supprimerFournisseur(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Fournisseur", "idFournisseur = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public int effectuerVente(int idClient, float montantTotal, float remise, float beneficeFacture, ArrayList<Produit> produitCartArrayList){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        cv.put("idClient",idClient);
        cv.put("dateVente",currentDate);
        cv.put("heureVente",currentTime);
        cv.put("montantTotal",montantTotal);
        cv.put("remise",remise);
        cv.put("beneficeFacture",beneficeFacture);

        long idFactureVente = db.insert("FactureVente",null,cv);

        for (int i=0 ;i<produitCartArrayList.size();i++){
            cv.clear();
            //ajouter les lignes de vente
            Produit produit= produitCartArrayList.get(i);
            cv.put("idProduit",produit.getId());
            cv.put("idFactureVente",idFactureVente);
            cv.put("qteVendu",produit.getClickCounter());
            cv.put("prixAchat",produit.getPrixAchat());
            cv.put("prixVente",produit.getPrixVente());
            cv.put("beneficeLigne",produit.getPrixVente()-produit.getPrixAchat());

            db.insert("LigneVente",null,cv);

            cv.clear();

            //modifier la quantité
            cv.put("idProduit", produit.getId());
            cv.put("qte", produit.getQte()-produit.getClickCounter());


            db.update("Produit", cv, "idProduit = ? ", new String[]{String.valueOf(produit.getId())});

        }



        db.close();
        return 1;
    }

    public ArrayList<Famille> afficherFamilles(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Famille",null);

        ArrayList<Famille> familleArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                familleArrayList.add(new Famille(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return familleArrayList;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Client");
        db.execSQL("DROP TABLE IF EXISTS Fournisseur");
        db.execSQL("DROP TABLE IF EXISTS Produit");
        db.execSQL("DROP TABLE IF EXISTS Famille");
        db.execSQL("DROP TABLE IF EXISTS FactureAchat");
        db.execSQL("DROP TABLE IF EXISTS FactureVente");
        db.execSQL("DROP TABLE IF EXISTS LigneAchat");
        db.execSQL("DROP TABLE IF EXISTS LigneVente");
        onCreate(db);
    }
}

