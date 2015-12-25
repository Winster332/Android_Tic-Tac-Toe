package com.example.stas.tic_tac_toe.GameAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stas on 20.12.2015.
 */
public class ManagerHistory {
    private ArrayList<History> history;

    public ManagerHistory()
    {
        history = new ArrayList<History>();
    }

    public void add(int[] table, int index, float[] x) {
        History h = new History();
        h.index = index;
        h.table = table;
        h.x = x;

        this.history.add(h);
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public void Clear() {
        history.clear();
    }
}
