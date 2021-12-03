public class Monster extends Creature {
    
    String name;

    public Monster()
    {
        super();
        System.out.println("Monster: Monster Constructor");
    }

    public void setID(int _room, int _serial)
    {
        room = _room;
        serial = _serial;
        System.out.println("Monster: setID");
    }

    public void setName(String _name)
    {
        name = _name;
        System.out.println("Monster: setName");
    }
}