package com.example.pfeact.myClasses;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pfeact.R;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private ArrayList<Client> clientArrayList;
    private Context context;
    private OnClientListener onClientListener;

    public ClientAdapter(ArrayList<Client> c, Context context, OnClientListener onClientListener) {
        clientArrayList = c;
        this.context = context;
        this.onClientListener = onClientListener;
    }

    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_rv_item,parent,false);

        return new ClientViewHolder(view,onClientListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, int position) {
        Client modal = clientArrayList.get(position);
        String clientp = String.valueOf(position+1);
        holder.nomClientTV.setText(modal.getNomClient());
        holder.adressClientTV.setText(modal.getAdresseClient());
        holder.phoneClientTV.setText(modal.getPhoneClient());
        holder.positionTV.setText(clientp);
    }


    @Override
    public int getItemCount() {
        return clientArrayList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nomClientTV, adressClientTV,phoneClientTV,positionTV;
        private ImageView iVdelete,iVedit;
        OnClientListener onClientListener;
        public ClientViewHolder(@NonNull View itemView, OnClientListener onClientListener) {
            super(itemView);

            nomClientTV = itemView.findViewById(R.id.idTvNomClient);
            adressClientTV = itemView.findViewById(R.id.idTvAdressClient);
            phoneClientTV = itemView.findViewById(R.id.idTvPhoneClient);
            positionTV = itemView.findViewById(R.id.idTVpositionClient);
            iVdelete = itemView.findViewById(R.id.idIVdeleteClient);
            iVedit = itemView.findViewById(R.id.idIVEditClient);
            this.onClientListener = onClientListener;
            iVedit.setOnClickListener(this);
            iVdelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.idIVEditClient:
                    onClientListener.onEditButtonClick(this.getLayoutPosition());
                    break;
                case R.id.idIVdeleteClient:
                    onClientListener.onDeleteButtonClick(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }

    }


    public void filterList(ArrayList<Client> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        clientArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    public interface OnClientListener{
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

}
