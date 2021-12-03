import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Dungeon
{
    String name;
    private int gameWidth;
    private int gameHeight;
    ArrayList<Room>  rooms;
    ArrayList<Creature>  creatures;
    ArrayList<Passage> passages;
    ArrayList<Item>  items;
    HashMap<String, Creature> creature_map = new HashMap<String, Creature>();
    HashMap<String, Stack<Item>> item_map = new HashMap<>();
    ArrayList<int[]> validPoint = new ArrayList<>();

    public Dungeon(String _name, int _gameWidth, int _gameHeight)
    {
        name = _name;
        gameHeight = _gameHeight;
        gameWidth = _gameWidth;
        rooms = new ArrayList<Room>();
        creatures = new ArrayList<Creature>();
        passages = new ArrayList<Passage>();
        items = new ArrayList<Item>();
        System.out.println("Dungeon: Dungeon Constructor");
    }

    public void getDungeon(String name, int width, int gameHeight)
    {
        System.out.println("Dungeon:\n\t name: " + name + "\n\t width: " + width + "\n\t gameHeight: " + gameHeight);
        System.out.println("Dungeon: getDungeon");
    }

    public int getHeight() {
        return gameHeight;
    }
    public int getWidth() {
        return gameWidth;
    }

    public void addRoom(Room newRoom)
    {
        rooms.add(newRoom);
        System.out.println("Dungeon: addRoom");
    }

    public void addCreature(Creature newCreature)
    {
        creatures.add(newCreature);
        System.out.println("Dungeon: addCreature");
    }

    public void addPassage(Passage newPassage)
    {
        passages.add(newPassage);
        System.out.println("Dungeon: addPassage");
    }

    public void addItem(Item newItem)
    {
        items.add(newItem);
        System.out.println("Dungeon: addItem");
    }
}