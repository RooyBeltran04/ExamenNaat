package com.rba.examennaat.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rba.examennaat.pojos.Empresa;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderRecycler> {
    ArrayList<Empresa> empresas;

    public RecyclerAdapter(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }

    @NonNull
    @Override
    public ViewHolderRecycler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
        }
    }
}
