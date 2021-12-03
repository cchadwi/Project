public class Passage extends Structure {
    String name;
    int room1;
    int room2;
    
    public Passage()
    {
        System.out.println("Passage: Passage Constructor");
    }

    public void setName(String _name)
    {
        name = _name;
        System.out.println("Passage: setName");
    }

    public void setID(int _room1, int _room2)
    {
        room1 = _room1;
        room2 = _room2;
        System.out.println("Passage: setID");
    }
}