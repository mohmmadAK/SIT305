package isleofdead.mohm.isleofdead;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Level2Activity extends AppCompatActivity {

    ImageView character;
    TextView name;
    int backgroundCounter = 1;

    String charName = null;
    int charNum = 0;
    int playerHP = 5;
    int playerHunger = 0;
    int actionCounter = 0;
    int playerAttack = 1;
    int playerdefense = 4;

    int[][][][] map = new int[3][3][3][3];

    int mapDim = 0;
    int[] mapDimValue = {0, 0, 0, 0};
    int itemChoice = 0;
    boolean[] inventory = new boolean[6];

    ConstraintLayout background = null;
    ImageButton item = null;
    ImageView enemy = null;
    Button left = null;
    Button straight = null;
    Button right = null;
    Button attack = null;
    Button defend = null;
    TextView centerText = null;
    TextView hungerText = null;
    TextView hpText = null;
    TextView enemyHp = null;
    TextView enemyName = null;

    boolean inBattle = false;
    int enemyHealth = 0;
    int enemyAttack = 0;
    int enemyNum = 0;
    int enemysKilled = 0;

    int bubble = 0;

    static final int requestItem = 2;//must be greater than 0

    //variables to give to bag view
    public static final String booInventory = "the booleans to see if user has picked up item";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        background = (ConstraintLayout) findViewById(R.id.back);
        enemyHp = (TextView) findViewById(R.id.tvEnemyHP);
        enemyName = (TextView) findViewById(R.id.tvEnemyName);
        enemy = (ImageView) findViewById(R.id.ivEnemy);

        left = (Button) findViewById(R.id.btnLeft);
        straight = (Button) findViewById(R.id.btnStraight);
        right = (Button) findViewById(R.id.btnRight);
        attack = (Button) findViewById(R.id.btnAttack);
        defend = (Button) findViewById(R.id.btnDefend);

        character = (ImageView) findViewById(R.id.ivCharacter);
        name = (TextView) findViewById(R.id.tvName);

        item = (ImageButton) findViewById(R.id.IbItemDrop);

        attack.setVisibility(View.INVISIBLE);
        defend.setVisibility(View.INVISIBLE);
        enemyName.setVisibility(View.INVISIBLE);
        enemyHp.setVisibility(View.INVISIBLE);
        item.setVisibility(View.INVISIBLE);
        enemy.setVisibility(View.INVISIBLE);

        Intent start = getIntent();
        charNum = start.getIntExtra(CreateCharacter.charactNum, 0);
        charName = start.getStringExtra(CreateCharacter.name);

        //checks to see if game was loaded or just created
        if (charName == null) {
            Intent loaded = getIntent();

            charName = loaded.getStringExtra("The name");
            playerHunger = loaded.getIntExtra("hunger", 0);
            playerAttack = loaded.getIntExtra("attack power", 0);
            enemysKilled = loaded.getIntExtra("enemys killed", 0);
            charNum = loaded.getIntExtra("character number", 0);
            actionCounter = loaded.getIntExtra("action counter", 0);
            playerdefense = loaded.getIntExtra("player defence", 0);
            inventory = loaded.getBooleanArrayExtra("Inventory");
            inBattle = loaded.getBooleanExtra("in battle", true);
            enemyAttack = loaded.getIntExtra("enemy attack", 0);
            enemyHealth = loaded.getIntExtra("enemy health", 0);
            enemyNum = loaded.getIntExtra("enemy number", 0);
            bubble = loaded.getIntExtra("bubble", 0);
            backgroundCounter = loaded.getIntExtra("background counter", 0);

            //change background
            if (backgroundCounter == 9) {
                backgroundCounter = 1;
            }

            switch (backgroundCounter) {
                case 1:
                    background.setBackgroundResource(R.drawable.tunnel);
                    break;
                case 2:
                    background.setBackgroundResource(R.drawable.tunnel2);
                    break;
                case 3:
                    background.setBackgroundResource(R.drawable.tunnel3);
                    break;
                case 4:
                    background.setBackgroundResource(R.drawable.tunnel4);
                    break;
                case 5:
                    background.setBackgroundResource(R.drawable.tunnel5);
                    break;
                case 6:
                    background.setBackgroundResource(R.drawable.tunnel6);
                    break;
                case 7:
                    background.setBackgroundResource(R.drawable.tunnel7);
                    break;
                case 8:
                    background.setBackgroundResource(R.drawable.tunnel8);
                    break;
            }

            //starts battle if user ened in a battle
            if (inBattle == true) {
                fight();
            }

            toaster("Your game was successfully loaded", 1000);
        }

        //set name
        name.setText(charName);

        //set character picture
        switch (charNum) {
            case 0:
                character.setImageDrawable(getDrawable(R.drawable.man1));
                break;
            case 1:
                character.setImageDrawable(getDrawable(R.drawable.man2));
                break;
            case 2:
                character.setImageDrawable(getDrawable(R.drawable.man3));
                break;
            case 3:
                character.setImageDrawable(getDrawable(R.drawable.woman1));
                break;
            case 4:
                character.setImageDrawable(getDrawable(R.drawable.woman2));
                break;
        }

        int treasurePlacement = (int) Math.random() * 81;//getts the random place for the treasure
        int indexCount = 0;

        //set all indexs of the map to 0
        for (int a = 0; a < 3; a += 1) {
            for (int b = 0; b < 3; b += 1) {
                for (int c = 0; c < 3; c += 1) {
                    for (int d = 0; d < 3; d += 1) {
                        if (indexCount == treasurePlacement) {
                            map[a][b][c][d] = 2;//places the treasure in a random spot
                        } else {
                            map[a][b][c][d] = 3;
                        }

                        indexCount += 1;
                    }
                }
            }
        }
    }


    public void DirectionChange(View V) {
        background = (ConstraintLayout) findViewById(R.id.back);
        item = (ImageButton) findViewById(R.id.IbItemDrop);
        centerText = (TextView) findViewById(R.id.tvCentertext);
        hungerText = (TextView) findViewById(R.id.tvHunger);
        hpText = (TextView) findViewById(R.id.tvHP);

        left = (Button) findViewById(R.id.btnLeft);
        straight = (Button) findViewById(R.id.btnStraight);
        right = (Button) findViewById(R.id.btnRight);

        item.setVisibility(View.INVISIBLE);

        //changes where the user was back to 3
        map[mapDimValue[0]][mapDimValue[1]][mapDimValue[2]][mapDimValue[3]] = 3;

        switch (V.getId()) {

            case R.id.btnLeft:
                mapDimValue[mapDim] = 0;
                break;
            case R.id.btnStraight:
                mapDimValue[mapDim] = 1;
                break;
            case R.id.btnRight:
                mapDimValue[mapDim] = 2;
                break;
        }
        //player wins game start activity displaying the treasure
        if (map[mapDimValue[0]][mapDimValue[1]][mapDimValue[2]][mapDimValue[3]] == 2) {
            win(V);
        }

        //changes where the user is now to 1
        map[mapDimValue[0]][mapDimValue[1]][mapDimValue[2]][mapDimValue[3]] = 1;

        mapDim += 1;

        if (mapDim == 4) {
            mapDim = 0;
        }

        centerText.setText("Which way will you go");

        //raises action by 1 and hunger by 1 every 3 actions
        actionCounter += 1;

        if (actionCounter % 5 == 0) {
            playerHunger += 1;
            hungerText.setText("Hunger: " + playerHunger);
        }

        //lowers hp acording to hunger rules, commented at declaration at top of code
        if (playerHunger >= 5 && actionCounter % 2 == 0) {
            playerHP -= playerHunger - 4;
            hpText.setText("HP: " + playerHP);

            if (playerHP <= 0) {
                gameOver(V);

                Toast.makeText(this, "You died of hunger", Toast.LENGTH_LONG).show();
            }
        }

        //changes background
        backgroundCounter += 1;

        if (backgroundCounter == 9) {
            backgroundCounter = 1;
        }

        switch (backgroundCounter) {
            case 1:
                background.setBackgroundResource(R.drawable.tunnel);
                break;
            case 2:
                background.setBackgroundResource(R.drawable.tunnel2);
                break;
            case 3:
                background.setBackgroundResource(R.drawable.tunnel3);
                break;
            case 4:
                background.setBackgroundResource(R.drawable.tunnel4);
                break;
            case 5:
                background.setBackgroundResource(R.drawable.tunnel5);
                break;
            case 6:
                background.setBackgroundResource(R.drawable.tunnel6);
                break;
            case 7:
                background.setBackgroundResource(R.drawable.tunnel7);
                break;
            case 8:
                background.setBackgroundResource(R.drawable.tunnel8);
                break;
        }

        //decides if monster will attack
        int attakced = (int) (Math.random() * 100) + 1;

        //20 percent chance for a monster to attack
        if (attakced <= 20) {
            if (playerAttack <= 2) {
                enemyNum = (int) ((Math.random() * 3) + 1);
                enemyAttack = 1;
                enemyHealth = 4;
                fight();
            } else if (playerAttack <= 4) {
                enemyNum = (int) ((Math.random() * 3) + 1);
                enemyAttack = 2;
                enemyHealth = 13;
                fight();
            } else {
                enemyNum = (int) ((Math.random() * 3) + 1);
                enemyAttack = playerAttack / 2 + 1;
                enemyHealth = playerAttack * 3 + (int) ((Math.random() * 4) + 1);
                fight();
            }
        }

        //decides if an item will drop
        int dropNumber = (int) ((Math.random() * 100) + 1);//number is random between 0 and 100 if the number is below 20 player gets an item

        if (dropNumber <= 40) {
            centerText.setText("You've found an item");

            item.setVisibility(View.VISIBLE);

            itemChoice = (int) ((Math.random() * (6 - 1)) + 1);

            //these are the 6 items that can drop, a number is chosen randomly
            switch (itemChoice) {
                case 1:
                    item.setImageResource(R.drawable.bread);
                    break;
                case 2:
                    item.setImageResource(R.drawable.potion);
                    break;
                case 3:
                    item.setImageResource(R.drawable.sword);
                    break;
                case 4:
                    item.setImageResource(R.drawable.fire);
                    break;
                case 5:
                    item.setImageResource(R.drawable.bubble);
                    break;
                case 6:
                    item.setImageResource(R.drawable.teleport);
                    break;
            }
        }
    }

    public void ItemPickUp(View v) {
        item = (ImageButton) findViewById(R.id.IbItemDrop);
        centerText = (TextView) findViewById(R.id.tvCentertext);

        item.setVisibility(View.INVISIBLE);

        centerText.setText("Which way will you go");

        //this is where we keep track of what items the user has
        switch (itemChoice) {
            case 1:
                inventory[0] = true;
                break;
            case 2:
                inventory[1] = true;
                break;
            case 3:
                inventory[2] = true;
                break;
            case 4:
                inventory[3] = true;
                break;
            case 5:
                inventory[4] = true;
                break;
            case 6:
                inventory[5] = true;
                break;
        }

    }

    public void fight() {

        inBattle = true;

        enemyHp = (TextView) findViewById(R.id.tvEnemyHP);
        enemyName = (TextView) findViewById(R.id.tvEnemyName);
        enemy = (ImageView) findViewById(R.id.ivEnemy);
        attack = (Button) findViewById(R.id.btnAttack);
        defend = (Button) findViewById(R.id.btnDefend);

        left = (Button) findViewById(R.id.btnLeft);
        straight = (Button) findViewById(R.id.btnStraight);
        right = (Button) findViewById(R.id.btnRight);
        centerText = (TextView) findViewById(R.id.tvCentertext);

        attack.setVisibility(View.VISIBLE);
        defend.setVisibility(View.VISIBLE);
        enemyName.setVisibility(View.VISIBLE);
        enemyHp.setVisibility(View.VISIBLE);
        enemy.setVisibility(View.VISIBLE);

        left.setVisibility(View.INVISIBLE);
        straight.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        centerText.setVisibility(View.INVISIBLE);

        //defence is set to 4 before every battle
        playerdefense = 4;

        //1 is spider, 2 is skeleton, 3 is wolf
        if (enemyNum == 1) {
            enemyName.setText("Spider");
            enemyHp.setText("Spider HP: " + enemyHealth);
            enemy.setImageResource(R.drawable.spider);
        } else if (enemyNum == 2) {
            enemyName.setText("Skeleton");
            enemyHp.setText("Skeleton HP: " + enemyHealth);
            enemy.setImageResource(R.drawable.skeleton);
        } else if (enemyNum == 3) {
            enemyName.setText("Wolf");
            enemyHp.setText("Wolf HP: " + enemyHealth);
            enemy.setImageResource(R.drawable.wolf);
        }

    }

    public void attack(View V) {

        enemyHp = (TextView) findViewById(R.id.tvEnemyHP);
        hpText = (TextView) findViewById(R.id.tvHP);

        int attackChance = (int) ((Math.random() * 100) + 1);

        //you have a 65% chance of hitting if you don't hit you get hit
        if (attackChance >= 35) {
            enemyHealth -= playerAttack;

            if (enemyNum == 1) {
                enemyHp.setText("Spider HP: " + enemyHealth);
            } else if (enemyNum == 2) {
                enemyHp.setText("Skeleton HP: " + enemyHealth);
            } else if (enemyNum == 3) {
                enemyHp.setText("Wolf HP: " + enemyHealth);
            }

            toaster("Hit", 500);
        } else {
            if (bubble == 0) {//player is hit if he does nto have a bubble
                playerHP -= enemyAttack;

                hpText.setText("HP: " + playerHP);

                toaster("Miss", 500);
            } else if (bubble > 0) {//if the player has a bubble
                toaster("Your bubble has deflected the attack", 1000);
                bubble -= 1;
            }
        }

        //checks to see if everyone is alive
        if (enemyHealth <= 0) {
            enemysKilled += 1;
            inBattle = false;
            reset();
            DirectionChange(V);
        }

        if (playerHP <= 0) {
            gameOver(V);

            // Toast.makeText(this,"You where killed", Toast.LENGTH_LONG).show();
        }
    }

    public void defend(View V) {

        hpText = (TextView) findViewById(R.id.tvHP);

        //if defence is at 0 user gets hit else defence goes down by 1
        if (playerdefense == 0) {
            playerHP -= enemyAttack;

            hpText.setText("HP: " + playerHP);

            toaster("Your shield has broke", 500);
        } else {
            playerdefense -= 1;

            toaster("block", 500);
        }

        //checks to see is user is alive
        if (playerHP <= 0) {
            gameOver(V);

            Toast.makeText(this, "You where killed", Toast.LENGTH_LONG).show();
        }
    }

    public void toaster(String message, int length) {

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

    //puts the screen back to normal after a battle
    public void reset() {
        enemyName = (TextView) findViewById(R.id.tvEnemyName);
        enemy = (ImageView) findViewById(R.id.ivEnemy);

        centerText = (TextView) findViewById(R.id.tvCentertext);
        left = (Button) findViewById(R.id.btnLeft);
        straight = (Button) findViewById(R.id.btnStraight);
        right = (Button) findViewById(R.id.btnRight);
        attack = (Button) findViewById(R.id.btnAttack);
        defend = (Button) findViewById(R.id.btnDefend);

        attack.setVisibility(View.INVISIBLE);
        defend.setVisibility(View.INVISIBLE);
        enemyName.setVisibility(View.INVISIBLE);
        enemyHp.setVisibility(View.INVISIBLE);
        item.setVisibility(View.INVISIBLE);
        enemy.setVisibility(View.INVISIBLE);

        left.setVisibility(View.VISIBLE);
        straight.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        centerText.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void openInventory(View V) {
        Intent bag = new Intent(Level2Activity.this, Inventory.class);

        
        bag.putExtra(booInventory, inventory);
        bag.putExtra("The name", charName);
        bag.putExtra("hunger", playerHunger);
        bag.putExtra("attack power", playerAttack);
        bag.putExtra("player HP", playerHP);
        bag.putExtra("enemys killed", enemysKilled);
        bag.putExtra("character number", charNum);
        bag.putExtra("action counter", actionCounter);
        bag.putExtra("player defence", playerdefense);
        bag.putExtra("map", map);
        bag.putExtra("mapDim", mapDim);
        bag.putExtra("mapDimValue", mapDimValue);
        bag.putExtra("Inventory", inventory);
        bag.putExtra("in battle", inBattle);
        bag.putExtra("enemy attack", enemyAttack);
        bag.putExtra("enemy health", enemyHealth);
        bag.putExtra("enemy number", enemyNum);
        bag.putExtra("bubble", bubble);
        bag.putExtra("background counter", backgroundCounter);

        startActivityForResult(bag, requestItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    boolean[] usedItem = data.getBooleanArrayExtra(Inventory.tools);

                    //runs the correct items method
                    if (usedItem[0] == true) {
                        breadItem();
                    } else if (usedItem[1] == true) {
                        potionItem();
                    } else if (usedItem[2] == true) {
                        swordItem();
                    } else if (usedItem[3] == true) {
                        fireAction();
                    } else if (usedItem[4] == true) {
                        bubbleAction();
                    } else if (usedItem[5] == true) {
                        Action();
                    } else {
                        toaster("No item was used", 1000);
                    }

                    //resets the item array so items are not used twice
                    for (int x = 0; x < 6; x += 1) {
                        if (usedItem[x] == true) {
                            inventory[x] = false;
                        }
                    }

                } else {
                    toaster("data is null", 1000);
                }
            }
        }
    }

    public void breadItem() {
        hungerText = (TextView) findViewById(R.id.tvHunger);

        playerHunger -= 2;

        if (playerHunger < 0) {
            playerHunger = 0;
        }

        hungerText.setText("Hunger: " + playerHunger);

        toaster("You eat some bread", 1200);
    }

    public void potionItem() {
        hpText = (TextView) findViewById(R.id.tvHP);

        playerHP += 2;

        hpText.setText("HP: " + playerHP);

        toaster("You drank a health potion", 1200);
    }

    public void swordItem() {
        playerAttack += 1;

        toaster("Your sword is more sharp", 1200);
    }

    public void fireAction() {
        if (enemyHealth != 0) {
            enemyHealth = 0;
            enemysKilled += 1;
            inBattle = false;
            reset();
            toaster("The enemy was burned to death", 1000);
        } else if (enemyHealth == 0) {
            toaster("There is not a enemy to kill", 1000);
        }
    }

    public void bubbleAction() {
        bubble += 3;
        toaster("A bubble surounds you", 1200);
    }

    public void Action() {
        //set where the player is to 3
        map[mapDimValue[0]][mapDimValue[1]][mapDimValue[2]][mapDimValue[3]] = 3;

        //set back the direct that will be changed
        mapDim -= 1;

        backgroundCounter -= 1;

        if (backgroundCounter < 1) {
            backgroundCounter = 8;
        }

        switch (backgroundCounter) {
            case 1:
                background.setBackgroundResource(R.drawable.tunnel);
                break;
            case 2:
                background.setBackgroundResource(R.drawable.tunnel2);
                break;
            case 3:
                background.setBackgroundResource(R.drawable.tunnel3);
                break;
            case 4:
                background.setBackgroundResource(R.drawable.tunnel4);
                break;
            case 5:
                background.setBackgroundResource(R.drawable.tunnel5);
                break;
            case 6:
                background.setBackgroundResource(R.drawable.tunnel6);
                break;
            case 7:
                background.setBackgroundResource(R.drawable.tunnel7);
                break;
            case 8:
                background.setBackgroundResource(R.drawable.tunnel8);
                break;
        }

        if (backgroundCounter == 8) {
            backgroundCounter = 0;
        }

        reset();

        toaster("Display messsage", 1000);
    }

    public void win(View V) {

    }

    public void gameOver(View V) {

    }
}