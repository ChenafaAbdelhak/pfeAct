package com.example.pfeact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.pfeact.databinding.CartDialogLayoutBinding;
import com.example.pfeact.myClasses.Client;
import com.example.pfeact.myClasses.Produit;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartDialog extends BottomSheetDialogFragment implements ProduitCartAdapter.OnProduitCartListener{

    private RecyclerView recyclerView;
    private ProduitCartAdapter produitCartAdapter;
    private ArrayList<Produit> produitCartArrayList;
    private ImageButton deleteAllCartItemsButton;
    private Button soumettreCommandeCartBTN;
    private TextView montantTotalTV,montantTotalApresRemiseTV,designationCartDialogItemTV,stockDispoCartDialogTV, prixVenteUniCartDialogTV
            ,prixAchatCartDialogTV;
    private EditText qteVenduCartDialogET,changerPrixCartDialogET,remiseET;
    private CheckBox modifierPrixVenteCb,afficherPrixAchatCB;
    private float montantTotal,montantTotalApresRemise,remise;
    private DatabaseHelper databaseHelper;
    private ArrayAdapter<Client> clientArrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_layout,
                container, false);

        recyclerView = view.findViewById(R.id.idRVCart);
        deleteAllCartItemsButton = view.findViewById(R.id.idIBdelete_all_cart_items);
        montantTotalTV = view.findViewById(R.id.idTvMontantTotalCart);
        montantTotalApresRemiseTV = view.findViewById(R.id.idTvMontantTotalApresRemisCart);
        remiseET = view.findViewById(R.id.idETRemiseCart);
        soumettreCommandeCartBTN = view.findViewById(R.id.idBtnSoumettreCommande);
        databaseHelper = new DatabaseHelper(getContext());

        produitCartArrayList = new ArrayList<>();
        for (int i = 0; i < ComptoirActivity.produitArrayList.size(); i++) {
            if (ComptoirActivity.produitArrayList.get(i).getClickCounter() > 0){
                produitCartArrayList.add(ComptoirActivity.produitArrayList.get(i));
        }}
        if (produitCartArrayList.size() == 0) {
            Toast.makeText(getContext(), "panier vide !!", Toast.LENGTH_SHORT).show();
        }
            buildRecyclerView();

        Spinner spinner = (Spinner) view.findViewById(R.id.idSpClientFactureVente);
        ArrayList<Client> clientArrayList = new ArrayList<>();
        clientArrayList = databaseHelper.afficherClient();
        clientArrayAdapter = new ArrayAdapter<Client>(getContext(), android.R.layout.simple_spinner_item,clientArrayList);
        clientArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(clientArrayAdapter);
        remiseET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculerTotal();
            }
        });


        deleteAllCartItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viderCart();
            }
        });
        soumettreCommandeCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculerTotal();
                if (remise > montantTotal) {
                    Snackbar.make(remiseET, "La remise ne peut pas être supérieure au prix total", Snackbar.LENGTH_LONG).show();
                } else {
                    Client client = (Client) spinner.getSelectedItem();
                    float beneficeFacture = 0;
                    for (int i=0;i<produitCartArrayList.size();i++){
                        Produit produit = produitCartArrayList.get(i);
                        beneficeFacture = beneficeFacture +(produit.getClickCounter() * (produit.getPrixVente()-produit.getPrixAchat()));
                    }

                        beneficeFacture = beneficeFacture - remise;

                    int res = 0;
                    res =databaseHelper.effectuerVente(client.getId_client(),montantTotalApresRemise,remise,beneficeFacture
                    ,produitCartArrayList);

                    if (res == 1) {
                        for (int i=0;i<ComptoirActivity.produitArrayList.size();i++){
                            ComptoirActivity.produitArrayList.get(i).setClickCounter(0);
                        }
                        produitCartArrayList.clear();
                        ((ComptoirActivity) getActivity()).afterCheckout();
                        dismiss();


                    }
                }
            }
        });
        return view;
    }

    private void buildRecyclerView() {



        // below line we are creating a new array list



        produitCartAdapter = new ProduitCartAdapter(produitCartArrayList, getContext(),this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        recyclerView.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        recyclerView.setAdapter(produitCartAdapter);
        calculerTotal();
    }

    private void  calculerTotal(){
            montantTotal = 0;
            remise = 0;
            if (remiseET.getText().toString().equals("") == false)
                remise = Float.parseFloat(remiseET.getText().toString());
        for (Produit produit : produitCartArrayList){
            montantTotal = montantTotal+produit.getPrixVente();
        }
        montantTotalTV.setText("Total :  "+montantTotal+" DZD");
        montantTotalApresRemise = montantTotal - remise;
        if (remise > montantTotal){
            montantTotalApresRemiseTV.setText("erreur ");
        }else {
            montantTotalApresRemiseTV.setText("Total aprés remise : "+montantTotalApresRemise+" DZD");

        }
    }

    @Override
    public void onProduitCartClick(int position) {
        final AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cart_item_dialog_layout, null);
        d.setView(dialogView);

                designationCartDialogItemTV = dialogView.findViewById(R.id.idTvDesignationCartDialogItem);
                qteVenduCartDialogET = dialogView.findViewById(R.id.idQteCartDialogET);
                stockDispoCartDialogTV = dialogView.findViewById(R.id.idTvStockDispoCartDialog);
                prixVenteUniCartDialogTV = dialogView.findViewById(R.id.idTvPrixVenteUniCartDialog);
                changerPrixCartDialogET = dialogView.findViewById(R.id.idETChangerPrix);
                modifierPrixVenteCb = dialogView.findViewById(R.id.idCBModifierPrixVenteCartDialog);
                afficherPrixAchatCB = dialogView.findViewById(R.id.idCBAfficherPrixAchatCartDialog);
                prixAchatCartDialogTV =dialogView.findViewById(R.id.idTvPrixAchatCartDialog);
        designationCartDialogItemTV.setText(produitCartArrayList.get(position).getDesignation());
        stockDispoCartDialogTV.setText("stock : "+produitCartArrayList.get(position).getQte());
        prixVenteUniCartDialogTV.setText(""+produitCartArrayList.get(position).getPrixVente()+" DZD");
        qteVenduCartDialogET.setText(""+produitCartArrayList.get(position).getClickCounter());
        changerPrixCartDialogET.setText(""+produitCartArrayList.get(position).getPrixVente());
        prixAchatCartDialogTV.setText(""+produitCartArrayList.get(position).getPrixAchat());
        modifierPrixVenteCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(modifierPrixVenteCb.isChecked())
                {
                    // ....
                    changerPrixCartDialogET.setVisibility(View.VISIBLE);
                    // ....
                }
                else
                {
                    // ....
                    changerPrixCartDialogET.setVisibility(View.INVISIBLE);
                    // ....
                }
            }
        });
        afficherPrixAchatCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(afficherPrixAchatCB.isChecked())
                {
                    // ....
                    prixAchatCartDialogTV.setVisibility(View.VISIBLE);
                    // ....
                }
                else
                {
                    // ....
                    prixAchatCartDialogTV.setVisibility(View.INVISIBLE);
                    // ....
                }
            }
        });

        d.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        d.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(),"negative button",Toast.LENGTH_SHORT).show();
            }
        });
            AlertDialog alertDialog = d.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean wantToCloseDialog = false;

                    String qteVendu = qteVenduCartDialogET.getText().toString();
                    if (Integer.parseInt(qteVendu) > produitCartArrayList.get(position).getQte()){
                        qteVenduCartDialogET.setError("Veuillez saisir une quantité inférieure ou égale à "+produitCartArrayList.get(position).getQte());
                    }else {wantToCloseDialog = true;}
                    if (Float.parseFloat(changerPrixCartDialogET.getText().toString()) < produitCartArrayList.get(position).getPrixAchat()){
                        changerPrixCartDialogET.setError("Attention, prix de vente est inferieure à prix d'achat");
                    }
                    if (wantToCloseDialog){
                        produitCartArrayList.get(position).setClickCounter(Integer.parseInt(qteVenduCartDialogET.getText().toString()));
                        if(modifierPrixVenteCb.isChecked())
                            produitCartArrayList.get(position).setPrixVente(Float.parseFloat(changerPrixCartDialogET.getText().toString()));
                        changerPrixCartDialogET.setText(""+produitCartArrayList.get(position).getPrixVente());
                        produitCartAdapter.notifyDataSetChanged();
                        calculerTotal();
                        alertDialog.dismiss();}
                }
            });


        ((ComptoirActivity) getActivity()).dataChanged();
        calculerTotal();


    }


    @Override
    public void onDeleteButtonClick(int position) {
        for (int i=0;i<ComptoirActivity.produitArrayList.size();i++){
            if (ComptoirActivity.produitArrayList.get(i).getId() == produitCartArrayList.get(position).getId()){
                ComptoirActivity.produitArrayList.get(i).setClickCounter(0);
            }
        }
        produitCartArrayList.remove(position);
        produitCartAdapter.notifyDataSetChanged();
        calculerTotal();
        ((ComptoirActivity) getActivity()).dataChanged();
        if (produitCartArrayList.size() == 0)
            dismiss();
    }

    private void viderCart(){
        for (int i=0;i<ComptoirActivity.produitArrayList.size();i++){
            ComptoirActivity.produitArrayList.get(i).setClickCounter(0);
        }
        produitCartArrayList.clear();
        produitCartAdapter.notifyDataSetChanged();
        ((ComptoirActivity) getActivity()).dataChanged();
        dismiss();
    }
}

