package com.example.pfeact.myClasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfeact.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder>{

    private ArrayList<Produit> produitArrayList;
    private Context context;
    private OnProduitCClickListener onProduitCClickListener;

    public ProduitAdapter(ArrayList<Produit> produitArrayList, Context context, OnProduitCClickListener onProduitCClickListener) {
        this.produitArrayList = produitArrayList;
        this.context = context;
        this.onProduitCClickListener = onProduitCClickListener;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produitcomptoir_rv_item,parent,false);
        return new ProduitViewHolder(view,onProduitCClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        Produit modal = produitArrayList.get(position);

        holder.designationTv.setText(modal.getDesignation());
        holder.prixVenteTv.setText(modal.getPrixVente()+"  "+context.getString(R.string.devise_dapplication));
        holder.qteExisteTv.setText(String.valueOf(modal.getQte()));
        holder.clickCounterTv.setText(String.valueOf(modal.getClickCounter()));
        if (modal.getClickCounter() == 0) {
            holder.clickCounterTv.setVisibility(View.INVISIBLE);
        }else {holder.clickCounterTv.setVisibility(View.VISIBLE);}
        if (modal.getQte() == 0 ){
            holder.cardProduitC.setCardBackgroundColor(Color.RED);
        }else {
            holder.cardProduitC.setCardBackgroundColor(null);
        }


    }

    @Override
    public int getItemCount() {
        return produitArrayList.size();
    }

    public class ProduitViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private TextView designationTv,clickCounterTv,qteExisteTv,prixVenteTv;
        private CardView cardProduitC;

        OnProduitCClickListener onProduitCClickListener;

        public ProduitViewHolder(@NonNull View itemView, OnProduitCClickListener onProduitCClickListener) {
            super(itemView);
            designationTv = itemView.findViewById(R.id.idTVDesignation);
            clickCounterTv = itemView.findViewById(R.id.idTVclickCounter);
            qteExisteTv = itemView.findViewById(R.id.idQteExisteTv);
            prixVenteTv = itemView.findViewById(R.id.idPrixVenteTv);
            cardProduitC = itemView.findViewById(R.id.idCardProduitC);
            this.onProduitCClickListener = onProduitCClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (isValideQte(getLayoutPosition())) {
                clickCounterTv.setVisibility(View.VISIBLE);
                produitArrayList.get(getLayoutPosition())
                        .setClickCounter(produitArrayList.get(getLayoutPosition()).getClickCounter() + 1);
                clickCounterTv.setText(String.valueOf(produitArrayList.get(getLayoutPosition()).getClickCounter()));
            }else{
                Snackbar.make(clickCounterTv,"Quantité insufisante",Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    public void filterList(ArrayList<Produit> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        produitArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public void resetCounter(){

        for (Produit produit : produitArrayList){
            produit.setClickCounter(0);
        }

    }
    public boolean isValideQte(int position){
        if (produitArrayList.get(position).getQte() > produitArrayList.get(position).getClickCounter()){
            return  true;
        }else {return  false;}
    }

    public interface OnProduitCClickListener{
        void onProduitCClickListener(int position);
    }

    public void changeArraylist(ArrayList<Produit> newArray){
        produitArrayList = newArray;
        notifyDataSetChanged();
    }
}
