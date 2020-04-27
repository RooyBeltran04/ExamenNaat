package com.rba.examennaat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rba.examennaat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdministracionFragment extends Fragment {

    public AdministracionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administracion, container, false);
    }
}
