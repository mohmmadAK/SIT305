package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import isleofdead.mohm.isleofdead.datamodels.User;

/**
 * This class provides information and description about the different quests in the game
 */
public class QuestInfoActivity extends AppCompatActivity {

    public User user;
    public JSONObject gameJson;
    public String gameJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_info);

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(QuestInfoActivity.this, GameHomeMenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("user", user);
        i.putExtra("gameJson", gameJson.toString());
        startActivity(i);
        finish();
    }
}
