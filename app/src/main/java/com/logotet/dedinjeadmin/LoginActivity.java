package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logotet.dedinjeadmin.model.AppHeaderData;
import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Button btnLogin;
    EditText etPassword;
    Intent firstActivity;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppHeaderData.getInstance();

        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        firstActivity = new Intent(this, AfterLoginActivity.class);
        btnLogin.setEnabled(false);

//        firstActivity = new Intent(this, EventsActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppHeaderData.getInstance().getPassword().equals(etPassword.getText().toString())) {
                    AllStatic.loggedUser = true;
                    startActivity(firstActivity);
                    finish();
                } else {
                    AllStatic.loggedUser = false;
                    if (AppHeaderData.getInstance().getPassword().length() > 0)
                        Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.wrong_password),Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                }
            }
        });

        handler = new Handler();/* {
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 1)
                    btnLogin.setEnabled(true);
                if(msg.arg1 == -1)
                    Toast.makeText(getApplicationContext(),"URL connection error", Toast.LENGTH_LONG).show();
            }
        };*/
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(!Checker.isInternetAvailable(getApplicationContext())){
            btnLogin.setEnabled(false);
            Toast.makeText(getApplicationContext(),
                    getApplicationContext().getString(R.string.network_error),Toast.LENGTH_LONG).show();

        }else{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    HttpCatcher httpCatcher = null;
                    try {
                        httpCatcher = new HttpCatcher(RequestPreparator.GETLIGA, AllStatic.HTTPHOST, null);
                        httpCatcher.catchData();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                btnLogin.setEnabled(true);
                            }
                        });
                    } catch (IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"URL connection error", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
            });
            thread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AllStatic.lastActiveTime = System.currentTimeMillis();
    }
}
