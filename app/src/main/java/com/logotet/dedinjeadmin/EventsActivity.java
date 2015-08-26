package com.logotet.dedinjeadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class EventsActivity extends AppCompatActivity {
    private static final String TAG = "EventsActivity";

    RadioGroup rgTimeEvents;
    RadioGroup rgGol;
    RadioGroup rgKartoni;
    RadioGroup rgWhatTeam;

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        rgGol = (RadioGroup) findViewById(R.id.rgGol);
        rgKartoni = (RadioGroup) findViewById(R.id.rgKartoni);
        rgWhatTeam = (RadioGroup) findViewById(R.id.rgWhatTeam);
        rgTimeEvents = (RadioGroup) findViewById(R.id.rgTimeEvents);
        spinner = (Spinner) findViewById(R.id.spinStrelac);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
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
