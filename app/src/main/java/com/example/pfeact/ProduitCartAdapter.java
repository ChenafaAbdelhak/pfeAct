package com.example.pfeact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfeact.myClasses.Produit;
import com.example.pfeact.myClasses.ProduitAdapter;

import java.util.ArrayList;

public class ProduitCartAdapter extends RecyclerView.Adapter<ProduitCartAdapter.ProduitCartViewHolder>{

    private ArrayList<Produit> produitArrayList;
    private Context context;

    public ProduitCartAdapter(ArrayList<Produit> produitArrayList, Context context) {
        this.produitArrayList = produitArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProduitCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produitcart_rv_item,parent,false);
        return new ProduitCartAdapter.ProduitCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitCartViewHolder holder, int position) {
            Produit modal = produitArrayList.get(position);

            holder.qteAchete.setText(""+modal.getClickCounter()+"x");
            holder.designation.setText(""+modal.getDesignation());
            holder.stockDispo.setText(""+modal.getQte());
            holder.prixVenteUni.setText(""+modal.getPrixVente()+" DZD");
            holder.prixVentQteAchete.setText(""+modal.getPrixVente()*modal.getClickCounter()+" DZD");
    }

    @Override
    public int getItemCount() {
        return produitArrayList.size();
    }

    public class ProduitCartViewHolder extends RecyclerView.ViewHolder {
        private TextView qteAchete,designation,stockDispo,prixVenteUni,prixVentQteAchete;
        private ImageView deleteItemCart;
        public ProduitCartViewHolder(@NonNull View itemView) {
            super(itemView);
            qteAchete  = itemView.findViewById(R.id.idTvQteAcheteCart);
            designation  = itemView.findViewById(R.id.idTvDesignationCart);
            stockDispo  = itemView.findViewById(R.id.idTvStockCart);
            prixVenteUni  = itemView.findViewById(R.id.idTvPrixVenteUniCart);
            prixVentQteAchete  = itemView.findViewById(R.id.idTvPrixVenteQteAcheteCart);
            deleteItemCart  = itemView.findViewById(R.id.idIvDeleteItemCart);
        }
    }
}




