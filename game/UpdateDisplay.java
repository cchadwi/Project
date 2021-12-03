public class UpdateDisplay extends CreatureAction{

    String name;

    public UpdateDisplay(String _name, Creature _owner)
    {
        super(_owner);
        name = _name;
        System.out.println("UpdateDisplay: UpdateDisplay Constructor");
    }
}