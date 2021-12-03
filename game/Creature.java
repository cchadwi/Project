public class Creature extends Displayable
{
    CreatureAction ha;
    CreatureAction da;
    int room;
    int serial;

    public Creature()
    {
        System.out.println("Creature: Creature Constructor");
    }

    public void setHp(int _hp)
    {
        hp = _hp;
        System.out.println("Creature: setHp");
    }

    public void setHpMoves(int _hpMoves)
    {
        hpMoves = _hpMoves;
        System.out.println("Creature: setHpMoves");
    }

    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
        System.out.println("Creature: room: " + room + "\n   serial: " + serial);
    }

    public void setDeathAction(CreatureAction _da)
    {
        da = _da;
        System.out.println("Creature: setDeathAction");
    }

    public void setHitAction(CreatureAction _ha)
    {
        ha = _ha;
        System.out.println("Creature: setHitAction");
    }
}