public class ChangedDisplayType extends CreatureAction
{
    String name;

    public ChangedDisplayType(String _name, Creature _owner)
    {
        super(_owner);
        name = _name;
        System.out.println("ChangedDisplayType: ChangedDisplayType Constructor");
    }
}
