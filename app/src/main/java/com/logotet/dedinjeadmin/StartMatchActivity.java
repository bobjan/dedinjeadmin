package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.ArrayList;

public class StartMatchActivity extends AppCompatActivity {
    private static final String TAG = "StartMatchActivity";

    TextView tvDatum;
    TimePicker tpStartTime;
    BJTime vreme;
    Spinner spStadion;
    Spinner spProtivnik;
    CheckBox cbDomacin;

    Stadion selectedStadion;
    Tim selectedProtivnik;
    BJTime selectedTime;


    Button btnCreateMatch;

    ArrayList listaStadiona;
    ArrayList listaProtivnika;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_match);

        if (!AllStatic.loggedUser) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        tvDatum = (TextView) findViewById(R.id.tvDatum);
        tvDatum.setText((new BJDatum().toString()));

        tpStartTime = (TimePicker) findViewById(R.id.tpStartTime);
        tpStartTime.setIs24HourView(true);
        selectedTime = new BJTime();

        spProtivnik = (Spinner) findViewById(R.id.spProtivnik);
        spStadion = (Spinner) findViewById(R.id.spStadion);
        btnCreateMatch = (Button) findViewById(R.id.btnCreateMatch);
        cbDomacin = (CheckBox) findViewById(R.id.cbDomacin);

        listaStadiona = BazaStadiona.getInstance().getTereni();
        listaProtivnika = BazaTimova.getInstance().getProtivnici();


        ArrayAdapter<String> stadionAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, listaStadiona);

        stadionAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spStadion.setAdapter(stadionAdapter);


        ArrayAdapter<String> protivnikAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, listaProtivnika);

        protivnikAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spProtivnik.setAdapter(protivnikAdapter);


        tpStartTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                selectedTime = new BJTime(hourOfDay + ":" + minute);
            }
        });


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


        intent = new Intent(this, AfterLoginActivity.class);

        btnCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utakmica utakmica = Utakmica.getInstance();
//                utakmica.setPlaniranoVremePocetka();
                utakmica.setDatum(new BJDatum());
                utakmica.setProtivnikId(selectedProtivnik.getId());
                utakmica.setStadionId(selectedStadion.getId());
                utakmica.setPlaniranoVremePocetka(selectedTime.toString());
                Thread th = new RequestThread(RequestPreparator.STARTMATCH, AllStatic.HTTPHOST);
                th.start();
                Toast.makeText(getApplicationContext(), "Utakmica je kreirana", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });

        cbDomacin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utakmica.getInstance().setUserTeamDomacin(isChecked);
            }
        });

    }


}
