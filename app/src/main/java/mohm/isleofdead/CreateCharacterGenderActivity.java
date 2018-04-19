package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

public class CreateCharacterGenderActivity extends AppCompatActivity {
    public MediaPlayer buttonSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character_gender);
        Intent character_creation_sex = getIntent();
        final User user = (User) getIntent().getSerializableExtra("user");
        buttonSound = MediaPlayer.create(this, R.raw.button_press);
        Button male_button = (Button) findViewById(R.id.maleButton);
        male_button.setBackgroundResource(R.drawable.woodbutton);
        male_button.setTextColor(WHITE);
        male_button.setSoundEffectsEnabled(false);
        male_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                user.gender = "male";
                Intent intent = new Intent(v.getContext(), BattleGroundActivity.class);
                intent.putExtra("user",user);
                buttonSound.start();
                startActivity(intent);
                finishAfterSound(buttonSound);
                //finish();
            }
        });

        Button female_button = (Button) findViewById(R.id.femaleButton);
        female_button.setBackgroundResource(R.drawable.woodbutton);
        female_button.setTextColor(WHITE);
        female_button.setSoundEffectsEnabled(false);
        female_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                user.gender = "female";
                Intent intent = new Intent(v.getContext(), BattleGroundActivity.class);
                intent.putExtra("user",user);
                buttonSound.start();
                startActivity(intent);
                finishAfterSound(buttonSound);
                //finish();
            }
        });

        Button back_button = (Button) findViewById(R.id.backButtonGender);
        back_button.setBackgroundResource(R.drawable.woodbutton);
        back_button.setTextColor(WHITE);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), CreateCharacterNameActivity.class);
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
}

