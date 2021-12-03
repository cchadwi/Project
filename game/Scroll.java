public class Scroll extends Item {

    public Scroll(String _name)
    {
        name = _name;
        System.out.println("Scroll: Scroll Constructor");
        this.type = '?';
    }

    public void setID(int _room, int _serial)
    {
        room = _room;
        serial = _serial;
        System.out.println("Scroll: setID");
    }

}