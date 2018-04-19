package mohm.isleofdead;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import isleofdead.mohm.isleofdead.datamodels.User;

import static android.graphics.Color.WHITE;

//Activity to launch the game
public class GameStartActivity extends AppCompatActivity {

    public MediaPlayer mp;
    public MediaPlayer buttonSound;
    public User u;
    public  boolean overwrite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        Intent start_page_intent = getIntent();
        u = load("savefile.txt");

        overwrite = false;

        buttonSound  = MediaPlayer.create(this, R.raw.button_press);
        final Button new_button = (Button) findViewById(R.id.newGame);
        new_button.setBackgroundResource(R.drawable.woodbutton);
        new_button.setTextColor(WHITE);
        new_button.setTextSize(20);
        new_button.setSoundEffectsEnabled(false);

        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n)
            {
                //intent char creation
                if(u == null || u.gender == null)
                {
                    Intent intent = new Intent(n.getContext(), CreateCharacterNameActivity.class);
                    buttonSound.start();
                    startActivity(intent);

                    finishAfterSound(buttonSound);
                    //finish();
                }
                else
                {
                    registerForContextMenu(new_button);
                    openContextMenu(new_button);
                }
            }

        });


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

    public void save (User user, String filename)
    {
        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User load (String filename)
    {
        FileInputStream fis;
        User user = null;
        try {
            fis = getApplicationContext().openFileInput(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    final int CONTEXT_MENU_OVERWRITE = 1;
    final int CONTEXT_MENU_CANCEL = 2;
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        //Context menu

        menu.setHeaderTitle("Do you want to overwrite your save?");
        menu.add(Menu.NONE, CONTEXT_MENU_OVERWRITE, Menu.NONE, "Overwrite");
        menu.add(Menu.NONE, CONTEXT_MENU_CANCEL, Menu.NONE, "Cancel");

    }

    @Override
    public boolean onContextItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case CONTEXT_MENU_OVERWRITE:
            {
                User empty = new User("empty");
                save(empty,"savefile.txt");
                Intent intent = new Intent(this, CreateCharacterNameActivity.class);
                buttonSound.start();
                startActivity(intent);

                finish();
            }
            break;
            case CONTEXT_MENU_CANCEL:
            {
                closeContextMenu();
            }
            break;
        }
        return super.onContextItemSelected(item);
    }
}
