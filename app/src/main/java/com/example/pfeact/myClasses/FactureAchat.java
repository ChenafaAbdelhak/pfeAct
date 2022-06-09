package com.example.pfeact.myClasses;

public class FactureAchat {
    private long id;
    private int idFournisseur;
    private String dateAchat;
    private String heureAchat;
    private float montantTotal;

    public FactureAchat(long id, int idFournisseur, String dateAchat, String heureAchat, float montantTotal) {
        this.id = id;
        this.idFournisseur = idFournisseur;
        this.dateAchat = dateAchat;
        this.heureAchat = heureAchat;
        this.montantTotal = montantTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getHeureAchat() {
        return heureAchat;
    }

    public void setHeureAchat(String heureAchat) {
        this.heureAchat = heureAchat;
    }

    public float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(float montantTotal) {
        this.montantTotal = montantTotal;
    }
}
