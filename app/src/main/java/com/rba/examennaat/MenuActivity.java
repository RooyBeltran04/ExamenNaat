package com.rba.examennaat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rba.examennaat.adapters.PagerAdapter;
import com.rba.examennaat.pojos.Empresa;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private TabLayout mtabL;
    private ViewPager mvPager;
    private TabItem mtabRecaudacion,mtabRecargas,mtabAdministracion;
    private PagerAdapter pagerAdapter;
    private ArrayList<Empresa> arrayEmpresas;
    private RecyclerView mrecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mtabL=findViewById(R.id.tabL);
        mvPager=findViewById(R.id.vPager);
        mtabRecaudacion=findViewById(R.id.tabRecaudacion);
        mtabRecargas=findViewById(R.id.tabRecargas);
        mtabAdministracion=findViewById(R.id.tabAdministracion);

        //inicializamos ArrayList
        arrayEmpresas=new ArrayList<>();



        //PagerAdapter
        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),mtabL.getTabCount());
        mvPager.setAdapter(pagerAdapter);
        mtabL.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mvPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2)
                    pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mvPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mtabL) );

    }


}
