package com.logotet.dedinjeadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.logotet.dedinjeadmin.model.BazaStadiona;
import com.logotet.dedinjeadmin.model.BazaTimova;
import com.logotet.dedinjeadmin.model.Stadion;
import com.logotet.dedinjeadmin.model.Tim;
import com.logotet.dedinjeadmin.model.Utakmica;
import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;
import com.logotet.util.BJDatum;
import com.logotet.util.BJTime;

import java.text.ParseException;
import java.util.ArrayList;

public class StartMatchActivity extends AppCompatActivity {
    private static final String TAG = "StartMatchActivity";

    EditText etDatumUtakmice;
    EditText etVremePocetka;

    BJTime vreme;
    BJDatum datum;

    Spinner spStadion;
    Spinner spProtivnik;
    CheckBox cbDomacin;

    Stadion selectedStadion;
    Tim selectedProtivnik;


    Button btnCreateMatch;

    ArrayList listaStadiona;
    ArrayList listaProtivnika;

    Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_match);


        etDatumUtakmice = (EditText) findViewById(R.id.etDatumUtakmice);
        etVremePocetka = (EditText) findViewById(R.id.etVremePocetka);


        spProtivnik = (Spinner) findViewById(R.id.spProtivnik);
        spStadion = (Spinner) findViewById(R.id.spStadion);
        btnCreateMatch = (Button) findViewById(R.id.btnCreateMatch);
        cbDomacin = (CheckBox) findViewById(R.id.cbDomacin);

        listaStadiona = BazaStadiona.getInstance().getTereni();
        listaProtivnika = BazaTimova.getInstance().getProtivnici();


        ArrayAdapter<String> stadionAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, listaStadiona);

        stadionAdapter.setDropDownViewResource
                (R.layout.spinner_dropdown_item);

        spStadion.setAdapter(stadionAdapter);


        ArrayAdapter<String> protivnikAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, listaProtivnika);

        protivnikAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spProtivnik.setAdapter(protivnikAdapter);


        spStadion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStadion = (Stadion) spStadion.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStadion = (Stadion) listaStadiona.get(0);
            }
        });


        spProtivnik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProtivnik = (Tim) spProtivnik.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedProtivnik = (Tim) listaProtivnika.get(0);
            }
        });


        nextIntent = new Intent(this, AfterLoginActivity.class);

        btnCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utakmica utakmica = Utakmica.getInstance();
                if ((utakmica.isFromHttpServer()) && utakmica.getDatum().isToday()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartMatchActivity.this);
                    alertDialogBuilder.setTitle("Већ постоји данашња утакмица. Креирањем нове ће бити обрисани сви постојећи подаци(састав...)");

                    alertDialogBuilder.setMessage("Да, за креирање утакмице!");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setNegativeButton("Не", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            startActivity(nextIntent);
                            finish();
                        }
                    });
                    alertDialogBuilder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            kreirajUtakmicu();

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    kreirajUtakmicu();
                }
            }
        });

        cbDomacin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utakmica.getInstance().setUserTeamDomacin(isChecked);
            }
        });
    }

    private void kreirajUtakmicu() {
        Utakmica utakmica = Utakmica.getInstance();

        try {
            datum = new BJDatum(etDatumUtakmice.getText().toString());
        } catch (ParseException e) {
            datum = new BJDatum();
        }

        vreme = new BJTime(etVremePocetka.getText().toString());

        utakmica.setDatum(datum);
        utakmica.setProtivnikId(selectedProtivnik.getId());
        utakmica.setStadionId(selectedStadion.getId());
        utakmica.setPlaniranoVremePocetka(vreme);
        Thread th = new RequestThread(RequestPreparator.STARTMATCH, AllStatic.HTTPHOST, utakmica);
        th.start();
        Toast.makeText(getApplicationContext(), "Utakmica je kreirana", Toast.LENGTH_LONG).show();
        startActivity(nextIntent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if ((System.currentTimeMillis() - AllStatic.lastActiveTime) > AllStatic.TIMEOUT) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }
}
