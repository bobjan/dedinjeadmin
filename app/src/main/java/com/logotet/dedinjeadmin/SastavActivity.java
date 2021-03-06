package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.logotet.dedinjeadmin.adapters.DresIgracAdapter;
import com.logotet.dedinjeadmin.adapters.SimpleIgracAdapter;
import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Igrac;
import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

import java.io.IOException;

public class SastavActivity extends AppCompatActivity {
    private static final String TAG = "SastavActivity";
    private static final int MIN_BROJ_IGRACA = 8;
    BazaIgraca bazaIgraca;

    Button btnSviIgraci;
    Button btnUProtokolu;

    ListView lvIgraci;
    ListView lvUProtokolu;

    LinearLayout llSviIgraci;
    LinearLayout llUProtokolu;


    private int clrSelected;
    private int clrDeselected;

    SimpleIgracAdapter fullAdapter;
    DresIgracAdapter protokolAdapter;

    String tekstProtokol;

    Button btnConfirmsastav;
    Intent intent;

    Handler handler;

    boolean fetched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sastav);

        bazaIgraca = BazaIgraca.getInstance();

        clrSelected = getResources().getColor(R.color.seltabclr);
        clrDeselected = getResources().getColor(R.color.unseltabclr);

        llSviIgraci = (LinearLayout) findViewById(R.id.llSviIgraci);
        llUProtokolu = (LinearLayout) findViewById(R.id.llProtokol);

        tekstProtokol = getResources().getString(R.string.btn_protokol);

        btnSviIgraci = (Button) findViewById(R.id.btnVanProtokola);
        btnUProtokolu = (Button) findViewById(R.id.btnUProtokolu);
        btnConfirmsastav = (Button) findViewById(R.id.btnConfirmSastav);

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

        btnUProtokolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUProtokolu.setBackgroundColor(clrSelected);
                btnSviIgraci.setBackgroundColor(clrDeselected);
                llSviIgraci.setVisibility(View.GONE);
                llUProtokolu.setVisibility(View.VISIBLE);
                if(fetched) {
                    fullAdapter.notifyDataSetChanged();
                    btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
                }
                if (BazaIgraca.getInstance().getuProtokolu().size() < MIN_BROJ_IGRACA)
                    btnConfirmsastav.setEnabled(false);
                else
                    btnConfirmsastav.setEnabled(true);

            }
        });

        btnSviIgraci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUProtokolu.setBackgroundColor(clrDeselected);
                btnSviIgraci.setBackgroundColor(clrSelected);
                llSviIgraci.setVisibility(View.VISIBLE);
                llUProtokolu.setVisibility(View.GONE);
                if(fetched) {
                    fullAdapter.notifyDataSetChanged();
                    btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
                }
            }
        });

        lvIgraci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Igrac igrac = (Igrac) parent.getItemAtPosition(position);
                bazaIgraca.ubaciUProtokol(igrac);
                fullAdapter.notifyDataSetChanged();
                protokolAdapter.notifyDataSetChanged();
                btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
            }
        });

        lvUProtokolu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Igrac igrac = (Igrac) parent.getItemAtPosition(position);
                bazaIgraca.izbaciIzProtokola(igrac);
                BazaIgraca.getInstance().sortirajVanProtokola();
                fullAdapter.notifyDataSetChanged();
                protokolAdapter.notifyDataSetChanged();
                btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
                if (BazaIgraca.getInstance().getuProtokolu().size() < MIN_BROJ_IGRACA)
                    btnConfirmsastav.setEnabled(false);
                else
                    btnConfirmsastav.setEnabled(true);

            }
        });

        intent = new Intent(this, AfterLoginActivity.class);
        btnConfirmsastav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new RequestThread(RequestPreparator.MAKESASTAV, AllStatic.HTTPHOST, null);
                th.start();
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.sastav_message), Toast.LENGTH_LONG).show();
                finish();
                startActivity(intent);
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 100){
                    fetched = true;
                    btnUProtokolu.setEnabled(true);
                    btnSviIgraci.setEnabled(true);
                    BazaIgraca.getInstance().refreshProtokol();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.w(TAG, "onResum()");
        btnUProtokolu.setEnabled(false);
        btnSviIgraci.setEnabled(false);
        BazaIgraca.getInstance().refreshBrojeviNaDresu();
        fullAdapter.notifyDataSetChanged();
        protokolAdapter.notifyDataSetChanged();
        fetched = false;
        fetchSastav();
//        Log.w(TAG, BazaIgraca.getInstance().getNaTerenu().size() + "......" + BazaIgraca.getInstance().getNaKlupi().size());

    }

    private void fetchSastav() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpCatcher httpCatcher = null;
                try {
                    httpCatcher = new HttpCatcher(RequestPreparator.GETSASTAV, AllStatic.HTTPHOST, null);
                    httpCatcher.catchData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            fetched = true;
                            btnUProtokolu.setEnabled(true);
                            btnSviIgraci.setEnabled(true);
                            BazaIgraca.getInstance().refreshProtokol();

                            fullAdapter.notifyDataSetChanged();
                            protokolAdapter.notifyDataSetChanged();
                            btnUProtokolu.setText(tekstProtokol + "(" + BazaIgraca.getInstance().getuProtokolu().size() + ")");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }
}
