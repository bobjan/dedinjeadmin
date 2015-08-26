package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.logotet.dedinjeadmin.model.AllStatic;
import com.logotet.util.BJDatum;
import com.logotet.util.BJTime;
import com.logotet.util.MainConverter;

public class StartMatchActivity extends AppCompatActivity {
    TimePicker tpStartTime;
    BJTime vreme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_match);

        tpStartTime = (TimePicker) findViewById(R.id.tpStartTime);
        tpStartTime.setIs24HourView(true);
        vreme = new BJTime();
        tpStartTime.setCurrentHour(vreme.getHour());
        tpStartTime.setCurrentMinute(vreme.getMinute());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_match, menu);
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
