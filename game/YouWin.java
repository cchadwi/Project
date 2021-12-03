public class YouWin extends CreatureAction{

    String name;

    public YouWin(String _name, Creature _owner)
    {
        super(_owner);
        name = _name;
        System.out.println("YouWin: YouWin Constructor");
    }
}