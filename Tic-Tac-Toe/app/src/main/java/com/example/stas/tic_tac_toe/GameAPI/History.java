package com.example.stas.tic_tac_toe.GameAPI;

/**
 * Created by Stas on 20.12.2015.
 */
public class History {
    public int[] table;
    public int index;
    public float[] x;

    public History() {
        x = new float[9];
        table = new int[9];
    }
}
