package com.example.pfeact.myClasses;

public class LigneVente {
    private int idProduit;
    private long idFactureVente;
    private int QteVendu;
    private float prixAchat;
    private float prixVente;
    private float beneficeLigne;

    public LigneVente(int idProduit, long idFactureVente, int qteVendu, float prixAchat, float prixVente, float beneficeLigne) {
        this.idProduit = idProduit;
        this.idFactureVente = idFactureVente;
        QteVendu = qteVendu;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.beneficeLigne = beneficeLigne;
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

    public int getQteVendu() {
        return QteVendu;
    }

    public void setQteVendu(int qteVendu) {
        QteVendu = qteVendu;
    }

    public float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public float getBeneficeLigne() {
        return beneficeLigne;
    }

    public void setBeneficeLigne(float beneficeLigne) {
        this.beneficeLigne = beneficeLigne;
    }
}
