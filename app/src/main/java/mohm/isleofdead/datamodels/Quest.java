package isleofdead.mohm.isleofdead.datamodels;


import java.io.Serializable;

/**
 * Quest class to represent a Quest in the game
 */
public class Quest implements Serializable
{
    public int quest_id;
    String quest_name;
    String quest_description;
    String item_name;
    int count;
    Boolean isActive;
    public Boolean isComplete;
    int get_reward;
    Item item_reward;


    public Quest (int id,String qname, String description,String iname, int er, Item ir)
    {
        quest_id = id;
        quest_name = qname;
        quest_description = description;
        count = 0;
        isActive = true;
        isComplete = false;
        item_name = iname;
        get_reward = er;
        item_reward = ir;
    }
}
