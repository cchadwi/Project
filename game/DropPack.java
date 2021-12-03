public class DropPack extends CreatureAction
{
    String name;

    public DropPack(String _name, Creature owner)
    {
        super(owner);
        name = _name;
        System.out.println("DropPack: DropPack Constructor");
    }
}