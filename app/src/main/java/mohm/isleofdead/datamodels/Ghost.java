package isleofdead.mohm.isleofdead.datamodels;


public class Ghost extends CharacterActor
{
    public  int mob_id;
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
        mob_id        = id;
    }

    public void addMove(String move)
    {
        return;
    }
}
