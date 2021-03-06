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
import android.widget.TextView;

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.BazaPozicija;
import com.logotet.dedinjeadmin.model.BazaStadiona;
import com.logotet.dedinjeadmin.model.Utakmica;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AfterLoginActivity extends AppCompatActivity {
    private static final String TAG = "AfterLoginActivity";

    Button btnStartMatch;
    Button btnMakeSastav;
    Button btnEnterEvent;
    Button btnDeleteEvent;
    Button btnClientLook;
    Button btnLogout;

    TextView tvMatchInfoFirstLine;
    TextView tvMatchInfoSecondLine;

    Activity ovaAktivnost;
    ProgressBar progressBar;

    Handler handler;

    Thread threadOne = new Thread();
    Thread threadTwo = new Thread();

    private boolean sastavKreiran;

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

        tvMatchInfoFirstLine = (TextView) findViewById(R.id.tvMatchInfoFirstLine);
        tvMatchInfoSecondLine = (TextView) findViewById(R.id.tvMatchInfoSecondLine);

        progressBar = (ProgressBar) findViewById(R.id.pbProgressBar);

        ovaAktivnost = this;
        sastavKreiran = false;

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
            finish();
            startActivity(intent);
        }
        progressBar.setVisibility(View.VISIBLE);
        fetchBaseData();
        while (threadOne.isAlive()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        progressBar.setVisibility(View.VISIBLE);
        fetchMatchData();

        while (threadTwo.isAlive()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BazaIgraca.getInstance().refreshProtokol();
//        Log.w(TAG, "**" +  BazaIgraca.getInstance().getProtokol());
//        Log.w(TAG, "**" +  BazaIgraca.getInstance().getNaTerenu().size() + "......" + BazaIgraca.getInstance().getNaKlupi().size());

    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }


    private void fetchBaseData() {
        threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpCatcher httpCatcher = null;
                try {
//                    if (!BazaTimova.getInstance().isLoaded()) {
//                        BazaTimova.getInstance().getProtivnici().clear();
//                        httpCatcher = new HttpCatcher(RequestPreparator.GETLIGA, AllStatic.HTTPHOST, null);
//                        httpCatcher.catchData();
//                    }
                    if (!BazaStadiona.getInstance().isLoaded()) {
                        BazaStadiona.getInstance().getTereni().clear();
                        httpCatcher = new HttpCatcher(RequestPreparator.GETSTADION, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                    }
                    if (!BazaPozicija.getInstance().isLoaded()) {
                        BazaPozicija.getInstance().getTimposition().clear();
                        httpCatcher = new HttpCatcher(RequestPreparator.GETPOZICIJA, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                    }
                    if (!BazaIgraca.getInstance().isLoaded()) {
                        BazaIgraca.getInstance().getSquad().clear();
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
        threadOne.start();
    }

    private void fetchMatchData() {
        threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpCatcher httpCatcher = null;
                try {
                    httpCatcher = new HttpCatcher(RequestPreparator.GETLIVEMATCH, AllStatic.HTTPHOST, null);
                    httpCatcher.catchData();
                    try {
                        httpCatcher = new HttpCatcher(RequestPreparator.GETSASTAV, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                        if (BazaIgraca.getInstance().getuProtokolu().size() < 8)
                            sastavKreiran = false;
                        else
                            sastavKreiran = true;
                    } catch (FileNotFoundException fne) {
                        BazaIgraca.getInstance().refreshBrojeviNaDresu();
                    }
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
                                          displayMatchInfo();
                                          BazaIgraca.getInstance().refreshProtokol();
                                      }
                                  }
                    );
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
        threadTwo.start();
    }

    public void displayMatchInfo() {
        Utakmica utakmica = Utakmica.getInstance();
        tvMatchInfoFirstLine.setText(utakmica.getDatum().toString());
        tvMatchInfoSecondLine.setText(utakmica.getHomeTeamName() + "  -  " + utakmica.getAwayTeamName());
        tvMatchInfoFirstLine.setVisibility(View.VISIBLE);
        tvMatchInfoSecondLine.setVisibility(View.VISIBLE);
    }


    private boolean eveythingOK() {
        Utakmica utakmica = Utakmica.getInstance();
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
        if (sastavKreiran) {
            btnEnterEvent.setEnabled(true);
            btnDeleteEvent.setEnabled(true);
        }
    }
}
