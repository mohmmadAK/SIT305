package isleofdead.mohm.isleofdead;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;


public class GameHomeMenuActivity extends Activity {


    public MediaPlayer buttonSound;
    public MediaPlayer townMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_home_menu);

        final User user = (User) getIntent().getSerializableExtra("user");

        buttonSound  = MediaPlayer.create(this, R.raw.button_press);
        townMusic = MediaPlayer.create(this, R.raw.background_music);
        townMusic.setLooping(true);
        townMusic.start();

        if(user.autoSave)
        {
            if(save(user,"savefile.txt"))
            {
               // Toast autoSave = Toast.makeText(getApplicationContext(),"Autosaving...",Toast.LENGTH_SHORT);
                //autoSave.show();
            }
        }

        Button dungeonButton = (Button) findViewById(R.id.dungeonButton);
        ImageView titleView = (ImageView) findViewById(R.id.regionTitleView);
        titleView.setVisibility(View.VISIBLE);


        dungeonButton.setBackgroundResource(R.drawable.woodbutton);
        dungeonButton.setTextColor(WHITE);
        dungeonButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                buttonSound.start();
                townMusic.release();
                if(user.current_health > user.max_health) user.current_health = user.max_health;
                Intent intent = new Intent(getApplicationContext(), BattleGroundActivity.class);
                intent.putExtra("battleData", "na");
                intent.putExtra("user", user);
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


    public Boolean save (User user, String filename)
    {
        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
