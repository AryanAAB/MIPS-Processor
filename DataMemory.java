public class DataMemory 
{
    public DataMemory()
    {
        
    }    
}

class DataAddress
{
    public static final long START = 268500992L;
    private static long count = START;

    private long address;
    private int [] bytes;

    public DataAddress(String hex)
    {
        address = count;
        bytes = new int[4];

        for(int i = hex.length() - 1, j = 0; i >= 1; i -= 2, j++)
        {
            bytes[j] = Integer.parseInt(hex.substring(i - 1, i + 1));
        }

        count += 4;
    }

    public long getAddress()
    {
        return address;
    }
}