import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

public class InstructionMemory extends TableWorks
{
    private ArrayList<MemAddress> instructions;
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

    public InstructionMemory()
    {
        instructions = readInstructionFile();

        GridBagConstraints gbc = setLayout();

        Object [][] data = new Object[instructions.size()][1];

        for(int i = 0; i < data.length; i++)
        {
            Object[] obj = {instructions.get(i).getAddress(), instructions.get(i).getValue()};

            data[i] = obj;
        }

        table = new Table(data, COLUMNS);
        table.setHeaderColor(Color.BLUE);
        table.setAlternateColor(FIRST, SECOND);

        setBackground(Color.YELLOW);

        add(table.getScrollPane(), gbc);
    } 

    private ArrayList<MemAddress> readInstructionFile()
    {
        ReadFile file = new ReadFile("Please enter name of instruction file : ");

        ArrayList<MemAddress> mem = new ArrayList<>();

        while(file.ready())
        {
            mem.add(new MemAddress(file.readLine()));
        }

        file.close();

        return mem;
    }

    public void updateInstruction(long address)
    {
        int row = (int)(address - MemAddress.START);

        if(row < 0 || row >= instructions.size())
            throw new IndexOutOfBoundsException("Index of out bounds for length " + instructions.size() + ". Given : " + row + ".");
        
        table.highlight(FIRST, SECOND, Color.ORANGE, row);
    }

    public void reset()
    {
        table.reset(FIRST, SECOND);
    }
}

class MemAddress extends Memory
{
    public static final long START = 4194304L;

    private static long count = START;

    private long address;

    public MemAddress(String value)
    {
        super(value);

        this.address = count;
        count += 4;
    }

    public String getValue()
    {
        return "0x" + super.getValue().toUpperCase();
    }

    public String getAddress()
    {
        return "0x" + Long.toHexString(address).toUpperCase();
    }
}