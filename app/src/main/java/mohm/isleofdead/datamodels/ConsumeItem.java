package isleofdead.mohm.isleofdead.datamodels;


import android.media.Image;

import java.io.Serializable;

class ConsumeItem extends Item implements Serializable
{

    ConsumeItem(String n, Image i, String d, int[] ss, int gold_value)
    {
        super(n);
        icon = i;
        description = d;
        stats = ss;
        value = gold_value;
    }

    int[] getStatBuffs()
    {
        return stats;
    }
}
