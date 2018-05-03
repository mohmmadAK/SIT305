package isleofdead.mohm.isleofdead;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Inventory extends AppCompatActivity {

    ImageButton bread=null;
    ImageButton potion =null;
    ImageButton sword =null;
    ImageButton fire =null;
    ImageButton bubble =null;
    ImageButton teleport =null;
    ImageButton stats =null;
    ImageButton save =null;

    //info to save and give to stats intent
    String charatcerName =null;
    int playerHunger = 0;
    int attackPower =0;
    int playerHP =0;
    int enemysKilled= 0;

    public static final String tools= "an item was used";

    boolean[] itemUsed = new boolean[6];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        bread= (ImageButton) findViewById(R.id.ibBread);
        potion =(ImageButton) findViewById(R.id.ibPotion);
        sword =(ImageButton) findViewById(R.id.ibSword);
        fire =(ImageButton) findViewById(R.id.ibFire);
        bubble =(ImageButton) findViewById(R.id.ibBubble);
        teleport =(ImageButton) findViewById(R.id.ibTeleport);
        stats =(ImageButton) findViewById(R.id.ibStats);
        save =(ImageButton) findViewById(R.id.ibSave);

        ImageButton[] items = {bread, potion, sword, fire, bubble, teleport};
        Intent bag = getIntent();
        final boolean[] boolItems = bag.getBooleanArrayExtra(Level2Activity.booInventory);

        for(int x =0; x <6; x +=1) {
            itemUsed[x] = false;

            //sets teh items image to question mark if the user did not pick it up
            if (boolItems[x] == false)
            {
                items[x].setImageDrawable(getDrawable(R.drawable.question_mark));
            }
        }

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent charInfo = getIntent();

                //getting info from mainGame activity
                charatcerName = charInfo.getStringExtra("The name");
                playerHunger = charInfo.getIntExtra("hunger", 0);
                attackPower = charInfo.getIntExtra("attack power",0);
                playerHP = charInfo.getIntExtra("player HP", 0);
                enemysKilled = charInfo.getIntExtra("enemys killed", 0);

                Intent stat = new Intent(Inventory.this, GameStatistics.class);

                //these are the vaibles needed in the stats activity
                stat.putExtra("name", charatcerName);
                stat.putExtra("hunger", playerHunger);
                stat.putExtra("attack", attackPower);
                stat.putExtra("hp", playerHP);
                stat.putExtra("killed", enemysKilled);

                startActivity(stat);

            }});

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SharedPreferences save = getSharedPreferences("Saved progress", MODE_PRIVATE);
                SharedPreferences.Editor myEditor = save.edit();

                Intent charInfo = getIntent();

                if(charInfo.getIntExtra("player HP", 0) <= 0){
                    toaster("There is not a game to save", 1500);
                }
                else{
                    // saving all the data
                    myEditor.putString("player name",charInfo.getStringExtra("The name"));
                    myEditor.putInt("player hunger", charInfo.getIntExtra("hunger", 0));
                    myEditor.putInt("player attack",charInfo.getIntExtra("attack power",0));
                    myEditor.putInt("Health",charInfo.getIntExtra("player HP", 0));
                    myEditor.putInt("enemys killed",charInfo.getIntExtra("enemys killed", 0));
                    myEditor.putInt("character number", charInfo.getIntExtra("character number", 0));
                    myEditor.putInt("action counter", charInfo.getIntExtra("action counter",0));
                    myEditor.putInt("player defense", charInfo.getIntExtra("player defence",0));
                    //myEditor.putBoolean("inventory", charInfo.getBooleanExtra("Inventory", false));
                    for(int x =0;  x<  boolItems.length; x+=1){
                        myEditor.putBoolean("boolitems"+"_" + x, boolItems[x]);
                    }
                    myEditor.putBoolean("in battle", charInfo.getBooleanExtra("in battle", false));
                    myEditor.putInt("enemy attack", charInfo.getIntExtra("enemy attack",0));
                    myEditor.putInt("enemy health", charInfo.getIntExtra("enemy health",0));
                    myEditor.putInt("enemy number", charInfo.getIntExtra("enemy number",0));
                    myEditor.putInt("bubble", charInfo.getIntExtra("bubble",0));
                    myEditor.putInt("background counter", charInfo.getIntExtra("background counter", 0));
                    myEditor.apply();

                    toaster("Your game was successfully saved", 1000);
                }
            }});
    }

    public void toaster(String message, int length){

        final Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, length);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void breadAction(View v)//lowers hunger by 2
    {
        bread= (ImageButton) findViewById(R.id.ibBread);

        if(bread.getDrawable() != getDrawable(R.drawable.question_mark))
        {
            //set image back to question mark
            bread.setImageDrawable(getDrawable(R.drawable.question_mark));

            itemUsed[0] = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void potionAction(View v)//raises health by 2
    {
        potion =(ImageButton) findViewById(R.id.ibPotion);

        if(potion.getDrawable() != getDrawable(R.drawable.question_mark))
        {
            potion.setImageDrawable(getDrawable(R.drawable.question_mark));

            itemUsed[1]= true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void swordAction(View v)//raises attack by 1
    {
        sword =(ImageButton) findViewById(R.id.ibSword);

        if(sword.getDrawable() != getDrawable(R.drawable.question_mark))
        {
            sword.setImageDrawable(getDrawable(R.drawable.question_mark));

            itemUsed[2]= true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void fireAction(View v)//kills one enemy
    {
        fire =(ImageButton) findViewById(R.id.ibFire);

        if(fire.getDrawable() != getDrawable(R.drawable.question_mark))
        {
            fire.setImageDrawable(getDrawable(R.drawable.question_mark));

            itemUsed[3]= true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void bubbleAction(View v)//blocks 3 attacks
    {
        bubble =(ImageButton) findViewById(R.id.ibBubble);

        if(bubble.getDrawable() != getDrawable(R.drawable.question_mark))
        {
            bubble.setImageDrawable(getDrawable(R.drawable.question_mark));

            itemUsed[4]= true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void teleportAction(View v)
    {
        teleport =(ImageButton) findViewById(R.id.ibTeleport);

        if(teleport.getDrawable() != getDrawable(R.drawable.question_mark))
        {
            teleport.setImageDrawable(getDrawable(R.drawable.question_mark));

            itemUsed[5]= true;
        }

    }

    public void openStats() {
        //this method is not working so i created a on click listener

        Intent charInfo = getIntent();

        //getting info from mainGame activity
        charatcerName = charInfo.getStringExtra("The name");
        playerHunger = charInfo.getIntExtra("hunger", 0);
        attackPower = charInfo.getIntExtra("attack",0);
        playerHP = charInfo.getIntExtra("player HP", 0);
        enemysKilled = charInfo.getIntExtra("enemys killed", 0);

        Intent stat = new Intent(Inventory.this, GameStatistics.class);

        //these are the varibles needed in the stats activity
        stat.putExtra("name", charatcerName);
        stat.putExtra("hunger", playerHunger);
        stat.putExtra("attack", attackPower);
        stat.putExtra("hp", playerHP);
        stat.putExtra("killed", enemysKilled);

        startActivity(stat);
    }

    public void saveGame() {
        //thsi method was is not working so i created a on click listener

        Intent charInfo = getIntent();

        SharedPreferences save = getSharedPreferences("Saved Progress", MODE_PRIVATE);
        SharedPreferences.Editor myEditor = save.edit();

        //saving all the data
        myEditor.putString("player name",charInfo.getStringExtra("The name"));
        myEditor.putInt("player hunger", charInfo.getIntExtra("hunger", 0));
        myEditor.putInt("player attack",charInfo.getIntExtra("attack power",0));
        myEditor.putInt("Health",charInfo.getIntExtra("player HP", 0));
        myEditor.putInt("enemys killed",charInfo.getIntExtra("enemys killed", 0));
        myEditor.putInt("character number", charInfo.getIntExtra("character number", 0));
        myEditor.putInt("action counter", charInfo.getIntExtra("action counter",0));
        myEditor.putInt("action defense", charInfo.getIntExtra("player defence",0));
        myEditor.putInt("map", charInfo.getIntExtra("map",0));
        myEditor.putInt("map dimitions", charInfo.getIntExtra("mapDim",0));
        myEditor.putInt("map dimetion value", charInfo.getIntExtra("mapDimValue",0));
        myEditor.putBoolean("inventory", charInfo.getBooleanExtra("Inventory", true));
        myEditor.putBoolean("in battle", charInfo.getBooleanExtra("in battle", true));
        myEditor.putInt("enemy attack", charInfo.getIntExtra("enemy attack",0));
        myEditor.putInt("enemy health", charInfo.getIntExtra("enemy health",0));
        myEditor.putInt("enemy number", charInfo.getIntExtra("enemy number",0));
        myEditor.putInt("bubble", charInfo.getIntExtra("bubble",0));
        myEditor.apply();

        toaster("Your game was successfully saved", 1000);

    }

    public void onBackPressed(){
        Intent returnIntent = new Intent();//data sent to MainGame activity
        returnIntent.putExtra(tools, itemUsed);
        setResult(RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}

