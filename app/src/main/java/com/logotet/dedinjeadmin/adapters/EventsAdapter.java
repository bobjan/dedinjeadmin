package com.logotet.dedinjeadmin.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.logotet.dedinjeadmin.R;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Utakmica;

import java.util.ArrayList;

/**
 * Created by boban on 8/30/15.
 */
public class EventsAdapter extends BaseAdapter {
    private static final String TAG = "EventsAdapter";


    Activity activity;

    private LayoutInflater inflater;

    ArrayList arrayList;
    public EventsAdapter(Activity activity) {
        this.activity = activity;
        arrayList = Utakmica.getInstance().getSvaDogadjanja();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
       return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.dogadjaj_row, null);

        TextView tvServerTime = (TextView) convertView.findViewById(R.id.tvServerTime);
        TextView tvDogadjaj = (TextView) convertView.findViewById(R.id.tvDogadjaj);

        Dogadjaj dogadjaj = (Dogadjaj) getItem(position);

        tvServerTime.setText(dogadjaj.getServerTime().toString());
        tvDogadjaj.setText(dogadjaj.toString());

        return convertView;
    }
}
