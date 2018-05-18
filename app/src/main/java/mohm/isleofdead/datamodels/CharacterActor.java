package isleofdead.mohm.isleofdead.datamodels;


import android.media.Image;

import java.io.Serializable;

/**
 * Class to represent the role playing character in the game and attributes
 */
public class CharacterActor implements Serializable
{
    public String actor_name;
    public String type;
    public int base_health;
    public int current_health;
    public int strength;
    public int dexterity;
    public int constitution;
    public int defense;
    public int willpower;
    public Image graphic;

}
