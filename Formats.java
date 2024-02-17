/**
 * This file stores the formats, i.e., the different positions where
 * the 
 */
public enum Formats 
{
    OPCODE_START   (0,  31),
    OPCODE_END     (6,  26),
    RS_START       (6,  25),
    RS_END         (11, 21),
    RT_START       (11, 20),
    RT_END         (16, 16),
    
    IMM_START      (16, 15),
    IMM_END        (32, 0),
    
    RD_START       (16, 15),
    RD_END         (21, 11),
    SHAMT_START    (21, 10),
    SHAMT_END      (26, 6),
    FUNC_START     (26, 5),
    FUNC_END       (32, 0),

    J_START        (6, 25),
    J_END          (32, 0)
    ;

    private final int inString;
    private final int actual;

    private Formats(int inString, int actual)
    {
        this.inString = inString;
        this.actual = actual;
    }

    public int get()
    {
        return inString;
    }

    public int getActual()
    {
        return actual;
    }
}
