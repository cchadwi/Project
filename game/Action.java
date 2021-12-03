public class Action
{
    String message;
    int intValue;
    char charValue;

    public void setMessage(String _message)
    {
         message = _message;
         System.out.println("Action: setMessage");
    }

    public void setIntValue(int _intValue)
    {
        intValue = _intValue;
        System.out.println("Action: setIntValue");
    }

    public void setCharValue(char _charValue)
    {
        charValue = _charValue;
        System.out.println("Action: setCharValue");
    }
}