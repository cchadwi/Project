public class Room extends Structure {
    String name;
    int room;
    Creature monster;

    public Room(String _name)
    {
        name = _name;
        System.out.println("Room: Room Constructor");
    }

    public void setId(int _room)
    {
        room = _room;
        System.out.println("Room: setId");
    }

    public void setCreature(Creature _monster)
    {
        monster = _monster;
        System.out.println("Room: setCreature");
    }
}