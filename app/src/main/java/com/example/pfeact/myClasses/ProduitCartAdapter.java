package com.example.pfeact.myClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfeact.R;

import java.util.ArrayList;

public class ProduitCartAdapter extends RecyclerView.Adapter<ProduitCartAdapter.ProduitCartViewHolder>{

    private ArrayList<Produit> produitArrayList;
    private Context context;
    private OnProduitCartListener onProduitCartListener;

    public ProduitCartAdapter(ArrayList<Produit> produitArrayList, Context context,OnProduitCartListener onProduitCartListener) {
        this.produitArrayList = produitArrayList;
        this.context = context;
        this.onProduitCartListener = onProduitCartListener;
    }

    @NonNull
    @Override
    public ProduitCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produitcart_rv_item,parent,false);
        return new ProduitCartAdapter.ProduitCartViewHolder(view,onProduitCartListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitCartViewHolder holder, int position) {
            Produit modal = produitArrayList.get(position);

            holder.qteVendu.setText(""+modal.getClickCounter()+"x");
            holder.designation.setText(""+modal.getDesignation());
            holder.stockDispo.setText("Stock : "+modal.getQte());
            holder.prixVenteUni.setText(""+modal.getPrixVente()+" DZD");
            holder.prixVentQteAchete.setText(""+modal.getPrixVente()*modal.getClickCounter()+" DZD");


    }

    @Override
    public int getItemCount() {
        return produitArrayList.size();
    }

    public class ProduitCartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView qteVendu,designation,stockDispo,prixVenteUni,prixVentQteAchete;
        private ImageView deleteItemCart;
        OnProduitCartListener onProduitCartListener;
        public ProduitCartViewHolder(@NonNull View itemView,OnProduitCartListener onProduitCartListener) {
            super(itemView);
            qteVendu  = itemView.findViewById(R.id.idTvQteVenduCart);
            designation  = itemView.findViewById(R.id.idTvDesignationCart);
            stockDispo  = itemView.findViewById(R.id.idTvStockCart);
            prixVenteUni  = itemView.findViewById(R.id.idTvPrixVenteUniCart);
            prixVentQteAchete  = itemView.findViewById(R.id.idTvPrixVenteQteAcheteCart);
            deleteItemCart  = itemView.findViewById(R.id.idIvDeleteItemCart);
            this.onProduitCartListener = onProduitCartListener;
            itemView.setOnClickListener(this);
            deleteItemCart.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.idProduit_cart_rv_item_layout:
                    onProduitCartListener.onProduitCartClick(this.getLayoutPosition());
                    break;
                case R.id.idIvDeleteItemCart:
                    onProduitCartListener.onDeleteButtonClick(this.getLayoutPosition());
                    break;
                default:
                    break;
            }

        }
    }
    public interface OnProduitCartListener{
        void onProduitCartClick(int position);
        void onDeleteButtonClick(int position);
    }
}





