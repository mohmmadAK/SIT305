package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateCharacter extends AppCompatActivity {

    ConstraintLayout box;

    ImageButton man1;
    ImageButton man2;
    ImageButton man3;
    ImageButton woman1;
    ImageButton woman2;

    ImageView frameMan1;
    ImageView frameMan2;
    ImageView frameMan3;
    ImageView frameWoman1;
    ImageView frameWoman2;

    EditText inputName;

    String characterName;
    int characterNum;

    public static final String name = "string name";
    public static final String charactNum= "The characters number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);



        man1 = (ImageButton) findViewById(R.id.ivMan1);
        man2 = (ImageButton) findViewById(R.id.ivMan2);
        man3 = (ImageButton) findViewById(R.id.ivMan3);
        woman1 = (ImageButton) findViewById(R.id.ivWoman1);
        woman2 = (ImageButton) findViewById(R.id.ivWoman2);

        frameMan1 = (ImageView) findViewById(R.id.iFrameMan1);
        frameMan2 = (ImageView) findViewById(R.id.iFrameMan2);
        frameMan3 = (ImageView) findViewById(R.id.iFrameMan3);
        frameWoman1 = (ImageView) findViewById(R.id.iFrameWoman1);
        frameWoman2 = (ImageView) findViewById(R.id.iFrameWoman2);

        frameMan1.setVisibility(View.INVISIBLE);
        frameMan2.setVisibility(View.INVISIBLE);
        frameMan3.setVisibility(View.INVISIBLE);
        frameWoman1.setVisibility(View.INVISIBLE);
        frameWoman2.setVisibility(View.INVISIBLE);

    }

    public void characterSeleted(View character) {
        man1 = (ImageButton) findViewById(R.id.ivMan1);
        man2 = (ImageButton) findViewById(R.id.ivMan2);
        man3 = (ImageButton) findViewById(R.id.ivMan3);
        woman1 = (ImageButton) findViewById(R.id.ivWoman1);
        woman2 = (ImageButton) findViewById(R.id.ivWoman2);

        frameMan1 = (ImageView) findViewById(R.id.iFrameMan1);
        frameMan2 = (ImageView) findViewById(R.id.iFrameMan2);
        frameMan3 = (ImageView) findViewById(R.id.iFrameMan3);
        frameWoman1 = (ImageView) findViewById(R.id.iFrameWoman1);
        frameWoman2 = (ImageView) findViewById(R.id.iFrameWoman2);

        //puts all character buttons in an array and the same for character frames
        ImageButton[] characterArr = {man1, man2, man3, woman1, woman2};
        ImageView[] characterFrameArr = {frameMan1, frameMan2, frameMan3, frameWoman1, frameWoman2};

        for (int x = 0; x < 5; x += 1) {
            if (character == characterArr[x]) {
                characterFrameArr[x].setVisibility(View.VISIBLE);

                //makes the selected character the character so it can be displayed on the main game screen
                characterNum = x;
            } else {
                characterFrameArr[x].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void startGame(View V) {
        //gets the character name
        inputName = (EditText) findViewById(R.id.etCharacterName);
        characterName = inputName.getText().toString();

        if(characterName == null|| characterName == ""){
            toaster("You much choose a character name", 1500);
        }else {
            Intent game = new Intent(this, TunnelBattleActivity.class);

            game.putExtra(name, characterName);
            game.putExtra(charactNum, characterNum);

            //finish();
            startActivity(game);
        }
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
}
