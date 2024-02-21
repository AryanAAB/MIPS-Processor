/**
 * This class creates a Memory abstract class that represents the methods that are required by Memory objects.
 *
 * @author : Aryan, Pratham, Arnav
 * @version : 1.0
 * @since : 15/02/24
 */
public abstract class Memory 
{
    private String value;

    /**
     * @param value : The String representation of this Memory object.
     */
    public Memory(String value)
    {
        this.value = value;
    }    

    /**
     * @return : The String representation of this Memory object.
     */
    public String getValue()
    {
        return value;
    }
}
