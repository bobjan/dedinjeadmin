package com.logotet.dedinjeadmin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.logotet.dedinjeadmin.adapters.DresIgracAdapter;
import com.logotet.dedinjeadmin.adapters.SimpleIgracAdapter;

public class SastavActivity extends AppCompatActivity {
    Button btnVanProtokola;
    Button btnUProtokolu;

    ListView lvIgraci;
    ListView lvUProtokolu;

    LinearLayout llSviIgraci;
    LinearLayout llUProtokolu;


    private int clrSelected;
    private int clrDeselected;
    private Context context;

    SimpleIgracAdapter fullAdapter;
    DresIgracAdapter protokolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sastav);
        context = getApplicationContext();

        clrSelected = getResources().getColor(R.color.white);
        clrDeselected = getResources().getColor(R.color.grey);

        llSviIgraci = (LinearLayout) findViewById(R.id.llSviIgraci);
        llUProtokolu = (LinearLayout) findViewById(R.id.llProtokol);


        btnVanProtokola = (Button) findViewById(R.id.btnVanProtokola);
        btnUProtokolu = (Button) findViewById(R.id.btnUProtokolu);

        lvIgraci = (ListView) findViewById(R.id.lvIgraci);
        lvUProtokolu = (ListView) findViewById(R.id.lvUProtokolu);


        llSviIgraci.setVisibility(View.VISIBLE);
        llUProtokolu.setVisibility(View.GONE);
        btnVanProtokola.setBackgroundColor(clrDeselected);
        btnUProtokolu.setBackgroundColor(clrSelected);

        fullAdapter = new SimpleIgracAdapter(this);
        protokolAdapter = new DresIgracAdapter(this);

        lvIgraci.setAdapter(fullAdapter);
        lvUProtokolu.setAdapter(protokolAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sastav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
