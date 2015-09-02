package com.logotet.dedinjeadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.logotet.dedinjeadmin.adapters.EventsAdapter;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Utakmica;
import com.logotet.dedinjeadmin.threads.RequestThread;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;

public class DeleteEventsActivity extends AppCompatActivity {
    private static final String TAG = "DeleteEventsActivity";
    ListView lvDeleteEvents;
    private EventsAdapter eventsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_events);


        lvDeleteEvents = (ListView) findViewById(R.id.lvDeleteEvents);
        eventsAdapter = new EventsAdapter(this);

        lvDeleteEvents.setAdapter(eventsAdapter);

        lvDeleteEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dogadjaj d = (Dogadjaj) parent.getItemAtPosition(position);
                Dogadjaj.currentDogadjaj = d;
                Thread th = new RequestThread(RequestPreparator.DELETEEVENT, AllStatic.HTTPHOST);
                th.start();
                Utakmica.getInstance().remove(d);
                eventsAdapter.notifyDataSetChanged();
            }
        });


    }

}
