public class EndGame extends CreatureAction
{
    String name;

    public EndGame(String _name, Creature _owner)
    {
        super(_owner);
        name = _name;
        System.out.println("EndGame: EndGame Constructor");
    }
}