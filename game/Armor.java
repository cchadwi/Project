public class Armor extends Item
{

    public Armor(String _name) {
        name = _name;
        System.out.println("Armor: Armor Constructor");
        this.type = ']';
    }

    public void setID(int _room, int _serial)
    {
        room = _room;
        serial = _serial;
        System.out.println("Armor: setID");
    }

    public void setName(String _name)
    {
        name = _name;
        System.out.println("Armor: setName");
    }
}