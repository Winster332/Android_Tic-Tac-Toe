package com.example.stas.tic_tac_toe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stas.tic_tac_toe.GameAPI.History;
import com.example.stas.tic_tac_toe.GameAPI.ManagerHistory;
import com.example.stas.tic_tac_toe.GameAPI.ManagerInformation;
import com.example.stas.tic_tac_toe.Neural_Network.Net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class game_auto extends AppCompatActivity {
    public int count_win_x = 0;
    public int count_win_o = 0;
    public int count_n = 0;
    public final String x = "x";
    public final String o = "o";
    public Game.STATE_STEP present_step;
    public Net net;
    public int count_step = 0;
    public ManagerHistory mHistory;
    public Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_auto);


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        present_step = Game.STATE_STEP.x;
        mHistory = new ManagerHistory();
        //    this.textTitle = (TextView)findViewById(R.id.gameTitle);

        Button[] but = getButtonMas();

        for (int i = 0; i < but.length; i++) {
            but[i].setTextColor(Color.argb(255, 192, 192, 192));
            but[i].setTextSize(30);
        }

        net = new Net();
        if (net.getCount() <= 0)
            net.AddNeuron(5, getVectorX());



     //   MyTimerTask myTimerTask = new MyTimerTask(this);

     //   timer = new Timer();
     //   timer.schedule(myTimerTask, 1000, 5000);
    }

    public void buttonClickGameTopPanelListner(View view) {
        String tag = view.getTag().toString();

        switch (tag) {
            case "back": BackToMenu(); break;
            case "stop": StopAndStartTimer(); break;
            case "save": String result = ManagerInformation.Save(net, getCacheDir());
                ((TextView)findViewById(R.id.textTitle)).setText(result + " " + net.getCount() + " нейрона");
                break;
            case "load": ManagerInformation.Load(net, getCacheDir());
                ((TextView)findViewById(R.id.textTitle)).setText("Загружено " + net.getCount() + " нейрона"); break;
        }
    }

    private void BackToMenu() {
        super.onBackPressed();
    //    Intent intend = new Intent(this, MainActivity.class);
     //   startActivity(intend);
    }

    public void StopAndStartTimer(){
        Button but = (Button)findViewById(R.id.startAndStopAutoGame);

        if (timer != null) {
            but.setText("Старт");
            timer.cancel();
            timer = null;
        } else {
            MyTimerTask myTimerTask = new MyTimerTask(this);
            but.setText("Стоп");
            timer = new Timer();
            timer.schedule(myTimerTask, 100, 5);
        }
    }

    public void setX(Game.POSITION pos) {
        switch (pos)
        {
            case A1: ((Button)findViewById(R.id.A1)).setText(x); break;
            case A2: ((Button)findViewById(R.id.A2)).setText(x); break;
            case A3: ((Button)findViewById(R.id.A3)).setText(x); break;
            case B1: ((Button)findViewById(R.id.B1)).setText(x); break;
            case B2: ((Button)findViewById(R.id.B2)).setText(x); break;
            case B3: ((Button)findViewById(R.id.B3)).setText(x); break;
            case C1: ((Button)findViewById(R.id.C1)).setText(x); break;
            case C2: ((Button)findViewById(R.id.C2)).setText(x); break;
            case C3: ((Button)findViewById(R.id.C3)).setText(x); break;
        }
    }
    public void setO(Game.POSITION pos) {
        switch (pos)
        {
            case A1: ((Button)findViewById(R.id.A1)).setText(o); break;
            case A2: ((Button)findViewById(R.id.A2)).setText(o); break;
            case A3: ((Button)findViewById(R.id.A3)).setText(o); break;
            case B1: ((Button)findViewById(R.id.B1)).setText(o); break;
            case B2: ((Button)findViewById(R.id.B2)).setText(o); break;
            case B3: ((Button)findViewById(R.id.B3)).setText(o); break;
            case C1: ((Button)findViewById(R.id.C1)).setText(o); break;
            case C2: ((Button)findViewById(R.id.C2)).setText(o); break;
            case C3: ((Button)findViewById(R.id.C3)).setText(o); break;
        }
    }

    public void setO(int index) {
        Button[] but = getButtonMas();
        but[index].setText(o);
    }
    public void setX(int index) {
        Button[] but = getButtonMas();
        but[index].setText(x);
    }

    public float[] getVectorX() {
        Button[] but = getButtonMas();
        float[] x = new float[9];

        float _x = 0.1f;
        float _o = -0.9f;
        float _n = -0.5f;

        for (int i = 0; i < but.length; i++) {
            if (but[i].getText() == "x")
                x[i] = _x;
            else if (but[i].getText() == "o")
                x[i] = _o;
            else x[i] = _n;
        }

        return x;
    }

    public Button[] getButtonMas() {
        Button[] but = new Button[9];

        but[0] = (Button)findViewById(R.id.A1);
        but[1] = (Button)findViewById(R.id.A2);
        but[2] = (Button)findViewById(R.id.A3);
        but[3] = (Button)findViewById(R.id.B1);
        but[4] = (Button)findViewById(R.id.B2);
        but[5] = (Button)findViewById(R.id.B3);
        but[6] = (Button)findViewById(R.id.C1);
        but[7] = (Button)findViewById(R.id.C2);
        but[8] = (Button)findViewById(R.id.C3);

        return  but;
    }

    public int[] getTableInt() {
        Button[] but = getButtonMas();
        int[] table = new int[9];

        for (int i = 0; i < but.length; i++) {
            if (but[i].getText() == x)
                table[i] = 1;
            else if (but[i].getText() == o)
                table[i] = 2;
            else table[i] = 0;
        }

        return table;
    }

    public void RestartGame() {
        Button[] but = getButtonMas();
        count_step = 0;
        mHistory.Clear();
        for (int i = 0; i < but.length; i++)
            but[i].setText("");
    }

    private Boolean CheckWinX() {
        Boolean result = false;
        Button[] but = getButtonMas();
        String s = x;

        if (but[0].getText() == s && but[1].getText() == s && but[2].getText() == s)
            result = true;
        else if (but[3].getText() == s && but[4].getText() == s && but[5].getText() == s)
            result  = true;
        else if (but[6].getText() == s && but[7].getText() == s && but[8].getText() == s)
            result  = true;
        else if (but[0].getText() == s && but[3].getText() == s && but[6].getText() == s)
            result  = true;
        else if (but[1].getText() == s && but[4].getText() == s && but[7].getText() == s)
            result  = true;
        else if (but[2].getText() == s && but[5].getText() == s && but[8].getText() == s)
            result  = true;
        else if (but[0].getText() == s && but[4].getText() == s && but[8].getText() == s)
            result  = true;
        else if (but[2].getText() == s && but[4].getText() == s && but[6].getText() == s)
            result  = true;


        return result;
    }

    private Boolean CheckWinO() {
        Boolean result = false;
        Button[] but = getButtonMas();
        String s = o;

        if (but[0].getText() == s && but[1].getText() == s && but[2].getText() == s)
            result = true;
        else if (but[3].getText() == s && but[4].getText() == s && but[5].getText() == s)
            result  = true;
        else if (but[6].getText() == s && but[7].getText() == s && but[8].getText() == s)
            result  = true;
        else if (but[0].getText() == s && but[3].getText() == s && but[6].getText() == s)
            result  = true;
        else if (but[1].getText() == s && but[4].getText() == s && but[7].getText() == s)
            result  = true;
        else if (but[2].getText() == s && but[5].getText() == s && but[8].getText() == s)
            result  = true;
        else if (but[0].getText() == s && but[4].getText() == s && but[8].getText() == s)
            result  = true;
        else if (but[2].getText() == s && but[4].getText() == s && but[6].getText() == s)
            result  = true;


        return result;
    }

    public void WinXRemember() {
        ArrayList<History> history = mHistory.getHistory();
        int num = 0;
        int d = 1;

        int index = history.get(history.size() - 1).index;
        float[] x = history.get(history.size() - 3).x;

        net.AddNeuron(index, x);
    }

    public String getCell(int index) {
        Button[] but = getButtonMas();
        return but[index - 1].getText().toString();
    }

    public Boolean CheckCell(int index) {
        Boolean result = false;

        if (getCell(index) == "x")
            result = false;
        else if (getCell(index) == "o")
            result = false;
        else result = true;

        return result;
    }

    public int RandomStep(Game.STATE_STEP step) {
        Random ran = new Random();
        int index = 0;
        Boolean rect = false;

        do {
            index = ran.nextInt(10 - 1) + 1;

            rect = CheckCell(index);
        }while(!rect);

        if (step == Game.STATE_STEP.x)
            setX(index-1);
        else if (step == Game.STATE_STEP.o)
            setO(index-1);

        return index;
    }

    public Boolean CheckNoWin() {
        Button[] but = getButtonMas();
        Boolean result = false;
        int n = 0;

        for (int i = 0; i < but.length; i++)
            if (but[i].getText() == "")
                n++;

        if (n >= 9)
            result = true;

        return result;
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
        getMenuInflater().inflate(R.menu.menu_game_auto, menu);
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

    class MyTimerTask extends TimerTask {
        private game_auto game;
        private TextView textTitle;

        public MyTimerTask(game_auto gameAuto) {
            this.game = gameAuto;
            this.textTitle = (TextView)findViewById(R.id.textTitle);
        }

        @Override
        public void run() {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    game.count_step++;
                    int index = 0;

                    if (game.count_step > 9)
                    {
                        game.count_step = 0;
                        game.RestartGame();
                        game.present_step = Game.STATE_STEP.x;
                        game.mHistory.Clear();
                        game.count_n++;
                    } else {
                        index = game.RandomStep(Game.STATE_STEP.x);

                        game.mHistory.add(game.getTableInt(), index, game.getVectorX());

                        if (game.CheckWinX()) {
                            game.WinXRemember();
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.mHistory.Clear();
                            game.count_win_x++;
                        } else if (game.CheckWinO()) {
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.mHistory.Clear();
                            game.count_win_o++;
                        }
                    }

                    game.count_step++;

                    if (game.count_step > 9)
                    {
                        game.count_step = 0;
                        game.RestartGame();
                        game.present_step = Game.STATE_STEP.x;
                        game.mHistory.Clear();
                        game.count_n++;
                    } else {
                        index = game.net.Recognize(getVectorX());

                        if (game.CheckCell(index))
                            game.setO(index - 1);
                        else index = game.RandomStep(Game.STATE_STEP.o);

                        game.mHistory.add(game.getTableInt(), index, game.getVectorX());

                        if (game.CheckWinX()) {
                            game.WinXRemember();
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.mHistory.Clear();
                            game.count_win_x++;
                        } else if (game.CheckWinO()) {
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.mHistory.Clear();
                            game.count_win_o++;
                        }
                    }
                    textTitle.setText("X[" + game.count_win_x + "]" + " O[" + game.count_win_o + "]" +
                            " N[" + game.count_n + "]" + " Neurons[" + game.net.getCount() + "]");
                }
            });
        }
    }
}

/*
    game.count_step++;

                    if (game.count_step > 9)
                    {
                        game.count_step = 0;
                        game.RestartGame();
                        game.present_step = Game.STATE_STEP.x;
                        game.mHistory.Clear();
                    } else {
                        if (game.CheckWinX())
                        {
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.WinXRemember();
                            game.mHistory.Clear();
                        } else if (game.CheckWinO())
                        {
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.mHistory.Clear();
                        } else {
                            int index = game.RandomStep(Game.STATE_STEP.x);
                            game.mHistory.add(game.getTableInt(), index, game.getVectorX());
                        }
                    }
                    game.count_step++;
                    if (game.count_step > 9)
                    {
                        game.count_step = 0;
                        game.RestartGame();
                        game.present_step = Game.STATE_STEP.x;
                        game.mHistory.Clear();
                    } else {
                        if (game.CheckWinX())
                        {
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.WinXRemember();
                            game.mHistory.Clear();
                        } else if (game.CheckWinO())
                        {
                            game.count_step = 0;
                            game.RestartGame();
                            game.present_step = Game.STATE_STEP.x;
                            game.mHistory.Clear();
                        } else {
                            int index = game.RandomStep(Game.STATE_STEP.o);
                            game.mHistory.add(game.getTableInt(), index, game.getVectorX());
                        }
                    }
*/