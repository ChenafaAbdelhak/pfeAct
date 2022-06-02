package com.example.pfeact.myClasses;

public class Famille {
    private int id;
    private String nomFamille;

    public Famille(int id, String nomFamille) {
        this.id = id;
        this.nomFamille = nomFamille;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomFamille() {
        return nomFamille;
    }

    public void setNomFamille(String nomFamille) {
        this.nomFamille = nomFamille;
    }

    @Override
    public String toString() {
        return nomFamille;
    }
}
