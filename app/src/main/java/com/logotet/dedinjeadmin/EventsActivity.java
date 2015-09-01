package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        if(!AllStatic.loggedUser){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }



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


        for (btnIdx = 0; btnIdx < btnExpand.length; btnIdx++) {
            btnExpand[btnIdx].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < llExpand.length; j++) {
                        llExpand[j].setVisibility(View.GONE);
                    }
                    for (int i = 0; i < btnExpand.length; i++) {
                        if (btnExpand[i].getId() == v.getId()) {
                            llExpand[i].setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == btnSendTime.getId()) {

        }

        if (v.getId() == btnSendGoal.getId()) {

        }

        if (v.getId() == btnSendIzmena.getId()) {

        }


        if (v.getId() == btnSendKarton.getId()) {

        }

        if (v.getId() == btnSendKomentar.getId()) {

        }
    }

}
