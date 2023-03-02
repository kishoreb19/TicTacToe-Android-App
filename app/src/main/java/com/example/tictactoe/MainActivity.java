package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int x_pts=0,o_pts=0;
    int active_player=0;
    int ap=0;
    int player_pos[] = {2,2,2,2,2,2,2,2,2};
    boolean gameActive;
    int win_pos[][] =
            {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
            };
    public void gameReset(){
        gameActive = true;
        TextView status = (TextView) findViewById(R.id.status);
        if(active_player==0){
            status.setText("X's Turn");
        }
        else{
            status.setText("O's Turn");
        }
        for(int i=0;i<player_pos.length;i++){
            player_pos[i] = 2;
        }
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView9)).setImageResource(0);
    }
    public void tapped(View view){
        ImageView cur_img = (ImageView) view;
        TextView status = (TextView) findViewById(R.id.status);
        TextView xpts = (TextView) findViewById(R.id.x_pts);
        TextView opts = (TextView) findViewById(R.id.o_pts);
        MediaPlayer pressed = MediaPlayer.create(this,R.raw.pressed);
        MediaPlayer winning = MediaPlayer.create(this,R.raw.win);
        MediaPlayer draw = MediaPlayer.create(this,R.raw.draw);
        int tag = Integer.parseInt(cur_img.getTag().toString());
        if(player_pos[tag]==2){
            player_pos[tag] = active_player;
            if(active_player==0){
                active_player=1;
                cur_img.setImageResource(R.drawable.x);
                pressed.start();
                status.setText("O's Turn");

            }
            else{
                active_player=0;
                cur_img.setImageResource(R.drawable.o);
                pressed.start();
                status.setText("X's Turn");
            }
        }
        //for winning positions
        String winner;
        for(int [] win : win_pos){
            if(player_pos[win[0]]==player_pos[win[1]] && player_pos[win[1]]== player_pos[win[2]] && player_pos[win[0]] != 2){
                if(player_pos[win[0]]==0){
                    winner = "X has Won !";
                    x_pts++;
                    xpts.setText(Integer.toString(x_pts));
                    pressed.stop();
                    winning.start();
                }
                else{
                    winner = "O has Won !";
                    o_pts++;
                    opts.setText(Integer.toString(o_pts));
                    pressed.stop();
                    winning.start();
                }
                gameActive = false;
                status.setText(winner);
                //callinig gameReset() after 2.6 seconds
                //callinig gameReset() after 2.6 seconds
                if(gameActive == false){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("Please Wait...");
                        }
                    },2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //to alternate players
                            if(ap==0){
                                active_player = 1;
                                ap=1;
                            }
                            else{
                                active_player = 0;
                                ap=0;
                            }
                            gameReset();
                        }
                    },2600);
                }

            }
            else{ //To check for draw
                int flag1=0,flag2=0;
                for(int j=0;j<player_pos.length;j++){
                    if(player_pos[j] != 2){
                        flag1++;
                    }
                    else{
                        flag2++;
                    }
                }
                if(flag1==9 && flag2==0){
                    status.setText("DRAW ! ");
                    pressed.stop();
                    draw.start();
                    gameActive=false;
                    //callinig gameReset() after 2.6 seconds
                    if(gameActive == false){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Please Wait...");
                            }
                        },2000);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //to alternate players
                                if(ap==0){
                                    active_player = 1;
                                    ap=1;
                                }
                                else{
                                    active_player = 0;
                                    ap=0;
                                }
                                gameReset();
                            }
                        },2600);
                    }
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        TextView status = (TextView) findViewById(R.id.status);
        if(active_player==0){
            status.setText("X's Turn");
        }
        else{
            status.setText("O's Turn");
        }
        Button res_btn = (Button) findViewById(R.id.reset_btn);
        res_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameReset();
            }
        });

    }
}