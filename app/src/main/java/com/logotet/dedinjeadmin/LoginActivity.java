package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logotet.dedinjeadmin.model.AppHeaderData;
import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Button btnLogin;
    EditText etPassword;
    Intent firstActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Thread thread = new RequestThread(RequestPreparator.GETLIGA, AllStatic.HTTPHOST);
        thread.start();


        thread = new RequestThread(RequestPreparator.GETSTADION, AllStatic.HTTPHOST);
        thread.start();
        thread = new RequestThread(RequestPreparator.GETPOZICIJA, AllStatic.HTTPHOST);
        thread.start();
        thread = new RequestThread(RequestPreparator.GETEKIPA, AllStatic.HTTPHOST);
        thread.start();

        thread = new RequestThread(RequestPreparator.GETLIVEMATCH, AllStatic.HTTPHOST);
        thread.start();

        thread = new RequestThread(RequestPreparator.ALLEVENTS, AllStatic.HTTPHOST);
        thread.start();



        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        firstActivity = new Intent(this, AfterLoginActivity.class);

        if(AllStatic.loggedUser)
            startActivity(firstActivity);


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
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getString(R.string.wrong_password),
                                Toast.LENGTH_LONG).show();
                    else Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.network_error),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
//       firstActivity = new Intent(this, StartMatchActivity.class);
//
//        if(AllStatic.loggedUser)
//            startActivity(firstActivity);

    }
}
