package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

/**
 * Game Start Activity is the first main activity to be called after Splash Screen
 * It checks if the game has been played before by checking for saved game data
 * If available it asks the user to either start fresh by overwriting or loading save game
 * If starting fresh it copies the game data json from assets to in memory
 * The inmemory game data json string is updated across the activities and saved to shared preferences
 * when required to save key game information.
 */
public class GameStartActivity extends AppCompatActivity {

    public MediaPlayer mp;
    public MediaPlayer buttonSound;
    public User user;
    public JSONObject gameJson;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        SharedPreferences pref = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        String gameJsonString = pref.getString("gameJson", null);

        JsonUtil jsonUtil = new JsonUtil();

        if(gameJsonString == null) {
            //Copy the default game load from assets to external storage as this is first time game is loaded
            gameJsonString = jsonUtil.loadJSONFromAsset(this, "gameInfo");
            Log.d("CopyJSON", "Game Json file info copied from assets : " + gameJsonString);
        }

        try {
            gameJson = new JSONObject(gameJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user = loadUserData(gameJson);

        buttonSound  = MediaPlayer.create(this, R.raw.button_press);
        final Button new_button = (Button) findViewById(R.id.newGame);
        new_button.setBackgroundResource(R.drawable.woodbutton);
        new_button.setTextColor(WHITE);
        new_button.setTextSize(20);
        new_button.setSoundEffectsEnabled(false);

        //On click listener for new game. If new game move to character selection
        // if not check for reload or overwrite game options
        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n)
            {
                //If the user data in json is defaults then its a fresh new game
                if(user.actor_name.equals("empty") || user.gender.equals("null"))
                {
                    Intent intent = new Intent(n.getContext(), CreateCharacterNameActivity.class);
                    buttonSound.start();
                    intent.putExtra("user",user);
                    intent.putExtra("gameJson", gameJson.toString());
                    startActivity(intent);
                    finishAfterSound(buttonSound);
                }
                else
                {
                    registerForContextMenu(new_button);
                    openContextMenu(new_button);
                }
            }

        });


        //Exit button stops the game.
        Button exit_button = (Button) findViewById(R.id.exitGame);
        exit_button.setBackgroundResource(R.drawable.woodbutton);
        exit_button.setTextColor(WHITE);
        exit_button.setTextSize(20);
        exit_button.setSoundEffectsEnabled(false);

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e)
            {

                buttonSound.start();
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

                        finish();
                    }
                });

            }
        }).start();
    }

    /**
     * Function to handle the converstion of game data JSON object to Class object
     * @param gameJson
     * @return
     */
    public User loadUserData (JSONObject gameJson)
    {
        User user = new User("empty");
        try {
            JSONObject userObj = gameJson.getJSONObject("User");

            //Retrieve all fields from the JSON Object
            String name = userObj.getString("name");
            String type = userObj.getString("type");
            String gender = userObj.getString("gender");
            Integer strength = userObj.getInt("strength");
            Integer defense = userObj.getInt("defense");
            Integer willpower = userObj.getInt("willpower");
            Integer dexterity = userObj.getInt("dexterity");
            Integer constitution = userObj.getInt("constitution");
            Integer max_health = userObj.getInt("max_health");
            Integer current_health = userObj.getInt("current_health");
            String battledata = userObj.getString("battledata");

           //Create the user object from the JSON Object fields
            user.actor_name = name;
            user.type = type;
            user.gender = gender;
            user.strength = strength;
            user.defense = defense;
            user.willpower = willpower;
            user.dexterity = dexterity;
            user.constitution = constitution;
            user.max_health = max_health;
            user.current_health = current_health;
            user.battledata = battledata;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    final int CONTEXT_MENU_OVERWRITE = 1;
    final int CONTEXT_MENU_LOAD = 2;
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        //Context menu

        menu.setHeaderTitle("Do you want to overwrite your save?");
        menu.add(Menu.NONE, CONTEXT_MENU_OVERWRITE, Menu.NONE, "Overwrite");
        menu.add(Menu.NONE, CONTEXT_MENU_LOAD, Menu.NONE, "Load Saved");

    }

    @Override
    public boolean onContextItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case CONTEXT_MENU_OVERWRITE:
            {
                try {
                    Log.d("Test_JSON", "Print before update : " + gameJson.toString());
                    JSONObject userObject = gameJson.getJSONObject("User");
                    userObject.put("name", "empty");
                    userObject.put("gender", "null");
                    userObject.put("strength", 5);
                    userObject.put("defense", 5);
                    userObject.put("willpower", 5);
                    userObject.put("dexterity", 5);
                    userObject.put("constitution", 5);
                    userObject.put("max_health", 100);
                    userObject.put("current_health", 100);
                    userObject.put("battledata", "lose");
                    Log.d("Test_JSON", "Print after update : " + gameJson.toString());

                    getSharedPreferences("PREFS_NAME",MODE_PRIVATE).edit()
                            .putString("gameJson", gameJson.toString()).commit();

                    user = loadUserData(gameJson);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, CreateCharacterNameActivity.class);
                buttonSound.start();
                intent.putExtra("user",user);
                intent.putExtra("gameJson", gameJson.toString());
                startActivity(intent);

                finish();
            }
            break;
            case CONTEXT_MENU_LOAD:
            {
                User temp;
                temp = loadUserData(gameJson);
                if(user != null && temp != null && !temp.actor_name.equals("empty"))
                {
                    user = temp;
                    Toast load = Toast.makeText(getApplicationContext(),"Load Successful.",Toast.LENGTH_SHORT);
                    load.show();

                    Intent intent = new Intent(this, IsleActivity.class);
                    buttonSound.start();
                    intent.putExtra("user",user);
                    intent.putExtra("gameJson", gameJson.toString());
                    intent.putExtra("battleData", user.battledata);
                    startActivity(intent);
                    finish();
                } else {

                    try {
                        Log.d("Test_JSON", "Print before update : " + gameJson.toString());
                        JSONObject userObject = gameJson.getJSONObject("User");
                        userObject.put("name", "empty");
                        Log.d("Test_JSON", "Print after update : " + gameJson.toString());

                        getSharedPreferences("PREFS_NAME",MODE_PRIVATE).edit()
                                .putString("gameJson", gameJson.toString()).commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(this, CreateCharacterNameActivity.class);
                    buttonSound.start();
                    startActivity(intent);
                    finish();
                }
            }
            break;
        }
        return super.onContextItemSelected(item);
    }
}
