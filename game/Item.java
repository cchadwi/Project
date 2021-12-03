import java.util.ArrayList;

public class Item extends Displayable {

    Creature owner;
    int room;
    int serial;
    char type;
    char temp = '.';
    String name;
    ArrayList <ItemAction> itemActions = new ArrayList<>();

    public void setOwner(Creature _owner)
    {
        owner = _owner;
        System.out.println("Item: setOwner");
    }

    public void setItemAction(ItemAction itemAction)
    {
        itemActions.add(itemAction);
    }
    public String preformItemActions(KeyStrokePrinter ksp)
    {
        Player p = ksp.player;
        String str = null;
        for (int i = 0; i < itemActions.size(); i++)
        {
            if (itemActions.get(i).name.equals("Hallucinate"))
            {
                str = ksp.hallucinate(itemActions.get(i).intValue);
            }
            else
            {
                str = itemActions.get(i).blessCurse(p);
            }
        }
        return str;
    }
}