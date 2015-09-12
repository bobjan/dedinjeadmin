package com.logotet.dedinjeadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.logotet.dedinjeadmin.model.Utakmica;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

import java.io.IOException;

public class AfterLoginActivity extends AppCompatActivity {
    Button btnStartMatch;
    Button btnMakeSastav;
    Button btnEnterEvent;
    Button btnDeleteEvent;
    Button btnLogout;

    Activity ovaAktivnost;
    ProgressBar progressBar;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        btnStartMatch = (Button) findViewById(R.id.btnStartMatch);
        btnMakeSastav = (Button) findViewById(R.id.btnMakeSastav);
        btnEnterEvent = (Button) findViewById(R.id.btnEnterEvent);
        btnDeleteEvent = (Button) findViewById(R.id.btnDeleteEvent);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        progressBar = (ProgressBar) findViewById(R.id.pbProgressBar);

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
                System.exit(0);
            }
        });

        handler = new Handler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((System.currentTimeMillis() - AllStatic.lastActiveTime) > AllStatic.TIMEOUT) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        HttpCatcher.fetchBaseData(handler);
//        checkEveything();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }



    private void checkEveything() {
        Utakmica utakmica = Utakmica.getInstance();
        if (!utakmica.getDatum().isToday()) {
            disableAllButtons();
            return;
        }
        if (!utakmica.isFromHttpServer())
            disableAllButtons();
        else {
            enableAllButtons();
        }
    }

    private void disableAllButtons() {
        btnMakeSastav.setEnabled(false);
        btnEnterEvent.setEnabled(false);
        btnDeleteEvent.setEnabled(false);
    }


    private void enableAllButtons() {
        btnMakeSastav.setEnabled(true);
        btnEnterEvent.setEnabled(true);
        btnDeleteEvent.setEnabled(true);
    }

}
