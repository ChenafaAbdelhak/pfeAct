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

public class PProduitAdapter extends RecyclerView.Adapter<PProduitAdapter.PProduitViewHolder> {

    private ArrayList<Produit> produitArrayList;
    private  ArrayList<Famille> familleArrayList;
    private Context context;
    private OnProduitListener onProduitListener;

    public PProduitAdapter(ArrayList<Produit> produitArrayList, ArrayList<Famille> familleArrayList, Context context, OnProduitListener onProduitListener) {
        this.produitArrayList = produitArrayList;
        this.familleArrayList = familleArrayList;
        this.context = context;
        this.onProduitListener = onProduitListener;

    }

    @NonNull
    @Override
    public PProduitAdapter.PProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produit_rv_item,parent,false);

        return new PProduitViewHolder(view,onProduitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PProduitAdapter.PProduitViewHolder holder, int position) {
        Produit modal = produitArrayList.get(position);
        String nomFamille ="";
        for (Famille famille : familleArrayList){
            if (modal.getIdFamille() == famille.getId()){
                nomFamille = famille.getNomFamille();
            }
        }
        //String clientp = String.valueOf(position+1);
        holder.designationTV.setText("Designation : "+modal.getDesignation());
        holder.prixAchatTV.setText("Prix Achat    : "+String.valueOf(modal.getPrixAchat())+" DZD");
        holder.prixVenteTV.setText("Prix Vente    : "+String.valueOf(modal.getPrixVente())+" DZD");
        holder.quantiteTV.setText("Quantite       : "+String.valueOf(modal.getQte()));
        holder.barcodeTV.setText("Barcode       : "+modal.getCodebarreProduit());
        holder.familleTV.setText("Famille         : "+nomFamille);
    }


    @Override
    public int getItemCount() {
        return produitArrayList.size();
    }

    public class PProduitViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView designationTV, prixAchatTV,prixVenteTV,quantiteTV,barcodeTV,familleTV;
        private ImageView iVdelete,iVedit;
        OnProduitListener onProduitListener;

        public PProduitViewHolder(@NonNull View itemView, OnProduitListener onProduitListener) {
            super(itemView);

            designationTV = itemView.findViewById(R.id.idTvDesignationProduit);
            prixAchatTV = itemView.findViewById(R.id.idTvPrixAchatProduit);
            prixVenteTV = itemView.findViewById(R.id.idTvPrixVenteProduit);
            quantiteTV = itemView.findViewById(R.id.idTvQuantiteProduit);
            barcodeTV = itemView.findViewById(R.id.idTvBarcodeProduit);
            familleTV = itemView.findViewById(R.id.idTvFamilleProduit);
            iVdelete = itemView.findViewById(R.id.idBtnDeleteProduit);
            iVedit = itemView.findViewById(R.id.idBtnEditProduit);
            this.onProduitListener = onProduitListener;

            iVdelete.setOnClickListener(this);
            iVedit.setOnClickListener( this);

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.idBtnEditProduit:
                    onProduitListener.onEditButtonClick(this.getLayoutPosition());
                    break;
                case R.id.idBtnDeleteProduit:
                    onProduitListener.onDeleteButtonClick(this.getLayoutPosition());
                    break;
                default:
                    break;
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
    public interface OnProduitListener{
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

}

