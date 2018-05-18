package isleofdead.mohm.isleofdead.datamodels;


/** Class to represent a Ghost object of the game with attributes**/

public class Ghost extends CharacterActor
{
    public  int id;
    public String imagename;

    public Ghost(String name,String image_name,int id,int hp, int str, int def ,int wil, int dex)
    {
        actor_name    = name;
        imagename     = image_name;
        base_health   = hp;
        current_health= hp;
        strength      = str;
        dexterity     = dex;
        defense       = def;
        willpower     = wil;
        type          = "ghost";
        this.id        = id;
    }

    public void addMove(String move)
    {
        return;
    }
}
