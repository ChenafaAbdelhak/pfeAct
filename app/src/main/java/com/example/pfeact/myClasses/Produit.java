package com.example.pfeact.myClasses;

public class Produit {
        private long id;
        private String designation;
        private String codebarreProduit;
        private long qte;
        private float prixAchat;
        private  float prixVente;


        public Produit( String designation, String codebarreProduit, long qte, float prixAchat, float prixVente) {
                this.designation = designation;
                this.codebarreProduit = codebarreProduit;
                this.qte = qte;
                this.prixAchat = prixAchat;
                this.prixVente = prixVente;
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
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

        public String getQte() {
                return String.valueOf(qte);
        }

        public void setQte(long qte) {
                this.qte = qte;
        }

        public String getPrixAchat() {
                return String.valueOf(prixAchat);
        }

        public void setPrixAchat(float prixAchat) {
                this.prixAchat = prixAchat;
        }

        public String getPrixVente() {
                return String.valueOf(prixVente);
        }

        public void setPrixVente(float prixVente) {
                this.prixVente = prixVente;
        }
}
