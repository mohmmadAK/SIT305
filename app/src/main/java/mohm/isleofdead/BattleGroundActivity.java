package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import isleofdead.mohm.isleofdead.datamodels.Ghost;
import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

public class BattleGroundActivity extends AppCompatActivity {

    User user;
    Ghost enemy;
    int bNum;
    Button attackButton;
    Button runButton;
    TextView userHPtext;
    TextView enemyHPtext;
    TextView chooseActiontext;
    ImageView enemyHitImage;
    ImageView userHitImage;
    ProgressBar userHealthProgress;
    ProgressBar enemyHealthProgress;
    ImageView monsterImage;
    ImageView userImage;
    Boolean isFleeing = false;
    Boolean userAttacksFirst = false;
    Boolean userWin = false;
    Boolean userLose = false;


    public void actionChosen(){
        attackButton.setVisibility(View.INVISIBLE);
        runButton.setVisibility(View.INVISIBLE);
        chooseActiontext.setVisibility(View.INVISIBLE);
    }
    public void turnFinish(){
        attackButton.setVisibility(View.VISIBLE);
        runButton.setVisibility(View.VISIBLE);
        chooseActiontext.setVisibility(View.VISIBLE);
    }

    public void turnSequence(final boolean playerFirst){
        final MediaPlayer swordAttackSound = MediaPlayer.create(this, R.raw.sword_attack);
        final MediaPlayer enemyPunchSound = MediaPlayer.create(this, R.raw.enemy_punch);

        if(playerFirst){
            swordAttackSound.start();
            changeEnemyHealthBar(enemy.current_health - (int)Math.max(user.strength*3-enemy.defense/2,1));
            userAttackAnimation();
        }else{
            enemyPunchSound.start();
            changeUserHealthBar(user.current_health - (int)Math.max(enemy.strength*3-user.defense/2,1));
            enemyAttackAnimation();
        }

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Sleep not working:" + userHealthProgress.getProgress());
                }
                if(user.current_health == 0){
                    userLose = true;
                    swordAttackSound.release();
                    enemyPunchSound.release();
                    return;
                }else if(enemy.current_health == 0){
                    userWin = true;
                    swordAttackSound.release();
                    enemyPunchSound.release();
                    return;
                }
                if(playerFirst) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Sleep not working:" + userHealthProgress.getProgress());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            enemyPunchSound.start();
                            changeUserHealthBar(user.current_health - (int)Math.max(enemy.strength*3-user.defense/2,1));
                            enemyAttackAnimation();
                        }
                    });

                }else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            swordAttackSound.start();
                            changeEnemyHealthBar(enemy.current_health - (int)Math.max(user.strength*3-enemy.defense/2,1));
                            userAttackAnimation();
                        }
                    });

                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Sleep not working:" + userHealthProgress.getProgress());
                }
                swordAttackSound.release();
                enemyPunchSound.release();
                if(user.current_health == 0){
                    userLose = true;
                    return;
                }else if(enemy.current_health == 0){
                    userWin = true;
                    return;
                }
            }
        }).start();
    }

    public void healthBarColorMonitor(){

    }

    public void changeUserHealthBar(final int n){

    }

    public void changeEnemyHealthBar(final int n){

    }

    public void userAttackAnimation(){
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(400);
                }catch (Exception e){
                    System.out.println("Sleep not working:"+userHealthProgress.getProgress());
                }

                int i = 11;
                while(i <= 31){
                    final int temp = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String name = "battle_slash"+temp;
                            int id = getResources().getIdentifier(name, "drawable", getPackageName());
                            enemyHitImage.setImageResource(id);

                        }
                    });
                    try{
                        Thread.sleep(100);
                    }catch (Exception e){
                        System.out.println("Sleep not working:"+userHealthProgress.getProgress());
                    }
                    i++;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int id = getResources().getIdentifier("battle_slash0", "drawable", getPackageName());
                        enemyHitImage.setImageResource(id);
                        turnFinish();
                    }
                });
            }
        }).start();
    }

    public void enemyAttackAnimation(){
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                int i = 0;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userHitImage.setVisibility(View.VISIBLE);
                        //turnFinish();
                    }
                });
                while(i <= 10){
                    final int temp = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String name = "claw_attack"+temp;
                            int id = getResources().getIdentifier(name, "drawable", getPackageName());
                            userHitImage.setImageResource(id);

                        }
                    });
                    try{
                        Thread.sleep(100);
                    }catch (Exception e){
                        System.out.println("Sleep not working:"+userHealthProgress.getProgress());
                    }
                    i++;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userHitImage.setVisibility(View.INVISIBLE);
                        //turnFinish();
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ground);
        user = (User) getIntent().getSerializableExtra("user");
        enemy = (Ghost) getIntent().getSerializableExtra("enemy");
        //bNum = (int) getIntent().getSerializableExtra("bNum");
        bNum = 1;
        userHPtext = (TextView)  findViewById(R.id.userHealthText);
        enemyHPtext = (TextView)  findViewById(R.id.enemyHealthText);
        chooseActiontext = (TextView)  findViewById(R.id.textchooseaction);
        userHealthProgress = (ProgressBar) findViewById(R.id.userHealthBar);
        enemyHealthProgress = (ProgressBar) findViewById(R.id.enemyHealthBar);
        enemyHitImage = (ImageView) findViewById(R.id.enemyHitImage);
        userHitImage = (ImageView) findViewById(R.id.userHitImage);
        userImage = (ImageView)  findViewById(R.id.userView);
        monsterImage = (ImageView)  findViewById(R.id.monsterView);
        TextView hpLabel = (TextView) findViewById(R.id.HPLabel);
        hpLabel.setText(user.actor_name+"'s HP:");
        TextView enemeyHpLabel = (TextView) findViewById(R.id.enemyHPLabel);
        //enemeyHpLabel.setText(enemy.actor_name+"'s HP:");
        // Initialize stuff
        userHPtext.setText(user.current_health + " / " + user.max_health);
        userHealthProgress.setMax(user.max_health);
        userHealthProgress.setProgress(user.current_health);
        userHealthProgress.getProgressDrawable().setColorFilter(Color.rgb(0,200,0), PorterDuff.Mode.SRC_IN);
        if(user.gender.equalsIgnoreCase("female")){
            int id = getResources().getIdentifier("female_high_health", "drawable", getPackageName());
            userImage.setImageResource(id);
        }

        //enemyHPtext.setText(enemy.current_health + " / " + enemy.base_health);
        //enemyHealthProgress.setMax(enemy.base_health);
        //enemyHealthProgress.setProgress(enemy.current_health);
        enemyHealthProgress.getProgressDrawable().setColorFilter(Color.rgb(0,200,0), PorterDuff.Mode.SRC_IN);
        //int id = getResources().getIdentifier(enemy.imagename, "drawable", getPackageName());
        //monsterImage.setImageResource(id);
        monsterImage.setImageResource(R.drawable.old_woman_ghost);

        //Roll the Dice
        int dice = (int) (Math.random()*10)+1;
        Toast showDice;

        if(dice > 5)
            showDice = Toast.makeText(getApplicationContext(),"Dice Rolled("+dice+"): Enemy attacks first",Toast.LENGTH_LONG);
        else {
            showDice = Toast.makeText(getApplicationContext(), "Dice Rolled("+dice+"): "+user.actor_name+" attacks first", Toast.LENGTH_LONG);
            userAttacksFirst = true;
        }
        showDice.show();

        final MediaPlayer fleeSound = MediaPlayer.create(this, R.raw.flee_sound);

        healthBarColorMonitor();

        attackButton = (Button) findViewById(R.id.attackButton);
        attackButton.setBackgroundResource(R.drawable.woodbutton);
        attackButton.setTextColor(WHITE);
        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attackSound.start();
                actionChosen();
                turnSequence(userAttacksFirst);
            }
        });

        runButton = (Button) findViewById(R.id.runButton);
        runButton.setBackgroundResource(R.drawable.woodbutton);
        runButton.setTextColor(WHITE);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                actionChosen();


                fleeSound.start();

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        isFleeing = true;
                    }
                });


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1000);
                        }catch (Exception e){
                            System.out.println("FLEE Sleep not working:");
                        }
                        fleeSound.release();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(v.getContext(), GameStartActivity.class);
                                intent.putExtra("battleData", "flee");
                                intent.putExtra("user",user);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }).start();
                //finish();
            }
        });

        //Make thread for catching lose/win
        final Handler handler = new Handler();
        //isFleeing = true;
        final MediaPlayer dSound = MediaPlayer.create(this, R.raw.deathsound);
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(!userLose && !userWin) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("FLEE Sleep not working:");
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(isFleeing) return;
                        }
                    });


                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        actionChosen();

                        fleeSound.release();
                    }
                });
                dSound.start();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("FLEE Sleep not working:");
                }
                dSound.stop();
                dSound.release();


                if(userWin) {
                    Intent intent = new Intent(BattleGroundActivity.this, GameStartActivity.class);
                    intent.putExtra("battleData", "win"+bNum);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
                if(userLose) {
                    Intent intent = new Intent(BattleGroundActivity.this, GameStartActivity.class);
                    intent.putExtra("battleData", "lose");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();

    }
}

