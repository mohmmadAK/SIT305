package mohm.isleofdead.datamodels;

import android.media.Image;

import java.io.Serializable;

public class Weapon extends Item implements Serializable
{

    public Weapon(String n, Image i, String d, int[] ss, int gold_value)
    {
        super(n);
        icon = i;
        description = d;
        stats = ss;
        value = gold_value;
    }

    public int[] getStatBuffs()
    {
        return stats;
    }
}