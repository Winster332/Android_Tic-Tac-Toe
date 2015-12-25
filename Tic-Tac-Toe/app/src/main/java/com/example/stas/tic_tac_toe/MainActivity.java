package com.example.stas.tic_tac_toe;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void listnerButtonClickMenu(View view) {
        String tag = view.getTag().toString();

        switch (tag)
        {
            case "play": Play(); break;
            case "auto": Auto(); break;
            case "info": Info(); break;
            case "exit": Exit(); break;
        }
    }

    private void Play() {
        Intent intend = new Intent(this, Game.class);
        startActivity(intend);
    }
    private void Auto() {
        Intent intend = new Intent(this, game_auto.class);
        startActivity(intend);
    }
    private void Info() {
        Intent intend = new Intent(this, Information.class);
        startActivity(intend);
    }
    private void Exit() {
        System.exit(0);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
