package com.logotet.dedinjeadmin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.logotet.dedinjeadmin.adapters.DresIgracAdapter;
import com.logotet.dedinjeadmin.adapters.SimpleIgracAdapter;
import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Igrac;

public class SastavActivity extends AppCompatActivity {
    private static final String TAG = "SastavActivity";


    Button btnSviIgraci;
    Button btnUProtokolu;

    ListView lvIgraci;
    ListView lvUProtokolu;

    LinearLayout llSviIgraci;
    LinearLayout llUProtokolu;


    private int clrSelected;
    private int clrDeselected;
    private Context context;

    SimpleIgracAdapter fullAdapter;
    DresIgracAdapter protokolAdapter;

    String tekstProtokol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sastav);
        context = getApplicationContext();

        clrSelected = getResources().getColor(R.color.white);
        clrDeselected = getResources().getColor(R.color.grey);

        llSviIgraci = (LinearLayout) findViewById(R.id.llSviIgraci);
        llUProtokolu = (LinearLayout) findViewById(R.id.llProtokol);

        tekstProtokol = getResources().getString(R.string.btn_protokol);

        btnSviIgraci = (Button) findViewById(R.id.btnVanProtokola);
        btnUProtokolu = (Button) findViewById(R.id.btnUProtokolu);

        lvIgraci = (ListView) findViewById(R.id.lvIgraci);
        lvUProtokolu = (ListView) findViewById(R.id.lvUProtokolu);


        llSviIgraci.setVisibility(View.VISIBLE);
        llUProtokolu.setVisibility(View.GONE);
        btnSviIgraci.setBackgroundColor(clrSelected);
        btnUProtokolu.setBackgroundColor(clrDeselected);

        btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");



        fullAdapter = new SimpleIgracAdapter(this);
        protokolAdapter = new DresIgracAdapter(this);

        lvIgraci.setAdapter(fullAdapter);
        lvUProtokolu.setAdapter(protokolAdapter);

// **** u protokolu
        btnUProtokolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUProtokolu.setBackgroundColor(clrSelected);
                btnSviIgraci.setBackgroundColor(clrDeselected);
                llSviIgraci.setVisibility(View.GONE);
                llUProtokolu.setVisibility(View.VISIBLE);
                randomInOut();
                btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
            }
        });


//  ****   svi igraci

        btnSviIgraci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUProtokolu.setBackgroundColor(clrDeselected);
                btnSviIgraci.setBackgroundColor(clrSelected);
                llSviIgraci.setVisibility(View.VISIBLE);
                llUProtokolu.setVisibility(View.GONE);
                randomInOut();
                btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
            }
        });


        //  ******************

    }
    public void randomInOut(){
        BazaIgraca bazaIgraca = BazaIgraca.getInstance();
        int rnd = (int) (Math.random() * bazaIgraca.getVanProtokola().size());

        Igrac igr = bazaIgraca.getVanProtokola().get(rnd);

        bazaIgraca.ubaciUProtokol(igr);
        fullAdapter.notifyDataSetChanged();
        protokolAdapter.notifyDataSetChanged();

    }

}
