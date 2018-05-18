package isleofdead.mohm.isleofdead.datamodels;

import java.io.Serializable;
import java.util.ArrayList;

/**Class to represent the User object. Extends CharacterActor
 * Has additional attributes.
 * These can be extended by anybody wanting to add more functionality to the User
 * This combined with all data model objects allows for extending the game to include
 * more user information, weapons and quests to create more adventures
 */
public class User extends CharacterActor implements Serializable {

    ArrayList<Item> inventory;

    //Armour equiped_armor;
    Weapon equiped_weapon;
    public int current_gold;
    public int user_level;
    public int experince_bar;

    //public int experience_needed_to_level;
    public ArrayList<Quest> quest_list;
    public int current_addtional_stat_points;
    public String gender;
    public int max_health;
    public ArrayList<Integer> completedBattles;
    public ArrayList<Integer> completedQuests;

    public  Boolean autoSave = true;
    public String battledata;

    public User(String name)
    {
        actor_name = name;
        type = "user";
        gender = null;
        strength = 5;
        defense = 5;
        willpower = 5;
        dexterity = 5;
        constitution = 5;
        max_health = 50 + constitution*10;
        current_health = max_health;
        //superAttackCharge = 0;
        current_gold = 10;
        user_level = 1;
        experince_bar = 0;
        //experience_needed_to_level = 100;
        current_addtional_stat_points = 3;
        //equiped_armor = null;
        equiped_weapon = null;
        inventory = new ArrayList<>();
        quest_list = new ArrayList<>();
        completedBattles = new ArrayList<>();
        completedBattles.add(0);
        completedQuests = new ArrayList<>();
        //lastTownAt = null;
        battledata = "";


        int[] weapon_stat_change = {0,1,0,0,0,0};
        int[] item_stat_change   = {10,0,0,0,0,0};

        //Weapon start_weapon = new Weapon("Sword",null,"Its something.(+1 STR)",weapon_stat_change,0);
        //ConsumeItem water = new ConsumeItem("Water",null,"A bottle of water (+10 HP).",item_stat_change,0);

        //Quest sample_quest2 = new Quest(2,"Welcome to Isle Of Dead","Start Game","Sword",0,start_weapon);

        //inventory.add(start_weapon);
        //inventory.add(water);

        //quest_list.add(sample_quest2);
    }


    public void recreate()
    {
        current_gold = (int)(current_gold * 0.8);
        experince_bar = 0;
    }
}


