import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import asciiPanel.AsciiPanel;
import org.xml.sax.SAXException;

public class Rogue implements Runnable{

    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private int game_height;
    private int game_width;
    private int bottom_height = 0;
    private int top_height = 0;
    private int display_height;
    public Dungeon d;
    public int HP;
    public Player player;

    Rogue(Dungeon _d)
    {
        d = _d;
        game_height = d.getHeight();
        game_width = d.getWidth();
        System.out.println("Created dungeon of height " + game_height + " and width " + game_width);
        display_height = game_height + top_height + bottom_height;
        displayGrid = new ObjectDisplayGrid(game_width, display_height);
    }

    private void drawCreatures(Dungeon d)
    {
        for (int i = 0; i < d.creatures.size(); i++)
        {
            Creature c = d.creatures.get(i);
            System.out.println(c.room - 1);
            Room r = d.rooms.get(c.room - 1);
            char type = c.type;
            int x = c.posX.get(0) + r.posX.get(0) + 1;
            int y = c.posY.get(0) + r.posY.get(0) + 2;
            if(c.ha != null)
                System.out.println("ha not null");
            else
                System.out.println("ha null");
            if (type == '@')
            {
                player = (Player) c;
                player.posX.set(0, x);
                player.posY.set(0, y);
                displayGrid.addObjectToDisplay(new Char(type), x, y, AsciiPanel.brightCyan);
                HP = player.hp;
            }
            else {
                displayGrid.addObjectToDisplay(new Char(type), x, y, AsciiPanel.green);
                d.creature_map.put(x + ", " + y, c);
                System.out.println(c.type + ", " + x + ", " + y);
            }
            displayGrid.addStringToDisplay(Integer.toString(HP), 4, 0);
        }
    }
    private void drawRooms(Dungeon d)
    {
        for (int i = 0; i < d.rooms.size(); i++)
        {
            Room r = d.rooms.get(i);
            int x = r.posX.get(0) + 1;
            int y = r.posY.get(0) + 2;
            int h = r.height;
            int w = r.width;
            for (int j = x; j < x + w; j++)
            {
                displayGrid.addObjectToDisplay(new Char('x'), j, y + top_height, AsciiPanel.brightYellow);
                displayGrid.addObjectToDisplay(new Char('x'), j, y + h + top_height - 1, AsciiPanel.brightYellow);
            }
            for (int j = y; j < y + h; j++)
            {
                displayGrid.addObjectToDisplay(new Char('x'), x, j + top_height, AsciiPanel.brightYellow);
                displayGrid.addObjectToDisplay(new Char('x'), x + w - 1, j + top_height, AsciiPanel.brightYellow);
                if (j > y && j < y + h - 1)
                {
                    for (int k = x + 1; k <= x + w - 2; k++)
                    {
                        displayGrid.addObjectToDisplay(new Char('.'), k, j + top_height);
                        int[] arr = {k, j + top_height};
                        d.validPoint.add(arr);
                    }
                }
            }
        }
    }

    private void drawItems(Dungeon d)
    {
        for (int i = 0; i < d.items.size(); i++)
        {
            Item item = d.items.get(i);
            Room r = d.rooms.get(item.room - 1);
            char type = item.type;
            int x = item.posX.get(0) + r.posX.get(0) + 1;
            int y = item.posY.get(0) + r.posY.get(0) + 2;

            Stack<Item> itemStack = new Stack<>();
            itemStack.push(item);
            d.item_map.put(x + ", " + y, itemStack);


            displayGrid.addObjectToDisplay(new Char(type), x, y);
        }
    }

    private void drawPassages(Dungeon d)
    {
        for (int i = 0; i < d.passages.size(); i++)
        {
            Passage p = d.passages.get(i);
            System.out.println(p.posX.toString());
            for(int q = 0; q < p.posX.size() - 1; q++)
            {
                int x1 = p.posX.get(q) + 1;
                int y1 = p.posY.get(q) + 2;
                int x2 = p.posX.get(q + 1) + 1;
                int y2 = p.posY.get(q + 1) + 2;
                if(y1 == y2)
                {
                    for(int w = x1; w < x2; w++) {
                        displayGrid.addObjectToDisplay(new Char('#'), w, y2, AsciiPanel.brightBlack);
                        int [] arr = {w, y2};
                        d.validPoint.add(arr);
                    }
                    for(int w = x2; w < x1; w++) {
                        displayGrid.addObjectToDisplay(new Char('#'), w, y2, AsciiPanel.brightBlack);
                        int [] arr = {w, y2};
                        d.validPoint.add(arr);
                    }
                }
                else if(x1 == x2)
                {
                    for (int w = y1; w < y2; w++) {
                        displayGrid.addObjectToDisplay(new Char('#'), x2, w, AsciiPanel.brightBlack);
                        int [] arr = {x2, w};
                        d.validPoint.add(arr);
                    }
                    for (int w = y2; w < y1; w++) {
                        displayGrid.addObjectToDisplay(new Char('#'), x2, w + 1, AsciiPanel.brightBlack);
                        int [] arr = {x2, w};
                        d.validPoint.add(arr);
                    }
                }
            }
            int x = p.posX.get(0) + 1;
            int y = p.posY.get(0) + 2;
            displayGrid.addObjectToDisplay(new Char('+'), x, y, AsciiPanel.red);
            int [] arr = {x, y};
            d.validPoint.add(arr);
            x = p.posX.get(p.posX.size() - 1) + 1;
            y = p.posY.get(p.posY.size() - 1) + 2;
            displayGrid.addObjectToDisplay(new Char('+'), x, y, AsciiPanel.red);
            int [] arr1 = {x, y};
            d.validPoint.add(arr1);
        }
    }
    public void run() {
        displayGrid.initializeDisplay();

        displayGrid.addStringToDisplay("HP: ", 1, 0);
        displayGrid.addStringToDisplay("Score:", 10, 0);
        displayGrid.addStringToDisplay("Pack:", 1, game_height - 6);
        displayGrid.addStringToDisplay("Info:", 1, game_height - 4);

        drawRooms(d);
        drawCreatures(d);
        drawItems(d);
        drawPassages(d);
        KeyStrokePrinter keystroke = new KeyStrokePrinter(displayGrid, player, d);
        displayGrid.updateHp(player.hp);
        displayGrid.registerInputObserver(keystroke);
    }
    public static void main(String[] args) {
        String fileName = null;
        switch (args.length) {
            case 1:
                fileName = "game/xmlFiles/" + args[0];
                break;
            default:
                System.out.println("java Test <xmlfilename>");
                return;
        }
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DungeonXMLHandler handler = new DungeonXMLHandler();
            saxParser.parse(new File(fileName), handler);
            ArrayList<Room> rooms = handler.getRooms();
            Dungeon dungeon = handler.getDungeon();

            for (Room room : rooms) {
                System.out.println(room);
            }
            Rogue Runner = new Rogue(dungeon);
            Runner.run();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}