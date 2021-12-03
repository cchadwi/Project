import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import asciiPanel.AsciiPanel;

public class KeyStrokePrinter implements InputObserver{

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    public Player player;
    public char temp = '.';
    private static HashMap<Character, Color> hash_map = new HashMap<Character, Color>();
    private static HashMap<Character, String> type_to_string = new HashMap<Character, String>();
    public ArrayList<Item> pack;
    public ArrayList <Character> command_que;
    private ArrayList <Character> charSet = new ArrayList<>();
    public int x;
    public Dungeon d;
    public int y;
    public boolean dead = false;
    public int count = 0;
    public int h_count = 0;


    public KeyStrokePrinter(ObjectDisplayGrid grid, Player _player, Dungeon dungeon) {
        hash_map.put('x', AsciiPanel.brightYellow);
        hash_map.put('#', AsciiPanel.brightBlack);
        hash_map.put('+', AsciiPanel.red);
        hash_map.put('.', AsciiPanel.white);
        hash_map.put('@', AsciiPanel.brightCyan);
        hash_map.put('S', AsciiPanel.green);
        hash_map.put('H', AsciiPanel.green);
        hash_map.put('T', AsciiPanel.green);
        type_to_string.put('S', "Snake");
        type_to_string.put('H', "HobGoblin");
        type_to_string.put('T', "Troll");
        type_to_string.put(']', "Armor");
        type_to_string.put(')', "Sword");
        type_to_string.put('?', "Scroll");

        displayGrid = grid;
        player = _player;
        d = dungeon;
        int pos [] = {(int) player.getPoint().get(0).get(0), (int) player.getPoint().get(1).get(0)};
        x = (int) player.getPoint().get(0).get(0);
        y = (int) player.getPoint().get(1).get(0);
        pack = new ArrayList<>();
        command_que = new ArrayList<Character>();
        charSet.add('.');
        charSet.add('#');
        charSet.add('T');
        charSet.add('S');
        charSet.add('H');
        charSet.add('+');
    }

    @Override
    public boolean observerUpdate(char ch) {
        if (dead)
            return false;
        if (ch == 'X') {
            System.out.println("got an X, ending input checking");
            return false;
        }
        else if(ch == 'h') {
            command_que.clear();
            if (displayGrid.objectGrid[x - 1][y].getChar() == 'x' || displayGrid.objectGrid[x - 1][y].getChar() == ' ') {
                displayGrid.messageToString("Invalid Movement");
                System.out.println("invalid movement");
                return false;
            }
            else if (displayGrid.objectGrid[x - 1][y].getChar() == 'S' || displayGrid.objectGrid[x - 1][y].getChar() == 'H' || displayGrid.objectGrid[x - 1][y].getChar() == 'T' ) {
                combat_simulation(x - 1, y);
                if(d.creature_map.get((x - 1) + ", " + y) != null && d.creature_map.get((x - 1) + ", " + y).ha != null && d.creature_map.get((x - 1) + ", " + y).ha.name.equals("Teleport")) {
                    int[] res = teleport(x-1, y);
                    Creature Temp_Creature = d.creature_map.get((x-1) + ", " + y);
                    d.creature_map.put(res[0] + ", " + res[1], Temp_Creature);
                    d.creature_map.remove((x-1) + ", " + y);
                    displayGrid.addObjectToDisplay(new Char(displayGrid.objectGrid[x - 1][y].getChar()), res[0], res[1], hash_map.get(displayGrid.objectGrid[x - 1][y].getChar()));
                    displayGrid.addObjectToDisplay(new Char('.'), x - 1, y);
                }
            }
            else {
                move('L');
            }
        }
        if (h_count > 0)
        {
            if (ch == 'l')
            {
                command_que.clear();
                if (displayGrid.objectGrid[x + 1][y].getChar() == 'x' || displayGrid.objectGrid[x + 1][y].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                move('R');
            }
            else if (ch == 'h')
            {
                command_que.clear();
                if (displayGrid.objectGrid[x - 1][y].getChar() == 'x' || displayGrid.objectGrid[x - 1][y].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                move('L');
            }
            else if (ch == 'j')
            {
                command_que.clear();
                if (displayGrid.objectGrid[x][y + 1].getChar() == 'x' || displayGrid.objectGrid[x][y + 1].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                move('D');
            }
            else if (ch == 'k')
            {
                command_que.clear();
                if (displayGrid.objectGrid[x][y - 1].getChar() == 'x' || displayGrid.objectGrid[x][y - 1].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                move('U');
            }
        }
        else {
            if(ch == 'l'){
                command_que.clear();
                if (displayGrid.objectGrid[x + 1][y].getChar() == 'x' || displayGrid.objectGrid[x + 1][y].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                else if (displayGrid.objectGrid[x + 1][y].getChar() == 'S' || displayGrid.objectGrid[x + 1][y].getChar() == 'H' || displayGrid.objectGrid[x + 1][y].getChar() == 'T' ) {
                    combat_simulation(x+1, y);
                    if(d.creature_map.get((x + 1) + ", " + y) != null && d.creature_map.get((x + 1) + ", " + y).ha != null && d.creature_map.get((x + 1) + ", " + y).ha.name.equals("Teleport")) {
                        int[] res = teleport(x+1, y);
                        Creature Temp_Creature = d.creature_map.get((x+1) + ", " + y);
                        d.creature_map.put(res[0] + ", " + res[1], Temp_Creature);
                        d.creature_map.remove((x+1) + ", " + y);
                        displayGrid.addObjectToDisplay(new Char(displayGrid.objectGrid[x + 1][y].getChar()), res[0], res[1], hash_map.get(displayGrid.objectGrid[x + 1][y].getChar()));
                        displayGrid.addObjectToDisplay(new Char('.'), x + 1, y);
                    }
                }
                else {
                    move('R');
                }
            }
            else if(ch == 'j'){
                command_que.clear();
                if (displayGrid.objectGrid[x][y + 1].getChar() == 'x' || displayGrid.objectGrid[x][y + 1].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                else if (displayGrid.objectGrid[x][y + 1].getChar() == 'S' || displayGrid.objectGrid[x][y + 1].getChar() == 'H' || displayGrid.objectGrid[x][y + 1].getChar() == 'T' ) {
                    combat_simulation(x, y+1);
                    if(d.creature_map.get(x + ", " + (y+1)) != null && d.creature_map.get(x + ", " + (y+1)).ha != null && d.creature_map.get(x + ", " + (y+1)).ha.name.equals("Teleport")) {
                        int[] res = teleport(x, (y + 1));
                        Creature Temp_Creature = d.creature_map.get(x + ", " + (y+1));
                        d.creature_map.put(res[0] + ", " + res[1], Temp_Creature);
                        d.creature_map.remove(x + ", " + (y+1));
                        displayGrid.addObjectToDisplay(new Char(displayGrid.objectGrid[x][y + 1].getChar()), res[0], res[1], hash_map.get(displayGrid.objectGrid[x][y + 1].getChar()));
                        displayGrid.addObjectToDisplay(new Char('.'), x, y+1);
                    }
                }
                else {
                    move('D');
                }
            }
            else if(ch == 'k'){
                command_que.clear();
                if (displayGrid.objectGrid[x][y - 1].getChar() == 'x' || displayGrid.objectGrid[x][y - 1].getChar() == ' ') {
                    displayGrid.messageToString("Invalid Movement");
                    System.out.println("invalid movement");
                    return false;
                }
                else if (displayGrid.objectGrid[x][y - 1].getChar() == 'S' || displayGrid.objectGrid[x][y - 1].getChar() == 'H' || displayGrid.objectGrid[x][y - 1].getChar() == 'T' ) {
                    combat_simulation(x, y-1);
                    if(d.creature_map.get(x + ", " + (y-1)) != null && d.creature_map.get(x + ", " + (y-1)).ha != null && d.creature_map.get(x + ", " + (y-1)).ha.name.equals("Teleport")) {
                        int[] res = teleport(x, (y - 1));
                        Creature Temp_Creature = d.creature_map.get(x + ", " + (y-1));
                        d.creature_map.put(res[0] + ", " + res[1], Temp_Creature);
                        d.creature_map.remove(x + ", " + (y-1));
                        displayGrid.addObjectToDisplay(new Char(displayGrid.objectGrid[x][y - 1].getChar()), res[0], res[1], hash_map.get(displayGrid.objectGrid[x][y - 1].getChar()));
                        displayGrid.addObjectToDisplay(new Char('.'), x, y-1);
                    }
                }
                else {
                    move('U');
                }
            }
            else if (ch == 'i')
            {
                command_que.clear();
                displayGrid.clearItems();
                String str = "";
                for(int i = 1; i < pack.size() + 1; i++)
                {
                    if(pack.get(i-1).type == ')') {
                        str = str + ", " + i + ". " + ((Sword) pack.get(i - 1)).name;
                    }
                    else if(pack.get(i-1).type == ']') {
                        str = str + ", " + i + ". " + ((Armor) pack.get(i - 1)).name;
                    }
                    else if(pack.get(i-1).type == '?') {
                        str = str + ", " + i + ". " + ((Scroll) pack.get(i - 1)).name;
                    }
                }
                if(pack.size() == 0) {
                    displayGrid.itemToString("Pack Empty");
                }
                else{
                    System.out.println(str.substring(2));
                    displayGrid.itemToString(str.substring(2));
                }
            }
            else if (ch == 'p')
            {
                command_que.clear();
                if (temp == '?')
                {
                    pickItem();
                    System.out.println("added scroll to pack stack");
                    if (pack.get(pack.size() - 1).name.equals("Hallucinate")){
                        displayGrid.messageToString("You have picked up a scroll of hallucination!");
                    }
                    if (pack.get(pack.size() - 1).name.equals("BlessArmor")) {
                        displayGrid.messageToString("You have picked up a scroll of weakened armor");
                    }
                    if (pack.get(pack.size() - 1).name.equals("BlessSword")) {
                        displayGrid.messageToString("You have picked up a scroll of weakened sword");
                    }
                }
                else if (temp == ']')
                {
                    pickItem();
                    System.out.println("added armor to pack stack");
                    displayGrid.messageToString("Found armor!");
                }
                else if (temp == ')')
                {
                    pickItem();
                    System.out.println("added sword to pack stack");
                    displayGrid.messageToString("Found sword!");
                }
                else {
                    System.out.println("No item to pick up here");
                    displayGrid.messageToString("No item to pick up here");
                }
            }
            else if (ch == '?')
            {
                command_que.clear();
                displayGrid.messageToString("h,l,k,j,i,?,H,c,d,p,r,t,w,e,0-9. H<cmd> for more info");
            }
            else if (ch == 'c')
            {
                if(player.armor == null) {
                    displayGrid.messageToString("No Armor equipped!");
                }
                else {
                    displayGrid.messageToString("Armor has been unequipped!");
                    ((Armor) player.armor).name = ((Armor) player.armor).name.substring(2);
                    player.armor = null;
                }
            }
            else {
                if (command_que.size() < 1) {
                    command_que.add(ch);
                }
                else {
                    command_que.add(ch);
                    if (command_que.size() > 2) {
                        command_que.remove(0);
                    }
                    String command = String.valueOf(command_que.get(0)) + String.valueOf(command_que.get(1));
                    if (command.toLowerCase().equals("ey")) {
                        System.exit(0);
                    }
                    else if (command_que.get(0) == 'd' && Character.isDigit(command_que.get(1))) {
                        if (pack.size() == 0) {
                            displayGrid.messageToString("Pack is empty, cannot drop anything");
                        }
                        else if (Character.getNumericValue(command_que.get(1)) > 0 && (pack.size() >= Character.getNumericValue(command_que.get(1)))) {
                            temp = dropItem(Character.getNumericValue(command_que.get(1)) - 1);
                        }
                        else {
                            displayGrid.messageToString("Item no." + command_que.get(1) + " not available to drop");
                        }
                    } else if (command_que.get(0) == 'w' && Character.isDigit(command_que.get(1))) {
                        if (pack.size() == 0) {
                            displayGrid.messageToString("Pack is empty, cannot equip any armor");
                        }
                        else if (Character.getNumericValue(command_que.get(1)) > 0
                                && (pack.size() >= Character.getNumericValue(command_que.get(1)))
                                && pack.get(Character.getNumericValue(command_que.get(1)) - 1).type == ']') {
                            if (player.armor != null) {
                                ((Armor) player.armor).name = ((Armor) player.armor).name.substring(2);
                            }
                            ((Armor) pack.get(Character.getNumericValue(command_que.get(1)) - 1)).name = "a " + ((Armor) pack.get(Character.getNumericValue(command_que.get(1)) - 1)).name;
                            player.setArmor(pack.get(Character.getNumericValue(command_que.get(1)) - 1));

                            displayGrid.messageToString("Armor equipped!");
                        }
                        else {
                            displayGrid.messageToString("Item no." + command_que.get(1) + " not available to equip");
                        }
                    }
                    else if (command_que.get(0) == 't' && Character.isDigit(command_que.get(1))) {
                        if (pack.size() == 0) {
                            displayGrid.messageToString("Pack is empty, cannot equip any weapon");
                        }
                        else if (Character.getNumericValue(command_que.get(1)) > 0
                                && (pack.size() >= Character.getNumericValue(command_que.get(1)))
                                && pack.get(Character.getNumericValue(command_que.get(1)) - 1).type == ')') {
                            if (player.sword != null) {
                                ((Sword) player.sword).name = ((Sword) player.sword).name.substring(2);
                            }
                            ((Sword) pack.get(Character.getNumericValue(command_que.get(1)) - 1)).name = "w " +
                                    ((Sword) pack.get(Character.getNumericValue(command_que.get(1)) - 1)).name;
                            player.setWeapon(pack.get(Character.getNumericValue(command_que.get(1)) - 1));
                            displayGrid.messageToString("Weapon equipped!");
                        }
                        else {
                            displayGrid.messageToString("Item no." + command_que.get(1) + " not available to equip");
                        }
                    } else if (command_que.get(0) == 'r' && Character.isDigit(command_que.get(1))) {
                        if (pack.size() == 0) {
                            displayGrid.messageToString("Pack is empty, cannot equip any weapon");
                        }
                        else if (Character.getNumericValue(command_que.get(1)) > 0
                                && (pack.size() >= Character.getNumericValue(command_que.get(1)))
                                && pack.get(Character.getNumericValue(command_que.get(1)) - 1).type == '?') {
                            String str = pack.get(Character.getNumericValue(command_que.get(1)) - 1).preformItemActions(this);
                            if (str != null) {
                                pack.remove(Character.getNumericValue(command_que.get(1) - 1));
                                displayGrid.messageToString(str);
                            }
                            else {
                                System.out.println("scroll not used");
                            }
                        }
                    }
                    else if (command_que.get(0) == 'H') {
                        if (command_que.get(1) == 'H') {
                            displayGrid.messageToString("USAGE: H [char] - description of command [char]");
                        }
                        else if (command_que.get(1) == 'l') {
                            displayGrid.messageToString("USAGE: l - move right");
                        }
                        else if (command_que.get(1) == 'h') {
                            displayGrid.messageToString("USAGE: h - move left");
                        }
                        else if (command_que.get(1) == 'k') {
                            displayGrid.messageToString("USAGE: k - move up");
                        }
                        else if (command_que.get(1) == 'j') {
                            displayGrid.messageToString("USAGE: j - move right");
                        }
                        else if (command_que.get(1) == 'i') {
                            displayGrid.messageToString("USAGE: i - show inventory");
                        }
                        else if (command_que.get(1) == '?') {
                            displayGrid.messageToString("USAGE: ? - list of commands");
                        }
                        else if (command_que.get(1) == 'c') {
                            displayGrid.messageToString("USAGE: c [index] - take off armor at [index]");
                        }
                        else if (command_que.get(1) == 'd') {
                            displayGrid.messageToString("USAGE: d [index] - drop item at [index]");
                        }
                        else if (command_que.get(1) == 'p') {
                            displayGrid.messageToString("USAGE: p - pick up item");
                        }
                        else if (command_que.get(1) == 'r') {
                            displayGrid.messageToString("USAGE: r [index] - read scroll at [index]");
                        }
                        else if (command_que.get(1) == 't') {
                            displayGrid.messageToString("USAGE: t [index] - equip weapon at [index]");
                        }
                        else if (command_que.get(1) == 'l') {
                            displayGrid.messageToString("USAGE: w [index] - equip armor at [index]");
                        }
                        else if (command_que.get(1) == 'e') {
                            displayGrid.messageToString("USAGE: e + [y or Y] - end game");
                        }
                        else if (Character.isDigit(command_que.get(1))) {
                            displayGrid.messageToString("USAGE: 0 - 9 - Index for c, d, r, t, w");
                        }
                    }
                }
            }
        }
        if(player.hp <= 0)
        {
            displayGrid.addObjectToDisplay(new Char('+'), player.posX.get(0), player.posY.get(0), AsciiPanel.brightRed);
            displayGrid.messageToString("**** YOU DIED ****");
            dead = true;
        }
        return true;

    }

   public void combat_simulation(int monster_x, int monster_y)
   {
       int [] result = damageCalc(d.creature_map.get(monster_x + ", " + monster_y).maxHit, player.maxHit, d.creature_map.get(monster_x + ", " + monster_y).hp, player.hp);
       if(player.sword != null) {
           result[1] = result[1] - Integer.parseInt(((Sword) player.sword).name.substring(2).split(" ", 2)[0].substring(1));
           result[3] = result[3] + Integer.parseInt(((Sword) player.sword).name.substring(2).split(" ", 2)[0].substring(1));
       }
       if(player.armor != null) {
           if((Integer.parseInt(((Armor) player.armor).name.substring(2).split(" ", 2)[0].substring(1)) - result[2]) >= 0) {
               result[0] = player.hp;
               result[2] = 0;
           }
           else {
               result[0] = player.hp - (result[2] - (Integer.parseInt(((Armor) player.armor).name.substring(2).split(" ", 2)[0].substring(1))));
               result[2] = (result[2] - (Integer.parseInt(((Armor) player.armor).name.substring(2).split(" ", 2)[0].substring(1))));
           }

       }
       if(result[1] < 0)
       {
           displayGrid.messageToString("You have defeated the " + type_to_string.get(d.creature_map.get(monster_x + ", " + monster_y).type));
           d.creature_map.remove(monster_x + ", " + monster_y);
           displayGrid.addObjectToDisplay(new Char('.'), monster_x, monster_y);
       }
       else {
           displayGrid.messageToString("Monster Took " + result[3] + " Damage! Player Took " + result[2] + " Damage!");
           d.creature_map.get(monster_x + ", " + monster_y).hp = result[1];
       }
       player.hp = result[0];
       displayGrid.updateHp(player.hp);

   }

    int [] damageCalc(int Monster_MaxHit, int Player_MaxHit, int Monster_Hp, int Player_Hp)
    {
        int Monster_Attack = new Random().nextInt(Monster_MaxHit + 1);
        int Player_Attack = new Random().nextInt(Player_MaxHit + 1);
        return new int[] {Player_Hp - Monster_Attack, Monster_Hp - Player_Attack, Monster_Attack, Player_Attack};
    }

   private char dropItem(int index)
   {
       Item item = pack.get(index);
       if(item.type == ']' && ((Armor) item).name.contains("a ")) {
           ((Armor) item).name = ((Armor) item).name.substring(2);
       }
       else if(item.type == ')' && ((Sword) item).name.contains("w ")) {
           ((Sword) item).name = ((Sword) item).name.substring(2);
       }

       player.armor = null;
       String coords = x + ", " + y;
       if (!d.item_map.containsKey(coords))
       {
           item.temp = temp;
           Stack<Item> itemStack = new Stack<>();
           itemStack.push(item);
           d.item_map.put(x + ", " + y, itemStack);
       }
       else {
           d.item_map.get(coords).push(item);
       }
       pack.remove(index);
       return d.item_map.get(coords).peek().type;
   }

    private void move(char dir)
    {
        count++;
        if (h_count > 0)
        {
            hallucinator();
            h_count--;
            if (h_count == 0){
                System.out.println("assasa");
            }
            restoreDisplay();
            displayGrid.objectGrid = displayGrid.objectGridTemp;
        }
        if(count == player.hpMoves)
        {
            count = 0;
            player.hp++;
            displayGrid.updateHp(player.hp+1);
        }
        displayGrid.clearMessage();
        displayGrid.addObjectToDisplay(new Char(temp), x, y, hash_map.get(temp));
        if (dir == 'U')
            y -= 1;
        else if (dir == 'D')
            y += 1;
        else if (dir == 'L')
            x -= 1;
        else if (dir == 'R')
            x += 1;
        player.posX.set(0, x);
        player.posY.set(0, y);
        temp = displayGrid.objectGrid[x][y].getChar();
        displayGrid.addObjectToDisplay(new Char('@'), x, y, hash_map.get('@'));
    }

   private void pickItem()
   {
       String coords = x + ", " + y;
       Stack <Item> dropStack = d.item_map.get(coords);
       pack.add(dropStack.pop());
       if (dropStack.size() == 0)
       {
           temp = pack.get(pack.size() - 1).temp;
       }
       else {
           temp = dropStack.peek().type;
       }
       return;
   }

    public int[] teleport(int monster_x, int monster_y)
    {
        while(true)
        {
            int rand = new Random().nextInt(d.validPoint.size());
            int[] randCoords = d.validPoint.get(rand);
            if (randCoords[0] == player.posX.get(0) && randCoords[1] == player.posY.get(0)) ;
            else if (randCoords[0] == monster_x && randCoords[1] == monster_y) ;
            else
                return randCoords;
        }
    }

    public String hallucinate(int _h_count)
    {
        String str = "You will hallucinate for " + h_count + " moves...";
        return str;
    }
    public void hallucinator()
    {
        displayGrid.objectGridTemp = displayGrid.objectGrid.clone();

        for (int i = 0; i < d.validPoint.size(); i++)
        {
            int rand = new Random().nextInt(charSet.size());
            displayGrid.addObjectToDisplay(new Char(charSet.get(rand)), d.validPoint.get(i)[0], d.validPoint.get(i)[1], hash_map.get(charSet.get(rand)));
        }

    }
    public void restoreDisplay()
    {
        for (int i = 0; i < displayGrid.objectGridTemp.length; i++)
        {
            for (int j = 0; j < displayGrid.objectGridTemp[i].length; j++)
            {
                char c = displayGrid.objectGridTemp[i][j].getChar();
                System.out.println(c);
                if (charSet.contains(c))
                {
                    System.out.println("assa");
                    displayGrid.addObjectToDisplay(new Char(c), i, j, hash_map.get(c));
                }
            }
        }
    }

}
