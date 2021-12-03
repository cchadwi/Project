public class Teleport extends CreatureAction
{
    String name;

    public Teleport(String _name, Creature _owner)
    {
        super(_owner);
        name = _name;
        System.out.println("Teleport: Teleport Constructor");
    }
}