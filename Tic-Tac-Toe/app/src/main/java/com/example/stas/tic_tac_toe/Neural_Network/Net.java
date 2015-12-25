package com.example.stas.tic_tac_toe.Neural_Network;

import java.util.ArrayList;

/**
 * Created by Stas on 20.12.2015.
 */
public class Net {
    private ArrayList<Neuron> neurons;

    public Net() {
        neurons = new ArrayList<Neuron>();
    }

    public int Recognize(float[] x) {
        int index = 0;

        for (int i = 0; i < neurons.size(); i++) {
            for (int j = 0; j < neurons.get(i).w.length; j++) {
                neurons.get(i).power += neurons.get(i).w[j] * x[j];
            }

            if (neurons.get(i).power > neurons.get(index).power) {
                index = i;
            }
        }

        for (int i = 0; i < neurons.size(); i++)
            neurons.get(i).power = 0;

        return neurons.get(index).index;
    }

    public void Correct(int index, float speed, int delta, float[] x) {
        for (int i = 0; i < neurons.get(index).w.length; i++)
            neurons.get(index).w[i] = neurons.get(index).w[i] + speed * delta * x[i];
    }

    public Neuron AddNeuron(int index, float[] w) {
        Neuron n = new Neuron();
        n.index = index;
        n.w = w;
        neurons.add(n);
        return n;
    }

    public void Clear() {
        neurons.clear();
    }

    public void RemoveAt(int index) {
        neurons.remove(index);
    }

    public ArrayList<Neuron> getAllNeurons() {
        return neurons;
    }

    public Neuron getNeuron(int index) {
        return neurons.get(index);
    }
    public void setNeurons(ArrayList<Neuron> net) {
        neurons = net;
    }

    public int getCount() {
        return neurons.size();
    }
}
