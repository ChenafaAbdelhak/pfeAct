package com.example.pfeact.myClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfeact.R;

import java.util.ArrayList;
import java.util.Collections;

public class LigneVenteAdapter extends  RecyclerView.Adapter<LigneVenteAdapter.LigneVenteViewHolder>{

    private ArrayList<LigneVente> ligneVenteArrayList;
    private ArrayList<Produit> produitArrayList;
    private Context context;

    public LigneVenteAdapter(ArrayList<LigneVente> ligneVenteArrayList, ArrayList<Produit> produitArrayList, Context context) {
        this.ligneVenteArrayList = ligneVenteArrayList;
        this.produitArrayList = produitArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LigneVenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lignevente_rv_item,parent,false);

        return new LigneVenteAdapter.LigneVenteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigneVenteViewHolder holder, int position) {
        LigneVente modal = ligneVenteArrayList.get(position);
        String designationProduit = "";
        for (int i=0 ;i<produitArrayList.size();i++){
            if (modal.getIdProduit() == produitArrayList.get(i).getId())
                designationProduit = produitArrayList.get(i).getDesignation();
        }

        holder.qteVenduTv.setText("x"+modal.getQteVendu());
        holder.designationTv.setText(designationProduit);
        holder.prixVenteUniTv.setText(""+modal.getPrixVente()+" DZD");
        holder.prixVenteTotTv.setText(""+modal.getPrixVente()*modal.getQteVendu()+" DZD");

    }

    @Override
    public int getItemCount() {
        return ligneVenteArrayList.size();
    }

    public class LigneVenteViewHolder extends RecyclerView.ViewHolder {

        private TextView qteVenduTv,designationTv,prixVenteUniTv,prixVenteTotTv;
        public LigneVenteViewHolder(@NonNull View itemView) {
            super(itemView);

            qteVenduTv = itemView.findViewById(R.id.idTvQteVenduDetailVente);
            designationTv = itemView.findViewById(R.id.idTvDesignationDetailVente);
            prixVenteUniTv = itemView.findViewById(R.id.idTvPrixVenteUniDetailVente);
            prixVenteTotTv = itemView.findViewById(R.id.idTvPrixVenteQteAcheteDetailVente);
        }
    }
}
