import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

public class DataMemory extends TableWorks
{
    private ArrayList<DataAddress> memData;
    private Table table;
    
    public static final String [] COLUMNS;

    static
    {
        COLUMNS = new String[]
        {
            "Address",
            "Value"
        };
    }

    public DataMemory()
    {
        memData = readDataFile();

        GridBagConstraints gbc = setLayout();

        Object [][] data = new Object[memData.size()][1];

        for(int i = 0; i < data.length; i++)
        {
            Object[] obj = {memData.get(i).getAddress(), memData.get(i).getValue()};

            data[i] = obj;
        }

        table = new Table(data, COLUMNS);
        table.setHeaderColor(Color.BLUE);
        table.setAlternateColor(FIRST, SECOND);

        setBackground(Color.YELLOW);

        add(table.getScrollPane(), gbc);
    }

    private ArrayList<DataAddress> readDataFile()
    {
        ReadFile file = new ReadFile("Please enter name of data file : ");

        ArrayList<DataAddress> mem = new ArrayList<>();

        while(file.ready())
        {
            mem.add(new DataAddress(file.readLine()));
        }

        file.close();

        return mem;
    }

    public void updateInstruction(long address)
    {
        int row = (int)(address - DataAddress.START);

        if(row < 0 || row >= memData.size())
            throw new IndexOutOfBoundsException("Index of out bounds for length " + memData.size() + ". Given : " + row + ".");
        
        table.highlight(FIRST, SECOND, Color.ORANGE, row);
    }

    public void reset()
    {
        table.reset(FIRST, SECOND);
    }
}

class DataAddress
{
    public static final long START = 268500992L;
    private static long count = START;

    private long address;
    private String value;
    private int [] bytes;

    public DataAddress(String hex)
    {
        address = count;
        value = hex;
        bytes = new int[4];

        for(int i = hex.length() - 1, j = 0; i >= 1; i -= 2, j++)
        {
            bytes[j] = Integer.parseInt(hex.substring(i - 1, i + 1), 16);
        }

        count += 4;
    }

    public String getAddress()
    {
        return "0x" + Long.toHexString(address).toUpperCase();
    }

    public String getValue()
    {
        return "0x" + value.toUpperCase();
    }
}