package isleofdead.mohm.isleofdead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__statistics);

        TextView name =(TextView) findViewById(R.id.tvCharacterName);
        TextView attack =(TextView) findViewById(R.id.tvAttackPower);
        TextView health =(TextView) findViewById(R.id.tvHealth);
        TextView hunger =(TextView) findViewById(R.id.tvhunger);
        TextView killed =(TextView) findViewById(R.id.tvEnemysKilled);

        Intent info = getIntent();

        name.setText("Name: " + info.getStringExtra("name"));
        attack.setText("Attack Power: " + String.valueOf(info.getIntExtra("attack", 0)));
        health.setText("Health: " + String.valueOf(info.getIntExtra("hp", 0)));
        hunger.setText("Hunger: " + String.valueOf(info.getIntExtra("hunger", 0)));
        killed.setText("Enemies Killed: " + String.valueOf(info.getIntExtra("killed", 0)));
    }
}
