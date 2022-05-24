package com.example.pfeact;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfeact.databinding.CartDialogLayoutBinding;
import com.example.pfeact.myClasses.ClientAdapter;
import com.example.pfeact.myClasses.Produit;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CartDialog extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private ProduitCartAdapter produitCartAdapter;
    private ArrayList<Produit> produitCartArrayList;
    BottomSheetBehavior bottomSheetBehavior;
    ViewDataBinding bi;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_dialog_layout,
                container, false);

        recyclerView = view.findViewById(R.id.idRVCart);




        produitCartArrayList = new ArrayList<>();
        for (int i = 0; i < ComptoirActivity.produitArrayList.size(); i++) {
            if (ComptoirActivity.produitArrayList.get(i).getClickCounter() > 0){
                produitCartArrayList.add(ComptoirActivity.produitArrayList.get(i));
            Log.d("TAG", "onCreateView: produit "+i+" added, clickcounter = "+ComptoirActivity.produitArrayList.get(i).getClickCounter()+"");
        }}
        if (produitCartArrayList.size() == 0) {
            Toast.makeText(getContext(), "panier vide !!", Toast.LENGTH_SHORT).show();
        }
            buildRecyclerView();



        return view;
    }

    private void buildRecyclerView() {



        // below line we are creating a new array list



        produitCartAdapter = new ProduitCartAdapter(produitCartArrayList, getContext());

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        recyclerView.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        recyclerView.setAdapter(produitCartAdapter);
    }
}

