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

public class FactureAchatAdapter extends RecyclerView.Adapter<FactureAchatAdapter.FactureAchatViewHolder>{

    private ArrayList<FactureAchat> factureAchatArrayList;
    private ArrayList<Fournisseur> fournisseurArrayList;
    private Context context;
    private FactureAchatAdapter.OnFactureAchatListener onFactureAchatListener;

    public FactureAchatAdapter(ArrayList<FactureAchat> factureAchatArrayList, ArrayList<Fournisseur> fournisseurArrayList, Context context, OnFactureAchatListener onFactureAchatListener) {
        this.factureAchatArrayList = factureAchatArrayList;
        this.fournisseurArrayList = fournisseurArrayList;
        this.context = context;
        this.onFactureAchatListener = onFactureAchatListener;
    }

    @NonNull
    @Override
    public FactureAchatAdapter.FactureAchatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.factureachat_rv_item,parent,false);

        return new FactureAchatAdapter.FactureAchatViewHolder(view,onFactureAchatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FactureAchatAdapter.FactureAchatViewHolder holder, int position) {
        FactureAchat modal = factureAchatArrayList.get(position);
        Fournisseur fournisseur = fournisseurArrayList.get(0);

        for (int i=0;i<factureAchatArrayList.size();i++){
            if (fournisseurArrayList.get(i).getId() == modal.getIdFournisseur())
                fournisseur = fournisseurArrayList.get(i);
        }

        holder.dateFactureAchatTV.setText(modal.getDateAchat());
        holder.heureFactureAchatTV.setText(modal.getHeureAchat());
        holder.idFactureAchatTV.setText("id Facture : #"+modal.getId());
        holder.nomFournisseurTV.setText("Fournisseur : "+fournisseur.getNomF());
        holder.montantTotalFactureAchatTV.setText("DZD "+modal.getMontantTotal());
    }

    @Override
    public int getItemCount() {
        return factureAchatArrayList.size();
    }

    public class FactureAchatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView dateFactureAchatTV,heureFactureAchatTV,idFactureAchatTV,nomFournisseurTV,montantTotalFactureAchatTV;

        FactureAchatAdapter.OnFactureAchatListener onFactureAchatListener;
        public FactureAchatViewHolder(@NonNull View itemView, FactureAchatAdapter.OnFactureAchatListener onFactureAchatListener) {
            super(itemView);

            dateFactureAchatTV = itemView.findViewById(R.id.idTvDateFactureAchat);
            heureFactureAchatTV = itemView.findViewById(R.id.idTvHeureFactureAchat);
            idFactureAchatTV = itemView.findViewById(R.id.idTvIdFactureAchat);
            nomFournisseurTV = itemView.findViewById(R.id.idTvNomFournisseurFactureAchat);
            montantTotalFactureAchatTV = itemView.findViewById(R.id.idTvMontantTotalFactureAchat);
            this.onFactureAchatListener = onFactureAchatListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            onFactureAchatListener.onFactureAchatClick(this.getLayoutPosition());

        }

    }


    public void setArraylist(ArrayList<FactureAchat> filtredFactureAchatArrayList) {
        factureAchatArrayList = filtredFactureAchatArrayList;
        notifyDataSetChanged();
    }

    public ArrayList<FactureAchat> getArraylist() {

        return factureAchatArrayList;
    }


    public interface OnFactureAchatListener{

        void onFactureAchatClick(int position);
    }
}
