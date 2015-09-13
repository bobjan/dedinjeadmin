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

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.BazaPozicija;
import com.logotet.dedinjeadmin.model.BazaStadiona;
import com.logotet.dedinjeadmin.model.Utakmica;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

import java.io.IOException;

public class AfterLoginActivity extends AppCompatActivity {
    Button btnStartMatch;
    Button btnMakeSastav;
    Button btnEnterEvent;
    Button btnDeleteEvent;
    Button btnClientLook;
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
        btnClientLook = (Button) findViewById(R.id.btnClientLook);
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

        btnClientLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ovaAktivnost, ClientLookActivity.class);
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
        btnStartMatch.setEnabled(true);
        disableAllButtons();
        if ((System.currentTimeMillis() - AllStatic.lastActiveTime) > AllStatic.TIMEOUT) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        progressBar.setVisibility(View.VISIBLE);
        fetchBaseData();
        progressBar.setVisibility(View.VISIBLE);
        fetchmatchdata();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }


    private void fetchBaseData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpCatcher httpCatcher = null;
                try {
                    if (!BazaStadiona.getInstance().isLoaded()) {
                        httpCatcher = new HttpCatcher(RequestPreparator.GETSTADION, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                    }
                    if (!BazaPozicija.getInstance().isLoaded()) {
                        httpCatcher = new HttpCatcher(RequestPreparator.GETPOZICIJA, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                    }
                    if (!BazaIgraca.getInstance().isLoaded()) {
                        httpCatcher = new HttpCatcher(RequestPreparator.GETEKIPA, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                    }
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          btnStartMatch.setEnabled(true);
                                      }
                                  }
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void fetchmatchdata() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpCatcher httpCatcher = null;
                try {
                    httpCatcher = new HttpCatcher(RequestPreparator.GETLIVEMATCH, AllStatic.HTTPHOST, null);
                    httpCatcher.catchData();
//                    Thread.sleep(1000);
                    httpCatcher = new HttpCatcher(RequestPreparator.GETSASTAV, AllStatic.HTTPHOST, null);
                    httpCatcher.catchData();
                    httpCatcher = new HttpCatcher(RequestPreparator.ALLEVENTS, AllStatic.HTTPHOST, null);
                    httpCatcher.catchData();
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (eveythingOK())
                                              enableAllButtons();
                                          else
                                              disableAllButtons();
                                          progressBar.setVisibility(View.INVISIBLE);
                                      }
                                  }
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private boolean eveythingOK() {
        Utakmica utakmica = Utakmica.getInstance();
        if (!utakmica.getDatum().isToday()) {
            disableAllButtons();
            return false;
        }
        if (!utakmica.isFromHttpServer())
            return false;
        else {
            return true;
        }
    }

    public void disableAllButtons() {
        btnMakeSastav.setEnabled(false);
        btnEnterEvent.setEnabled(false);
        btnDeleteEvent.setEnabled(false);
    }


    public void enableAllButtons() {
        btnMakeSastav.setEnabled(true);
        btnEnterEvent.setEnabled(true);
        btnDeleteEvent.setEnabled(true);
    }

}
