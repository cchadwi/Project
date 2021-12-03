public class ItemAction extends Action
{
    Item owner;
    String name;
    String type;

    public ItemAction(Item _owner)
    {
        owner = _owner;
        System.out.println("ItemAction: ItemAction Constructor");
    }

    public void setName(String _name){
        name = _name;
    }

    public void setType(String _type){
        type = _type;
    }
    public String blessCurse(Player p)
    {
        String str = null;
        Item weapon = p.sword;
        Item armor = p.armor;
        if (weapon != null && charValue == 'w')
        {
            weapon.intValue += this.intValue;
            str =  "Sword cursed! " + intValue + " taken from its effectiveness.";
            weapon.name = "w +" + weapon.intValue + " sword";
        }
        else if (armor != null && charValue == 'a')
        {
            armor.intValue += this.intValue;
            str = "Armor cursed! " + intValue + " taken from its effectiveness.";
            armor.name = "a +" + armor.intValue + " armor";
        }
        return str;
    }
}