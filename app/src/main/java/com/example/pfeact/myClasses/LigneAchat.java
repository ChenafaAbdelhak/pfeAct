package com.example.pfeact.myClasses;

public class LigneAchat {
    private int idProduit;
    private long idFactureVente;
    private int QteAchete;
    private double prixAchat;
    private double prixVente;

    public LigneAchat(int idProduit, long idFactureVente, int qteAchete, double prixAchat, double prixVente) {
        this.idProduit = idProduit;
        this.idFactureVente = idFactureVente;
        QteAchete = qteAchete;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public long getIdFactureVente() {
        return idFactureVente;
    }

    public void setIdFactureVente(long idFactureVente) {
        this.idFactureVente = idFactureVente;
    }

    public int getQteAchete() {
        return QteAchete;
    }

    public void setQteAchete(int qteAchete) {
        QteAchete = qteAchete;
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
}
