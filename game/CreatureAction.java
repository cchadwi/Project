public class CreatureAction extends Action
{
    Creature owner;
    String name;
    String type;

    public CreatureAction(Creature _owner)
    {
        owner = _owner;
        System.out.println("CreatureAction: CreatureAction Constructor");
    }
    public void setName(String _name){
        name = _name;
    }

    public void setType(String _type){
        type = _type;
    }
}