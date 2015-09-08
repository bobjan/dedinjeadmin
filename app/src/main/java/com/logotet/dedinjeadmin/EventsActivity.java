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
import android.widget.Toast;

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Igrac;
import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

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
    RadioGroup rgWhoseGoal;
    RadioGroup rgGoalKind;
    RadioGroup rgKartoni;
    RadioGroup rgWhoseKarton;

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

        btnSendTime = (Button) findViewById(R.id.btnTimeEvents);
        btnSendGoal = (Button) findViewById(R.id.btnGoal);
        btnSendKarton = (Button) findViewById(R.id.btnKarton);
        btnSendIzmena = (Button) findViewById(R.id.btnIzmena);
        btnSendKomentar = (Button) findViewById(R.id.btnKomentar);

        rgGoalKind = (RadioGroup) findViewById(R.id.rgGoalKind);
        rgWhoseGoal = (RadioGroup) findViewById(R.id.rgWhoseGol);
        rgKartoni = (RadioGroup) findViewById(R.id.rgKartoni);
        rgTimeEvents = (RadioGroup) findViewById(R.id.rgTimeEvents);
        rgWhoseKarton = (RadioGroup) findViewById(R.id.rgWhoseKarton);

        spKarton = (Spinner) findViewById(R.id.spinKarton);
        spStrelac = (Spinner) findViewById(R.id.spinStrelac);
        spIgracIn = (Spinner) findViewById(R.id.spinPlayerIn);
        spIgracOut = (Spinner) findViewById(R.id.spinPlayerOut);


        ArrayAdapter<String> naTerenuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, naTerenu);
        naTerenuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> naKlupiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, naKlupi);
        naKlupiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spKarton.setAdapter(naTerenuAdapter);
        spStrelac.setAdapter(naTerenuAdapter);
        spIgracOut.setAdapter(naTerenuAdapter);
        spIgracIn.setAdapter(naKlupiAdapter);

        spIgracIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracUnutra = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                igracUnutra = (Igrac) parent.getItemAtPosition(0);
            }
        });
        spIgracOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracNapolje = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                igracNapolje = (Igrac) parent.getItemAtPosition(0);

            }
        });
        spStrelac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracStrelac = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                igracStrelac = (Igrac) parent.getItemAtPosition(0);

            }
        });
        spKarton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                igracKarton = (Igrac) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                igracKarton = (Igrac) parent.getItemAtPosition(0);

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

        rgWhoseKarton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbKartonDedinje) {
                    spKarton.setVisibility(View.VISIBLE);
                } else {
                    spKarton.setVisibility(View.INVISIBLE);
                }
            }
        });

        rgWhoseGoal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbDedinjeGol) {
                    spStrelac.setVisibility(View.VISIBLE);
                } else {
                    spStrelac.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((System.currentTimeMillis() - AllStatic.lastActiveTime) > AllStatic.TIMEOUT) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        btnSendTime.setOnClickListener(this);
        btnSendGoal.setOnClickListener(this);
        btnSendIzmena.setOnClickListener(this);
        btnSendKarton.setOnClickListener(this);
        btnSendKomentar.setOnClickListener(this);
        setDefaults();
    }

    private void setDefaults() {
        spKarton.setVisibility(View.INVISIBLE);
        spStrelac.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        Dogadjaj dogadjaj = new Dogadjaj();
        dogadjaj.setTipDogadjaja(-1);
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
            switch (rgGoalKind.getCheckedRadioButtonId()) {
                case R.id.rbGolGame:
                    if (rgWhoseGoal.getCheckedRadioButtonId() == R.id.rbDedinjeGol) {
                        dogadjaj.setTipDogadjaja(Dogadjaj.GOLFKDEDINJE);
                        dogadjaj.setPlayerId(igracStrelac.getId());
                    } else {
                        dogadjaj.setTipDogadjaja(Dogadjaj.GOLPROTIVNIK);
                    }
                    break;
                case R.id.rbGolPenal:
                    if (rgWhoseGoal.getCheckedRadioButtonId() == R.id.rbDedinjeGol) {
                        dogadjaj.setTipDogadjaja(Dogadjaj.GOLPENALFKDEDINJE);
                        dogadjaj.setPlayerId(igracStrelac.getId());
                    } else {
                        dogadjaj.setTipDogadjaja(Dogadjaj.GOLPENALPROTIVNIK);
                    }

                    break;
                case R.id.rbMissedPenal:
                    if (rgWhoseGoal.getCheckedRadioButtonId() == R.id.rbDedinjeGol) {
                        dogadjaj.setTipDogadjaja(Dogadjaj.MISSEDPENALFKDEDINJE);
                        dogadjaj.setPlayerId(igracStrelac.getId());

                    } else {
                        dogadjaj.setTipDogadjaja(Dogadjaj.MISSEDPENALPROTIVNIK);
                    }
                    break;
            }
        }

        if (v.getId() == btnSendIzmena.getId()) {
            dogadjaj.setTipDogadjaja(Dogadjaj.IZMENAIGRACA);
            dogadjaj.setPlayerInId(igracUnutra.getId());
            dogadjaj.setPlayerOutId(igracNapolje.getId());
        }

        if (v.getId() == btnSendKarton.getId()) {
            switch (rgKartoni.getCheckedRadioButtonId()) {
                case R.id.rbZutiKarton:
                    if (rgWhoseKarton.getCheckedRadioButtonId() == R.id.rbKartonDedinje) {
                        dogadjaj.setTipDogadjaja(Dogadjaj.ZUTIKARTONFKDEDINJE);
                        dogadjaj.setPlayerId(igracKarton.getId());
                    } else {
                        dogadjaj.setTipDogadjaja(Dogadjaj.ZUTIKARTONPROTIVNIK);
                    }
                    break;

                case R.id.rbDrugiZutiKarton:
                    if (rgWhoseKarton.getCheckedRadioButtonId() == R.id.rbKartonDedinje) {
                        dogadjaj.setTipDogadjaja(Dogadjaj.DRUGIZUTIFKDEDINJE);
                        dogadjaj.setPlayerId(igracKarton.getId());
                    } else {
                        dogadjaj.setTipDogadjaja(Dogadjaj.DRUGIZUTIPROTIVNIK);
                    }
                    break;

                case R.id.rbCrveniKarton:
                    if (rgWhoseKarton.getCheckedRadioButtonId() == R.id.rbKartonDedinje) {
                        dogadjaj.setTipDogadjaja(Dogadjaj.CRVENIKARTONFKDEDINJE);
                        dogadjaj.setPlayerId(igracKarton.getId());
                    } else {
                        dogadjaj.setTipDogadjaja(Dogadjaj.CRVENIKARTONPROTIVNIK);
                    }
                    break;
            }
        }

        if (v.getId() == btnSendKomentar.getId()) {
            EditText etKomentar = (EditText) findViewById(R.id.etKomentar);
            dogadjaj.setKomentar(etKomentar.getText().toString());
            dogadjaj.setTipDogadjaja(Dogadjaj.KOMENTAR);
        }

        if (dogadjaj.getTipDogadjaja() >= 0) {
            Thread thread = new RequestThread(RequestPreparator.MAKEEVENT, AllStatic.HTTPHOST, dogadjaj);
            thread.start();
        } else {
            Toast.makeText(getApplicationContext(),
                    getApplicationContext().getString(R.string.invalid_entry), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }
}
