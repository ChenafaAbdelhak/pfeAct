package com.example.pfeact.myClasses;

public class Produit {
        private int id;
        private String designation;
        private String codebarreProduit;
        private int qte;
        private float prixAchat;
        private  float prixVente;
        private int idFamille;
        private  int clickCounter;

        public Produit(int id, String designation, String codebarreProduit, int qte, float prixAchat, float prixVente, int idFamille, int clickCounter) {
                this.id = id;
                this.designation = designation;
                this.codebarreProduit = codebarreProduit;
                this.qte = qte;
                this.prixAchat = prixAchat;
                this.prixVente = prixVente;
                this.idFamille = idFamille;
                this.clickCounter = clickCounter;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getDesignation() {
                return designation;
        }

        public void setDesignation(String designation) {
                this.designation = designation;
        }

        public String getCodebarreProduit() {
                return codebarreProduit;
        }

        public void setCodebarreProduit(String codebarreProduit) {
                this.codebarreProduit = codebarreProduit;
        }

        public int getQte() {
                return qte;
        }

        public void setQte(int qte) {
                this.qte = qte;
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

        public int getIdFamille() {
                return idFamille;
        }

        public void setIdFamille(int idFamille) {
                this.idFamille = idFamille;
        }

        public int getClickCounter() {
                return clickCounter;
        }

        public void setClickCounter(int clickCounter) {
                this.clickCounter = clickCounter;
        }
}
