package com.logotet.dedinjeadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                Dogadjaj.currentDogadjaj = (Dogadjaj) parent.getItemAtPosition(position);
                Log.w(TAG, " dogadja = " + Dogadjaj.currentDogadjaj.toString() + "\t" + Dogadjaj.currentDogadjaj.getFileName());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DeleteEventsActivity.this);
                alertDialogBuilder.setTitle("Да ли си сигуран да желиш да обришеш догађај?");
//                alertDialogBuilder.setMessage("Click yes to exit!");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utakmica.getInstance().remove(Dogadjaj.currentDogadjaj);

                        Thread th = new RequestThread(RequestPreparator.DELETEEVENT, AllStatic.HTTPHOST);
                        th.start();
                        Log.w(TAG, " thread startovan, i adapter ce biti obavesten");
//                        DeleteEventsActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                        eventsAdapter.notifyDataSetChanged();
//                            }
//                        });
//                        Dogadjaj.currentDogadjaj= null;
                    }
                });
                alertDialogBuilder.setNegativeButton("Не", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.w(TAG, " Cance dialog");
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

}
