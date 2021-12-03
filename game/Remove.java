public class Remove extends CreatureAction
{
    String name;

    public Remove(String _name, Creature _owner)
    {
        super(_owner);
        name = _name;
        System.out.println("Remove: Remove Constructor");
    }
}