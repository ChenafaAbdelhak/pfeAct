package com.example.pfeact.myClasses;

import java.util.Comparator;

public class FactureVente {
    private long id;
    private  int idClient;
    private String dateVente;
    private String heureVente;
    private float montantTotal;
    private float remise;
    private float beneficeFacture;

    public FactureVente(long id, int idClient, String dateVente, String heureVente, float montantTotal, float remise, float beneficeFacture) {
        this.id = id;
        this.idClient = idClient;
        this.dateVente = dateVente;
        this.heureVente = heureVente;
        this.montantTotal = montantTotal;
        this.remise = remise;
        this.beneficeFacture = beneficeFacture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getDateVente() {
        return dateVente;
    }

    public void setDateVente(String dateVente) {
        this.dateVente = dateVente;
    }

    public String getHeureVente() {
        return heureVente;
    }

    public void setHeureVente(String heureVente) {
        this.heureVente = heureVente;
    }

    public float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(float montantTotal) {
        this.montantTotal = montantTotal;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

    public float getBeneficeFacture() {
        return beneficeFacture;
    }

    public void setBeneficeFacture(float beneficeFacture) {
        this.beneficeFacture = beneficeFacture;
    }


}
