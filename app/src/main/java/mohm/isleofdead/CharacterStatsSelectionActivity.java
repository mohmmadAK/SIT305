package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

/**
 * Class for user to select the statistics like strength, willpower ,
 * dexterity and other attributes before game start
 */
public class CharacterStatsSelectionActivity extends AppCompatActivity {
    public MediaPlayer buttonSound;

    public User user;
    public JSONObject gameJson;
    public String gameJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_stats_selection);
        Intent character_creation_stat = getIntent();

        user = (User) getIntent().getSerializableExtra("user");
        gameJsonString = getIntent().getStringExtra("gameJson");
        try {
            gameJson = new JSONObject(gameJsonString);
        }catch (JSONException e){

        }


        buttonSound = MediaPlayer.create(this, R.raw.button_press);
        final TextView spend_points_message = (TextView) findViewById(R.id.spendPointsMessage);
        spend_points_message.setVisibility(View.INVISIBLE);

        //Set the user attributes selection text
        final TextView pointsRemaining = (TextView) findViewById(R.id.remainingPoints);
        pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
        final TextView strength = (TextView) findViewById(R.id.strView);
        strength.setText("STR: "+user.strength);
        final TextView defense= (TextView) findViewById(R.id.defView);
        defense.setText("DEF: "+user.defense);
        final TextView dexterity= (TextView) findViewById(R.id.dexView);
        dexterity.setText("DEX: "+user.dexterity);
        final TextView willpower= (TextView) findViewById(R.id.wilView);
        willpower.setText("WIL: "+user.willpower);
        final TextView constitution= (TextView) findViewById(R.id.conView);
        constitution.setText("CON: "+user.constitution);

        //Button to decrease the strength and update the user strength statistics
        Button decrease_strength = (Button) findViewById(R.id.strDec);
        decrease_strength.setSoundEffectsEnabled(false);
        decrease_strength.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.strength > 1)
                {
                    user.strength--;
                    user.current_addtional_stat_points++;
                    strength.setText("STR: "+user.strength);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to increase the strength and update the user strength statistics
        Button increase_strength = (Button) findViewById(R.id.strInc);
        increase_strength.setSoundEffectsEnabled(false);
        increase_strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.current_addtional_stat_points > 0)
                {
                    user.strength++;
                    user.current_addtional_stat_points--;
                    strength.setText("STR: "+user.strength);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });


        //Button to decrease the dexterity and update the user dexterity statistics
        Button decrease_dexterity = (Button) findViewById(R.id.dexDec);
        decrease_dexterity.setSoundEffectsEnabled(false);
        decrease_dexterity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if( user.dexterity > 1 )
                {
                    user.dexterity--;
                    user.current_addtional_stat_points++;
                    dexterity.setText("DEX: "+user.dexterity);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to increase the dexterity and update the user dexterity statistics
        Button increase_dexterity = (Button) findViewById(R.id.dexInc);
        increase_dexterity.setSoundEffectsEnabled(false);
        increase_dexterity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.current_addtional_stat_points > 0)
                {
                    user.dexterity++;
                    user.current_addtional_stat_points--;
                    dexterity.setText("DEX: "+user.dexterity);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to decrease the defense and update the user defense statistics
        Button decrease_defense = (Button) findViewById(R.id.defDec);
        decrease_defense.setSoundEffectsEnabled(false);
        decrease_defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.defense > 1 )
                {
                    user.defense--;
                    user.current_addtional_stat_points++;
                    defense.setText("DEF: "+user.defense);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to increase the defense and update the user defense statistics
        Button increase_defense = (Button) findViewById(R.id.defInc);
        increase_defense.setSoundEffectsEnabled(false);
        increase_defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.current_addtional_stat_points > 0)
                {
                    user.defense++;
                    user.current_addtional_stat_points--;
                    defense.setText("DEF: "+user.defense);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to decrease the willpower and update the user willpower statistics
        Button decrease_willpower = (Button) findViewById(R.id.wilDec);
        decrease_willpower.setSoundEffectsEnabled(false);
        decrease_willpower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.willpower > 1)
                {
                    user.willpower--;
                    user.current_addtional_stat_points++;
                    willpower.setText("WIL: "+user.willpower);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to increase the willpower and update the user willpower statistics
        Button increase_willpower = (Button) findViewById(R.id.wilInc);
        increase_willpower.setSoundEffectsEnabled(false);
        increase_willpower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.current_addtional_stat_points > 0)
                {
                    user.willpower++;
                    user.current_addtional_stat_points--;
                    willpower.setText("WIL: "+user.willpower);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to decrease the constitution and update the user constitution statistics
        Button decrease_constitution = (Button) findViewById(R.id.conDec);
        decrease_constitution.setSoundEffectsEnabled(false);
        decrease_constitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.constitution > 1 )
                {
                    user.constitution--;
                    user.current_addtional_stat_points++;
                    user.max_health = 50 + user.constitution*10;
                    user.current_health = user.max_health;
                    constitution.setText("CON: "+user.constitution);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Button to increase the constitution and update the user constitution statistics
        Button increase_constitution = (Button) findViewById(R.id.conInc);
        increase_constitution.setSoundEffectsEnabled(false);
        increase_constitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonSound.start();
                if(user.current_addtional_stat_points > 0)
                {
                    user.constitution++;
                    user.current_addtional_stat_points--;
                    user.max_health = 50 + user.constitution*10;
                    user.current_health = user.max_health;
                    constitution.setText("CON: "+user.constitution);
                    pointsRemaining.setText("Points remaining: "+ user.current_addtional_stat_points);
                }

            }
        });

        //Back button takes back to Gender Selection Activity
        Button back_button = (Button) findViewById(R.id.charStatsBack);
        back_button.setBackgroundResource(R.drawable.woodbutton);
        back_button.setTextColor(WHITE);
        back_button.setSoundEffectsEnabled(false);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), CreateCharacterGenderActivity.class);
                user.strength = 5;
                user.defense = 5;
                user.dexterity = 5;
                user.constitution = 5;
                user.willpower = 5;
                user.current_addtional_stat_points = 5;
                intent.putExtra("user",user);
                buttonSound.start();
                startActivity(intent);
                finishAfterSound(buttonSound);
                //finish();
            }
        });

        //Confirm button takes to next stage if all statistics are updated.
        Button confirm_button = (Button) findViewById(R.id.charStatConfirm);
        confirm_button.setBackgroundResource(R.drawable.woodbutton);
        confirm_button.setTextColor(WHITE);
        confirm_button.setTextSize(15);
        confirm_button.setSoundEffectsEnabled(false);
        confirm_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(user.current_addtional_stat_points != 0)
                {
                    spend_points_message.setVisibility(View.VISIBLE);
                }
                else
                {
                    saveGameData(user);
                    Intent intent = new Intent(v.getContext(), GameHomeMenuActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("gameJson", gameJson.toString());
                    buttonSound.start();
                    startActivity(intent);
                    finishAfterSound(buttonSound);
                    //finish();
                }
            }
        });

        //Reloads the statistics screen
        Button stat_info = (Button) findViewById(R.id.statInfo);
        stat_info.setSoundEffectsEnabled(false);
        stat_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), CharacterStatsSelectionActivity.class);
                //intent.putExtra("user",user);
                buttonSound.start();
                //finish();
            }
        });
    }

    public void finishAfterSound(final MediaPlayer mp){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("finishAfterSound sleep ERROR");
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mp.release();
                        finish();
                    }
                });

            }
        }).start();
    }

    /**
     * Method to save the user's stats to game data json
     * @param user
     */
    public void saveGameData(User user) {

        try {
            JSONObject userObject = gameJson.getJSONObject("User");
            userObject.put("strength", user.strength);
            userObject.put("defense", user.defense);
            userObject.put("willpower", user.willpower);
            userObject.put("dexterity", user.dexterity);
            userObject.put("constitution", user.constitution);

            getSharedPreferences("PREFS_NAME", MODE_PRIVATE).edit()
                    .putString("gameJson", gameJson.toString()).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
