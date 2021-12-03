public class Player extends Creature
{
    Item sword;
    Item armor;

    public Player()
    {
        super();
        System.out.println("Player: Player Constructor");
        setType('@');
    }

    public void setWeapon(Item _sword)
    {
        sword = _sword;
        System.out.println("player: setWeapon");
    }

    public void setArmor(Item _armor)
    {
        armor = _armor;
        System.out.println("Player: setArmor");
    }
}