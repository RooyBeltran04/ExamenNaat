package com.rba.examennaat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rba.examennaat.R;
import com.rba.examennaat.adapters.RecyclerAdapter;
import com.rba.examennaat.pojos.Empresa;

import java.util.ArrayList;

public class RecargasFragment extends Fragment {
    RecyclerView mrVRecarga;
    public ArrayList<Empresa> arrayEmpresas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public RecargasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_recargas, container, false);
        arrayEmpresas= new ArrayList<>();
        mrVRecarga= (RecyclerView) vista.findViewById(R.id.rVRecarga);
        mrVRecarga.setLayoutManager(new GridLayoutManager(getContext(),3));

        //inicializamos atributos de firebase
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        databaseReference.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Empresa aux=new Empresa();
                    Empresa e =(Empresa) objSnaptshot.getValue(Empresa.class);
                    aux.setNombre(e.getNombre());
                    aux.setOpciones(e.getOpciones());
                    arrayEmpresas.add(aux);
                    Log.d("RBA","Size:"+arrayEmpresas.size());
                }
                RecyclerAdapter adapter=new RecyclerAdapter(arrayEmpresas);
                mrVRecarga.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });




        return vista;
    }

    private void llenarLista() {


    }
}
