package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.logotet.dedinjeadmin.adapters.EventsAdapter;

public class DeleteEventsActivity extends AppCompatActivity {
    ListView lwDeleteEvents;
    private EventsAdapter eventsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_events);

        if(!AllStatic.loggedUser){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


        lwDeleteEvents = (ListView) findViewById(R.id.lwDeleteEvents);
        eventsAdapter = new EventsAdapter(this);
    }

}
