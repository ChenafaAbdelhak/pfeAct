package com.example.pfeact.myClasses;

public class Client {
        private int id_client;
        private String nomClient;
        private String adresseClient;
        private String phoneClient;
        private double detteMax;
        private double dette;

        public Client(int id_client, String nomClient, String adresseClient, double detteMax, double dette, String phoneClient) {
                this.id_client = id_client;
                this.nomClient = nomClient;
                this.adresseClient = adresseClient;
                this.phoneClient = phoneClient;
                this.detteMax = detteMax;
                this.dette = dette;
        }

        public int getId_client() {
                return id_client;
        }

        public void setId_client(int id_client) {
                this.id_client = id_client;
        }

        public String getNomClient() {
                return nomClient;
        }

        public void setNomClient(String nomClient) {
                this.nomClient = nomClient;
        }

        public String getAdresseClient() {
                return adresseClient;
        }

        public void setAdresseClient(String adresseClient) {
                this.adresseClient = adresseClient;
        }

        public String getPhoneClient() {
                return phoneClient;
        }

        public void setPhoneClient(String phoneClient) {
                this.phoneClient = phoneClient;
        }

        public double getDetteMax() {
                return detteMax;
        }

        public void setDetteMax(double detteMax) {
                this.detteMax = detteMax;
        }

        public double getDette() {
                return dette;
        }

        public void setDette(double dette) {
                this.dette = dette;
        }
}
