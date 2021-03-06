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

public class FactureVenteAdapter extends RecyclerView.Adapter<FactureVenteAdapter.FactureVenteViewHolder>{

    private ArrayList<FactureVente> factureVenteArrayList;
    private ArrayList<Client> clientArrayList;
    private Context context;
    private OnFactureVenteListener onFactureVenteListener;

    public FactureVenteAdapter(ArrayList<FactureVente> factureVenteArrayList, ArrayList<Client> clientArrayList, Context context, OnFactureVenteListener onFactureVenteListener) {
        this.factureVenteArrayList = factureVenteArrayList;
        this.clientArrayList = clientArrayList;
        this.context = context;
        this.onFactureVenteListener = onFactureVenteListener;
    }

    @NonNull
    @Override
    public FactureVenteAdapter.FactureVenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facturevente_rv_item,parent,false);

        return new FactureVenteViewHolder(view,onFactureVenteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FactureVenteAdapter.FactureVenteViewHolder holder, int position) {
        FactureVente modal = factureVenteArrayList.get(position);
        Client client = clientArrayList.get(0);
        
        for (int i=0;i<clientArrayList.size();i++){
            if (clientArrayList.get(i).getId_client() == modal.getIdClient())
                client = clientArrayList.get(i);
        }

        holder.dateFactureVenteTV.setText(modal.getDateVente());
        holder.heureFactureVenteTV.setText(modal.getHeureVente());
        holder.idFactureVenteTV.setText("id Facture : #"+modal.getId());
        holder.nomClientTV.setText("Client : "+client.getNomClient());
        holder.montantTotalFactureVenteTV.setText("DZD "+modal.getMontantTotal());
    }

    @Override
    public int getItemCount() {
        return factureVenteArrayList.size();
    }

    public class FactureVenteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView dateFactureVenteTV,heureFactureVenteTV,idFactureVenteTV,nomClientTV,montantTotalFactureVenteTV;

        OnFactureVenteListener onFactureVenteListener;
        public FactureVenteViewHolder(@NonNull View itemView, OnFactureVenteListener onFactureVenteListener) {
            super(itemView);

            dateFactureVenteTV = itemView.findViewById(R.id.idTvDateFactureVente);
            heureFactureVenteTV = itemView.findViewById(R.id.idTvHeureFactureVente);
            idFactureVenteTV = itemView.findViewById(R.id.idTvIdFactureVente);
            nomClientTV = itemView.findViewById(R.id.idTvNomClientFactureVente);
            montantTotalFactureVenteTV = itemView.findViewById(R.id.idTvMontantTotalFactureVente);
            this.onFactureVenteListener = onFactureVenteListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

                    onFactureVenteListener.onFactureVenteClick(this.getLayoutPosition());

        }

    }


    public void setArraylist(ArrayList<FactureVente> filtredFactureVenteArrayList) {
        factureVenteArrayList = filtredFactureVenteArrayList;
        notifyDataSetChanged();
    }

    public ArrayList<FactureVente> getArraylist() {

        return factureVenteArrayList;
    }


    public interface OnFactureVenteListener{

        void onFactureVenteClick(int position);
    }
}
