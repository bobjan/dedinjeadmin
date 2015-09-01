package com.logotet.dedinjeadmin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

public class AfterLoginActivity extends AppCompatActivity {
    Button btnStartMatch;
    Button btnMakeSastav;
    Button btnEnterEvent;
    Button btnDeleteEvent;
    Button btnLogout;

    Activity ovaAktivnost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);


        btnStartMatch = (Button) findViewById(R.id.btnStartMatch);
        btnMakeSastav = (Button) findViewById(R.id.btnMakeSastav);
        btnEnterEvent = (Button) findViewById(R.id.btnEnterEvent);
        btnDeleteEvent = (Button) findViewById(R.id.btnDeleteEvent);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        ovaAktivnost = this;



        btnStartMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ovaAktivnost, StartMatchActivity.class);
                startActivity(intent);
            }
        });

        btnMakeSastav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ovaAktivnost, SastavActivity.class);
                startActivity(intent);
            }
        });

        btnEnterEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ovaAktivnost, EventsActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ovaAktivnost, DeleteEventsActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

}
