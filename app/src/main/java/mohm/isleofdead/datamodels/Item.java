package isleofdead.mohm.isleofdead.datamodels;

import android.media.Image;
import java.io.Serializable;

/**
 * Item represents any item in the game
 */
public class Item implements Serializable {

    String name;
    Image icon;
    String description;
    int value;
    boolean isEquipped = false; //boolean variable used to detect for weapons and armor;

    public Item (String n)
    {
        name = n;
        icon = null;
        description = null;
    }

    int[] stats = new int[6];
}
