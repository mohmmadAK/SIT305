package isleofdead.mohm.isleofdead;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

/**
 * This class displays the option to user to enter a character role name
 */
public class CreateCharacterNameActivity extends AppCompatActivity {

    public MediaPlayer buttonSound;
    public User user;
    public JSONObject gameJson;
    public String gameJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createchar_name);

        user = (User) getIntent().getSerializableExtra("user");
        gameJsonString = getIntent().getStringExtra("gameJson");
        try {
            gameJson = new JSONObject(gameJsonString);
        }catch (JSONException e){

        }

        Intent char_creation_name = getIntent();
        final TextView errorName = (TextView) findViewById(R.id.errorMessageName);
        errorName.setTextColor(WHITE);
        errorName.setTextSize(20);
        errorName.setVisibility(View.INVISIBLE);
        buttonSound = MediaPlayer.create(this, R.raw.button_press);
        final EditText enter_name_field = (EditText) findViewById(R.id.editTextCharName);
        Button back_button = (Button) findViewById(R.id.backCharName);
        back_button.setBackgroundResource(R.drawable.woodbutton);
        back_button.setTextColor(WHITE);
        back_button.setSoundEffectsEnabled(false);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), GameStartActivity.class);
                buttonSound.start();
                startActivity(intent);
                finishAfterSound(buttonSound);
                //finish();
            }
        });

        Button confirm_button = (Button) findViewById(R.id.confirmCharName);
        confirm_button.setBackgroundResource(R.drawable.woodbutton);
        confirm_button.setTextColor(WHITE);
        confirm_button.setSoundEffectsEnabled(false);

        //Confirm after user character name validation
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String player_name = enter_name_field.getText().toString();
                String[] name_split = player_name.split("");
                int space_count = 0;
                for(int i = 0; i < name_split.length; i++)
                {
                    if(name_split[i].equals(" "))
                    {
                        space_count++;
                    }
                }

                if(player_name.length() < 2 || player_name.length() > 15 || space_count == player_name.length())
                {
                    errorName.setVisibility(View.VISIBLE);
                    return;
                }

                user.actor_name = player_name;

                saveGameData(user, player_name);
                Intent intent = new Intent(v.getContext(), CreateCharacterGenderActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("gameJson", gameJson.toString());
                buttonSound.start();
                startActivity(intent);
                finishAfterSound(buttonSound);
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
     * Save the User's name information to game data json
     * @param user
     * @param playerName
     */
    public void saveGameData(User user, String playerName) {

        try {
            JSONObject userObject = gameJson.getJSONObject("User");
            userObject.put("name", playerName);

            getSharedPreferences("PREFS_NAME", MODE_PRIVATE).edit()
                    .putString("gameJson", gameJson.toString()).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

