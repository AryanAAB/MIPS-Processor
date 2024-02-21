/**
 * This file takes care of the instruction memory (i.e. the text data).
 * It creates a table that demonstrates the instructions in the GUI.
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/24
 */

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

    /**
     * Reads the instruction file after getting the name of the instruction file from the user.
     * @return ArrayList<MemAddress> : an ArrayList consisting of all the instructions
     */
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

    /**
     * This function returns the the instruction stored at the given address.
     * @param address : the address location of the memory.
     * @return MemAddress : MemAddress object representing the memory address.
     */
    public MemAddress updateInstruction(long address)
    {
        int row = (int)(address - MemAddress.START) / 4;

        if(row < 0 || row >= instructions.size())
            throw new IndexOutOfBoundsException("Index of out bounds for length " + instructions.size() + ". Given : " + row + ".");
        
        table.highlight(FIRST, SECOND, Color.ORANGE, row);

        return instructions.get(row);
    }

    /**
     * Resets the table to its original colors.
     */
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

    /**
     * @param value : A String representation of the memory
     */
    public MemAddress(String value)
    {
        super(value);

        this.address = count;
        count += 4;
    }

    /**
     * @return String : Returns the hexadecimal value of this object.
     */
    public String getValue()
    {
        return "0x" + super.getValue().toUpperCase();
    }

    /**
     * @return String : Returns the address of this instruction in hexadecimal.
     */
    public String getAddress()
    {
        return "0x" + Long.toHexString(address).toUpperCase();
    }

    /**
     * @return String : Returns the binary representation of this memory value.
     */
    public String getBinValue() 
    {
        String hex = super.getValue().toUpperCase();

        long value = Long.parseLong(hex, 16);
        String binString = Long.toBinaryString(value);

        while(binString.length() < 32)
            binString = "0" + binString;
        
        return binString;
    }
}