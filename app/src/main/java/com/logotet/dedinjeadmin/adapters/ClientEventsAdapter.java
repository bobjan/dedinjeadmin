package com.logotet.dedinjeadmin.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.logotet.dedinjeadmin.R;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Utakmica;

import java.util.ArrayList;

/**
 * Created by boban on 8/30/15.
 */
public class ClientEventsAdapter extends BaseAdapter {
    private static final String TAG = "ClientEventsAdapter";


    Activity activity;

    private LayoutInflater inflater;

    private boolean userTeamHome;


    Drawable football;
    Drawable swap;
    Drawable yellowCard;
    Drawable redCard;
    Drawable secondYellow;
    Drawable missedPenalty;



    ArrayList<Dogadjaj> arrayList;

    public ClientEventsAdapter(Activity activity) {
        this.activity = activity;
        arrayList = Utakmica.getInstance().getTokZaPrikaz();
        userTeamHome = Utakmica.getInstance().isUserTeamDomacin();

        football = activity.getResources().getDrawable(R.drawable.football30x30);
        swap = activity.getResources().getDrawable(R.drawable.swap30x30);
        yellowCard = activity.getResources().getDrawable(R.drawable.yellow30x30);
        secondYellow = activity.getResources().getDrawable(R.drawable.secondyellow30x30);
        redCard = activity.getResources().getDrawable(R.drawable.red30x30);
        missedPenalty = activity.getResources().getDrawable(R.drawable.football30x30);

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
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        int resourceType = 0;
        Dogadjaj dogadjaj = (Dogadjaj) getItem(position);

        if (userTeamHome)
            if (dogadjaj.isForDedinje() || dogadjaj.isForProtivnik())
                resourceType = 1;
        if (!userTeamHome)
            if (dogadjaj.isForDedinje() || dogadjaj.isForProtivnik())
                resourceType = 2;
        if (dogadjaj.isIzmena())
            resourceType = 3;
        if (dogadjaj.isKomentar())
            resourceType = 4;
        return resourceType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Dogadjaj dogadjaj = (Dogadjaj) getItem(position);

        if (convertView == null){
            switch (getItemViewType(position)){
                case 1:
                    convertView = inflater.inflate(R.layout.homeevent_row, null);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.awayevent_row, null);
                    break;
                case 3:
                    convertView = inflater.inflate(R.layout.swapevent_row, null);
                    break;
                case 4:
                    convertView = inflater.inflate(R.layout.komentar_row, null);
                    break;
                default:
                    convertView = inflater.inflate(R.layout.komentar_row, null);
                    break;

            }
        }
        TextView tvMinut;
        TextView tvPlayer;
        TextView tvPlayerIn;
        TextView tvPlayerOut;
        TextView tvScore;
        TextView tvKomentar;
        ImageView ivFirstIcon;
        ImageView ivSecondIcon;


        switch (getItemViewType(position)){
            case 1:
                tvMinut = (TextView) convertView.findViewById(R.id.tvhEventMinut);
                tvPlayer = (TextView) convertView.findViewById(R.id.tvhPlayer);
                tvScore = (TextView) convertView.findViewById(R.id.tvhScore);
                ivFirstIcon = (ImageView) convertView.findViewById(R.id.ivhFirstIcon);
                ivSecondIcon = (ImageView) convertView.findViewById(R.id.ivhSecondIcon);

                tvMinut.setText(dogadjaj.getMinutIgre() + "'");
                if(dogadjaj.isIgracki())
                    tvPlayer.setText(dogadjaj.getPlayerName());
                if(dogadjaj.isForDedinje()) {
                    if (dogadjaj.isGoal())
                        ivFirstIcon.setImageDrawable(football);
                    if (dogadjaj.isZutiKarton())
                        ivFirstIcon.setImageDrawable(yellowCard);
                    if (dogadjaj.isDrugiZuti())
                        ivFirstIcon.setImageDrawable(secondYellow);
                    if (dogadjaj.isCrveniKarton())
                        ivFirstIcon.setImageDrawable(redCard);
                    if (dogadjaj.isMissedPenalty())
                        ivFirstIcon.setImageDrawable(missedPenalty);
                }else{
                    if (dogadjaj.isGoal())
                        ivSecondIcon.setImageDrawable(football);
                    if (dogadjaj.isZutiKarton())
                        ivSecondIcon.setImageDrawable(yellowCard);
                    if (dogadjaj.isDrugiZuti())
                        ivSecondIcon.setImageDrawable(secondYellow);
                    if (dogadjaj.isCrveniKarton())
                        ivSecondIcon.setImageDrawable(redCard);
                    if (dogadjaj.isMissedPenalty())
                        ivSecondIcon.setImageDrawable(missedPenalty);
                }
                if(dogadjaj.isGoal()){
                    int[] rez = dogadjaj.getRezultat();
                    tvScore.setText(rez[0] + " : " + rez[1]);
                }

                break;
            case 2:
                tvMinut = (TextView) convertView.findViewById(R.id.tvaEventMinut);
                tvPlayer = (TextView) convertView.findViewById(R.id.tvaPlayer);
                tvScore = (TextView) convertView.findViewById(R.id.tvaScore);
                ivFirstIcon = (ImageView) convertView.findViewById(R.id.ivaFirstIcon);
                ivSecondIcon = (ImageView) convertView.findViewById(R.id.ivaSecondIcon);
                tvMinut.setText(dogadjaj.getMinutIgre() + "'");

                if(dogadjaj.isIgracki())
                    tvPlayer.setText(dogadjaj.getPlayerName());
                if(dogadjaj.isForDedinje()) {
                    if (dogadjaj.isGoal())
                        ivFirstIcon.setImageDrawable(football);
                    if (dogadjaj.isZutiKarton())
                        ivFirstIcon.setImageDrawable(yellowCard);
                    if (dogadjaj.isDrugiZuti())
                        ivFirstIcon.setImageDrawable(secondYellow);
                    if (dogadjaj.isCrveniKarton())
                        ivFirstIcon.setImageDrawable(redCard);
                    if (dogadjaj.isMissedPenalty())
                        ivFirstIcon.setImageDrawable(missedPenalty);
                }else{
                    if (dogadjaj.isGoal())
                        ivSecondIcon.setImageDrawable(football);
                    if (dogadjaj.isZutiKarton())
                        ivSecondIcon.setImageDrawable(yellowCard);
                    if (dogadjaj.isDrugiZuti())
                        ivSecondIcon.setImageDrawable(secondYellow);
                    if (dogadjaj.isCrveniKarton())
                        ivSecondIcon.setImageDrawable(redCard);
                    if (dogadjaj.isMissedPenalty())
                        ivSecondIcon.setImageDrawable(missedPenalty);
                }
                if(dogadjaj.isGoal()){
                    int[] rez = dogadjaj.getRezultat();
                    tvScore.setText(rez[1] + " : " + rez[0]);
                }
                break;
            case 3:
                tvMinut = (TextView) convertView.findViewById(R.id.tvEventMinutSwap);
                ivFirstIcon = (ImageView) convertView.findViewById(R.id.ivFirstIconSwap);
                tvPlayerIn = (TextView) convertView.findViewById(R.id.tvPlayerIn);
                tvPlayerOut = (TextView) convertView.findViewById(R.id.tvPlayerOut);

                tvMinut.setText(dogadjaj.getMinutIgre() + "'");
                tvPlayerIn.setText(dogadjaj.getPlayerInName());
                tvPlayerOut.setText(dogadjaj.getPlayerOutName());

                break;
            case 4:
                tvMinut = (TextView) convertView.findViewById(R.id.tvKomentarMinut);
                tvKomentar = (TextView) convertView.findViewById(R.id.tvKomentar);
                tvMinut.setText(dogadjaj.getMinutIgre() + "'");
                tvKomentar.setText(dogadjaj.getKomentar());
                break;
            default:
                tvMinut = (TextView) convertView.findViewById(R.id.tvKomentarMinut);
                tvKomentar = (TextView) convertView.findViewById(R.id.tvKomentar);
                tvMinut.setText(" *");
                tvKomentar.setText("      ****   ");
                break;

        }
        return convertView;
    }
}
