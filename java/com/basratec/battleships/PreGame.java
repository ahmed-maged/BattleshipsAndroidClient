package com.basratec.battleships;

import java.util.ArrayList;
import java.util.Vector;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreGame extends Activity {
    private ArrayList<String> ARRV = new ArrayList<String>(25);
    private int[] gridMap = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private final int NUMBER_OF_SHIPS=6;
    private TextView timer;
    private Vector<Boolean> shipsStatus;
    private LinearLayout shipContainer;
    private void initilizeShips(){
        shipsStatus = new Vector< Boolean>(NUMBER_OF_SHIPS);
        for(int i =0;i<NUMBER_OF_SHIPS;++i){
            ImageButton ship = new ImageButton(getApplicationContext());
            shipContainer.addView(ship);
            ship =(ImageButton) shipContainer.getChildAt(i);
            ship.setLayoutParams(new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.ship),(int)getResources().getDimension(R.dimen.ship)));
            ((LinearLayout.LayoutParams)ship.getLayoutParams()).setMargins(10,10,10,10);
            ship.setBackgroundResource(R.drawable.ship);

            shipsStatus.add(new Boolean("true"));
        }
        shipContainer.invalidate();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);
        timer = (TextView)findViewById(R.id.timer);
        shipContainer = (LinearLayout) findViewById(R.id.shipContainer);
        //shipContainer.invalidate();
        initilizeShips();

        Thread timerThread = new Thread(new Runnable(){

            @Override
            public void run() {
                int counter=15;
                while(counter>0){
                    counter--;
                    System.out.println("test");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText("00:" + (Integer.parseInt(String.valueOf(timer.getText()).split(":")[1])-1)); //counter is reduced by one
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), "Time ran out", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
        timerThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu., menu);
        return true;
    }

    // usr clicked color
    public void placeShip(View view) {

        ImageButton imgView = (ImageButton) view;
        String cell = view.getTag().toString();
        int x = Integer.parseInt(cell);
        boolean listIsEmpty=true;
        if (gridMap[x] == 1){
            //TO Do add code to free a ship
            return;
        }
        for(int i=0;i<shipsStatus.size();++i){
            if(shipsStatus.elementAt(i).booleanValue()){ // if a ship is still not clicked

                gridMap[x] = 1;

                ImageButton ship= (ImageButton)shipContainer.getChildAt(i);
                ship.setAlpha(.5f);
                shipsStatus.set(i,Boolean.FALSE);

                imgView.setImageDrawable(getResources().getDrawable(
                        R.drawable.ship));
                listIsEmpty=false;
                break;

            }
            if(listIsEmpty){
                //toast
            }
        }

    }
}
