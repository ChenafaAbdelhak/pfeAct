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

public class LigneAchatAdapter extends  RecyclerView.Adapter<LigneAchatAdapter.LigneAchatViewHolder> {

        private ArrayList<LigneAchat> ligneAchatArrayList;
        private ArrayList<Produit> produitArrayList;
        private Context context;

    public LigneAchatAdapter(ArrayList<LigneAchat> ligneAchatArrayList, ArrayList<Produit> produitArrayList, Context context) {
        this.ligneAchatArrayList = ligneAchatArrayList;
        this.produitArrayList = produitArrayList;
        this.context = context;
    }

    @NonNull
        @Override
        public LigneAchatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ligneachat_rv_item,parent,false);

                return new LigneAchatAdapter.LigneAchatViewHolder(view);
                }

        @Override
        public void onBindViewHolder(@NonNull LigneAchatViewHolder holder, int position) {
                LigneAchat modal = ligneAchatArrayList.get(position);
                String designationProduit = "";
                for (int i=0 ;i<produitArrayList.size();i++){
                if (modal.getIdProduit() == produitArrayList.get(i).getId())
                designationProduit = produitArrayList.get(i).getDesignation();
                }

                holder.qteAcheteTv.setText("x"+modal.getQteAchete());
                holder.designationTv.setText(designationProduit);
                holder.prixAchatUniTv.setText(""+modal.getPrixAchat()+" DZD");
                holder.prixAchatTotTv.setText(""+modal.getPrixVente()*modal.getQteAchete()+" DZD");

                }

        @Override
        public int getItemCount() {
                return ligneAchatArrayList.size();
                }

        public class LigneAchatViewHolder extends RecyclerView.ViewHolder {

            private TextView qteAcheteTv,designationTv,prixAchatUniTv,prixAchatTotTv;
            public LigneAchatViewHolder(@NonNull View itemView) {
                super(itemView);

                qteAcheteTv = itemView.findViewById(R.id.idTvQteAcheteDetailAchat);
                designationTv = itemView.findViewById(R.id.idTvDesignationDetailAchat);
                prixAchatUniTv = itemView.findViewById(R.id.idTvPrixAchatUniDetailAchat);
                prixAchatTotTv = itemView.findViewById(R.id.idTvPrixAchatQteTotDetailAchat);
            }
        }
        }
