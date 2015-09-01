package com.logotet.dedinjeadmin;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.logotet.dedinjeadmin.adapters.DresIgracAdapter;
import com.logotet.dedinjeadmin.adapters.SimpleIgracAdapter;
import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Igrac;

public class SastavActivity extends AppCompatActivity {
    private static final String TAG = "SastavActivity";
    BazaIgraca bazaIgraca;

    Button btnSviIgraci;
    Button btnUProtokolu;

    ListView lvIgraci;
    ListView lvUProtokolu;

    LinearLayout llSviIgraci;
    LinearLayout llUProtokolu;


    private int clrSelected;
    private int clrDeselected;
    private int clrDragEntered;
    private Context context;

    SimpleIgracAdapter fullAdapter;
    DresIgracAdapter protokolAdapter;

    String tekstProtokol;

    AdapterView.OnItemLongClickListener sviIgraciLongListener;
    AdapterView.OnItemLongClickListener uProtokoluLongListener;

    View.OnDragListener sviDragListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sastav);
        context = getApplicationContext();


        bazaIgraca = BazaIgraca.getInstance();


        clrSelected = getResources().getColor(R.color.white);
        clrDeselected = getResources().getColor(R.color.grey);
        clrDragEntered = getResources().getColor(R.color.myyellow);

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
            }
        });

        sviIgraciLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                View.DragShadowBuilder shBuilder = new View.DragShadowBuilder(view);
                Igrac igrac = (Igrac) parent.getItemAtPosition(position);
                ClipData clip = ClipData.newPlainText("","");
                view.startDrag(clip,shBuilder,igrac,0);
                return true;
            }
        };

        sviDragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        btnUProtokolu.setBackgroundColor(clrDeselected);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        btnUProtokolu.setBackgroundColor(clrDragEntered);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        btnUProtokolu.setBackgroundColor(clrDeselected);
                        break;
                    case DragEvent.ACTION_DROP:
                        Igrac igrac = (Igrac) event.getLocalState();

                        bazaIgraca.ubaciUProtokol(igrac);
                        fullAdapter.notifyDataSetChanged();
                        protokolAdapter.notifyDataSetChanged();
                        btnUProtokolu.setBackgroundColor(clrDeselected);
                        btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        btnUProtokolu.setBackgroundColor(clrDeselected);
                        break;
                }
                return true;
            }
        };

        lvIgraci.setOnItemLongClickListener(sviIgraciLongListener);
        btnUProtokolu.setOnDragListener(sviDragListener);
        //  ******************

    }

}
