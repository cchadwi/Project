public class Sword extends Item {

    public Sword(String _name)
    {
        name = _name;
        System.out.println("Sword: Sword Constructor");
        this.type = ')';
    }

    public void setID(int _room, int _serial)
    {
        room = _room;
        serial = _serial;
        System.out.println("Sword: setID");
    }

}