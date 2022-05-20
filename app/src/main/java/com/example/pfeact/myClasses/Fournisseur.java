package com.example.pfeact.myClasses;

public class Fournisseur {
    private int id;
    private String nomF;
    private String adresseF;
    private String phoneF;
    private double dette;

    public Fournisseur(int id, String nomF, String adresseF, String phoneF, double dette) {
        this.id = id;
        this.nomF = nomF;
        this.adresseF = adresseF;
        this.phoneF = phoneF;
        this.dette = dette;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomF() {
        return nomF;
    }

    public void setNomF(String nomF) {
        this.nomF = nomF;
    }

    public String getAdresseF() {
        return adresseF;
    }

    public void setAdresseF(String adresseF) {
        this.adresseF = adresseF;
    }

    public String getPhoneF() {
        return phoneF;
    }

    public void setPhoneF(String phoneF) {
        this.phoneF = phoneF;
    }

    public double getDette() {
        return dette;
    }

    public void setDette(double dette) {
        this.dette = dette;
    }
}
