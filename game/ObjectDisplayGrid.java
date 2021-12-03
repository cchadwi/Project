import asciiPanel.AsciiPanel;
import asciiPanel.AsciiFont;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {
    int DEBUG = 0;
    String CLASSID = ".ObjectDisplayGrid";

    AsciiPanel terminal;
    Char[][] objectGrid;
    public Char[][] objectGridTemp;
    ArrayList<InputObserver> inputObservers;

    int gameHeight;
    int width;
    int topHeight;
    int height;
    String previousString;
    String previousItem;

    public ObjectDisplayGrid(int _width, int _height) {
        width = _width;
        height = _height;
        previousString = "";
        previousItem = "";
        terminal = new AsciiPanel(width, height);

        objectGrid = new Char[width][height];

        initializeDisplay();

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        super.setVisible(true);
        terminal.setVisible(true);

        super.addKeyListener(this);
        inputObservers = new ArrayList<>();

        super.repaint();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0)
        {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    public void deregisterInputObserver() {
        inputObservers.clear();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        Char ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addObjectToDisplay(ch, i, j);
            }
        }
        terminal.repaint();
    }

    public void fireUp() {
        if (terminal.requestFocusInWindow()) {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded");
        } else {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED");
        }
    }

    public void addObjectToDisplay(Char ch, int x, int y, Color color) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = ch;
                writeToTerminal(x, y, color);
            }
        }
    }
    public void addObjectToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = ch;
                writeToTerminal(x, y, null);
            }
        }
    }
    public void messageToString(String s) {
        removeStringToDisplay(previousString, 10, height - 4);
        addStringToDisplay(s, 10, height - 4);
        previousString = s;
    }
    public void itemToString(String s) {
        removeStringToDisplay(previousItem, 10, height - 6);
        addStringToDisplay(s, 10, height - 6);
        previousItem = s;
    }
    public void clearItems(){
        removeStringToDisplay(previousItem, 10, height - 6);
    }

    public void clearMessage(){
        removeStringToDisplay(previousString, 10, height - 4);
    }
    public void addStringToDisplay(String s, int x, int y) {
        for (int i = 0; i < s.length(); i++)
        {
            addObjectToDisplay(new Char(s.charAt(i)), x + i, y);
        }
    }
    public void removeStringToDisplay(String s, int x, int y) {
        for (int i = 0; i < s.length(); i++)
        {
            addObjectToDisplay(new Char(' '), x + i, y);
        }
    }
    public void removeObjectFromDisplay(int x, int y, Color color) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = new Char(' ');
                writeToTerminal(x, y, color);
            }
        }
    }

    private void writeToTerminal(int x, int y, Color color) {
        char ch = objectGrid[x][y].getChar();
        if(color == null)
            terminal.write(ch, x, y);
        else
            terminal.write(ch, x, y, color);
        terminal.repaint();
    }

    public void updateHp(int hp){
        addStringToDisplay("HP: "+ String.format("%03d", hp), 1, 0);
    }

    public void getObjectDisplayGrid(int _gameHeight, int _width, int _topHeight)
    {
        System.out.println("ObjectDisplayGrid:\n\t gameHeight: " + _gameHeight + "\n\t width: " + _width + "\n\t topHeight: " + _topHeight);
        System.out.println("ObjectDisplayGrid: getTopMessageHeight");
    }

    public void setTopMessageHeight(int _topHeight)
    {
        topHeight = _topHeight;
        System.out.println("ObjectDisplayGrid: setTopMessageHeight");
    }
}