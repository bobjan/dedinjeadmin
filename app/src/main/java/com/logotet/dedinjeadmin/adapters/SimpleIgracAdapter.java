package com.logotet.dedinjeadmin.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.logotet.dedinjeadmin.R;
import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Igrac;

import java.util.ArrayList;

/**
 * Created by boban on 8/30/15.
 */
public class SimpleIgracAdapter extends BaseAdapter {
    Activity activity;

    private LayoutInflater inflater;

    ArrayList arrayList;

    public SimpleIgracAdapter(Activity activity) {
        this.activity = activity;
        arrayList = BazaIgraca.getInstance().getVanProtokola();
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
            convertView = inflater.inflate(R.layout.igrac_row, null);

        TextView tvIgrac = (TextView) convertView.findViewById(R.id.tvIgrac);

        int clr = parent.getResources().getColor(R.color.complementlght);


        Igrac igrac = (Igrac) getItem(position);
        tvIgrac.setTextColor(clr);

        tvIgrac.setBackgroundResource(R.drawable.veznibutton);



        tvIgrac.setBackgroundResource(R.drawable.veznibutton);
/*
        switch(igrac.getDefaultPozicija()){
            case 1:
                tvIgrac.setBackgroundResource(R.drawable.golmanbutton);
                break;
            case 2:
                tvIgrac.setBackgroundResource(R.drawable.odbranabutton);
                break;
            case 3:
                tvIgrac.setBackgroundResource(R.drawable.veznibutton);
                break;
            default:
                tvIgrac.setBackgroundResource(R.drawable.napadbutton);
                break;

        }

*/
        tvIgrac.setText(igrac.getNaziv());

        return convertView;
    }
}
