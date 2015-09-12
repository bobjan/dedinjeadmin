package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class BazaPozicija {
    private static BazaPozicija bazaPozicija = null;
    private ArrayList<Pozicija> timposition;
    private boolean loaded;

    public static BazaPozicija getInstance() {
        if (bazaPozicija == null)
            bazaPozicija = new BazaPozicija();
        return bazaPozicija;
    }

    private BazaPozicija() {
        timposition = new ArrayList<Pozicija>();
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void add(Pozicija pos) {
        if (!timposition.contains(pos))
            timposition.add(pos);
    }

    public Pozicija getPozicija(int id) {
        for (int i = 0; i < timposition.size(); i++) {
            Pozicija tmp = timposition.get(id);
            if (tmp.getId() == id)
                return tmp;
        }
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = timposition.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");

        }
        return sb.toString();
    }
}
