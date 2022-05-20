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

public class FournisseurAdapter extends RecyclerView.Adapter<FournisseurAdapter.FournisseurViewHolder> {

    private ArrayList<Fournisseur> fournissseurArrayList;
    private Context context;
    private OnFournisseurListener onFournisseurListener;

    public FournisseurAdapter(ArrayList<Fournisseur> f, Context context, OnFournisseurListener onFournisseurListener) {
        fournissseurArrayList = f;
        this.context = context;
        this.onFournisseurListener = onFournisseurListener;
    }

    @NonNull
    @Override
    public FournisseurAdapter.FournisseurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fournisseur_rv_item,parent,false);

        return new FournisseurViewHolder(view,onFournisseurListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FournisseurAdapter.FournisseurViewHolder holder, int position) {
        Fournisseur modal = fournissseurArrayList.get(position);
        String fournisseurp = String.valueOf(position+1);
        holder.nomFournisseurTV.setText(modal.getNomF());
        holder.adressFournisseurTV.setText(modal.getAdresseF());
        holder.phoneFournisseurTV.setText(modal.getPhoneF());
        holder.positionTV.setText(fournisseurp);
    }


    @Override
    public int getItemCount() {
        return fournissseurArrayList.size();
    }

    public class FournisseurViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nomFournisseurTV, adressFournisseurTV,phoneFournisseurTV,positionTV;
        private ImageView iVdelete,iVedit;
        OnFournisseurListener onFournisseurListener;
        public FournisseurViewHolder(@NonNull View itemView, OnFournisseurListener onFournisseurListener) {
            super(itemView);

            nomFournisseurTV = itemView.findViewById(R.id.idTvNomFournisseur);
            adressFournisseurTV = itemView.findViewById(R.id.idTvAdressFournisseur);
            phoneFournisseurTV = itemView.findViewById(R.id.idTvPhoneFournisseur);
            positionTV = itemView.findViewById(R.id.idTVpositionFournisseur);
            iVdelete = itemView.findViewById(R.id.idIVdeleteFournisseur);
            iVedit = itemView.findViewById(R.id.idIVEditFournisseur);
            this.onFournisseurListener = onFournisseurListener;
            iVedit.setOnClickListener(this);
            iVdelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.idIVEditFournisseur:
                    onFournisseurListener.onEditButtonClick(this.getLayoutPosition());
                    break;
                case R.id.idIVdeleteFournisseur:
                    onFournisseurListener.onDeleteButtonClick(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }

    }


    public void filterList(ArrayList<Fournisseur> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        fournissseurArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    public interface OnFournisseurListener{
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

}
