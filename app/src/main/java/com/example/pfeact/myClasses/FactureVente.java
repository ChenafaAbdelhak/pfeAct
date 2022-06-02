package com.example.pfeact.myClasses;

public class FactureVente {
    private long id;
    private String dateVente;
    private String heureVente;
    private double montantTotal;

    public FactureVente(long id, String dateVente, String heureVente, double montantTotal) {
        this.id = id;
        this.dateVente = dateVente;
        this.heureVente = heureVente;
        this.montantTotal = montantTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }
}
