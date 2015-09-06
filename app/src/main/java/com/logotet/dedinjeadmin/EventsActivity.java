package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Igrac;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EventActivity";


    Button[] btnExpand;

    LinearLayout[] llExpand;

    Button btnSendTime;
    Button btnSendGoal;
    Button btnSendKarton;
    Button btnSendIzmena;
    Button btnSendKomentar;


    RadioGroup rgTimeEvents;
    RadioGroup rgGol;
    RadioGroup rgKartoni;
    RadioGroup rgWhatTeam;

    int btnIdx;

    ArrayList naTerenu;
    ArrayList naKlupi;

    Spinner spKarton;
    Spinner spStrelac;
    Spinner spIgracIn;
    Spinner spIgracOut;
    NumberPicker npMinut;

    Igrac igracStrelac;
    Igrac igracKarton;
    Igrac igracNapolje;
    Igrac igracUnutra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

/*
        if (!AllStatic.loggedUser) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
*/
        naTerenu = BazaIgraca.getInstance().getNaTerenu();
        naKlupi = BazaIgraca.getInstance().getNaKlupi();
        Log.w(TAG, "na terenu = " + naTerenu.size() + "\tna klupi = " + naKlupi.size());
        npMinut = (NumberPicker) findViewById(R.id.npMinuti);
        npMinut.setMaxValue(60);
        npMinut.setMinValue(0);
        npMinut.setValue(0);
        npMinut.setWrapSelectorWheel(false);


        btnExpand = new Button[5];
        llExpand = new LinearLayout[5];

        btnExpand[0] = (Button) findViewById(R.id.btnExpandTime);
        btnExpand[1] = (Button) findViewById(R.id.btnExpandGoal);
        btnExpand[2] = (Button) findViewById(R.id.btnExpandKarton);
        btnExpand[3] = (Button) findViewById(R.id.btnExpandIzmena);
        btnExpand[4] = (Button) findViewById(R.id.btnExpandKomentar);

        llExpand[0] = (LinearLayout) findViewById(R.id.llExpandTime);
        llExpand[1] = (LinearLayout) findViewById(R.id.llExpandGoal);
        llExpand[2] = (LinearLayout) findViewById(R.id.llExpandKarton);
        llExpand[3] = (LinearLayout) findViewById(R.id.llExpandIzmena);
        llExpand[4] = (LinearLayout) findViewById(R.id.llExpandKomentar);

        btnSendTime = (Button) findViewById(R.id.btnStartMatch);
        btnSendGoal = (Button) findViewById(R.id.btnGoal);
        btnSendKarton = (Button) findViewById(R.id.btnKarton);
        btnSendIzmena = (Button) findViewById(R.id.btnIzmena);
        btnSendKomentar = (Button) findViewById(R.id.btnKomentar);

        rgGol = (RadioGroup) findViewById(R.id.rgGol);
        rgKartoni = (RadioGroup) findViewById(R.id.rgKartoni);
        rgTimeEvents = (RadioGroup) findViewById(R.id.rgTimeEvents);
        rgWhatTeam = (RadioGroup) findViewById(R.id.rgWhatTeam);

        spKarton = (Spinner) findViewById(R.id.spinKarton);
        spStrelac = (Spinner) findViewById(R.id.spinStrelac);
        spIgracIn = (Spinner) findViewById(R.id.spinPlayerIn);
        spIgracOut = (Spinner) findViewById(R.id.spinPlayerOut);



        ArrayAdapter<String> naTerenuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, naTerenu);
        naTerenuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> naKlupiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, naTerenu);
        naKlupiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//    Log.w(TAG, "prvi set aapter");
//        spKarton.setAdapter(naTerenuAdapter);
//        Log.w(TAG, "drug set aapter");
//        spStrelac.setAdapter(naTerenuAdapter);
//        Log.w(TAG, "trec set aapter");
        spIgracOut.setAdapter(naTerenuAdapter);
        Log.w(TAG, "fourth set aapter");
        spIgracIn.setAdapter(naKlupiAdapter);
        Log.w(TAG, "fifth set aapter");

        spIgracIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracUnutra = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spIgracOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracNapolje = (Igrac) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spStrelac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracStrelac = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spKarton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracKarton = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        for (btnIdx = 0; btnIdx < btnExpand.length; btnIdx++) {
            btnExpand[btnIdx].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < llExpand.length; j++) {
                        llExpand[j].setVisibility(View.GONE);
                        btnExpand[j].setBackgroundResource(R.drawable.unselbutton);
                    }
                    for (int i = 0; i < btnExpand.length; i++) {
                        if (btnExpand[i].getId() == v.getId()) {
                            llExpand[i].setVisibility(View.VISIBLE);
                            btnExpand[i].setBackgroundResource(R.drawable.selbutton);
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        Dogadjaj dogadjaj = new Dogadjaj();
        if (v.getId() == btnSendTime.getId()) {
            switch (rgTimeEvents.getCheckedRadioButtonId()) {
                case R.id.rbStartGame:
                    dogadjaj.setTipDogadjaja(Dogadjaj.STARTUTAKMICE);
                    break;
                case R.id.rbHalfTime:
                    dogadjaj.setTipDogadjaja(Dogadjaj.HALFTIME);
                    break;
                case R.id.rbStartSecondHalf:
                    dogadjaj.setTipDogadjaja(Dogadjaj.STARTDRUGOPOLUVREME);
                    break;
                case R.id.rbFinalTime:
                    dogadjaj.setTipDogadjaja(Dogadjaj.FINALTIME);
                    break;
            }
        }

        if (v.getId() == btnSendGoal.getId()) {
            switch (rgGol.getCheckedRadioButtonId()) {
                case R.id.rbDedinjeEvent:
                    dogadjaj.setTipDogadjaja(Dogadjaj.GOLFKDEDINJE);
                    break;
                case R.id.rbProtivnikEvent:
                    dogadjaj.setTipDogadjaja(Dogadjaj.GOLPROTIVNIK);
                    break;
            }
        }

        if (v.getId() == btnSendIzmena.getId()) {

//            switch ((rgWhatTeam))

        }


        if (v.getId() == btnSendKarton.getId()) {

        }

        if (v.getId() == btnSendKomentar.getId()) {
            EditText etKomentar = (EditText) findViewById(R.id.etKomentar);
            dogadjaj.setKomentar(etKomentar.getText().toString());
            dogadjaj.setTipDogadjaja(Dogadjaj.KOMENTAR);
        }
    }

}
