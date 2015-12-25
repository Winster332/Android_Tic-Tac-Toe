package com.example.stas.tic_tac_toe.Neural_Network;

import java.io.Serializable;

/**
 * Created by Stas on 20.12.2015.
 */
public class Neuron implements Serializable {
    public float[] w;
    public int index;
    public float power;

    public Neuron() {
        w = new float[9];
    }
}
