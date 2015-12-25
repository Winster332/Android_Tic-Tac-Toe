package com.example.stas.tic_tac_toe.GameAPI;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.example.stas.tic_tac_toe.Neural_Network.Net;
import com.example.stas.tic_tac_toe.Neural_Network.Neuron;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.xml.transform.Result;

/**
 * Created by Stas on 21.12.2015.
 */
public class ManagerInformation {
    public static String Save(Net net, File dir) {
        try {
            File file = dir;
            file = new File(dir.getAbsolutePath(), "Brains");
            file.mkdirs();

            file = new File(file.getAbsolutePath(), "listBrains.xml");
            Boolean result = file.createNewFile();
            //   for (int i = 0; i < net.getCount(); i++) {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);



            Log.d("MyLog", "Result create neurons file: " + result);

            oos.writeObject(net.getAllNeurons());
            oos.flush();
            oos.close();
            //   }

            Log.d("MyLog", "Result exist " + file.exists());

            return "Сохранено";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    public static ArrayList<Neuron> Load(Net net, File dir) {
        try {
            File file = dir;
            file = new File(dir.getAbsolutePath(), "Brains");
            file.mkdirs();

            file = new File(file.getAbsolutePath(), "listBrains.xml");
            //   for (int i = 0; i < net.getCount(); i++) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);



       //     Log.d("MyLog", "Result create neurons file: " + result);

            ArrayList<Neuron> n = (ArrayList<Neuron>)ois.readObject();
            net.setNeurons(n);
            ois.close();
            //   }

            Log.d("MyLog", "n.size: " + n.size());

            return net.getAllNeurons();
        }catch (Exception ex) {
            return net.getAllNeurons();
        }
    }

    public static String createDir(File dir) {
        String result = "Yes";

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return "SD - con connect";
        }

        Log.d("Log", dir.getAbsolutePath());
        dir = new File(dir, "folder");

        Log.d("Log", dir.getAbsolutePath());

        Boolean r = dir.mkdirs();

        Log.d("Log", "Result create dir: " + r);
        Log.d("Log", "Result exist dir: " + dir.exists());

        return result;
    }
}
