package isleofdead.mohm.isleofdead;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;


/**Class to represent the Game home Menu selection options**/

public class GameHomeMenuActivity extends Activity {


    public MediaPlayer buttonSound;
    public MediaPlayer townMusic;

    public User user;
    public JSONObject gameJson;
    public String gameJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_home_menu);

        user = (User) getIntent().getSerializableExtra("user");
        gameJsonString = getIntent().getStringExtra("gameJson");
        try {
            if(null != gameJsonString) {
                gameJson = new JSONObject(gameJsonString);
            } else {
                SharedPreferences pref = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                gameJsonString = pref.getString("gameJson", null);
                gameJson = new JSONObject(gameJsonString);
            }
        }catch (JSONException e){

        }

        buttonSound  = MediaPlayer.create(this, R.raw.button_press);
        townMusic = MediaPlayer.create(this, R.raw.background_music);
        townMusic.setLooping(true);
        townMusic.start();

        saveGameData(user);


        Button isleButton = (Button) findViewById(R.id.isleButton);
        ImageView titleView = (ImageView) findViewById(R.id.regionTitleView);
        titleView.setVisibility(View.VISIBLE);


        isleButton.setBackgroundResource(R.drawable.woodbutton);
        isleButton.setTextColor(WHITE);
        isleButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                buttonSound.start();
                townMusic.release();
                if(user.current_health > user.max_health) {
                    user.current_health = user.max_health;
                }
                Intent intent = new Intent(getApplicationContext(), IsleActivity.class);
                intent.putExtra("battleData", "na");
                intent.putExtra("user", user);
                intent.putExtra("gameJson", gameJson.toString());
                startActivity(intent);
                finishAfterSound(buttonSound);
            }
        });

        Button questButton = (Button) findViewById(R.id.questBoard);
        questButton.setBackgroundResource(R.drawable.woodbutton);
        questButton.setTextColor(WHITE);

        questButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                buttonSound.start();
                townMusic.release();

                Intent intent = new Intent(getApplicationContext(), QuestInfoActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("gameJson", gameJson.toString());
                startActivity(intent);
                finishAfterSound(buttonSound);
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


    /**Method to save the game data json string to shared preferences**/

    public void saveGameData(User user) {

            getSharedPreferences("PREFS_NAME", MODE_PRIVATE).edit()
                    .putString("gameJson", gameJson.toString()).commit();
    }
}
