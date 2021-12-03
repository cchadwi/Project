import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class DungeonXMLHandler extends DefaultHandler {
    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";

    private StringBuilder data = null;

    private Dungeon dungeon;

    private boolean bposX = false;
    private boolean bposY = false;
    private boolean btype = false;
    private boolean bhp = false;
    private boolean bmaxHit = false;
    private boolean bhpMoves = false;
    private boolean bactionMessage = false;
    private boolean bactionIntValue = false;
    private boolean bactionCharValue = false;
    private boolean bitemIntValue = false;
    private boolean bvisible = false;
    private boolean bwidth = false;
    private boolean bheight = false;


    Displayable parsedObject = null;
    Action parsedAction = null;
    Creature parsedCreature = null;
    Item parsedItem = null;

    public Dungeon getDungeon() {
        return dungeon;
    }

    public ArrayList<Room> getRooms()
    {
        return dungeon.rooms;
    }

    public DungeonXMLHandler(){}

    @Override

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DEBUG > 1)
        {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        if(qName.equalsIgnoreCase("Dungeon"))
        {
            String name = attributes.getValue("name");
            int width = Integer.parseInt(attributes.getValue("width"));
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));

            dungeon = new Dungeon(name, width, gameHeight);
        }

        else if (qName.equalsIgnoreCase("Room"))
        {
            String id = attributes.getValue("room");
            Room room = new Room(id);

            dungeon.addRoom(room);
            parsedObject = room;
        }

        else if (qName.equalsIgnoreCase("Passage"))
        {
            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage();
            passage.setID(room1, room2);
            dungeon.addPassage(passage);
            parsedObject = passage;
        }

        else if (qName.equalsIgnoreCase("Armor"))
        {
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name);

            armor.setID(roomNum, serial);
            dungeon.addItem(armor);
            parsedObject = armor;
        }
        else if (qName.equalsIgnoreCase("Sword"))
        {
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name);

            sword.setID(roomNum, serial);
            dungeon.addItem(sword);
            parsedObject = sword;
            parsedItem = sword;
        }

        else if (qName.equalsIgnoreCase("Scroll"))
        {
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name);

            scroll.setID(roomNum, serial);
            dungeon.addItem(scroll);
            parsedObject = scroll;
            parsedItem = scroll;
        }

        else if (qName.equalsIgnoreCase("Player"))
        {
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));

            Player player = new Player();
            player.setID(roomNum, serial);

            dungeon.addCreature(player);
            parsedCreature = player;
            parsedObject = player;
        }

        else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            CreatureAction action = new CreatureAction((Creature)parsedCreature);
            action.setName(name);
            action.setType(type);
            if(type.equals("hit"))
                parsedCreature.setHitAction(action);
            else {
                parsedCreature.setDeathAction(action);
            }
            parsedAction = action;
        }
        else if (qName.equalsIgnoreCase("ItemAction"))
        {

            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            ItemAction action = new ItemAction((Item)parsedObject);
            action.setName(name);
            action.setType(type);
            parsedItem.setItemAction(action);
            parsedAction = action;
        }

        else if (qName.equalsIgnoreCase("Monster"))
        {
            String name = attributes.getValue("name");
            int roomNum = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));

            Monster monster = new Monster();
            monster.setName(name);
            monster.setID(roomNum, serial);

            dungeon.addCreature(monster);
            parsedCreature = monster;
            parsedObject = monster;
        }

        else if (qName.equalsIgnoreCase("posX"))
        {
            bposX = true;
        }

        else if (qName.equalsIgnoreCase("posY"))
        {
            bposY = true;
        }

        else if (qName.equalsIgnoreCase("type"))
        {
            btype = true;
        }
        else if (qName.equalsIgnoreCase("hp"))
        {
            bhp = true;
        }

        else if (qName.equalsIgnoreCase("maxhit"))
        {
            bmaxHit = true;
        }

        else if (qName.equalsIgnoreCase("hpMoves"))
        {
            bhpMoves = true;
        }

        else if (qName.equalsIgnoreCase("actionIntValue"))
        {
            bactionIntValue = true;
        }

        else if (qName.equalsIgnoreCase("actionMessage"))
        {
            bactionMessage = true;
        }

        else if (qName.equalsIgnoreCase("actionCharValue"))
        {
            bactionCharValue = true;
        }

        else if (qName.equalsIgnoreCase("itemIntValue"))
        {
            bitemIntValue = true;
        }

        else if (qName.equalsIgnoreCase("visible"))
        {
            bvisible = true;
        }

        else if (qName.equalsIgnoreCase("width"))
        {
            bwidth = true;
        }

        else if (qName.equalsIgnoreCase("height"))
        {
            bheight = true;
        }

        else {
            System.out.println("Invalid");
        }
        data = new StringBuilder();
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Displayable object = parsedObject;
        Action action = parsedAction;
        if (bposX) {
            object.setPosX(Integer.parseInt(data.toString()));
            bposX = false;
        } else if (bposY) {
            object.setPosY(Integer.parseInt(data.toString()));
            bposY = false;
        } else if (btype) {
            object.setType(data.charAt(0));
            btype = false;
        } else if (bhp) {
            object.setHp(Integer.parseInt(data.toString()));
            bhp = false;
        } else if (bhpMoves) {
            object.setHpMove(Integer.parseInt(data.toString()));
            bhpMoves = false;
        } else if (bmaxHit) {
            object = (Creature) object;
            object.setMaxHit(Integer.parseInt(data.toString()));
            bmaxHit = false;
        } else if (bactionMessage) {
            action.setMessage(data.toString());
            bactionMessage = false;
        } else if (bvisible) {
            bvisible = false;
        } else if (bwidth) {
            object.setWidth(Integer.parseInt(data.toString()));
            bwidth = false;
        } else if (bheight) {
            object.setHeight(Integer.parseInt(data.toString()));
            bheight = false;
        } else if (bactionIntValue) {
            action.setIntValue(Integer.parseInt(data.toString()));
            bactionIntValue = false;
        } else if (bitemIntValue) {
            object = (Item) object;
            object.setIntValue(Integer.parseInt(data.toString()));
            bitemIntValue = false;
        } else if (bactionCharValue) {
            action.setCharValue(data.charAt(0));
            bactionCharValue = false;
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }
}