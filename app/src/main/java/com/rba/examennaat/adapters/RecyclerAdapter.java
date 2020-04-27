package com.rba.examennaat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rba.examennaat.R;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderRecycler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycler holder, int position) {
        holder.midNombre.setText(empresas.get(position).getOpciones());
        switch (empresas.get(position).getNombre()){
            case "Claro":
                holder.midImagen.setImageResource(R.drawable.ic_claro);
                break;
            case "Tuenti":
                holder.midImagen.setImageResource(R.drawable.ic_tuenti);
                break;
            case "Entel":
                holder.midImagen.setImageResource(R.drawable.ic_entel);
                break;
            default:
                holder.midImagen.setImageResource(R.drawable.ic_logo);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return empresas.size();
    }

    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView midNombre;
        ImageView midImagen;

        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            midNombre=(TextView)itemView.findViewById(R.id.idNombre);
            midImagen=(ImageView)itemView.findViewById(R.id.idImagen);
        }
    }
}
