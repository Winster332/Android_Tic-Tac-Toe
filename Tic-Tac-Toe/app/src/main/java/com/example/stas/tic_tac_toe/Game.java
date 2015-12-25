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
import android.widget.Toast;

import com.example.stas.tic_tac_toe.GameAPI.History;
import com.example.stas.tic_tac_toe.GameAPI.ManagerHistory;
import com.example.stas.tic_tac_toe.GameAPI.ManagerInformation;
import com.example.stas.tic_tac_toe.Neural_Network.Net;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {
    public TextView textTitle;
    public enum POSITION { A1, A2, A3, B1, B2, B3, C1, C2, C3 };
    public enum STATE_STEP { x, o };
    public final String x = "x";
    public final String o = "o";
    public STATE_STEP present_step;
    public Net net;
    public int count_step = 0;
    public ManagerHistory mHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        present_step = STATE_STEP.x;
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
    }

    public void setX(POSITION pos) {
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
    public void setO(POSITION pos) {
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

    public void listnerButtonClickGame(View view) {
        Button[] but = getButtonMas();
        int index_x = 0;
        for (int i = 0; i < but.length; i++)
            if (but[i] == view)
                index_x = i;

        if (((Button) view).getText() == "") {
           ((Button) view).setText(x);
            mHistory.add(getTableInt(), index_x, getVectorX());
            count_step++;

            if (CheckWinX()) {
                mHistory.add(getTableInt(), index_x, getVectorX());
                WinXRemember();
                mHistory.Clear();
            } else if (CheckWinO()) {
                mHistory.Clear();
            } else if (count_step < 8) {
                count_step++;
                int index = net.Recognize(getVectorX());

                if (CheckCell(index)) {
                    setO(index - 1);
                //    textTitle.setText("net: " + index);
                } else {
                    index = RandomStep(STATE_STEP.o);
                 //   textTitle.setText("ran: " + index);
                }
                mHistory.add(getTableInt(), index, getVectorX());
            }
        }
    }

    public void buttonClickGameTopPanelListner(View view) {
        String tag = view.getTag().toString();

        switch (tag) {
            case "back": BackToMenu(); break;
            case "restart": RestartGame(); break;
            case "save": String result = ManagerInformation.Save(net, getCacheDir());
                ((TextView)findViewById(R.id.textTitle)).setText(result + " " + net.getCount() + " нейрона");
                break;
            case "load": ManagerInformation.Load(net, getCacheDir());
                ((TextView)findViewById(R.id.textTitle)).setText("Загружено " + net.getCount() + " нейрона");
                break;
        }
    }

    private void BackToMenu() {
        super.onBackPressed();
     //   Intent intend = new Intent(this, MainActivity.class);
     //   startActivity(intend);
    }

    private void RestartGame() {
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
        float[] x = history.get(history.size() - 2).x;

        net.AddNeuron(index+1, x);
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

    public int RandomStep(STATE_STEP step) {
        Random ran = new Random();
        int index = 0;
        Boolean rect = false;

        do {
            index = ran.nextInt(10 - 1) + 1;

            rect = CheckCell(index);
        }while(!rect);

        if (step == STATE_STEP.x)
            setX(index-1);
        else if (step == STATE_STEP.o)
            setO(index-1);

        return index;
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
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
