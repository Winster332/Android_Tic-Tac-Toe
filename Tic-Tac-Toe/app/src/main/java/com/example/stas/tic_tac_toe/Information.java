package com.example.stas.tic_tac_toe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.stas.tic_tac_toe.GameAPI.ManagerInformation;
import com.example.stas.tic_tac_toe.Neural_Network.Net;

import java.io.File;

public class Information extends AppCompatActivity {
    public Net net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        net = new Net();
        ManagerInformation.Load(net, getCacheDir());
        ViewResultNN();
    }

    public void listnerButtonClickInformation(View view) {
        String tag = view.getTag().toString();

        switch (tag) {
            case "dead_barin": DeadBrain(); ViewResultNN(); break;
            case "Go_LMD": GoLMD(); break;
            case "back": GoToMenu(); break;
        }
    }

    public void DeadBrain() {
        net.Clear();
        ManagerInformation.Save(net, getCacheDir());
    }

    public void ViewResultNN() {
        ((TextView)findViewById(R.id.count_neurons)).setText("Количество нейронов: " + net.getCount());
        ((TextView)findViewById(R.id.count_D)).setText("Количество весов: " + (net.getCount() * 9));
    }

    public void GoLMD() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/limide"));
        startActivity(intent);
    }

    public void GoToMenu() {
        super.onBackPressed();
    //    Intent intend = new Intent(this, MainActivity.class);
    //    startActivity(intend);
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onDestroy();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
