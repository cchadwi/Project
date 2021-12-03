import java.util.ArrayList;

public class Displayable
{
    int maxHit;
    int hpMoves;
    int hp;
    char type;
    int intValue;
    int width;
    int height;
    boolean visible;
    ArrayList<Integer> posX = new ArrayList<Integer>();
    ArrayList<Integer> posY = new ArrayList<Integer>();

    public Displayable()
    {
        System.out.println("Displayable: Displayable Constructor");
    }

    public ArrayList<ArrayList> getPoint()
    {
        ArrayList<ArrayList> Points = new ArrayList<ArrayList>();
        Points.add(posX);
        Points.add(posY);
        return Points;
    }

    public void setInvisible()
    {
        visible = false;
        System.out.println("Displayable: setInvisible");
    }

    public void setVisible()
    {
        visible = true;
        System.out.println("Displayable: setVisible");
    }

    public void setMaxHit(int _maxHit)
    {
        maxHit = _maxHit;
        System.out.println("Displayable: setMaxHit");
    }

    public void setHpMove(int _hpMoves)
    {
        hpMoves = _hpMoves;
        System.out.println("Displayable: setHpMoves");
    }

    public void setHp(int _hp)
    {
        hp = _hp;
        System.out.println("Displayable: setHp");
    }

    public void setType(char _type)
    {
        type = _type;
        System.out.println("Displayable: setType");
    }

    public void setIntValue(int _intValue)
    {
        intValue = _intValue;
        System.out.println("Displayable: setIntValue");
    }

    public void setPosX(int _posX)
    {
        posX.add(_posX);
        System.out.println("Displayable: setPosX" + "  " + _posX);
    }

    public void setPosY(int _posY)
    {
        posY.add(_posY);
        System.out.println("Displayable: setPosY" + "  " + _posY);
    }

    public void setWidth(int _width)
    {
        width = _width;
        System.out.println("Displayable: setWidth");
    }

    public void setHeight(int _height)
    {
        height = _height;
        System.out.println("Displayable: setHeight");
    }
}