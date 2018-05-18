package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import isleofdead.mohm.isleofdead.datamodels.Ghost;
import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

/**
 * This class represents the Isle Activity which gives the user options to get into battles
 * Based on the number of battles played and completed different levels are unlocked accordingly..
 */
public class IsleActivity extends AppCompatActivity {

    Button enterBattle;
    Button enterBattle2;
    Button enterBattle3;
    Button back;
    ImageView completedImage;

    User user;
    public JSONObject gameJson;
    public String gameJsonString;

    String battleData;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isle);

        enterBattle = (Button) findViewById(R.id.enterBattle1);
        enterBattle2 = (Button) findViewById(R.id.enterBattle2);
        enterBattle3 = (Button) findViewById(R.id.enterBattle3);
        back = (Button) findViewById(R.id.dungeonBack);
        completedImage = (ImageView) findViewById(R.id.completedImage);

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


        battleData = (String) getIntent().getSerializableExtra("battleData");

        if(user == null){
            user = loadUserData(gameJson);
            battleData = user.battledata;
        }

        enterBattle.setBackgroundResource(R.drawable.woodbutton);
        enterBattle.setTextColor(WHITE);
        enterBattle2.setBackgroundResource(R.drawable.woodbutton);
        enterBattle2.setTextColor(WHITE);
        enterBattle3.setBackgroundResource(R.drawable.woodbutton);
        enterBattle3.setTextColor(WHITE);


        //Based on the last battle status, set the user health paramaters accordingly.
        //This can be adjusted to make the game more difficult. Have kept it easy for demo purpose

        Toast showData = Toast.makeText(getApplicationContext(),"Battle Aborted",Toast.LENGTH_LONG);;
        if(battleData.equals("lose")) {
            showData = Toast.makeText(getApplicationContext(), "You lost the battle.", Toast.LENGTH_LONG);
            user.recreate();
            user.current_health = user.max_health/2;

            saveGameData(user);
        }
        if(battleData.equals("flee")) {
            showData = Toast.makeText(getApplicationContext(), "You successfully ran away from the battle", Toast.LENGTH_LONG);
            user.current_gold =  (int) (user.current_gold * .95);

            saveGameData(user);
        }
        if(battleData.equals("win1")) {
            showData = Toast.makeText(getApplicationContext(), "Bravo! You killed the conjuring ghost", Toast.LENGTH_LONG);
            user.current_gold += 10;
            user.experince_bar += 10;

            try{
                if(user.completedBattles.get(0) == 0)
                    user.completedBattles.set(0,1);}
            catch(Exception e){

            }

            try{
                if(user.completedBattles.get(0) == 3)
                    user.completedBattles.set(0,4);
            }catch(Exception e){

            }

            try{
                if(user.completedBattles.get(0) == 6)
                    user.completedBattles.set(0,7);}
            catch(Exception e){

            }

            user.current_health += (user.max_health - user.current_health)/2;

            saveGameData(user);
        }
        if(battleData.equals("win2")) {
            showData = Toast.makeText(getApplicationContext(), "Bravo! You killed the Monster ghost", Toast.LENGTH_LONG);
            user.current_gold += 15;
            user.experince_bar += 20;
            try {
                if (user.completedBattles.get(0) == 1) user.completedBattles.set(0, 2);
            } catch (Exception e) {
            }
            try {
                if (user.completedBattles.get(0) == 4) user.completedBattles.set(0, 5);
            } catch (Exception e) {
            }
            try {
                if (user.completedBattles.get(0) == 7) user.completedBattles.set(0, 8);
            } catch (Exception e) {
            }
            user.current_health += (user.max_health - user.current_health)/2;

            saveGameData(user);

        }
        if(battleData.equals("win3")) {
            showData = Toast.makeText(getApplicationContext(), "You have conquered Isle of the Dead", Toast.LENGTH_LONG);
            user.current_gold += 25;
            user.experince_bar += 50;
            try {
                if (user.completedBattles.get(0) == 2) user.completedBattles.set(0, 3);
            } catch (Exception e) {
            }
            try {
                if (user.completedBattles.get(0) == 5) user.completedBattles.set(0, 6);
            } catch (Exception e) {
            }
            try {
                if (user.completedBattles.get(0) == 8) user.completedBattles.set(0, 9);
            } catch (Exception e) {
            }
            user.current_health += (user.max_health - user.current_health)/2;

            saveGameData(user);

        }
        if(!battleData.equals("na"))
            showData.show();

        //Check the progress of the Battles
        int dProgress = 0;
        try{
            dProgress = user.completedBattles.get(0);
        }catch(Exception e){
            user.completedBattles.add(0);
        }
        // Case 0 : If no battles were completed
        if(dProgress == 0){
            completedImage.setVisibility(View.INVISIBLE);
            enterBattle2.setText("Locked");
            enterBattle3.setText("Locked");
        }
        // Case 1 : If the First Battle was completed
        if(dProgress == 1){
            completedImage.setVisibility(View.INVISIBLE);
            enterBattle2.setText("Monster Battle");
            enterBattle3.setText("Locked");
        }
        // Case 2 : If both First and Second Battle were completed
        if(dProgress == 2){
            completedImage.setVisibility(View.INVISIBLE);
            enterBattle2.setText("Monster Battle");
            enterBattle3.setText("Tunnel Battle");
        }
        // Case 3 : If all three battles were completed
        if(dProgress == 3){
            completedImage.setVisibility(View.VISIBLE);
            enterBattle2.setText("Monster");
            enterBattle3.setText("Tunnel Battle");
        }


        back.setBackgroundResource(R.drawable.woodbutton);
        back.setTextColor(WHITE);

        final MediaPlayer mp  = MediaPlayer.create(this, R.raw.isle_music);
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_press);
        mp.start();
        mp.setLooping(true);

        //Debug log to print the game data json for debugging
        Log.d("GameJSon", "Game JSON String: " + gameJsonString);
        Log.d("GameJSon", "Game JSON : " + gameJson.toString());


        //Enter the first battle
        enterBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Log.d("GameJSon", "Game JSON : " + gameJson.toString());
                    JSONArray ghostsObj = gameJson.getJSONArray("Ghosts");

                    for(int i=0; i< ghostsObj.length(); i++){
                        JSONObject ghostObj = ghostsObj.getJSONObject(i);
                        int id = ghostObj.getInt("id");
                        if(id == 111){
                            String name = ghostObj.getString("name");
                            String imageName = ghostObj.getString("imagename");
                            Integer hp = ghostObj.getInt("basehealth");
                            Integer strength = ghostObj.getInt("strength");
                            Integer defense = ghostObj.getInt("defense");
                            Integer dexterity = ghostObj.getInt("dexterity");
                            Integer willpower = ghostObj.getInt("willpower");

                            Ghost oldwoman = new Ghost(name, imageName,id, hp, strength, defense, willpower, dexterity);
                            Intent intent = new Intent(v.getContext(), BattleGroundActivity.class);
                            intent.putExtra("bNum",1);
                            intent.putExtra("user",user);
                            intent.putExtra("enemy",oldwoman);

                            mp.stop();
                            mp.release();
                            startActivity(intent);
                            buttonSound.start();
                            finishAfterSound(buttonSound);
                        }
                    }

                } catch (JSONException e){
                    Log.e("GhostError", "Error loading Ghost Info from JSON");
                }
            }
        });

        //Enter the second battle
        enterBattle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    JSONArray ghostsObj = gameJson.getJSONArray("Ghosts");

                    for(int i=0; i< ghostsObj.length(); i++){
                        JSONObject ghostObj = ghostsObj.getJSONObject(i);
                        int id = ghostObj.getInt("id");
                        if(id == 222){
                            String name = ghostObj.getString("name");
                            String imageName = ghostObj.getString("imagename");
                            Integer hp = ghostObj.getInt("basehealth");
                            Integer strength = ghostObj.getInt("strength");
                            Integer defense = ghostObj.getInt("defense");
                            Integer dexterity = ghostObj.getInt("dexterity");
                            Integer willpower = ghostObj.getInt("willpower");

                            Ghost wildbeast = new Ghost(name, imageName,id, hp, strength, defense, willpower, dexterity);
                            Intent intent = new Intent(v.getContext(), BattleGroundActivity.class);
                            intent.putExtra("bNum",2);
                            intent.putExtra("user",user);
                            intent.putExtra("enemy",wildbeast);

                            mp.stop();
                            mp.release();
                            startActivity(intent);
                            buttonSound.start();
                            finishAfterSound(buttonSound);
                        }
                    }

                } catch (JSONException e){
                    Log.e("GhostError", "Error loading Ghost Info from JSON");
                }

            }
        });


        //Enter the tunnel battle
        enterBattle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.stop();
                mp.release();

                loadTunnelBattle();

                buttonSound.start();
                finishAfterSound(buttonSound);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameHomeMenuActivity.class);
                intent.putExtra("user",user);
                mp.stop();
                mp.release();
                startActivity(intent);
                buttonSound.start();
                finishAfterSound(buttonSound);
            }
        });



    }

    /**
     * Method to load the user data object from game json object
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

            Log.d("testJson", "Dexterity = " + dexterity);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Method to save the current health of the user after each battle
     * @param user
     */
    public void saveGameData(User user) {

        try {
            JSONObject userObject = gameJson.getJSONObject("User");
            userObject.put("current_health", user.current_health);
            userObject.put("max_health", user.max_health);

            getSharedPreferences("PREFS_NAME", MODE_PRIVATE).edit()
                    .putString("gameJson", gameJson.toString()).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load the tunnel battle
     */
    public void loadTunnelBattle()
    {
        //getting saved data from old game
        SharedPreferences load = getSharedPreferences("Saved progress", MODE_PRIVATE);

        int charNum = load.getInt("character number", 0);
        int playerHP = load.getInt("Health", 0);
        int playerHunger = load.getInt("player hunger", 0);
        int actionCounter = load.getInt("action counter", 0);
        int playerAttack = load.getInt("player attack", 0);
        int playerdefense = load.getInt("player defense", 0);

        //getting inventory array
        boolean[] inventory = new boolean[6];
        for (int x = 0; x < 6; x += 1) {
            inventory[x] = load.getBoolean("boolitems" + "_" + x, true);
        }
        boolean inBattle = load.getBoolean("in battle", true);
        int enemyHealth = load.getInt("enemy health", 0);
        int enemyAttack = load.getInt("enemy attack", 0);
        int enemyNum = load.getInt("enemy number", 0);
        int enemysKilled = load.getInt("enemys killed", 0);
        int bubble = load.getInt("bubble", 0);
        int backgroundCounter = load.getInt("background counter",0);

        //send data to and open Tunnel Battle Activity
        Intent open = new Intent(IsleActivity.this, TunnelBattleActivity.class);

        open.putExtra("The name", user.actor_name);
        open.putExtra("hunger", playerHunger);
        open.putExtra("attack power", playerAttack);
        open.putExtra("player HP", playerHP);
        open.putExtra("enemys killed", enemysKilled);

        if(user.gender.equals("male")){
            charNum = 0;
        } else if (user.gender.equals("female")){
            charNum = 1;
        }

        open.putExtra("character number", charNum);
        open.putExtra("action counter", actionCounter);
        open.putExtra("player defence", playerdefense);
        open.putExtra("Inventory", inventory);
        for (int x = 0; x < 6; x += 1) {
            open.putExtra("inventory" + "_" + x, inventory[x]);
        }
        open.putExtra("in battle", inBattle);
        open.putExtra("enemy attack", enemyAttack);
        open.putExtra("enemy health", enemyHealth);
        open.putExtra("enemy number", enemyNum);
        open.putExtra("bubble", bubble);
        open.putExtra("background counter", backgroundCounter);

        startActivity(open);
    }
}
