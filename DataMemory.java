/**
 * This file is used for storing the data memory.
 * It uses DataAddress object to store one piece of information
 * and all of the data is stored in the DataMemory class. 
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/2024
 */

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

    /**
     * This method reads the data file from the user.
     * 
     * @return ArrayList<DataAddress> : an ArrayList consisting of all the data.
     */
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

    /**
     * This method is used for getting data at a particular address. 
     * It higlights the data that you have asked for in the table also.
     * 
     * @param address : the address from which to get the data
     * @return DataAddress : a DataAddress object consisting of the data.
     * @throws IndexOutOfBoundsException : if the address provided is out of bounds of the memory size.
     */
    public DataAddress getData(long address)
    {
        int row = (int)(address - DataAddress.START) / 4;

        if(row < 0 || row >= memData.size())
            throw new IndexOutOfBoundsException("Index of out bounds for length " + memData.size() + ". Given : " + row + ".");
        
        table.highlight(FIRST, SECOND, Color.ORANGE, row);

        return memData.get(row);
    }

    /**
     * This method is used for writing data at a particular address.
     * It highlights the data that you have asked for in the table also.
     * 
     * @param pos : the position of the data (address)
     * @param dataValue : the new data value that is to be set at this address.
     * @throws IndexOutOfBoundsException : if the address provided is out of bounds of the memory size.
     */
    public void writeData(int pos, int dataValue)
    {
        int row = (int)(pos - DataAddress.START) / 4;
        if(row < 0 || row >= memData.size())
            throw new IndexOutOfBoundsException("Index of out bounds for length " + memData.size() + ". Given : " + row + ".");

        memData.get(row).writeData(dataValue);

        table.setValueAt(dataValue, row, 1, FIRST, SECOND, Color.ORANGE);
    }

    /**
     * Resets the table and removes all highlighting.
     */
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

    /**
     * Creates a DataAddress object from the hex string that is given.
     * The address of the data starts from START and goes up by 4 every time.
     * This class uses byte addressable memory. 
     * @param hex : the hex string (must be of length 8)
     * @see #START
     */
    public DataAddress(String hex)
    {
        address = count;
        value = hex;
        bytes = new int[4];

        for(int i = hex.length() - 1, j = 0; i >= 1; i -= 2, j++)
        {
            bytes[j] = Processor.getIntFromHex(hex.substring(i - 1, i + 1));
        }

        count += 4;
    }

    /**
     * @return the address of the data in hex format
     */
    public String getAddress()
    {
        return "0x" + Long.toHexString(address).toUpperCase();
    }

    /**
     * @return the value of the data in hex format
     */
    public String getValue()
    {
        return "0x" + value.toUpperCase();
    }

    /**
     * This method allows you to access particular bytes.
     * @param pos : the position of the data you want (0, 1, 2, 3).
     * @return int : the value of the data in that location.
     */
    public int getByte(int pos)
    {
        if(pos < 0 || pos > 4)
            throw new IndexOutOfBoundsException(pos + " is out of bounds for length 4.");
        
        return bytes[pos];
    }

    /**
     * This method allows you to write data to this object
     * @param data : the data that you want to be wriiten
     */
    public void writeData(int data)
    {
        value = Integer.toHexString(data);

        for(int i = 0; i < 4; i++)
        {
            bytes[i] = data & (0xFFFFFFFF);
            data >>= 8;
        }
    }
}