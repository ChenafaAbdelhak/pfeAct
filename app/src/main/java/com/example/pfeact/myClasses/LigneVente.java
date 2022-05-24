package com.example.pfeact.myClasses;

public class LigneVente {
    private long id;
    private int QteVendu;
    private double prixAchat;
    private double prixVente;
    private double beneficeLigne;

    public LigneVente(long id, int qteVendu, double prixAchat, double prixVente, double beneficeLigne) {
        this.id = id;
        QteVendu = qteVendu;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.beneficeLigne = beneficeLigne;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQteVendu() {
        return QteVendu;
    }

    public void setQteVendu(int qteVendu) {
        QteVendu = qteVendu;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public double getBeneficeLigne() {
        return beneficeLigne;
    }

    public void setBeneficeLigne(double beneficeLigne) {
        this.beneficeLigne = beneficeLigne;
    }
}
