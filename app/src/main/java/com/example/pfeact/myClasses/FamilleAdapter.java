
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

public class FamilleAdapter extends RecyclerView.Adapter<FamilleAdapter.FamilleViewHolder> {

    private ArrayList<Famille> familleArrayList;
    private Context context;
    private OnFamilleListener onFamilleListener;

    public FamilleAdapter(ArrayList<Famille> familleArrayList, Context context, OnFamilleListener onFamilleListener) {
        this.familleArrayList = familleArrayList;
        this.context = context;
        this.onFamilleListener = onFamilleListener;
    }

    @NonNull
    @Override
    public FamilleAdapter.FamilleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.famille_rv_item,parent,false);

        return new FamilleViewHolder(view,onFamilleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilleAdapter.FamilleViewHolder holder, int position) {
        Famille modal = familleArrayList.get(position);
        String clientp = String.valueOf(position+1);
        holder.nomFamilleTV.setText(modal.getNomFamille());

    }


    @Override
    public int getItemCount() {
        return familleArrayList.size();
    }

    public class FamilleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nomFamilleTV;
        private ImageView iVdelete,iVedit;
        OnFamilleListener onFamilleListener;
        public FamilleViewHolder(@NonNull View itemView, OnFamilleListener onFamilleListener) {
            super(itemView);

            nomFamilleTV = itemView.findViewById(R.id.idTvNomFamilleFamille);

            iVdelete = itemView.findViewById(R.id.idIVdeleteFamille);
            iVedit = itemView.findViewById(R.id.idIVEditFamille);
            this.onFamilleListener = onFamilleListener;
            iVedit.setOnClickListener(this);
            iVdelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.idIVEditFamille:
                    onFamilleListener.onEditButtonClick(this.getLayoutPosition());
                    break;
                case R.id.idIVdeleteFamille:
                    onFamilleListener.onDeleteButtonClick(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }

    }


    public void filterList(ArrayList<Famille> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        familleArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    public interface OnFamilleListener{
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

}
