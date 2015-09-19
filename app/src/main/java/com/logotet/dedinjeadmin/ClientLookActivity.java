package com.logotet.dedinjeadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.logotet.dedinjeadmin.adapters.ClientEventsAdapter;
import com.logotet.dedinjeadmin.adapters.ClientIgracAdapter;
import com.logotet.dedinjeadmin.model.Utakmica;

public class ClientLookActivity extends AppCompatActivity {
    private boolean showSastav;
    private boolean showEvents;

    private ClientEventsAdapter eventsAdapter;
    private ClientIgracAdapter sastavAdapter;


    Button btnShowSastav;
    Button btnShowEvents;

    LinearLayout llEvents;
    LinearLayout llSastav;
    ListView lvClientEvents;
    ListView lvClientIgrac;

    TextView tvCurrentScore;
    TextView tvCurrentMinute;
    TextView tvHomeTeam;
    TextView tvAwayTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_look);

        btnShowSastav = (Button) findViewById(R.id.btnShowSastav);
        btnShowEvents = (Button) findViewById(R.id.btnShowEvents);
        llEvents = (LinearLayout) findViewById(R.id.llEvents);
        llSastav = (LinearLayout) findViewById(R.id.llSastav);


        lvClientEvents = (ListView) findViewById(R.id.lvClientEvents);
        lvClientIgrac = (ListView) findViewById(R.id.lvClientIgrac);

        eventsAdapter = new ClientEventsAdapter(this);
        sastavAdapter = new ClientIgracAdapter(this);

        lvClientEvents.setAdapter(eventsAdapter);
        lvClientIgrac.setAdapter(sastavAdapter);


        tvCurrentScore = (TextView) findViewById(R.id.tvCurrentScore);
        tvCurrentMinute = (TextView) findViewById(R.id.tvCurrentMinute);
        tvHomeTeam = (TextView) findViewById(R.id.tvHomeTeamName);
        tvAwayTeam = (TextView) findViewById(R.id.tvAwayTeamName);



        Utakmica utakmica = Utakmica.getInstance();
        utakmica.odrediMinutazu();
        tvCurrentScore.setText(utakmica.getCurrentRezulat());
        tvHomeTeam.setText(utakmica.getHomeTeamName());
        tvAwayTeam.setText(utakmica.getAwayTeamName());

        showSastav = true;
        showEvents = true;


        btnShowSastav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSastav = showSastav ? false : true;
                if(showSastav)
                    llSastav.setVisibility(View.VISIBLE);
                else
                    llSastav.setVisibility(View.GONE);
            }
        });

        btnShowEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEvents = showEvents ? false : true;
                if(showEvents)
                    llEvents.setVisibility(View.VISIBLE);
                else
                    llEvents.setVisibility(View.GONE);
            }
        });



    }


}
